(function() {
    'use strict';

    angular
        .module('smaartuApp')
        .controller('ControlSystemDetailController', ControlSystemDetailController);

    ControlSystemDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ControlSystem', 'Location', 'EndNode'];

    function ControlSystemDetailController($scope, $rootScope, $stateParams, previousState, entity, ControlSystem, Location, EndNode) {
        var vm = this;

        vm.controlSystem = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smaartuApp:controlSystemUpdate', function(event, result) {
            vm.controlSystem = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
