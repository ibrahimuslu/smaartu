(function() {
    'use strict';

    angular
        .module('smaartuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('serial-connection', {
            parent: 'entity',
            url: '/serial-connection',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'SerialConnections'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/serial-connection/serial-connections.html',
                    controller: 'SerialConnectionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('serial-connection-detail', {
            parent: 'serial-connection',
            url: '/serial-connection/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'SerialConnection'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/serial-connection/serial-connection-detail.html',
                    controller: 'SerialConnectionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'SerialConnection', function($stateParams, SerialConnection) {
                    return SerialConnection.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'serial-connection',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('serial-connection-detail.edit', {
            parent: 'serial-connection-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/serial-connection/serial-connection-dialog.html',
                    controller: 'SerialConnectionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SerialConnection', function(SerialConnection) {
                            return SerialConnection.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('serial-connection.new', {
            parent: 'serial-connection',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/serial-connection/serial-connection-dialog.html',
                    controller: 'SerialConnectionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                port: null,
                                baudRate: null,
                                dataBits: null,
                                parity: null,
                                stopBits: null,
                                flowControl: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('serial-connection', null, { reload: 'serial-connection' });
                }, function() {
                    $state.go('serial-connection');
                });
            }]
        })
        .state('serial-connection.edit', {
            parent: 'serial-connection',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/serial-connection/serial-connection-dialog.html',
                    controller: 'SerialConnectionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SerialConnection', function(SerialConnection) {
                            return SerialConnection.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('serial-connection', null, { reload: 'serial-connection' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('serial-connection.delete', {
            parent: 'serial-connection',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/serial-connection/serial-connection-delete-dialog.html',
                    controller: 'SerialConnectionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SerialConnection', function(SerialConnection) {
                            return SerialConnection.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('serial-connection', null, { reload: 'serial-connection' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
