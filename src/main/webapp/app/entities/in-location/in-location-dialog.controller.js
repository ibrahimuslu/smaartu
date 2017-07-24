(function() {
    'use strict';

    angular
        .module('smaartuApp')
        .controller('InLocationDialogController', InLocationDialogController);

    InLocationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'InLocation'];

    function InLocationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, InLocation) {
        var vm = this;

        vm.inLocation = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.inLocation.id !== null) {
                InLocation.update(vm.inLocation, onSaveSuccess, onSaveError);
            } else {
                InLocation.save(vm.inLocation, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smaartuApp:inLocationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
