Application.Services.service('personService', ['$q', 'sessionService', 'alerts', function ($q, session, alerts) {

    this.getPersons = function() {
        return this.persons;
    };

    this.setPersons = function (personItems) {
        this.persons = personItems;
    };

    this.list = function(account){
        var self = this;
        var deferred = $q.defer();
        console.log('listing persons');
        console.log(session.getAccount());
        var request = gapi.client.booking.calendar.listPersons({'account' : session.getAccount().id });

        request.execute(function (resp) {
            if (resp && resp.items) {
                console.log(resp.items);
                this.persons = self.setPersons(resp.items);
                deferred.resolve(resp);
            } else {
                deferred.reject('error');
            }
        });
        return deferred.promise;
    };

/*    this.delete = function(accountToDelete){
        var deferred = $q.defer();
        var message = {
            'personIds' : {},
            'accountId' : session.accountId.toString()
        };
        console.log('delete');
        console.log(message);
        var request = gapi.client.booking.account.deleteAccount(message);

        request.execute(function (resp) {
            if (resp && resp.items) {
                console.log(resp);
                deferred.resolve(resp);
            } else { //if (resp.code) {
                console.log(resp);
                deferred.reject('error');
                //deferred.reject(resp);
            }
        });
        return deferred.promise;
    };*/

}]);