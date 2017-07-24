(function() {
    'use strict';

    angular
        .module('smaartuApp')
        .controller('CountryDetailController', CountryDetailController);

    CountryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Country', 'Region'];

    function CountryDetailController($scope, $rootScope, $stateParams, previousState, entity, Country, Region) {
        var vm = this;

        vm.country = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smaartuApp:countryUpdate', function(event, result) {
            vm.country = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
