(function() {
    'use strict';

    angular
        .module('smaartuApp')
        .controller('InLocationDetailController', InLocationDetailController);

    InLocationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'InLocation'];

    function InLocationDetailController($scope, $rootScope, $stateParams, previousState, entity, InLocation) {
        var vm = this;

        vm.inLocation = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smaartuApp:inLocationUpdate', function(event, result) {
            vm.inLocation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
