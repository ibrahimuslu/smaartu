(function() {
    'use strict';

    angular
        .module('smaartuApp')
        .controller('ActionController', ActionController);

    ActionController.$inject = ['Action'];

    function ActionController(Action) {

        var vm = this;

        vm.actions = [];

        loadAll();

        function loadAll() {
            Action.query(function(result) {
                vm.actions = result;
                vm.searchQuery = null;
            });
        }
    }
})();
