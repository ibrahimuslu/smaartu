(function() {
    'use strict';

    angular
        .module('smaartuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('control-system', {
            parent: 'entity',
            url: '/control-system?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ControlSystems'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/control-system/control-systems.html',
                    controller: 'ControlSystemController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
            }
        })
        .state('control-system-detail', {
            parent: 'control-system',
            url: '/control-system/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ControlSystem'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/control-system/control-system-detail.html',
                    controller: 'ControlSystemDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ControlSystem', function($stateParams, ControlSystem) {
                    return ControlSystem.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'control-system',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('control-system-detail.edit', {
            parent: 'control-system-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/control-system/control-system-dialog.html',
                    controller: 'ControlSystemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ControlSystem', function(ControlSystem) {
                            return ControlSystem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('control-system.new', {
            parent: 'control-system',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/control-system/control-system-dialog.html',
                    controller: 'ControlSystemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                type: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('control-system', null, { reload: 'control-system' });
                }, function() {
                    $state.go('control-system');
                });
            }]
        })
        .state('control-system.edit', {
            parent: 'control-system',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/control-system/control-system-dialog.html',
                    controller: 'ControlSystemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ControlSystem', function(ControlSystem) {
                            return ControlSystem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('control-system', null, { reload: 'control-system' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('control-system.delete', {
            parent: 'control-system',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/control-system/control-system-delete-dialog.html',
                    controller: 'ControlSystemDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ControlSystem', function(ControlSystem) {
                            return ControlSystem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('control-system', null, { reload: 'control-system' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
