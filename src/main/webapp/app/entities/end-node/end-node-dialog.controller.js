(function() {
    'use strict';

    angular
        .module('smaartuApp')
        .controller('EndNodeDialogController', EndNodeDialogController);

    EndNodeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'EndNode', 'ControlSystem', 'InLocation', 'SerialConnection', 'EndNodeUnit'];

    function EndNodeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, EndNode, ControlSystem, InLocation, SerialConnection, EndNodeUnit) {
        var vm = this;

        vm.endNode = entity;
        vm.clear = clear;
        vm.save = save;
        vm.controlsystems = ControlSystem.query();
        vm.inlocations = InLocation.query({filter: 'endnode-is-null'});
        $q.all([vm.endNode.$promise, vm.inlocations.$promise]).then(function() {
            if (!vm.endNode.inLocationId) {
                return $q.reject();
            }
            return InLocation.get({id : vm.endNode.inLocationId}).$promise;
        }).then(function(inLocation) {
            vm.inlocations.push(inLocation);
        });
        vm.serialconnections = SerialConnection.query({filter: 'endnode-is-null'});
        $q.all([vm.endNode.$promise, vm.serialconnections.$promise]).then(function() {
            if (!vm.endNode.serialConnectionId) {
                return $q.reject();
            }
            return SerialConnection.get({id : vm.endNode.serialConnectionId}).$promise;
        }).then(function(serialConnection) {
            vm.serialconnections.push(serialConnection);
        });
        vm.endnodeunits = EndNodeUnit.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.endNode.id !== null) {
                EndNode.update(vm.endNode, onSaveSuccess, onSaveError);
            } else {
                EndNode.save(vm.endNode, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smaartuApp:endNodeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
