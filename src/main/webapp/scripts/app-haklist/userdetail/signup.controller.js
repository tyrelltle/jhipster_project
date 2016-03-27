'use strict';

angular.module('haklistUserApp')
    .controller('SignupController', function ($scope,$state,Auth,Country,Tag) {
        $scope.registerAccount={
            langKey:'en',
            userProfile:{
                country:'CH'
            }
        };
        $scope.tags=[];
        $scope.selectCountry={};


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

        $scope.country_select=function(index){
            event.preventDefault();
            $scope.selectCountry=$scope.countries[index];
        }
        $scope.confirm = function () {
            $scope.registerAccount.userProfile.tags=collectSelectedTags();
            $scope.registerAccount.userProfile.country=$scope.selectCountry?$scope.selectCountry.country_code:"CH";
            if($scope.registerAccount.email.indexOf('@')<0){
                alert('not valid email address!');
                return;
            }
            $scope.registerAccount.login=$scope.registerAccount.email;
            if ($scope.registerAccount.password == undefined ||
                $scope.registerAccount.password.trim() == '') {
                alert('Passwords can not be empty!');
                $state.go('home');
            } else {
                Auth.createAccountExt($scope.registerAccount).then(function () {
                    alert('Account Created!');
                }).catch(function (response) {
                    alert(response.data);
                });
            }
        };

    });

