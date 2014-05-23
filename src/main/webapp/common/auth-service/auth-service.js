Application.Services.service('auth', function (session, $q, AUTH_EVENTS, GAE_IDS) {

    this.oAuthenticate = function () {
        var oAuthUser = {};
        var deferred = $q.defer();

        var oAuthRequest = gapi.client.oauth2.userinfo.get().execute(function(resp) {
            if (!resp.code) {
                oAuthUser.email = resp.email;
                oAuthUser.family_name = resp.family_name;
                oAuthUser.given_name = resp.given_name;
                oAuthUser.user_id = resp.id;
                oAuthUser.name = resp.name;
                console.log(resp);
                deferred.resolve(resp);
            } else {
                deferred.reject('error');
            }
            return deferred.promise;
        });
    };

    this.userAuthed = function() {
        console.log("userAuthed");
        var testUserRequest = gapi.client.booking.account.listTestUser();
        testUserRequest.execute(function(resp) {
            if (!resp.code) {
                console.log(resp);
            } else {
                console.log(resp.code);
            }
        });

        var request = gapi.client.oauth2.userinfo.get().execute(function(resp) {
            if (!resp.code) {
                console.log(resp);
            } else {
                console.log(resp.code);
            }

        });

        testUserRequest.execute(function(resp) {
            if (!resp.code) {
                console.log(resp);
            } else {
                console.log(resp.code);
            }
        });
    };

    /**
     * Handles the auth flow, with the given value for immediate mode.
     * @param {boolean} mode Whether or not to use immediate mode.
     * @param {Function} callback Callback to call on completion.
     */
    this.signin = function(mode, callback) {
        gapi.auth.authorize({
            client_id: GAE_IDS.WEB_CLIENT_ID,
            scope: GAE_IDS.SCOPES,
            immediate: mode
        },
        callback);
    };

    this.login = function(credentials, $scope){

        this.oAuthRequest().then(function (data) {

            var deferred = $q.defer();

            var message = {
                userId: data.id
            };
            console.log(message);
            var request = gapi.client.booking.calendar.authUserSession(message);

            request.execute(function (resp) {
                console.log(resp);
                $scope.$apply(function() {
                    if (resp && resp.username) {
                        session.create(resp.id, resp.userType);
                        deferred.resolve(resp);
                    } else {
                        deferred.reject('error');
                    }
                });
            });
            return deferred.promise;


        }, function (reason) {
            alerts.setAlert({
                'alertMessage': "Uh Oh",
                'alertType': 'alert-danger'
            });
            $rootScope.$broadcast(AUTH_EVENTS.loginFailed);
        });
    };

    this.isAuthenticated = function () {
        return !!session.userId;
    };

    this.isAuthorized = function (authorizedRoles) {
        if (!angular.isArray(authorizedRoles)) {
            authorizedRoles = [authorizedRoles];
        }
        return (this.isAuthenticated() && authorizedRoles.indexOf(session.userRole) !== -1);
    };

 })
.service('session', function () {

    this.setAccount = function (accountId) {
        console.log('creating account session');
        console.log(accountId);
        this.accountId = accountId;
    };
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
