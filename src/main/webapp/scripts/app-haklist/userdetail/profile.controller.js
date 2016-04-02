'use strict';

angular.module('haklistUserApp')
    .controller('ProfileController', function ($scope,$state,Principal,UserProfile,Country,Tag,ProfilePhoto,ProfileFormRule) {
        $scope.formrule={};
        $scope.registerAccount={};
        $scope.editmode=true;
        $scope.tags=[];
        $scope.picturesrc="";
        function restImgSrc() {
            $scope.picturesrc = "api/userProfiles/login/" + $scope.userlogin + "/photo?" + new Date().toISOString();
        }
        Principal.identity().then(function(account) {
            $scope.userlogin=account.login;
            restImgSrc();
        });
        UserProfile.current({},function(result){
            $scope.registerAccount=result;
            updateTags(result.userProfile.tags);
            Country.list({}, function (result) {
                $scope.countries=result;
                $scope.countries.forEach(function(country){
                    if(country.country_code==$scope.registerAccount.userProfile.country)
                        $scope.selectCountry=country;
                });

            });
        });

        //TODO: lateron use Directive to encapsulate all tags-related code
        function updateTags(existingTagsInProfile) {
            if(existingTagsInProfile==undefined)
                existingTagsInProfile=[];
            Tag.query({}, function (returnTags) {
                returnTags.forEach(function(tag){
                    var selected=false;
                    for(var i=0;i<existingTagsInProfile.length;i++){
                        if(existingTagsInProfile[i]==tag.name) {
                            selected = true;
                            break;
                        }
                    }
                    $scope.tags.push({name:tag.name,selected:selected});

                });
            });
        }

        function collectSelectedTags(){
            var ret=[];
            for(var i=0;i<$scope.tags.length;i++){
                if($scope.tags[i].selected)
                    ret.push($scope.tags[i].name);
            }
            return ret;
        }



        $scope.country_select=function(index){
            event.preventDefault();
            $scope.selectCountry=$scope.countries[index];
        }

        $scope.confirm=function(valid){
            $scope.submitted=true;
            if(!valid)
                return;
            $scope.registerAccount.userProfile.tags=collectSelectedTags();
            $scope.registerAccount.userProfile.country=$scope.selectCountry?$scope.selectCountry.country_code:"CH";
            var additionalRule=ProfileFormRule.validate($scope.registerAccount);
            $scope.formrule=additionalRule.result;
            if(!additionalRule.pass)
                return;

            UserProfile.updateExt($scope.registerAccount,
            function(data) {
                alert('Successfully Updated Your Profile!');
            }, function(error) {
                alert(error);
            });

        }



        $scope.upload=function(){
            var file = document.getElementById("fileinput").files[0];
            ProfilePhoto.upload(file,function(){
                restImgSrc();
            });
        }


    });

