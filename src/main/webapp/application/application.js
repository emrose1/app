var gapi = gapi || {};
gapi.client = gapi.client || {};


// Utility functions
Storage.prototype.setObject = function(key, value) {
    this.setItem(key, JSON.stringify(value));
};

Storage.prototype.getObject = function(key) {
    var value = this.getItem(key);
    return value && JSON.parse(value);
};

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
    'localization',
    'application.filters',
    'application.services',
    'application.directives',
    'application.constants',
    'application.controllers'
])

.config(function($stateProvider, $urlRouterProvider, USER_ROLES) {

    $urlRouterProvider.otherwise( '/accounts' );

    $stateProvider
        .state('loading', {
            url: '/about',
            controller:'about',
            templateUrl: 'about/about-partial.html'
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
        })
        .state('accounts', {
            url: '/accounts',
            controller: 'accountsController',
            templateUrl: 'accounts/accounts-partial.html'
        })
        .state('editAccount', {
            url: '/accounts/:accountId',
            controller: 'accountsController',
            templateUrl: 'accounts/accounts-partial.html'
        })
        ;
})

.run(function ($state, $rootScope, AUTH_EVENTS, auth, sessionService, $window, $location, alerts) {
    $rootScope.$on('$stateChangeStart', function (event, next) {
        if(next.data && next.data.authorizedRoles) {
            var authorizedRoles = next.data.authorizedRoles;
            if (!auth.isAuthorized(authorizedRoles)) {
                event.preventDefault();
                if (auth.isAuthenticated()) {
                    $rootScope.$broadcast(AUTH_EVENTS.notAuthorized);

                } else {
                    $rootScope.$broadcast(AUTH_EVENTS.notAuthenticated);
                }
            }
        }
    });

    $rootScope.$on('$stateChangeSuccess', function (event, next) {
    });

    $rootScope.$on('$stateChangeError', function (event, next) {
    });

    $rootScope.$window = $window;


    $window.initialise = function() {
        var apisToLoad = 2;

        var callback = function() {
            if(--apisToLoad === 0) {

                // if has userid cookie, might be page refresh so check if authenticated
                console.log("reauthenticate");
                auth.authenticate(true);

                $rootScope.$broadcast('EventLoaded');

                // setup dummy users
                var request = gapi.client.booking.calendar.dummyUsers();
                request.execute(
                    function (resp) {
                        console.log(resp);
                        if(resp.items.length > 0) {
                            sessionService.setAccount(resp.items[0].id);
                        }
                    }
                );

            }
        };
        gapi.client.load('booking', 'v1', callback, 'http://localhost:8080/_ah/api');
        gapi.client.load('oauth2', 'v2', callback);
    };
})
;

