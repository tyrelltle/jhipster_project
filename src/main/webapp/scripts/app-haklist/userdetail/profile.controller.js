'use strict';

angular.module('haklistUserApp')
    .controller('ProfileController', function ($scope,$state,Principal,UserProfile,Country,Tag) {

        $scope.registerAccount={};
        $scope.editmode=true;
        $scope.tags=[];
        UserProfile.current({},function(result){
            $scope.registerAccount=result;
            updateTags(result.userProfile.tags);
            Country.query({}, function (result) {
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

        $scope.confirm=function(){
            $scope.registerAccount.userProfile.tags=collectSelectedTags();
            $scope.registerAccount.userProfile.country=$scope.selectCountry?$scope.selectCountry.country_code:"CH";
            UserProfile.updateExt($scope.registerAccount,
            function(data) {
                alert('Successfully Updated Your Profile!');
            }, function(error) {
                alert(error);
            });

        }


    });

