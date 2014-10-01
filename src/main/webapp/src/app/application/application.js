
/*var gapi = gapi || {};
gapi.client = gapi.client || {};*/

var Application = Application || {};

Application.Constants = angular.module('application.constants', ['application.constants.configuration']);
Application.Services = angular.module('application.services', [
    'application.services.alerts',
    'application.services.auth',
    'application.services.session',
    'application.services.progressBar'
]);
Application.Domains = angular.module('application.domains', [
    'application.controllers.application',
    'application.account',
    'application.account.calendar',
    'application.account.person',
    'application.account.calendar.eventAttribute',
    'application.account.calendar.eventCategory',
    'application.account.calendar.event',
    'application.account.calendar.event.schedule',
    'application.account.calendar.event.createEvent'
]);
Application.Filters = angular.module('application.filters', []);
Application.Directives = angular.module('application.directives', [
    'application.directives.alert'
]);

angular.module('application', [
    'ui.router',
    'ngResource',
    'ui.bootstrap',
    'validation',
    'validation.rule',
    'application.constants',
    'application.filters',
    'application.services',
    'application.directives',
    'application.domains'
])

.run(function ($state, $rootScope, AUTH_EVENTS, auth, sessionService, $window, $location, alerts, Account) {
    $rootScope.$on('$stateChangeStart', function (event, next) {

        if(!sessionService.getAccount()) {
            event.preventDefault();
            Account.query({}, function(data) {
                sessionService.setAccount(data[0].id);
                $state.transitionTo(next);
            });
        }

            /*var authorizedRoles = next.data.authorizedRoles;
            if (!auth.isAuthorized(authorizedRoles)) {
                event.preventDefault();
                if (auth.isAuthenticated()) {
                    $rootScope.$broadcast(AUTH_EVENTS.notAuthorized);

                } else {
                    $rootScope.$broadcast(AUTH_EVENTS.notAuthenticated);
                }
            }*/
       // }
    });

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

