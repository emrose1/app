Application.Controllers.controller('eventCtrl', ['$scope', 'Event', 'sessionService',
        function($scope, Event, session) {

        console.log(session.getAccount());
        console.log(new Date);

        $scope.event = new Event();
        $scope.events = Event.query({
            account_id: session.getAccount().id,
            calendar_id: session.getCalendar().id,
            date_range_start: "Sat Jul 19 2014 08:48:16 GMT"
        });
        console.log($scope.events);

        $scope.newEvent = function() {
            $scope.event = new Event();
            $scope.editing = false;
        };

        $scope.newEvent = function() {
            $scope.event = new Event();
            $scope.editing = false;
        };

        $scope.activeEvent = function(event) {
            $scope.event = event;
            $scope.editing = true;
        };

        $scope.save = function(event) {
            if ($scope.event.id) {
                Event.update(
                    {account_id: session.getAccount().id, id: $scope.event.id},
                    $scope.event
                );
            } else {
                console.log($scope.event);
                $scope.event.$save({account_id: session.getAccount().id, id: $scope.event.id}).then(function(response) {
                    $scope.events.push(response);
                });
            }
            $scope.editing = false;
            $scope.event = new Event();
        };

        $scope.delete = function(event) {
            Event.delete({account_id: session.getAccount().id, id: event.id}, event);
            _.remove($scope.events, event);
        };

        $scope.changeAccount = function() {
            console.log('account changed');
        };

    }
]);