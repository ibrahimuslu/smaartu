(function() {
    'use strict';

    angular
        .module('smaartuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('in-location', {
            parent: 'entity',
            url: '/in-location',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'InLocations'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/in-location/in-locations.html',
                    controller: 'InLocationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('in-location-detail', {
            parent: 'in-location',
            url: '/in-location/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'InLocation'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/in-location/in-location-detail.html',
                    controller: 'InLocationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'InLocation', function($stateParams, InLocation) {
                    return InLocation.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'in-location',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('in-location-detail.edit', {
            parent: 'in-location-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/in-location/in-location-dialog.html',
                    controller: 'InLocationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['InLocation', function(InLocation) {
                            return InLocation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('in-location.new', {
            parent: 'in-location',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/in-location/in-location-dialog.html',
                    controller: 'InLocationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                detail: null,
                                address: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('in-location', null, { reload: 'in-location' });
                }, function() {
                    $state.go('in-location');
                });
            }]
        })
        .state('in-location.edit', {
            parent: 'in-location',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/in-location/in-location-dialog.html',
                    controller: 'InLocationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['InLocation', function(InLocation) {
                            return InLocation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('in-location', null, { reload: 'in-location' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('in-location.delete', {
            parent: 'in-location',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/in-location/in-location-delete-dialog.html',
                    controller: 'InLocationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['InLocation', function(InLocation) {
                            return InLocation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('in-location', null, { reload: 'in-location' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
