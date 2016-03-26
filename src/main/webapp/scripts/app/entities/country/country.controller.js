'use strict';

angular.module('haklistApp')
    .controller('CountryController', function ($scope, $state, Country, ParseLinks) {

        $scope.countries = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function () {
            Country.query({
                page: $scope.page - 1,
                size: 20,
                sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']
            }, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.countries = result;
            });
        };
        $scope.loadPage = function (page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.country = {
                country_code: null,
                name: null,
                id: null
            };
        };
    });
