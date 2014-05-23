Application.Services.service('gapiService', function (session, $q, AUTH_EVENTS) {

    var gapi = gapi || {};
    gapi.client = gapi.client || {};

    var loading = true;

    this.loadGapi = function() {
        var deferred = $q.defer();
        var apisToLoad = 1;
        var callback = function() {
            if(--apisToLoad === 0) {
                console.log('api loaded');
                $rootScope.$broadcast('EventLoaded');
                deferred.resolve(resp);
            } else {
                deferred.reject('error');
            }
            return deferred.promise;
        };
        gapi.client.load('booking', 'v1', callback, 'http://localhost:8080/_ah/api');

    };

});
