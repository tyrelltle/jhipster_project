/*jshint bitwise: false*/
'use strict';

angular.module('haklistApp')
    .service('UniqueVisitor', function ($cookies, $http) {
        var allowedEvents = ['register_clicked', 'register_confirmed', 'contest_register_clicked'];

        function generateUUID() {
            var d = new Date().getTime();
            if (window.performance && typeof window.performance.now === "function") {
                d += performance.now(); //use high-precision timer if available
            }
            var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
                var r = (d + Math.random() * 16) % 16 | 0;
                d = Math.floor(d / 16);
                return (c == 'x' ? r : (r & 0x3 | 0x8)).toString(16);
            });
            return uuid;
        }

        this.checkNsetCookie = function () {

            if (!$cookies.get("VISITOR_ID")) {
                var generateUUID2 = generateUUID();
                var d = new Date();
                d.setYear(9999);
                $cookies.put("VISITOR_ID", generateUUID2, {'expires': d});
                console.log("new visitor " + $cookies.get("VISITOR_ID"));
            }
        };

        this.event = function (name) {
            if (allowedEvents.indexOf(name) < 0)
                throw new Error(name + " is not valid UniqueVisitor event!");
            $http.post('/api/event/record/'+name)
                .then(function () {

                }, function () {
                    console.log('error posting visitor event ' + name)
                });

        };

        this.getSummary=function(cb){
            $http.get('/api/event')
                .then(function (response) {
                    cb(null,response.data);
                }, function (response) {
                    cb(response.data,null);
                });
        }


    });
