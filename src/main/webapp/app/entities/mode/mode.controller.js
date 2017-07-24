(function() {
    'use strict';

    angular
        .module('smaartuApp')
        .controller('ModeController', ModeController);

    ModeController.$inject = ['Mode'];

    function ModeController(Mode) {

        var vm = this;

        vm.modes = [];

        loadAll();

        function loadAll() {
            Mode.query(function(result) {
                vm.modes = result;
                vm.searchQuery = null;
            });
        }
    }
})();
