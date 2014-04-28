Application.Services.service('accountService', function ($q) {

	this.listAccounts = function($scope){
        var deferred = $q.defer();

        var request = gapi.client.booking.account.listAccounts();

        request.execute(function (resp) {

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