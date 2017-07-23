(function() {
    'use strict';
    angular
        .module('smaartuApp')
        .factory('EndNodeUnit', EndNodeUnit);

    EndNodeUnit.$inject = ['$resource'];

    function EndNodeUnit ($resource) {
        var resourceUrl =  'api/end-node-units/:id';

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
