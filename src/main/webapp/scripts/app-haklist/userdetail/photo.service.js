angular.module('haklistUserApp')
    .service('ProfilePhoto', function ($http) {
        this.upload=function(file,cb){
            var formData = new FormData();
            formData.append('file', file);

            $http({
                method: 'POST',
                url: 'api/userProfiles/photo',
                headers: {'Content-Type': undefined},
                data: formData,
                transformRequest: function(data, headersGetterFunction) {
                    return data;
                }
            })
            .success(function(data, status) {
               cb();
            })


           /* $http.post('api/userProfiles/photo',  formData,{headers: { 'Content-Type': undefined, transformRequest: angular.identity }})
                .success(function() {
                    cb();
                });*/
        };
    });
