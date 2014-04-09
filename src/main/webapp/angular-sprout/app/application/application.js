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

.run(function ($rootScope, AUTH_EVENTS, auth) {

    $rootScope.$on('AUTH_EVENTS.notAuthenticated', function(){
        console.log('not authenticated');
    });

    $rootScope.$on('AUTH_EVENTS.notAuthorized', function(){
        console.log('not authorized');
    });

    $rootScope.$on('$stateChangeStart', function (event, next) {

        if(next.data && next.data.authorizedRoles) {
            var authorizedRoles = next.data.authorizedRoles;
            if (!auth.isAuthorized(authorizedRoles)) {
                event.preventDefault();
                if (auth.isAuthenticated()) {
                    // user is not allowed
                    $rootScope.$broadcast(AUTH_EVENTS.notAuthorized);
                } else {
                    // user is not logged in
                    $rootScope.$broadcast(AUTH_EVENTS.notAuthenticated);
                }
            }
        }
    });
})

.config(function($stateProvider, $urlRouterProvider, USER_ROLES) {
    $urlRouterProvider.otherwise( '/login' );

    $stateProvider
        .state('about', {
            url: '/about',
            controller:'application',
            templateUrl: 'about/about-partial.html'
            /*data: {
                authorizedRoles: [USER_ROLES.all, USER_ROLES.editor],
                pageTitle: 'Home'
            }*/
        })
        .state('details', {
            url: '/details',
            controller: 'details',
            templateUrl: 'details/details-partial.html',
            data: {
                authorizedRoles: [USER_ROLES.admin, USER_ROLES.editor],
                pageTitle: 'Details'
            }
        })
        .state('error', {
            url: '/error',
            controller: 'error',
            templateUrl: 'error/error-partial.html',
            data: {
                authorizedRoles: [USER_ROLES.admin, USER_ROLES.editor],
                pageTitle: 'Error'
            }
        })
        .state( 'login', {
            url: '/login',
            controller: 'application.login',
            templateUrl: 'login/login-partial.html'
        });
});

