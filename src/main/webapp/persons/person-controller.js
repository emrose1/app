Application.Controllers.controller('personCtrl', ['$scope', 'Person', 'sessionService',
        function($scope, Person, session) {

        $scope.person = new Person();
        $scope.persons = Person.query();

        $scope.person.username = session.username;
        $scope.person.email = session.email;
        $scope.person.userType = session.userType;
        $scope.person.account_id = session.getAccount();

        $scope.newPerson    = function() {
            $scope.person = new Person();
            $scope.editing = false;
        };

        $scope.activePerson = function(post) {
            $scope.person = person;
            $scope.editing = true;
        };

        $scope.save = function(post) {
            if ($scope.person.id) {
                Person.update({id: $scope.person.id}, $scope.person);
            } else {
                $scope.person.$save().then(function(response) {
                    $scope.persons.push(response);
                });
            }
            $scope.editing = false;
            $scope.person = new Person();
        };

        $scope.delete = function(person) {
            Person.delete(person);
            _.remove($scope.persons, person);
        };

    }
]);