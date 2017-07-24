(function() {
    'use strict';
    angular
        .module('smaartuApp')
        .factory('InLocation', InLocation);

    InLocation.$inject = ['$resource'];

    function InLocation ($resource) {
        var resourceUrl =  'api/in-locations/:id';

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
