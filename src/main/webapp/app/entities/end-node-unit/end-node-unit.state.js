(function() {
    'use strict';

    angular
        .module('smaartuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('end-node-unit', {
            parent: 'entity',
            url: '/end-node-unit',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'EndNodeUnits'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/end-node-unit/end-node-units.html',
                    controller: 'EndNodeUnitController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('end-node-unit-detail', {
            parent: 'end-node-unit',
            url: '/end-node-unit/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'EndNodeUnit'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/end-node-unit/end-node-unit-detail.html',
                    controller: 'EndNodeUnitDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'EndNodeUnit', function($stateParams, EndNodeUnit) {
                    return EndNodeUnit.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'end-node-unit',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('end-node-unit-detail.edit', {
            parent: 'end-node-unit-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/end-node-unit/end-node-unit-dialog.html',
                    controller: 'EndNodeUnitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EndNodeUnit', function(EndNodeUnit) {
                            return EndNodeUnit.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('end-node-unit.new', {
            parent: 'end-node-unit',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/end-node-unit/end-node-unit-dialog.html',
                    controller: 'EndNodeUnitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                type: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('end-node-unit', null, { reload: 'end-node-unit' });
                }, function() {
                    $state.go('end-node-unit');
                });
            }]
        })
        .state('end-node-unit.edit', {
            parent: 'end-node-unit',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/end-node-unit/end-node-unit-dialog.html',
                    controller: 'EndNodeUnitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EndNodeUnit', function(EndNodeUnit) {
                            return EndNodeUnit.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('end-node-unit', null, { reload: 'end-node-unit' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('end-node-unit.delete', {
            parent: 'end-node-unit',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/end-node-unit/end-node-unit-delete-dialog.html',
                    controller: 'EndNodeUnitDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['EndNodeUnit', function(EndNodeUnit) {
                            return EndNodeUnit.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('end-node-unit', null, { reload: 'end-node-unit' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
