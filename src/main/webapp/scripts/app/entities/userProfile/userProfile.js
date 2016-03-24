'use strict';

angular.module('haklistApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('userProfile', {
                parent: 'entity',
                url: '/userProfiles',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'UserProfiles'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/userProfile/userProfiles.html',
                        controller: 'UserProfileController'
                    }
                },
                resolve: {
                }
            })
            .state('userProfile.detail', {
                parent: 'entity',
                url: '/userProfile/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'UserProfile'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/userProfile/userProfile-detail.html',
                        controller: 'UserProfileDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'UserProfile', function($stateParams, UserProfile) {
                        return UserProfile.get({id : $stateParams.id});
                    }]
                }
            })
            .state('userProfile.new', {
                parent: 'userProfile',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/userProfile/userProfile-dialog.html',
                        controller: 'UserProfileDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    linkedin_path: null,
                                    github_path: null,
                                    twitter_path: null,
                                    website: null,
                                    country: null,
                                    company: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('userProfile', null, { reload: true });
                    }, function() {
                        $state.go('userProfile');
                    })
                }]
            })
            .state('userProfile.edit', {
                parent: 'userProfile',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/userProfile/userProfile-dialog.html',
                        controller: 'UserProfileDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['UserProfile', function(UserProfile) {
                                return UserProfile.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('userProfile', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('userProfile.delete', {
                parent: 'userProfile',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/userProfile/userProfile-delete-dialog.html',
                        controller: 'UserProfileDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['UserProfile', function(UserProfile) {
                                return UserProfile.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('userProfile', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
