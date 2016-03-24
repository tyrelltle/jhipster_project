'use strict';

angular.module('haklistApp')
	.controller('UserProfileDeleteController', function($scope, $uibModalInstance, entity, UserProfile) {

        $scope.userProfile = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            UserProfile.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
