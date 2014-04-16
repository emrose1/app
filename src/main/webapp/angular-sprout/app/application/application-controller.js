Application.Controllers.controller( 'application',
	['$rootScope', '$scope', '$location','$window', 'auth', 'USER_ROLES', 'AUTH_EVENTS', 'session',
    function ($rootScope, $scope, $location, $window, auth, USER_ROLES, AUTH_EVENTS, session) {

    $scope.currentUser = null;
    $scope.userSession = null;
    $scope.userRoles = USER_ROLES;
    $scope.isAuthenticated = auth.isAuthenticated;
    $scope.isAuthorized = auth.isAuthorized;

    $scope.alertType = "alert-info";
    $scope.alertMessage = "Welcome to the resolve demo";

	var loginSuccess = function () {

        console.log('login success');
        $scope.loginSuccessMessage = true;
        console.log(session.userRole);
        $scope.currentUser = session.userRole;


	};

    var loginFailed = function () {
        console.log('login failed');
        $scope.loginErrorMessage = true;
    };

    $rootScope.$on(AUTH_EVENTS.loginSuccess, loginSuccess);
    $rootScope.$on(AUTH_EVENTS.loginFailed, loginFailed);

    $scope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams){
        /*if ( angular.isDefined( toState.data.pageTitle ) ) {
            $scope.pageTitle = toState.data.pageTitle + ' | bookings' ;
        }*/
    });


}])
;
