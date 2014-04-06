var gapi = gapi || {};
gapi.client = gapi.client || {};

angular.module( 'bookings', [
    'templates-app',
    'templates-common',
    'bookings.home',
    'bookings.about',
    'bookings.login',
    'ui.state',
    'ui.route',
    'authService'
])

.config( function myAppConfig ( $stateProvider, $urlRouterProvider ) {
    $urlRouterProvider.otherwise( '/home' );
})

/*.run(["$rootScope", "AUTH_EVENTS", "authService",
        function ($rootScope, AUTH_EVENTS, authService) {

    $rootScope.$on('$stateChangeStart', function (event, next, authService) {
        var authorizedRoles = next.data.authorizedRoles;
        if (!authService.isAuthorized(authorizedRoles)) {
            event.preventDefault();
            if (authService.isAuthenticated()) {
                // user is not allowed
                $rootScope.$broadcast(AUTH_EVENTS.notAuthorized);
            } else {
                // user is not logged in
                $rootScope.$broadcast(AUTH_EVENTS.notAuthenticated);
            }
        }
    });

    var resolveDone = function () { $rootScope.doingResolve = false; };
    $rootScope.doingResolve = false;
    //$scope.$on('ApiLoaded', resolveDone);

        $rootScope.$on('$stateChangeStart', function () {
        $rootScope.doingResolve = true;
    });
    $rootScope.$on('$stateChangeSuccess', resolveDone);
    $rootScope.$on('$stateChangeError', resolveDone);
    $rootScope.$on('$statePermissionError', resolveDone);
}])*/

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
    admin: 'admin',
    editor: 'editor',
    guest: 'guest'
})

.controller( 'AppCtrl', function AppCtrl ($scope, $location, authService, $window) {
    $scope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams){
        if ( angular.isDefined( toState.data.pageTitle ) ) {
            $scope.pageTitle = toState.data.pageTitle + ' | bookings' ;
        }
    });

    $scope.currentUser = null;
    //$scope.userRoles = USER_ROLES;
    $scope.isAuthorized = authService.isAuthorized;

    $scope.$window = $window;

    $window.initialise = function() {
        var apisToLoad = 1;
        var callback = function() {
            if(--apisToLoad === 0) {
                console.log('api loaded');
                $scope.$broadcast('EventLoaded');
                $scope.is_backend_ready = true;
            }
        };
        gapi.client.load('booking', 'v1', callback, 'http://localhost:8080/_ah/api');
    };

})

;

