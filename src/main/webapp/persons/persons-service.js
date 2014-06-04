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
        var request = gapi.client.booking.person.listPersons({'account_id' : session.getAccount().id });
        request.execute(function (resp) {
            if (resp && resp.items) {
                this.persons = self.setPersons(resp.items);
                deferred.resolve(resp);
            } else {
                console.log(resp);
                deferred.reject('error');
            }
        });
        return deferred.promise;
    };

    this.delete = function(accountToDelete){
        var deferred = $q.defer();
        var message = {
            'person' : {},
            'account' : session.getAccount().id
        };
        var request = gapi.client.booking.person.removePerson(message);
        request.execute(function (resp) {
            if (resp && resp.items) {
                deferred.resolve(resp);
            } else {
                deferred.reject('error');
            }
        });
        return deferred.promise;
    };

    this.find = function(personId){
        var deferred = $q.defer();
        var message = {
            'id' : personId,
            'account_id' : session.getAccount().id
        };
        var request = gapi.client.booking.person.getPerson(message);
        request.execute(function (resp) {
            if (resp) {
                deferred.resolve(resp);
            } else {
                deferred.reject('error');
            }
        });
        return deferred.promise;
    };
}]);

