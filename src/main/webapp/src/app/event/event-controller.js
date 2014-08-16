angular.module('application.controllers.event', [])
.controller('eventCtrl', ['$rootScope', '$scope', 'Event', 'sessionService',
        function($rootScope, $scope, Event, session) {

        console.log(session.getAccount());

        var listEvents = function() {
            console.log(session.getAccount());
            console.log(session.getCalendar());

            $scope.events = Event.query({
                account_id: session.getAccount(),
                calendar_id: session.getCalendar(),
                date_range_start: "Sat Jul 19 2014 08:48:16 GMT"
            }, function(data){
                console.log(data);
            });
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

        $rootScope.$on('calendarLoaded', function(event, args) {
            console.log('Event event triggered');
            listEvents();
            $scope.$emit('eventsLoaded', {});
        });

    }
]);