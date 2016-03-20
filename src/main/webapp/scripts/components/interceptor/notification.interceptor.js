 'use strict';

angular.module('haklistApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-haklistApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-haklistApp-params')});
                }
                return response;
            }
        };
    });
