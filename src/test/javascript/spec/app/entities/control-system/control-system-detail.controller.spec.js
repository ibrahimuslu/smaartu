'use strict';

describe('Controller Tests', function() {

    describe('ControlSystem Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockControlSystem, MockLocation, MockEndNode;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockControlSystem = jasmine.createSpy('MockControlSystem');
            MockLocation = jasmine.createSpy('MockLocation');
            MockEndNode = jasmine.createSpy('MockEndNode');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ControlSystem': MockControlSystem,
                'Location': MockLocation,
                'EndNode': MockEndNode
            };
            createController = function() {
                $injector.get('$controller')("ControlSystemDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'smaartuApp:controlSystemUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
