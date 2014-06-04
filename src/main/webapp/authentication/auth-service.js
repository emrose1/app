Application.Services.service('auth', ['sessionService', '$q', 'AUTH_EVENTS', 'GAE_IDS', 'alerts',
    function (sessionService, $q, AUTH_EVENTS, GAE_IDS, alerts) {

    /**
     * Loads the application UI after the user has completed auth.
     */
    userAuthenticated = function() {
        var request = gapi.client.oauth2.userinfo.get().execute(function(resp) {
            if (!resp.code) {
                console.log(resp);
                sessionService.createUserDetails(resp);
                alerts.clear();
                //var token = gapi.auth.getToken();
                //token.access_token = token.id_token;
                //gapi.auth.setToken(token);
            } else {
                alerts.setAlert({
                'alertMessage': "Sorry you could not be authenticated",
                'alertType': 'alert-danger'});
            }
        });

    };

    /**
     * Handles the auth flow, with the given value for immediate mode.
     *
     * @param {boolean}
     *      mode Whether or not to use immediate mode.
     *      Immediate mode:false launches popup
     * @param {Function}
     *      callback Callback to call on completion.
     */
    signin = function(mode, callback) {
        gapi.auth.authorize({
            client_id : GAE_IDS.WEB_CLIENT_ID,
            scope : GAE_IDS.SCOPES,
            immediate : mode
        }, callback);
    };

    /**
     * Presents the user with the authorization popup.
     */
    this.authenticate = function(mode) {
        signin(mode, userAuthenticated);
    };

    this.isAuthenticated = function () {
        return !!sessionService.userId;
    };

    this.isAuthorized = function (authorizedRoles) {
        if (!angular.isArray(authorizedRoles)) {
            authorizedRoles = [authorizedRoles];
        }
        return (this.isAuthenticated() && authorizedRoles.indexOf(sessionService.userRole) !== -1);
    };

 }]);