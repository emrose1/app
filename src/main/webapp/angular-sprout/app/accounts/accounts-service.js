Application.Services.service('accounts', function ($q) {

	var deferred = $q.defer();

        var request = gapi.client.booking.account.listAccounts();

        request.execute(function (resp) {
            $scope.$apply(function() {
                /*if (resp && resp.user) {
                    session.create(resp.user.id, resp.user.userType.type);
                    deferred.resolve(resp);
                } else {
                    deferred.reject('error');
                }*/
            });
        });
        return deferred.promise;

});