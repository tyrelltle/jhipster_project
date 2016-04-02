angular.module('haklistUserApp')
    .service('ProfileFormRule', function () {
        this.validate=function(dto){
            var ret={
                NEED_SOCIAL_URL:false,
                NEED_TAG:false
            }
            var tags = dto.userProfile.tags;
            if(!tags||tags.length>4||tags.length<=0){
                ret.NEED_TAG=true;
            }
            if(!(dto.userProfile.linkedIn||dto.userProfile.gitHub||dto.userProfile.twitter||dto.userProfile.personalSite))
                ret.NEED_SOCIAL_URL=true;

            return {result:ret,pass:!(ret.NEED_SOCIAL_URL||ret.NEED_TAG)};
        };
    });
