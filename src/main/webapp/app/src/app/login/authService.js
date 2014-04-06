angular.module('authService', [] )
.service('authService', function ($rootScope, Session, $q, AUTH_EVENTS) {



    this.init = function(credentials){

        var one = $q.defer();

        one.promise.then(function (data) {
            console.log(data);

            $rootScope.$broadcast(AUTH_EVENTS.loginSuccess);
        }, function (reason) {
            console.log('Failed: ' + reason);
            $rootScope.$broadcast(AUTH_EVENTS.loginFailed);
        });

        var request = gapi.client.booking.calendar.authUserSession({
            username: credentials.username,
            password: credentials.password
        });

        request.execute(function (resp) {
            if (resp && resp.user) {
                Session.create(resp.user.id, resp.user.userType.type);
                console.log('yeah');
                one.resolve(resp);
            } else {
                one.reject('error');
            }
            return one.promise;
        });
    };

    var isAuthenticated = function () {
        console.log(!!Session.userId);
        return !!Session.userId;
    };

    var isAuthorized = function (authorizedRoles) {
        if (!angular.isArray(authorizedRoles)) {
            authorizedRoles = [authorizedRoles];
        }
        return (this.isAuthenticated() && authorizedRoles.indexOf(Session.userRole) !== -1);
    };

 })
.service('Session', function () {
	this.create = function (userId, userRole) {
        console.log('creating session');
		this.userId = userId;
		this.userRole = userRole;
	};
	this.destroy = function () {
		this.userId = null;
		this.userRole = null;
	};
	return this;
});