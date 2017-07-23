(function() {
    'use strict';

    angular
        .module('smaartuApp')
        .controller('EndNodeUnitDialogController', EndNodeUnitDialogController);

    EndNodeUnitDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'EndNodeUnit', 'EndNode', 'Action'];

    function EndNodeUnitDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, EndNodeUnit, EndNode, Action) {
        var vm = this;

        vm.endNodeUnit = entity;
        vm.clear = clear;
        vm.save = save;
        vm.endnodes = EndNode.query();
        vm.actions = Action.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.endNodeUnit.id !== null) {
                EndNodeUnit.update(vm.endNodeUnit, onSaveSuccess, onSaveError);
            } else {
                EndNodeUnit.save(vm.endNodeUnit, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smaartuApp:endNodeUnitUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
