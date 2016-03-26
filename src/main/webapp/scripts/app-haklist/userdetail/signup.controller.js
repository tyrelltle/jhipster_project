'use strict';

angular.module('haklistUserApp')
    .controller('SignupController', function ($scope,$state,Auth) {
        $scope.registerAccount={
            langKey:'en',
            userProfile:{
                country:'ch'
            }
        };

        $scope.confirm = function () {
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

