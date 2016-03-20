'use strict';

angular.module('haklistApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


