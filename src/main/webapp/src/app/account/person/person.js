angular.module('application.account.person', [
    'application.account.person.service'
])

.config([
  '$stateProvider',
  function config( $stateProvider ) {
    $stateProvider
        .state('persons', {
            url: '/persons',

            views: {
                'accounts': {
                    templateUrl: 'app/account/account-dropdown.tpl.html',
                    controller: 'AccountCtrl'
                },
                'content': {
                    controller: 'personCtrl',
                    templateUrl: 'app/account/person/person.tpl.html'
                }
            }
        });
    }
])

.controller('personCtrl', ['$rootScope', '$scope', 'Person', 'sessionService',
        function($rootScope, $scope, Person, session) {

        $scope.Person = new Person();

        var getPersons = function() {
            Person.query({'account_id' : session.getAccount()}, function(persons) {
                $scope.persons = persons;
                $scope.setPerson(persons[0].id);
              });
        };

        $scope.newPerson = function() {
            $scope.person = new Person();
            $scope.editing = false;
        };

        $scope.activePerson = function(person) {
            $scope.person = person;
            $scope.editing = true;
        };

        $scope.save = function(person) {
            if (person.id) {
                person.$update({'account_id' : session.getAccount()}, function(){
                    getPersons();
                });
            } else {
                person.$save({'account_id' : session.getAccount()}, function() {
                    getPersons();
                });
            }
            $scope.newPerson();
        };

        $scope.delete = function(person) {
            person.$delete({account_id: session.getAccount()}, function(){
                getPersons();
            });
        };

        $scope.setPerson = function(person) {
            $scope.selectedPerson = person;
            $scope.$emit('personLoaded', {});
        };

        $rootScope.$on('accountLoaded', function(event, args) {
            getPersons();
        });

    }
]);