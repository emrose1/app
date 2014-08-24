angular.module('application.account.calendar', [
    'application.account.calendar.service'
])

.config([
  '$stateProvider',
  function config( $stateProvider ) {
    $stateProvider
        .state('calendar', {
            url: '/calendar',

            views: {
                'accounts': {
                    templateUrl: 'app/account/account-dropdown.tpl.html',
                    controller: 'AccountCtrl'
                },
                'calendars': {
                    templateUrl: 'app/account/calendar/calendar.tpl.html',
                    controller: 'calendarCtrl'
                }
            }
        });
    }
])

.controller('calendarCtrl', ['$rootScope', '$scope', 'Calendar', 'sessionService',
        function($rootScope, $scope, Calendar, session) {

        $scope.calendar = new Calendar();

        var getCalendars = function() {
            Calendar.query({'account_id' : session.getAccount()}, function(calendars) {
                $scope.calendars = calendars;
                $scope.setCalendar(calendars[0].id);
              });
        };

/*        if(session.getAccount()) {
            getCalendars();
        } else {
            console.log('get Account to list Calendars');
        }*/

        $scope.newCalendar = function() {
            $scope.calendar = new Calendar();
            $scope.editing = false;
        };

        $scope.activeCalendar = function(calendar) {
            $scope.calendar = calendar;
            $scope.editing = true;
            session.setCalendar(calendar.id);
        };

        $scope.save = function(calendar) {
            if (calendar.id) {
                calendar.$update({'account_id' : session.getAccount()}, function(){
                    getCalendars();
                });
            } else {
                calendar.$save({'account_id' : session.getAccount()}, function() {
                    getCalendars();
                });
            }
            $scope.newCalendar();
        };

        $scope.delete = function(calendar) {
            calendar.$delete({account_id: session.getAccount()}, function(){
                getCalendars();
            });
        };

        $scope.setCalendar = function(calendar) {
            session.setCalendar(calendar);
            $scope.selectedCalendar = calendar;
            $scope.$emit('calendarLoaded', {});
        };

        $rootScope.$on('accountLoaded', function(event, args) {
            getCalendars();
        });

    }
]);