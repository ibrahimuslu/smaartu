(function() {
    'use strict';

    angular
        .module('smaartuApp')
        .controller('SerialConnectionController', SerialConnectionController);

    SerialConnectionController.$inject = ['SerialConnection'];

    function SerialConnectionController(SerialConnection) {

        var vm = this;

        vm.serialConnections = [];

        loadAll();

        function loadAll() {
            SerialConnection.query(function(result) {
                vm.serialConnections = result;
                vm.searchQuery = null;
            });
        }
    }
})();
