'use strict';

angular.module('haklistUserApp')
    .controller('ListingController', function ($scope, $state, UserProfile, ParseLinks, Country) {

        $scope.userProfiles = [];
        $scope.countries = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 0;
        $scope.loadAll = function () {
            UserProfile.queryExt({
                page: $scope.page,
                size: 20,
                sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']
            }, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.userProfiles.push(result[i]);
                }
            });
        };
        Country.query({}, function (countries) {
            $scope.countries = countries;
            $scope.loadAll();
        });
        $scope.lookupCountryName = function (countryCode) {
            for (var i = 0; i < $scope.countries.length; i++) {
                if (countryCode == $scope.countries[i].country_code)
                    return $scope.countries[i].name;
            }
        }

        $scope.reset = function () {
            $scope.page = 0;
            $scope.userProfiles = [];
            $scope.loadAll();
        };
        $scope.loadPage = function (page) {
            $scope.clear();
            $scope.page = page;
            $scope.loadAll();
        };


        $scope.refresh = function () {
            $scope.reset();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.userProfiles = [];
        };

        $scope.getProfileListNumArr = function (index) {
            var ret = [];
            for (var i = 0; i < 3; i++) {
                if (index + i < $scope.userProfiles.length)
                    ret.push(i);
                else
                    return ret;
            }
            return ret;
        }

    });
