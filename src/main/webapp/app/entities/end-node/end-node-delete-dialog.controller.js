(function() {
    'use strict';

    angular
        .module('smaartuApp')
        .controller('EndNodeDeleteController',EndNodeDeleteController);

    EndNodeDeleteController.$inject = ['$uibModalInstance', 'entity', 'EndNode'];

    function EndNodeDeleteController($uibModalInstance, entity, EndNode) {
        var vm = this;

        vm.endNode = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            EndNode.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
