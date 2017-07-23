(function() {
    'use strict';
    angular
        .module('smaartuApp')
        .factory('EndNode', EndNode);

    EndNode.$inject = ['$resource'];

    function EndNode ($resource) {
        var resourceUrl =  'api/end-nodes/:id';

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
