'use strict';

angular.module('haklistApp')
    .directive('onErrorSrc', function () {
        return {
            link: function(scope, element, attrs) {
                element.bind('error', function() {
                    if (attrs.src != attrs.onErrorSrc) {
                        attrs.$set('src', attrs.onErrorSrc);
                    }
                });
            }
        }
    });
