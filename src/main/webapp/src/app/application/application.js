
/*var gapi = gapi || {};
gapi.client = gapi.client || {};*/

var Application = Application || {};

Application.Constants = angular.module('application.constants', ['application.constants.configuration']);
Application.Services = angular.module('application.services', [
    'application.services.alerts',

    'application.services.calendar',
    'application.services.event',
    'application.services.eventAttribute',
    'application.services.eventCategory',
    'application.services.auth',
    'application.services.session',
    'application.services.progressBar'
]);
Application.Controllers = angular.module('application.controllers', [
    'application.controllers.application',
    'application.account',
    'application.controllers.calendar',
    'application.controllers.schedule',
    'application.controllers.event',
    'application.controllers.addEvent',
    'application.controllers.eventAttribute',
    'application.controllers.eventCategory'
]);
Application.Filters = angular.module('application.filters', []);
Application.Directives = angular.module('application.directives', [
    'application.directives.alert'
]);

angular.module('application', [
    'ui.router',
    'ngResource',
    'ui.bootstrap',
    'application.filters',
    'application.services',
    'application.directives',
    'application.constants',
    'application.controllers'
])

.config(function($stateProvider, $urlRouterProvider, USER_ROLES) {

    $urlRouterProvider.otherwise( '/schedule' );

    $stateProvider
        /*.state('loading', {
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
        })*/
        /*.state( 'login', {
            url: '/login',
            controller: 'login',
            templateUrl: 'app/login/login-partial.html'
        })*/

        .state('calendar', {
            url: '/calendar',
            controller: 'calendarCtrl',
            templateUrl: 'app/calendar/calendar.tpl.html'
        })
        .state('event', {
            url: '/event',
            controller: 'eventCtrl',
            templateUrl: 'app/schedule/event/event.tpl.html'
        })
        .state('addevent', {
            url: '/addevent',
            controller: 'addEventCtrl',
            templateUrl: 'app/schedule/event/add-event/add-event.tpl.html'
        })
        .state('schedule', {
            url: '/schedule',
            controller: 'eventCtrl',
            templateUrl: 'app/schedule/schedule.tpl.html'
        })
        .state('eventattribute', {
            url: '/eventattribute',
            controller: 'eventAttributeCtrl',
            templateUrl: 'app/event-attribute/event-attribute.tpl.html'
        })
        .state('eventcategory', {
            url: '/eventcategory',
            controller: 'eventCategoryCtrl',
            templateUrl: 'app/event-category/event-category.tpl.html'
        })
        ;
})

.run(function ($state, $rootScope, AUTH_EVENTS, auth, sessionService, $window, $location, alerts) {
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

