angular.module('application.account.calendar.event', [
    'application.account.calendar.event.service'
])

.config([
  '$stateProvider',
  function config( $stateProvider ) {
    $stateProvider
        .state('event', {
            url: '/event',

            views: {
                'accounts': {
                    templateUrl: 'app/account/account-dropdown.tpl.html',
                    controller: 'AccountCtrl'
                },
                'calendars': {
                    templateUrl: 'app/account/calendar/calendar-dropdown.tpl.html',
                    controller: 'calendarCtrl'
                },
                'content': {
                    templateUrl: 'app/account/calendar/schedule/event/event.tpl.html',
                    controller: 'eventCtrl'
                }
            }
        });
    }
])

.controller('eventCtrl', ['$rootScope', '$scope', 'Event', 'sessionService',
        function($rootScope, $scope, Event, session) {

        var getEvents = function() {

            Event.query({
                account_id: session.getAccount(),
                calendar_id: session.getCalendar(),
                date_range_start: new Date().getTime()

            }, function(data){
                $scope.events = data;
            });
        };

        if(session.getAccount() && session.getCalendar()) {
            getEvents();
        }

        $scope.newEvent = function() {
            $scope.event = new Event();
            $scope.editing = false;
        };

        $scope.activeEvent = function(event) {
            $scope.event = event;
            $scope.editing = true;
        };

        $scope.save = function(event) {
            if (event.id) {
                event.$update({calendar_id: session.getCalendar()}, function(){
                    getEvents();
                });
            } else {
                event.$save({calendar_id: session.getCalendar()}, function(){
                    getEvents();
                });
            }
            $scope.newEvent();
        };

        $scope.delete = function(event) {
            event.$delete({calendar_id: session.getCalendar()}, function(){
                getEvents();
            });
        };

        $rootScope.$on('calendarLoaded', function(event, args) {
            getEvents();
            $scope.$emit('eventsLoaded', {});
        });

    }
]);