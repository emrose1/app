Application.Services.service('accountService', function ($q, session) {

	this.list = function($scope){
        var deferred = $q.defer();

        console.log(session.accountId);

        var request = gapi.client.booking.account.listAccounts({'account' : session.accountId.toString()});
        console.log(session.accountId);

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