Application.Controllers.controller( 'application',
	['$rootScope', '$scope', '$location','$window', 'auth', 'USER_ROLES', 'AUTH_EVENTS', 'session', 'alerts',
    function ($rootScope, $scope, $location, $window, auth, USER_ROLES, AUTH_EVENTS, session, alerts) {

    $scope.currentUser = null;
    $scope.userSession = null;
    $scope.userRoles = USER_ROLES;
    $scope.isAuthenticated = auth.isAuthenticated;
    $scope.isAuthorized = auth.isAuthorized;
    $scope.feedbackMessage = false;

    $scope.alerts = alerts;

    $scope.logout = function () {
        session.destroy();
    };

	var loginSuccess = function () {
        console.log('login success');
        console.log(session.userRole);
        $scope.currentUser = session.userRole;
	};

    var loginFailed = function () {

    };

    $rootScope.$on(AUTH_EVENTS.loginSuccess, loginSuccess);
    $rootScope.$on(AUTH_EVENTS.loginFailed, loginFailed);

    $rootScope.$on(AUTH_EVENTS.notAuthenticated, function(){
        console.log('ctrl not authenticated');


        alerts.setAlert({
            'alertMessage': "You are not authorised to see this page",
            'alertType': 'alert-danger'});

        console.log('Not authorised');
    });

    $rootScope.$on(AUTH_EVENTS.notAuthorized, function(){
        console.log('ctrl not authorized');
    });



}])
;
