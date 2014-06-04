angular.module('app')
  .controller('PersonResourceCtrl', ['$scope', '$route', 'Person',
      function($scope, $route, Person) {

        $scope.person = new Person();
        $scope.persons = Person.query();

        $scope.newPerson    = function() {
          $scope.person = new Person();
          $scope.editing = false;
        };

        $scope.activePerson = function(person) {
          $scope.person = person;
          $scope.editing = true;
        };

        $scope.save = function(person) {
          if ($scope.person._id) {
            Person.update({_id: $scope.person._id}, $scope.person);
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
