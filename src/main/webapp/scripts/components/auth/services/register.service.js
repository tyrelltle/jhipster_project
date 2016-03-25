'use strict';

angular.module('haklistApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    })
    .factory('RegisterExt', function ($resource) {
        return $resource('api/signup', {}, {
        });
    });


