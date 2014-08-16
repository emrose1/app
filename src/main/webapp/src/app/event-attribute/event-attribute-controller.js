angular.module('application.controllers.eventAttribute', [])
.controller('eventAttributeCtrl', ['$scope', 'EventAttribute', 'sessionService',
        function($scope, EventAttribute, session) {

        console.log(session.getAccount());
        console.log('bla');

        $scope.eventAttribute = new EventAttribute();
        $scope.eventAttributes = EventAttribute.query({account_id: session.getAccount().id});
        console.log($scope.eventAttributes);

        $scope.newEventAttribute = function() {
            $scope.eventAttribute = new EventAttribute();
            $scope.editing = false;
        };

        $scope.activeEventAttribute = function(eventAttribute) {
            $scope.eventAttribute = eventAttribute;
            $scope.editing = true;
        };

        $scope.save = function(eventAttribute) {
            if ($scope.eventAttribute.id) {
                EventAttribute.update(
                    {account_id: session.getAccount().id, id: $scope.eventAttribute.id},
                    $scope.eventAttribute
                );
            } else {
                console.log($scope.eventAttribute);
                console.log(eventAttribute);
                $scope.eventAttribute.$save({account_id: session.getAccount().id, id: $scope.eventAttribute.id}).then(function(response) {
                    $scope.eventAttributes.push(response);
                });
            }
            $scope.editing = false;
            $scope.eventAttribute = new EventAttribute();
        };

        $scope.delete = function(eventAttribute) {
            EventAttribute.delete({account_id: session.getAccount().id, id: eventAttribute.id}, eventAttribute);
            _.remove($scope.eventAttributes, eventAttribute);
        };

    }
]);