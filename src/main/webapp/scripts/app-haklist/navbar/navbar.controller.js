'use strict';

angular.module('haklistUserApp')
    .controller('NavbarController', function ($scope, $location, $state,Principal,Auth) {
        $scope.user={username:'',password:''};

        $scope.gohome=function(){
            $state.go('home');
            $('body').attr('class','whitebody');
        }

        $scope.currentState=$state.current.name;
        if($scope.currentState=='contest_reg'){
            $('body').attr('class','blackbody');
            $('.footer').attr('class','footer_getdown');
        }else{
            $('body').attr('class','whitebody');
            $('.footer_getdown').attr('class','footer');

        }


        $scope.signup=function(){
            event.preventDefault();
            $state.go('signup');
        }

        $scope.goto_contest=function(){
            event.preventDefault();
            $state.go('contest_reg');
            $('body').attr('class','blackbody');

        }

        $scope.signin=function(){
            event.preventDefault();
            $scope.authenticationError = false;
            Auth.login({
                username: $scope.user.username,
                password: $scope.user.password
            }).then(function () {
                $scope.authenticationError = false;
                Principal.identity().then(function(account) {
                    $scope.identity=account;
                });
            }).catch(function () {
                $scope.authenticationError = true;
            });
        }
        $scope.isAuthenticated = Principal.isAuthenticated;


        Principal.identity().then(function(account) {
            $scope.identity=account;
        });

        $scope.viewprofile=function(){
            $state.go('profile');
        }

        $scope.signout=function(){


                Auth.logout();
                $state.go('home');

        }


    });
