angular.module('haklistUserApp')
    .controller('ContestRegController', function ($scope, $state, Principal,localStorageService,UserProfile) {
        $scope.alreadyRegistered=false;

        Principal.identity().then(function(id) {
            if(id){
                UserProfile.current({},function(result){
                    if(result.userProfile.contest_reg){
                        $scope.alreadyRegistered=true;
                    };
                });
            }else{

            }
        });

        $scope.register=function(event){
            event.preventDefault();

            if(Principal.isAuthenticated()){
                mixpanel.track(
                    "Contest Register",
                    {"authenticated": "yes"}
                );
                Principal.identity().then(function(id){
                    UserProfile.contest_reg({},function(){
                        $scope.alreadyRegistered=true;
                        $('#confirmmodal').modal();
                    });
                });

            }else{
                mixpanel.track(
                    "Contest Register",
                    {"authenticated": "nope"}
                );
                localStorageService.set('need_contest_Reg',true);
                $state.go('signup');
            }
        }

    }).controller('ContestRegSuccModalController',function($uibModalInstance){
        //TODO: bootstrap ui modal dosent work, temporily using normal bootstrap modal
    });
