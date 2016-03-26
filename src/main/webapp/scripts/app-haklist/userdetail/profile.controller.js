'use strict';

angular.module('haklistUserApp')
    .controller('ProfileController', function ($scope,$state,Principal,UserProfile) {

        $scope.registerAccount={};

         UserProfile.current({},function(result){
            $scope.registerAccount=result;
         });

        $scope.confirm=function(){
            UserProfile.updateExt($scope.registerAccount,
            function(data) {
                alert('Successfully Updated Your Profile!');
            }, function(error) {
                alert(error);
            });

        }


    });

