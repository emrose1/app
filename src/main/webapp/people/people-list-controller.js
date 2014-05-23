function PeopleListController($scope, people) {
    $scope.peopleList = [];

    $scope.init = function() {
        people.requestPeople().then(function() {
            $scope.peopleList = people.peopleStore;
        });
    };
}