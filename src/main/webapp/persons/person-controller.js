Application.Controllers.controller('personCtrl', ['$scope', 'Person', 'sessionService',
        function($scope, Person, session) {

        $scope.person = new Person();
        $scope.persons = Person.query({account_id: session.getAccount().id});

       /* $scope.person.username = session.username;
        $scope.person.email = session.email;
        $scope.person.userType = session.userType;
        $scope.person.account_id = session.getAccount();*/

        $scope.newPerson = function() {
            $scope.person = new Person();
            $scope.person.username = session.username;
            $scope.person.email = session.email;
            $scope.editing = false;
        };

        $scope.activePerson = function(person) {
            $scope.person = person;
            $scope.editing = true;
        };

        $scope.save = function(person) {
            if ($scope.person.id) {
                Person.update(
                    {account_id: session.getAccount().id, id: $scope.person.id},
                    $scope.person
                );
            } else {
                $scope.person.$save({account_id: session.getAccount().id, id: $scope.person.id}).then(function(response) {
                    $scope.persons.push(response);
                });
            }
            $scope.editing = false;
            $scope.person = new Person();
        };

        $scope.delete = function(person) {
            Person.delete({account_id: session.getAccount().id, id: person.id}, person);
            _.remove($scope.persons, person);
        };

    }
]);