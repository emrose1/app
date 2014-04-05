angular.module('authService', [] )
.factory('authService', function ($http,  Session) {
	return {
		login: function (credentials) {

            var message = {
                username: credentials.username,
                password: credentials.password
            };

			gapi.client.booking.calendar.authUserSession(message).execute(function(resp) {
				console.log('auth');
				console.log(resp);
                if(resp && resp.user) {
                    Session.create(resp.user.id, resp.user.userType.type);
                    return true;
                } else {
                    return false;
                }
			});
		},
		isAuthenticated: function () {
            console.log(!!Session.userId);
			return !!Session.userId;
		},
		isAuthorized: function (authorizedRoles) {
			if (!angular.isArray(authorizedRoles)) {
			authorizedRoles = [authorizedRoles];
		}
		return this.isAuthenticated();

	};
})
.service('Session', function () {
	this.create = function (userId, userRole) {
		this.userId = userId;
		this.userRole = userRole;
	};
	this.destroy = function () {
		this.userId = null;
		this.userRole = null;
	};
	return this;
});