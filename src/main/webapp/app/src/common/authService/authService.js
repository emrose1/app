bookings.service('authService', function (Session, $q, AUTH_EVENTS) {

    this.login = function(credentials, $scope){
        var deferred = $q.defer();

        var request = gapi.client.booking.calendar.authUserSession({
            username: credentials.username,
            password: credentials.password
        });

        request.execute(function (resp) {
            $scope.$apply(function() {
                if (resp && resp.user) {
                    Session.create(resp.user.id, resp.user.userType.type);
                    console.log('yeah');
                    deferred.resolve(resp);
                } else {
                    deferred.reject('error');
                }
            });
        });
        return deferred.promise;
    };

    this.isAuthenticated = function () {
        console.log(!!Session.userId);
        return !!Session.userId;
    };

    this.isAuthorized = function (authorizedRoles) {
        if (!angular.isArray(authorizedRoles)) {
            authorizedRoles = [authorizedRoles];
        }
        return (this.isAuthenticated() && authorizedRoles.indexOf(Session.userRole) !== -1);
    };

 })
.service('Session', function () {
	this.create = function (userId, userRole) {
        console.log('creating session');
        console.log(userId);
        console.log(userRole);
		this.userId = userId;
		this.userRole = userRole;
	};
	this.destroy = function () {
		this.userId = null;
		this.userRole = null;
	};
	return this;
});