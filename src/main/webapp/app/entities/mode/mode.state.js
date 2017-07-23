(function() {
    'use strict';

    angular
        .module('smaartuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('mode', {
            parent: 'entity',
            url: '/mode',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Modes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mode/modes.html',
                    controller: 'ModeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('mode-detail', {
            parent: 'mode',
            url: '/mode/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Mode'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mode/mode-detail.html',
                    controller: 'ModeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Mode', function($stateParams, Mode) {
                    return Mode.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'mode',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('mode-detail.edit', {
            parent: 'mode-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mode/mode-dialog.html',
                    controller: 'ModeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Mode', function(Mode) {
                            return Mode.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('mode.new', {
            parent: 'mode',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mode/mode-dialog.html',
                    controller: 'ModeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('mode', null, { reload: 'mode' });
                }, function() {
                    $state.go('mode');
                });
            }]
        })
        .state('mode.edit', {
            parent: 'mode',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mode/mode-dialog.html',
                    controller: 'ModeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Mode', function(Mode) {
                            return Mode.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('mode', null, { reload: 'mode' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('mode.delete', {
            parent: 'mode',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mode/mode-delete-dialog.html',
                    controller: 'ModeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Mode', function(Mode) {
                            return Mode.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('mode', null, { reload: 'mode' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
