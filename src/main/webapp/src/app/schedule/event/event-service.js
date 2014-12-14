angular.module('app.account.schedule.event.eventService', [])
.service('auth', ['sessionService', '$q',
    function (sessionService, $q) {

    this.isAuthorized = function (authorizedRoles) {
        if (!angular.isArray(authorizedRoles)) {
            authorizedRoles = [authorizedRoles];
        }
        return (this.isAuthenticated() && authorizedRoles.indexOf(sessionService.userRole) !== -1);
    };

 }]);