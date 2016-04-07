'use strict';

angular.module('haklistApp')
    .factory('UserProfile', function ($resource) {
        return $resource('api/userProfiles/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'queryExt': { method: 'GET', isArray: true,url:'api/userProfiles/public'},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' } ,
            'current': {
                method: 'GET',
                url: '/api/userProfiles/current',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'updateExt': {
                method: 'PUT',
                url: '/api/userProfiles/ext'
            },
            'contest_reg':{
                method: 'POST',
                url:'/api/userProfiles/contest_reg'
            }

        });
    });
