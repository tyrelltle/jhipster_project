'use strict';

angular.module('haklistUserApp')
    .controller('SignupController', function ($scope,$state,Auth,Country) {
        $scope.registerAccount={
            langKey:'en',
            userProfile:{
                country:'CH'
            }
        };
        $scope.selectCountry={};


        //TODO: country logics can be encapsulated in a dedicated Directive, do it after phase one
        Country.query({}, function (result) {
            $scope.countries=result;
        });

        $scope.country_select=function(index){
            event.preventDefault();
            $scope.selectCountry=$scope.countries[index];
        }
        $scope.confirm = function () {
            $scope.registerAccount.userProfile.country=$scope.selectCountry?$scope.selectCountry.country_code:"CH";
            if($scope.registerAccount.email.indexOf('@')<0){
                alert('not valid email address!');
                return;
            }
            $scope.registerAccount.login=$scope.registerAccount.email.split('@')[0];
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

