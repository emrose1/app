

/**
* Defines application-wide key value pairs
*/


Application.Constants.constant('AUTH_EVENTS', {
    loginSuccess: 'auth-login-success',
    loginFailed: 'auth-login-failed',
    logoutSuccess: 'auth-logout-success',
    sessionTimeout: 'auth-session-timeout',
    notAuthenticated: 'auth-not-authenticated',
    notAuthorized: 'auth-not-authorized'
});

Application.Constants.constant('USER_ROLES', {
    all: '*',
    admin: 'admin',
    owner: 'owner',
    organizer: 'organizer',
    attendee: 'attendee'
});



Application.Constants.constant('configuration', {

        ITEMS_URL : 'menu/items.json'

});
