(function() {
    'use strict';
    angular
        .module('smaartuApp')
        .factory('Mode', Mode);

    Mode.$inject = ['$resource'];

    function Mode ($resource) {
        var resourceUrl =  'api/modes/:id';

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
