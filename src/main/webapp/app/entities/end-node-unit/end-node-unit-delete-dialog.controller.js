(function() {
    'use strict';

    angular
        .module('smaartuApp')
        .controller('EndNodeUnitDeleteController',EndNodeUnitDeleteController);

    EndNodeUnitDeleteController.$inject = ['$uibModalInstance', 'entity', 'EndNodeUnit'];

    function EndNodeUnitDeleteController($uibModalInstance, entity, EndNodeUnit) {
        var vm = this;

        vm.endNodeUnit = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            EndNodeUnit.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
