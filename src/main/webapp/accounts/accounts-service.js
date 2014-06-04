Application.Services.service('accountService', ['$q', 'sessionService', 'alerts', function ($q, session, alerts) {

	this.listWithoutUser = function(){
        var self = this;
        var deferred = $q.defer();
        var request = gapi.client.booking.account.listAccountsWithoutUser();

        request.execute(function (resp) {
            if (resp && resp.items) {
                this.accounts = self.setAccounts(resp.items);
                deferred.resolve(resp);
            } else {
                deferred.reject('error');
            }
        });

        return deferred.promise;
    };

    this.getAccounts = function() {
        return this.accounts;
    };

    this.setAccounts = function (accountItems) {
        this.accounts = accountItems;
    };

    this.delete = function(accountToDelete){
        var deferred = $q.defer();
        var message = {
            'account_id' : session.getAccount().id.toString(),
            'accountDelete' : accountToDelete.toString()
        };

        var request = gapi.client.booking.account.removeAccount(message);
        request.execute(function (resp) {

            if (typeof resp == 'undefined') {
                deferred.resolve(resp);
            } else {
                deferred.reject('error');
            }
        });
        return deferred.promise;
    };
}]);