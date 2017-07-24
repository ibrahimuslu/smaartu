(function() {
    'use strict';

    angular
        .module('smaartuApp')
        .controller('SerialConnectionDetailController', SerialConnectionDetailController);

    SerialConnectionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SerialConnection', 'EndNode'];

    function SerialConnectionDetailController($scope, $rootScope, $stateParams, previousState, entity, SerialConnection, EndNode) {
        var vm = this;

        vm.serialConnection = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smaartuApp:serialConnectionUpdate', function(event, result) {
            vm.serialConnection = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
