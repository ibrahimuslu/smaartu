(function() {
    'use strict';

    angular
        .module('smaartuApp')
        .controller('ModeDetailController', ModeDetailController);

    ModeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Mode', 'Action'];

    function ModeDetailController($scope, $rootScope, $stateParams, previousState, entity, Mode, Action) {
        var vm = this;

        vm.mode = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smaartuApp:modeUpdate', function(event, result) {
            vm.mode = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
