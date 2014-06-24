Application.Controllers.controller('eventCategoryCtrl', ['$scope', 'EventCategory', 'sessionService',
        function($scope, EventCategory, session) {

        $scope.eventCategory = new EventCategory();
        $scope.eventCategorys = EventCategory.query({account_id: session.getAccount().id});

        $scope.newEventCategory = function() {
            $scope.eventCategory = new EventCategory();
            $scope.editing = false;
        };

        $scope.activeEventCategory = function(eventCategory) {
            $scope.eventCategory = eventCategory;
            $scope.editing = true;
        };

        $scope.save = function(eventCategory) {
            if ($scope.eventCategory.id) {
                EventCategory.update(
                    {account_id: session.getAccount().id, id: $scope.eventCategory.id},
                    $scope.eventCategory
                );
            } else {
                console.log($scope.eventCategory);
                console.log(eventCategory);
                $scope.eventCategory.$save({account_id: session.getAccount().id, id: $scope.eventCategory.id}).then(function(response) {
                    $scope.eventCategorys.push(response);
                });
            }
            $scope.editing = false;
            $scope.eventCategory = new EventCategory();
        };

        $scope.delete = function(eventCategory) {
            EventCategory.delete({account_id: session.getAccount().id, id: eventCategory.id}, eventCategory);
            _.remove($scope.eventCategorys, eventCategory);
        };

    }
]);