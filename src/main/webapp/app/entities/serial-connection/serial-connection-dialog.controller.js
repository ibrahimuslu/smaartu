(function() {
    'use strict';

    angular
        .module('smaartuApp')
        .controller('SerialConnectionDialogController', SerialConnectionDialogController);

    SerialConnectionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SerialConnection', 'EndNode'];

    function SerialConnectionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SerialConnection, EndNode) {
        var vm = this;

        vm.serialConnection = entity;
        vm.clear = clear;
        vm.save = save;
        vm.endnodes = EndNode.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.serialConnection.id !== null) {
                SerialConnection.update(vm.serialConnection, onSaveSuccess, onSaveError);
            } else {
                SerialConnection.save(vm.serialConnection, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smaartuApp:serialConnectionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
