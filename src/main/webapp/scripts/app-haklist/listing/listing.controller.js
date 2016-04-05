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
                    if(result[i].linkedIn&&result[i].linkedIn.indexOf('http://')<0)
                        result[i].linkedIn='http://'+result[i].linkedIn;

                    if(result[i].twitter&&result[i].twitter.indexOf('http://')<0)
                        result[i].twitter='http://'+result[i].twitter;

                    if(result[i].gitHub&&result[i].gitHub.indexOf('http://')<0)
                        result[i].gitHub='http://'+result[i].gitHub;
                    $scope.userProfiles.push(result[i]);
                }
            });
        };
        Country.list({}, function (countries) {
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
