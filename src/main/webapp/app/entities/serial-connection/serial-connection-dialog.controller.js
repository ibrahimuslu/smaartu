(function() {
    'use strict';

    angular
        .module('smaartuApp')
        .controller('SerialConnectionDialogController', SerialConnectionDialogController);

    SerialConnectionDialogController.$inject = ['$q','$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SerialConnection', 'EndNode','SerialConnectionPorts'];

    function SerialConnectionDialogController ($q,$timeout, $scope, $stateParams, $uibModalInstance, entity, SerialConnection, EndNode, SerialConnectionPorts) {
        var vm = this;

        vm.serialConnection = entity;
        vm.clear = clear;
        vm.save = save;
        vm.endnodes = EndNode.query();
        vm.ports = SerialConnectionPorts.query({filter: 'endnode-is-null'});
        $q.all([vm.serialConnection.$promise, vm.ports.$promise]).then(function() {
            if (!vm.serialConnection.serialConnectionId) {
                return $q.reject();
            }
            return SerialConnectionPorts.get({id : vm.serialConnection.serialConnectionId}).$promise;
        }).then(function(serialConnection) {
            vm.ports.push(serialConnection);
        });
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
            $scope.$emit('smaartApp:serialConnectionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
