angular.module('application.constants.configuration', [])

.constant('AUTH_EVENTS', {
    loginSuccess: 'auth-login-success',
    loginFailed: 'auth-login-failed',
    logoutSuccess: 'auth-logout-success',
    sessionTimeout: 'auth-session-timeout',
    notAuthenticated: 'auth-not-authenticated',
    notAuthorized: 'auth-not-authorized'
})

.constant('USER_ROLES', {
    all: '*',
    superadmin: 'SUPERADMIN',
    admin: 'ADMIN',
    owner: 'OWNER',
    instructor: 'INSTRUCTOR',
    attendee: 'ATTENDEE'
})

.constant('configuration', {
    ITEMS_URL : 'menu/items.json'
})

.constant('GAE_IDS', {
    WEB_CLIENT_ID : "389068630524-9rht9ebi7s7jpqos14a4im98iml6aet9.apps.googleusercontent.com",
    SCOPES : 'https://www.googleapis.com/auth/userinfo.email',
    RESPONSE_TYPE : 'token id_token'
})

;
