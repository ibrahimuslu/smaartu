(function() {
    'use strict';

    angular
        .module('smaartuApp')
        .controller('ActionDialogController', ActionDialogController);

    ActionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Action', 'EndNodeUnit', 'Mode'];

    function ActionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Action, EndNodeUnit, Mode) {
        var vm = this;

        vm.action = entity;
        vm.clear = clear;
        vm.save = save;
        vm.conditionunits = EndNodeUnit.query({filter: 'condition-is-null'});
        $q.all([vm.action.$promise, vm.conditionunits.$promise]).then(function() {
            if (!vm.action.conditionUnitId) {
                return $q.reject();
            }
            return EndNodeUnit.get({id : vm.action.conditionUnitId}).$promise;
        }).then(function(conditionUnit) {
            vm.conditionunits.push(conditionUnit);
        });
        vm.actionunits = EndNodeUnit.query({filter: 'action-is-null'});
        $q.all([vm.action.$promise, vm.actionunits.$promise]).then(function() {
            if (!vm.action.actionUnitId) {
                return $q.reject();
            }
            return EndNodeUnit.get({id : vm.action.actionUnitId}).$promise;
        }).then(function(actionUnit) {
            vm.actionunits.push(actionUnit);
        });
        vm.modes = Mode.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.action.id !== null) {
                Action.update(vm.action, onSaveSuccess, onSaveError);
            } else {
                Action.save(vm.action, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smaartuApp:actionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
