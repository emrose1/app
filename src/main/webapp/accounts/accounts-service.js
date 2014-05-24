Application.Services.service('accountService', ['$q', 'sessionService', function ($q, session) {

	this.list = function(account){
        var deferred = $q.defer();
        var request = gapi.client.booking.account.listAccounts({'account' : account.id });

        request.execute(function (resp) {
            if (resp && resp.items) {
                deferred.resolve(resp);
            } else {
                deferred.reject('error');
            }
        });
        return deferred.promise;
    };

    this.listWithoutUser = function(){
        var deferred = $q.defer();
        var request = gapi.client.booking.account.listAccountsWithoutUser();

        request.execute(function (resp) {
            if (resp && resp.items) {
                deferred.resolve(resp);
            } else {
                deferred.reject('error');
            }
        });

        return deferred.promise;
    };

}]);