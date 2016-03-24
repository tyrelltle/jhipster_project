'use strict';

angular.module('haklistApp')
    .controller('UserProfileDetailController', function ($scope, $rootScope, $stateParams, entity, UserProfile, User) {
        $scope.userProfile = entity;
        $scope.load = function (id) {
            UserProfile.get({id: id}, function(result) {
                $scope.userProfile = result;
            });
        };
        var unsubscribe = $rootScope.$on('haklistApp:userProfileUpdate', function(event, result) {
            $scope.userProfile = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
