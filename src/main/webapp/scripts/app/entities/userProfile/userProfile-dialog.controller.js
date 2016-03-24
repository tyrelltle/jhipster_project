'use strict';

angular.module('haklistApp').controller('UserProfileDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'UserProfile', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, UserProfile, User) {

        $scope.userProfile = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            UserProfile.get({id : id}, function(result) {
                $scope.userProfile = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('haklistApp:userProfileUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.userProfile.id != null) {
                UserProfile.update($scope.userProfile, onSaveSuccess, onSaveError);
            } else {
                UserProfile.save($scope.userProfile, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
