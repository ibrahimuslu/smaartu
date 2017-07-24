(function() {
    'use strict';

    angular
        .module('smaartuApp')
        .controller('ControlSystemDialogController', ControlSystemDialogController);

    ControlSystemDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'ControlSystem', 'Location', 'EndNode'];

    function ControlSystemDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, ControlSystem, Location, EndNode) {
        var vm = this;

        vm.controlSystem = entity;
        vm.clear = clear;
        vm.save = save;
        vm.locations = Location.query({filter: 'controlsystem-is-null'});
        $q.all([vm.controlSystem.$promise, vm.locations.$promise]).then(function() {
            if (!vm.controlSystem.locationId) {
                return $q.reject();
            }
            return Location.get({id : vm.controlSystem.locationId}).$promise;
        }).then(function(location) {
            vm.locations.push(location);
        });
        vm.endnodes = EndNode.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.controlSystem.id !== null) {
                ControlSystem.update(vm.controlSystem, onSaveSuccess, onSaveError);
            } else {
                ControlSystem.save(vm.controlSystem, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smaartuApp:controlSystemUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
