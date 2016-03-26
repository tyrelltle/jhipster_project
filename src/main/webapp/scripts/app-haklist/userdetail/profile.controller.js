'use strict';

angular.module('haklistUserApp')
    .controller('ProfileController', function ($scope,$state,Principal,UserProfile,Country) {

        $scope.registerAccount={};
        $scope.editmode=true;
        UserProfile.current({},function(result){
            $scope.registerAccount=result;

            Country.query({}, function (result) {
                $scope.countries=result;
                $scope.countries.forEach(function(country){
                    if(country.country_code==$scope.registerAccount.userProfile.company)
                        $scope.selectCountry=country;
                });

            });
        });


        $scope.country_select=function(index){
            event.preventDefault();
            $scope.selectCountry=$scope.countries[index];
        }

        $scope.confirm=function(){
            $scope.registerAccount.userProfile.country=$scope.selectCountry?$scope.selectCountry.country_code:"CH";
            UserProfile.updateExt($scope.registerAccount,
            function(data) {
                alert('Successfully Updated Your Profile!');
            }, function(error) {
                alert(error);
            });

        }


    });

