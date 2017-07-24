'use strict';

describe('Controller Tests', function() {

    describe('EndNodeUnit Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockEndNodeUnit, MockEndNode, MockAction;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockEndNodeUnit = jasmine.createSpy('MockEndNodeUnit');
            MockEndNode = jasmine.createSpy('MockEndNode');
            MockAction = jasmine.createSpy('MockAction');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'EndNodeUnit': MockEndNodeUnit,
                'EndNode': MockEndNode,
                'Action': MockAction
            };
            createController = function() {
                $injector.get('$controller')("EndNodeUnitDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'smaartuApp:endNodeUnitUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
