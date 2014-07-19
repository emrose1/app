Application.Controllers.controller('calendarCtrl', ['$rootScope', '$scope', 'Calendar', 'sessionService',
        function($rootScope, $scope, Calendar, session) {

        $scope.calendar = new Calendar();

        $scope.calendars = Calendar.query({account_id: session.getAccount()});

        var refreshCalendars = function() {
            Calendar.get({account_id: session.getAccount().id}, function(data) {
                $scope.calendars = data;
                console.log(data);
            });
        };

        $scope.newCalendar = function() {
            $scope.calendar = new Calendar();
            $scope.editing = false;
        };

        $scope.activeCalendar = function(calendar) {
            $scope.calendar = calendar;
            $scope.editing = true;
            session.setCalendar(calendar);
        };

        $scope.save = function(calendar) {
            if ($scope.calendar.id) {
                Calendar.update(
                    {account_id: session.getAccount().id, id: $scope.calendar.id},
                    $scope.calendar
                );
            } else {
                console.log($scope.calendar);
                console.log(calendar);
                $scope.calendar.$save({account_id: session.getAccount().id, id: $scope.calendar.id}).then(function(response) {
                    $scope.calendars.push(response);
                });
            }
            $scope.editing = false;
            $scope.calendar = new Calendar();
        };

        $scope.delete = function(calendar) {
            Calendar.delete({account_id: session.getAccount().id, id: calendar.id}, calendar);
            _.remove($scope.calendars, calendar);
        };

        $scope.$on('account-changed', function(event, args) {
            console.log('account changed');
            refreshCalendars();
        });

    }
]);