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
    admin: 'ADMIN',
    owner: 'OWNER',
    organizer: 'ORGANIZER',
    attendee: 'ATTENDEE'
});

Application.Constants.constant('configuration', {
    ITEMS_URL : 'menu/items.json'
});


Application.Constants.constant('GAE_IDS', {
    WEB_CLIENT_ID : "389068630524-9rht9ebi7s7jpqos14a4im98iml6aet9.apps.googleusercontent.com",
    SCOPES : 'https://www.googleapis.com/auth/userinfo.email'
});
