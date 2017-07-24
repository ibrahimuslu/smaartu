(function() {
    'use strict';

    angular
        .module('smaartuApp')
        .controller('EndNodeUnitDetailController', EndNodeUnitDetailController);

    EndNodeUnitDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'EndNodeUnit', 'EndNode', 'Action'];

    function EndNodeUnitDetailController($scope, $rootScope, $stateParams, previousState, entity, EndNodeUnit, EndNode, Action) {
        var vm = this;

        vm.endNodeUnit = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smaartuApp:endNodeUnitUpdate', function(event, result) {
            vm.endNodeUnit = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
