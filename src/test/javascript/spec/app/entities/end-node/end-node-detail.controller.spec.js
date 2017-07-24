'use strict';

describe('Controller Tests', function() {

    describe('EndNode Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockEndNode, MockControlSystem, MockInLocation, MockSerialConnection, MockEndNodeUnit;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockEndNode = jasmine.createSpy('MockEndNode');
            MockControlSystem = jasmine.createSpy('MockControlSystem');
            MockInLocation = jasmine.createSpy('MockInLocation');
            MockSerialConnection = jasmine.createSpy('MockSerialConnection');
            MockEndNodeUnit = jasmine.createSpy('MockEndNodeUnit');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'EndNode': MockEndNode,
                'ControlSystem': MockControlSystem,
                'InLocation': MockInLocation,
                'SerialConnection': MockSerialConnection,
                'EndNodeUnit': MockEndNodeUnit
            };
            createController = function() {
                $injector.get('$controller')("EndNodeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'smaartuApp:endNodeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
