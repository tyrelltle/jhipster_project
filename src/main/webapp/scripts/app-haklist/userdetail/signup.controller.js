'use strict';

angular.module('haklistUserApp')
    .controller('SignupController', function ($scope,$state,Auth,Country,Tag,Principal,ProfilePhoto,ProfileFormRule,localStorageService) {
        $scope.registerAccount={
            langKey:'en',
            userProfile:{
                country:'CH'
            }
        };
        $scope.tags=[];
        $scope.selectCountry={};


        $scope.picturesrc="/assets/images/defaul_avatar.png";

        //TODO: country logics can be encapsulated in a dedicated Directive, do it after phase one
        Country.list({}, function (result) {
            $scope.countries=result;
        });


        Tag.query({}, function (returnTags) {
            returnTags.forEach(function(tag){
                $scope.tags.push({name:tag.name,selected:false});
            });
        });

        function collectSelectedTags(){
            var ret=[];
            for(var i=0;i<$scope.tags.length;i++){
                if($scope.tags[i].selected)
                    ret.push($scope.tags[i].name);
            }
            return ret;
        }

        $scope.country_select=function(event,index){
            event.preventDefault();
            $scope.selectCountry=$scope.countries[index];
        }
        $scope.confirm = function(valid) {
            $scope.submitted=true;

            if(!valid)
                return;
            $scope.registerAccount.userProfile.tags=collectSelectedTags();
            $scope.registerAccount.userProfile.country=$scope.selectCountry?$scope.selectCountry.country_code:"CH";

            if($scope.registerAccount.email.indexOf('@')<0){
                alert('not valid email address!');
                return;
            }
            $scope.registerAccount.login=$scope.registerAccount.email;

            var additionalRule=ProfileFormRule.validate($scope.registerAccount);
            $scope.formrule=additionalRule.result;
            if(!additionalRule.pass)
                return;

            if(localStorageService.get('need_contest_Reg')){
                $scope.registerAccount.userProfile.contest_reg=true;
            }

            Auth.createAccountExt($scope.registerAccount).then(function () {

                Auth.login({
                    username: $scope.registerAccount.login,
                    password: $scope.registerAccount.password
                }).then(function () {
                    if($scope.file){
                        ProfilePhoto.upload($scope.file);
                    }
                    if(localStorageService.get('need_contest_Reg')){
                        localStorageService.set('contest_reg_confirmed',true);
                    }
                    $state.go('home');
                });

            }).catch(function (response) {
                alert(response.data);
            });

        };
        $scope.upload=function(){
            $scope.file=document.getElementById("fileinput").files[0];
            var reader=new FileReader();
            reader.onload=function(e){
                $scope.$apply(function () {
                    $scope.picturesrc = e.target.result;
                });
            }
            reader.readAsDataURL($scope.file);

        }

    });

