'use strict';

angular.module('haklistUserApp', ['ui.bootstrap', 'ui.router', 'angular-loading-bar'])

    .run(function ($rootScope, $location, $window, $http, $state) {
        $state.go('home');

        $rootScope.$on('$stateChangeStart', function (event, toState, toStateParams) {
            $rootScope.toState = toState;
            $rootScope.toStateParams = toStateParams;
        });

        $rootScope.$on('$stateChangeSuccess',  function(event, toState, toParams, fromState, fromParams) {


            // Remember previous state unless we've been redirected to login or we've just
            // reset the state memory after logout. If we're redirected to login, our
            // previousState is already set in the authExpiredInterceptor. If we're going
            // to login directly, we don't want to be sent to some previous state anyway
            if (toState.name != 'login' && $rootScope.previousStateName) {
                $rootScope.previousStateName = fromState.name;
                $rootScope.previousStateParams = fromParams;
            }

        });

        $rootScope.back = function() {
            // If previous state is 'activate' or do not exist go to 'home'
            if ($rootScope.previousStateName === 'activate' || $state.get($rootScope.previousStateName) === null) {
                $state.go('home');
            } else {
                $state.go($rootScope.previousStateName, $rootScope.previousStateParams);
            }
        };
    })
    .config(function ($stateProvider, $urlRouterProvider, $httpProvider) {

        $urlRouterProvider.otherwise('/');
        $stateProvider.state('home', {
            url:"/",
            views: {
                'navbar@': {
                    templateUrl: 'scripts/app-haklist/navbar/navbar.html',
                    controller: 'NavbarController'
                },
                'content@':{
                    templateUrl: 'scripts/app-haklist/listing/listing.html',
                    controller:'ListingController'
                }
            }
        })
        .state('signup', {
            url:'/signup',
            views: {
                'navbar@': {
                    templateUrl: 'scripts/app-haklist/navbar/navbar.html',
                    controller: 'NavbarController'
                },
                'content@':{
                    templateUrl: 'scripts/app-haklist/signup/signup.html',
                    controller:'SignupController'
                }
            }
        });

    });
