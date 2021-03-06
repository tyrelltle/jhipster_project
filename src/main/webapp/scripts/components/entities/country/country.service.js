'use strict';

angular.module('haklistApp')
    .factory('Country', function ($resource) {
        return $resource('api/country/:id', {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'list':{
                method: 'GET',
                isArray:true,
                url:'api/country/list'
            },
            'update': {method: 'PUT'}
        });
    });
