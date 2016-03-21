'use strict';

angular.module('haklistUserApp')
    .controller('NavbarController', function ($scope, $location, $state) {
        $scope.signup=function(){
            $state.go('signup');
        }
    });
