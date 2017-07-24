(function() {
    'use strict';

    angular
        .module('smaartuApp')
        .controller('SerialConnectionDeleteController',SerialConnectionDeleteController);

    SerialConnectionDeleteController.$inject = ['$uibModalInstance', 'entity', 'SerialConnection'];

    function SerialConnectionDeleteController($uibModalInstance, entity, SerialConnection) {
        var vm = this;

        vm.serialConnection = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SerialConnection.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
