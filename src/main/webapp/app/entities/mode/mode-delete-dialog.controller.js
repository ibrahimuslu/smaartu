(function() {
    'use strict';

    angular
        .module('smaartuApp')
        .controller('ModeDeleteController',ModeDeleteController);

    ModeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Mode'];

    function ModeDeleteController($uibModalInstance, entity, Mode) {
        var vm = this;

        vm.mode = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Mode.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
