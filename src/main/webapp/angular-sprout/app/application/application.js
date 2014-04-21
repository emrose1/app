var gapi = gapi || {};
gapi.client = gapi.client || {};

/**
* The application file bootstraps the angular app by  initializing the main module and
* creating namespaces and moduled for controllers, filters, services, and directives.
*/

var Application = Application || {};

Application.Constants = angular.module('application.constants', []);
Application.Services = angular.module('application.services', []);
Application.Controllers = angular.module('application.controllers', []);
Application.Filters = angular.module('application.filters', []);
Application.Directives = angular.module('application.directives', []);


angular.module('application', [
    'ui.router',
    'application.filters',
    'application.services',
    'application.directives',
    'application.constants',
    'application.controllers'
])

.config(function($stateProvider, $urlRouterProvider, USER_ROLES) {
    $urlRouterProvider.otherwise( '/login' );

    $stateProvider
        .state('about', {
            url: '/about',
            //controller:'application',
            templateUrl: 'about/about-partial.html',
            data: {
                authorizedRoles: [USER_ROLES.admin, USER_ROLES.owner],
                pageTitle: 'Home'
            }
        })
        .state('details', {
            url: '/details',
            controller: 'details',
            templateUrl: 'details/details-partial.html',
            data: {
                authorizedRoles: [USER_ROLES.admin],
                pageTitle: 'Details'
            }
        })
        .state('error', {
            url: '/error',
            controller: 'details',
            templateUrl: 'details/details-partial.html',
            data: {
                authorizedRoles: [USER_ROLES.admin, USER_ROLES.owner],
                pageTitle: 'Error'
            }
        })
        .state( 'login', {
            url: '/login',
            controller: 'login',
            templateUrl: 'login/login-partial.html'
        });
})

.run(function ($rootScope, AUTH_EVENTS, auth, $window) {

    $rootScope.$on('$stateChangeStart', function (event, next) {
        console.log('changestart');

        $rootScope.alertType = "alert-info";
        $rootScope.alertMessage = "Loading...";
        $rootScope.active = "progress-striped active progress-warning";

        if(next.data && next.data.authorizedRoles) {
            var authorizedRoles = next.data.authorizedRoles;
            if (!auth.isAuthorized(authorizedRoles)) {

                event.preventDefault();
                if (auth.isAuthenticated()) {
                    // user is not allowed
                    console.log('not authed');
                    $rootScope.$broadcast(AUTH_EVENTS.notAuthorized);
                } else {
                    // user is not logged in
                    console.log('not authenticated');
                    $rootScope.$broadcast(AUTH_EVENTS.notAuthenticated);
                }
            }
        }
    });

    $rootScope.$on('$stateChangeSuccess', function (event, next) {
        console.log('change success');

        $rootScope.alertType = "alert-success";
        $rootScope.alertMessage = "Successfully changed routes :)";
        $rootScope.active = "progress-success";


    });

    $rootScope.$on('$stateChangeError', function (event, next) {

        console.log('change error');

        $rootScope.alertType = "alert-warning";
        $rootScope.alertMessage = "Error changing routes :)";
        $rootScope.active = "progress-success";


    });

    $rootScope.$window = $window;

    $window.initialise = function() {
        var apisToLoad = 1;
        var callback = function() {
            if(--apisToLoad === 0) {
                console.log('api loaded');
                $rootScope.$broadcast('EventLoaded');
                //$scope.is_backend_ready = true;
            }
        };
        gapi.client.load('booking', 'v1', callback, 'http://localhost:8080/_ah/api');
    };
})
;

