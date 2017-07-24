(function() {
    'use strict';
    angular
        .module('smaartuApp')
        .factory('SerialConnectionPorts', SerialConnectionPorts);

    SerialConnectionPorts.$inject = ['$resource'];

    function SerialConnectionPorts ($resource) {
        var resourceUrl =  'api/serial-connections/ports';

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
