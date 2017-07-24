(function() {
    'use strict';

    angular
        .module('smaartuApp')
        .controller('ActionDetailController', ActionDetailController);

    ActionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Action', 'EndNodeUnit', 'Mode'];

    function ActionDetailController($scope, $rootScope, $stateParams, previousState, entity, Action, EndNodeUnit, Mode) {
        var vm = this;

        vm.action = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smaartuApp:actionUpdate', function(event, result) {
            vm.action = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
