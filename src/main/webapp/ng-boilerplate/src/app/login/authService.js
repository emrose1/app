angular.module('authService', [] )
.factory('authService', function ($http, Session) {
	return {
		login: function (credentials) {

			gapi.client.booking.calendar.authUserSession(credentials).execute(function(resp) {
				console.log('auth');
				console.log(resp);
			});
			console.log('bla');
			Session.create('1234', '1234', 'admin');
		},
		isAuthenticated: function () {
			return !!Session.userId;
		},
		isAuthorized: function (authorizedRoles) {
			if (!angular.isArray(authorizedRoles)) {
			authorizedRoles = [authorizedRoles];
		}
		return (this.isAuthenticated() &&
			authorizedRoles.indexOf(Session.userRole) !== -1);
		}
	};
})
.service('Session', function () {
	this.create = function (sessionId, userId, userRole) {
		this.id = sessionId;
		this.userId = userId;
		this.userRole = userRole;
	};
	this.destroy = function () {
		this.id = null;
		this.userId = null;
		this.userRole = null;
	};
	return this;
});