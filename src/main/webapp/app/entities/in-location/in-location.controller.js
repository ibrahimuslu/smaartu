(function() {
    'use strict';

    angular
        .module('smaartuApp')
        .controller('InLocationController', InLocationController);

    InLocationController.$inject = ['InLocation'];

    function InLocationController(InLocation) {

        var vm = this;

        vm.inLocations = [];

        loadAll();

        function loadAll() {
            InLocation.query(function(result) {
                vm.inLocations = result;
                vm.searchQuery = null;
            });
        }
    }
})();
