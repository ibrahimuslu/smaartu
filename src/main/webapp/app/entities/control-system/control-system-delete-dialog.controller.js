(function() {
    'use strict';

    angular
        .module('smaartuApp')
        .controller('ControlSystemDeleteController',ControlSystemDeleteController);

    ControlSystemDeleteController.$inject = ['$uibModalInstance', 'entity', 'ControlSystem'];

    function ControlSystemDeleteController($uibModalInstance, entity, ControlSystem) {
        var vm = this;

        vm.controlSystem = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ControlSystem.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
