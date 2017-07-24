(function() {
    'use strict';
    angular
        .module('smaartuApp')
        .factory('SerialConnection', SerialConnection);

    SerialConnection.$inject = ['$resource'];

    function SerialConnection ($resource) {
        var resourceUrl =  'api/serial-connections/:id';

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
