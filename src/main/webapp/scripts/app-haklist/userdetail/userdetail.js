'use strict';

angular.module('haklistApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('signup', {
                url: '/signup',
                views: {
                    'navbar@': {
                        templateUrl: 'scripts/app-haklist/navbar/navbar.html',
                        controller: 'NavbarController'
                    },
                    'content@': {
                        templateUrl: 'scripts/app-haklist/userdetail/userdetail.html',
                        controller: 'SignupController'
                    }
                }
            });
    });
