
/*var gapi = gapi || {};
gapi.client = gapi.client || {};*/
//

angular.module('app.account.person.model', []);
angular.module('app.account.person', [
    'app.account.person.model'
]);


angular.module('app.schedule.eventAttribute.model', []);
angular.module('app.schedule.eventAttribute', [
    'app.schedule.eventAttribute.model'
]);

angular.module('app.calendar.eventCategory.model', []);
angular.module('app.calendar.eventCategory', [
    'app.calendar.eventCategory.model'
]);

angular.module('app.account.calendar.service', []);

angular.module('app.account.calendar', [
    'app.account.calendar.service'
]);

angular.module('app.account.schedule.event.eventModel', [])

angular.module('app.account.schedule.event.eventService', []);

angular.module('app.account.schedule.event.repeatEventModal', [
    'checklist-model'
]);

angular.module('app.account.schedule.event.createEvent', [
    'app.account.calendar.service',
    'app.schedule.eventAttribute.model',
    'app.calendar.eventCategory.model',
    'app.account.person.model',
    'app.account.schedule.event.repeatEventModal'

])

angular.module( 'app.account.schedule', [
    'app.schedule.eventAttribute',
    'app.calendar.eventCategory',
    'app.account.schedule.event.eventModel',
    'app.account.schedule.event.eventService',
    'app.account.schedule.event.createEvent'

]);

angular.module('app.account.accountModel', []);

angular.module('app.account', [
    'app.account.accountModel',
    'app.account.calendar',
    'app.account.schedule'
]);

angular.module('app.services', [
    'app.services.alerts',
    'app.services.auth',
    'app.services.session',
    'app.services.progressBar'
]);

angular.module('app.directives', [
    'app.directives.alert'
]);

angular.module('app.constants', []);

angular.module('app', [
    'ui.router',
    'ngResource',
    'ui.bootstrap',
    'validation',
    'validation.rule',
    'app.constants',
    'app.services',
    'app.directives',
    'app.account'
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

