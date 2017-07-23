(function() {
    'use strict';

    angular
        .module('smaartuApp')
        .controller('EndNodeDetailController', EndNodeDetailController);

    EndNodeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'EndNode', 'ControlSystem', 'InLocation', 'SerialConnection', 'EndNodeUnit'];

    function EndNodeDetailController($scope, $rootScope, $stateParams, previousState, entity, EndNode, ControlSystem, InLocation, SerialConnection, EndNodeUnit) {
        var vm = this;

        vm.endNode = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smaartuApp:endNodeUpdate', function(event, result) {
            vm.endNode = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
