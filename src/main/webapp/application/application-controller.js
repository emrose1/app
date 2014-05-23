Application.Controllers.controller( 'application',[
    '$rootScope',
    '$scope',
    '$location',
    '$window',
    'auth',
    'USER_ROLES',
    'AUTH_EVENTS',
    'session',
    'alerts',
    'progressbarService',
    'accountService',

    function ($rootScope, $scope, $location, $window, auth, USER_ROLES, AUTH_EVENTS,
        session, alerts, progressbarService, accountService) {

    $scope.currentUser = null;
    $scope.userSession = null;
    $scope.userRoles = USER_ROLES;
    $scope.isAuthenticated = auth.isAuthenticated;
    $scope.isAuthorized = auth.isAuthorized;
    $scope.feedbackMessage = false;

    $scope.alerts = alerts;
    $scope.progressbarService = progressbarService;
    $scope.backendReady = false;

    /**
     * Presents the user with the authorization popup.
     */

    $scope.signin = false;

    $scope.authenticate = function() {
        console.log("authenticate");
        //if (!auth.isAuthenticated) {
            auth.signin(false, auth.userAuthed);
            $scope.signin = true;
        //} else {
          //  $scope.signin = false;
        //}
    };

    $scope.logout = function () {
        session.destroy();
    };

    var loginSuccess = function () {
        $scope.currentUser = session.userRole;
	};

    var loginFailed = function () {

    };

    $scope.loadProgressBar = function() {
        progressbarService.setProgressBar('progress-bar-warning');
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

}])
;
