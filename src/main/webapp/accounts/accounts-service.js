Application.Services.service('accountService', function ($q, session) {

	this.list = function(account){
        var deferred = $q.defer();
        console.log(account);
        var request = gapi.client.booking.account.listAccounts({'account' : account.id });

        request.execute(function (resp) {
            console.log(resp);
            if (resp && resp.items) {
                console.log(resp);
                deferred.resolve(resp);
            } else {
                console.log(resp);
                deferred.reject('error');
            }
        });

        return deferred.promise;
    };

    this.listWithoutUser = function(){
        var deferred = $q.defer();
        var request = gapi.client.booking.account.listAccountsWithoutUser();

        request.execute(function (resp) {
            console.log(resp);
            if (resp && resp.items) {
                console.log(resp);
                deferred.resolve(resp);
            } else {
                console.log(resp);
                deferred.reject('error');
            }
        });

        return deferred.promise;
    };

});