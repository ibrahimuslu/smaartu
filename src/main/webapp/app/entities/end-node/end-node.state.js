(function() {
    'use strict';

    angular
        .module('smaartuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('end-node', {
            parent: 'entity',
            url: '/end-node?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'EndNodes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/end-node/end-nodes.html',
                    controller: 'EndNodeController',
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
        .state('end-node-detail', {
            parent: 'end-node',
            url: '/end-node/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'EndNode'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/end-node/end-node-detail.html',
                    controller: 'EndNodeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'EndNode', function($stateParams, EndNode) {
                    return EndNode.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'end-node',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('end-node-detail.edit', {
            parent: 'end-node-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/end-node/end-node-dialog.html',
                    controller: 'EndNodeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EndNode', function(EndNode) {
                            return EndNode.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('end-node.new', {
            parent: 'end-node',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/end-node/end-node-dialog.html',
                    controller: 'EndNodeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                address: null,
                                type: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('end-node', null, { reload: 'end-node' });
                }, function() {
                    $state.go('end-node');
                });
            }]
        })
        .state('end-node.edit', {
            parent: 'end-node',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/end-node/end-node-dialog.html',
                    controller: 'EndNodeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EndNode', function(EndNode) {
                            return EndNode.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('end-node', null, { reload: 'end-node' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('end-node.delete', {
            parent: 'end-node',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/end-node/end-node-delete-dialog.html',
                    controller: 'EndNodeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['EndNode', function(EndNode) {
                            return EndNode.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('end-node', null, { reload: 'end-node' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
