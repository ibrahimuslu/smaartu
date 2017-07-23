(function() {
    'use strict';

    angular
        .module('smaartuApp')
        .controller('InLocationDeleteController',InLocationDeleteController);

    InLocationDeleteController.$inject = ['$uibModalInstance', 'entity', 'InLocation'];

    function InLocationDeleteController($uibModalInstance, entity, InLocation) {
        var vm = this;

        vm.inLocation = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            InLocation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
