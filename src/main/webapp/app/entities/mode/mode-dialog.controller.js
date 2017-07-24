(function() {
    'use strict';

    angular
        .module('smaartuApp')
        .controller('ModeDialogController', ModeDialogController);

    ModeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Mode', 'Action'];

    function ModeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Mode, Action) {
        var vm = this;

        vm.mode = entity;
        vm.clear = clear;
        vm.save = save;
        vm.actions = Action.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.mode.id !== null) {
                Mode.update(vm.mode, onSaveSuccess, onSaveError);
            } else {
                Mode.save(vm.mode, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smaartuApp:modeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
