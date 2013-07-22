define(['./index', 'config'], function(services, config) {

    services.service('SearchConcept', ['$http', function($http) {
            this.runQuery = function(searchTerm, currReqNum) {
                return $http.get(config.contextPath + '/ws/cpm/concepts?query=' +
                    encodeURIComponent(searchTerm) + "&requestNum=" + currReqNum);
            }
        }]);
    
});
