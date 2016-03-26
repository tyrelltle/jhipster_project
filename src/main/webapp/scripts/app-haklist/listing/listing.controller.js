'use strict';

angular.module('haklistUserApp')
    .controller('ListingController', function ($scope, $state, UserProfile, ParseLinks) {

        $scope.userProfiles = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 0;
        $scope.loadAll = function() {
            UserProfile.queryExt({page: $scope.page, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.userProfiles.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 0;
            $scope.userProfiles = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.reset();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.userProfile = {
                linkedin_path: null,
                firstName:null,
                lastName:null,
                email:null,
                github_path: null,
                twitter_path: null,
                website: null,
                country: null,
                company: null,
                id: null
            };
        };
    });
