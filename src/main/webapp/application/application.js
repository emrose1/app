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
    'ngResource',
    'mgcrea.ngStrap',
    'localization',
    'application.filters',
    'application.services',
    'application.directives',
    'application.constants',
    'application.controllers'
])

.config(function($stateProvider, $urlRouterProvider, USER_ROLES) {

    $urlRouterProvider.otherwise( '/account' );

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
        .state('persons', {
            url: '/persons',
            controller: 'personsController',
            templateUrl: 'persons/persons-partial.html'
        })
        .state('personDetails', {
            url: "/persons/:personId",
            controller: 'personDetailsController',
            templateUrl: 'persons/person_details/person-details-partial.html'
        })
        .state('account', {
            url: '/account',
            controller: 'MainCtrl',
            templateUrl: 'accounts/account-partial.html'
        })
        .state('calendar', {
            url: '/calendar',
            controller: 'calendarCtrl',
            templateUrl: 'calendar/calendar-partial.html'
        })
        .state('event', {
            url: '/event',
            controller: 'eventCtrl',
            templateUrl: 'event/event-partial.html'
        })
        .state('addevent', {
            url: '/addevent',
            controller: 'addEventCtrl',
            templateUrl: 'event/add-event/add-event-partial.html'
        })
        .state('schedule', {
            url: '/schedule',
            controller: 'eventCtrl',
            templateUrl: 'event/schedule/schedule-partial.html'
        })
        .state('eventattribute', {
            url: '/eventattribute',
            controller: 'eventAttributeCtrl',
            templateUrl: 'event-attribute/event-attribute-partial.html'
        })
        .state('eventcategory', {
            url: '/eventcategory',
            controller: 'eventCategoryCtrl',
            templateUrl: 'event-category/event-category-partial.html'
        })
        .state('person', {
            url: '/person',
            controller: 'personCtrl',
            templateUrl: 'persons/person-partial.html'
        })
        ;
})

.run(function ($state, $rootScope, AUTH_EVENTS, auth, sessionService, accountService, $window, $location, alerts) {
    /*$rootScope.$on('$stateChangeStart', function (event, next) {
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
    });*/

    $rootScope.$on('$stateChangeSuccess', function (event, next) {
    });

    $rootScope.$on('$stateChangeError', function (event, next) {
    });

    $rootScope.$window = $window;


    /*$window.initialise = function() {
        var apisToLoad = 2;

        var callback = function() {
            if(--apisToLoad === 0) {

                // setup dummy users
                var request = gapi.client.booking.calendar.dummyUsers();
                request.execute(
                    function (resp) {

                        // Setting up newly loaded DB
                        if(resp.items.length > 0) {
                            sessionService.setAccount(resp.items[0].id);
                            accountService.setAccounts(resp.items);

                            // TODO: detect existing session cookie
                            // if has userid cookie, might be page refresh so check if authenticated
                            auth.authenticate(true);
                        } else {
                            // Setting up user account from DB
                            accountService.listWithoutUser()
                            .then(function (data) {
                                sessionService.setAccount(data.items[0].id);
                                accountService.setAccounts(data.items);
                                auth.authenticate(true);

                            }, function (reason) {
                            });
                        }

                    }
                );
            }
        };
        gapi.client.load('booking', 'v1', callback, 'http://localhost:8080/_ah/api');
        gapi.client.load('oauth2', 'v2', callback);
    };*/
})
;

