'use strict';

angular.module('haklistApp')
    .controller('TagDetailController', function ($scope, $rootScope, $stateParams, entity, Tag, UserProfile) {
        $scope.tag = entity;
        $scope.load = function (id) {
            Tag.get({id: id}, function(result) {
                $scope.tag = result;
            });
        };
        var unsubscribe = $rootScope.$on('haklistApp:tagUpdate', function(event, result) {
            $scope.tag = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
