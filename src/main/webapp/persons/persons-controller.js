
Application.Controllers.controller( 'personsController', ['$rootScope', '$scope', 'alerts', 'personService', 'sessionService',
    function ($rootScope, $scope,  alerts, personService, sessionService) {

    $scope.loadPersons = function() {
        $scope.persons = personService.getPersons();
    };

    // to set current account
    var listPersons = function(){
        personService.list()
        .then(function (data) {
            alerts.clear();
            console.log('list Persons');
            console.log(data);
            $scope.persons = data.items;
        }, function (reason) {
            alerts.setAlert({
                'alertMessage': "Server Error can't retrieve persons",
                'alertType': 'alert-danger'});
        });
    };

    listPersons();

}])

;
