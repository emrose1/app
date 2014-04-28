Application.Controllers.controller( 'application',
	['$rootScope', '$scope', '$location','$window', 'auth', 'USER_ROLES', 'AUTH_EVENTS', 'session', 'alerts', 'progressbarService',
    function ($rootScope, $scope, $location, $window, auth, USER_ROLES, AUTH_EVENTS, session, alerts, progressbarService) {

    $scope.currentUser = null;
    $scope.userSession = null;
    $scope.userRoles = USER_ROLES;
    $scope.isAuthenticated = auth.isAuthenticated;
    $scope.isAuthorized = auth.isAuthorized;
    $scope.feedbackMessage = false;

    $scope.alerts = alerts;
    $scope.progressbarService = progressbarService;
    $scope.backendReady = false;

    $scope.logout = function () {
        session.destroy();
    };

    var loginSuccess = function () {
        $scope.currentUser = session.userRole;
	};

    var loginFailed = function () {

    };

    $rootScope.$on(AUTH_EVENTS.loginSuccess, loginSuccess);
    $rootScope.$on(AUTH_EVENTS.loginFailed, loginFailed);

    $rootScope.$on(AUTH_EVENTS.notAuthenticated, function(){
        alerts.setAlert({
            'alertMessage': "You are not authorised to see this page",
            'alertType': 'alert-danger'
        });
    });

    $rootScope.$on(AUTH_EVENTS.notAuthorized, function(){
        console.log('ctrl not authorized');
    });

    var apiLoadedSuccess = function () {
        $scope.backendReady = true;
        $scope.$apply();
    };

    $rootScope.$on('EventLoaded', apiLoadedSuccess);

    var apiLoading = function () {
        progressbarService.setProgressBar("progress-bar-danger");
    };

    $rootScope.$on('EventLoading', function(){
        console.log('boing');
        alerts.setAlert({
            'alertMessage': "loading",
            'alertType': 'alert-danger'
        });
    });

}])
;
