(function() {
    'use strict';
    angular
        .module('smaartuApp')
        .factory('ControlSystem', ControlSystem);

    ControlSystem.$inject = ['$resource'];

    function ControlSystem ($resource) {
        var resourceUrl =  'api/control-systems/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
