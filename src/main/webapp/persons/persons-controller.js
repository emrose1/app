
Application.Controllers.controller( 'personsController', ['$rootScope', '$scope', 'alerts', 'personService', 'sessionService',
    function ($rootScope, $scope,  alerts, personService, sessionService) {



    $scope.loadPersons = function() {
        $scope.persons = personService.getPersons();
        console.log($scope.accounts);

    };

    // to set current account
    var listPersons = function(){
        console.log('persons');
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


/*    // to set current account
    $scope.deleteAccount = function(accountToDelete){
        console.log(accountToDelete);
        console.log(sessionService.accountId);
        accountService.delete(accountToDelete)
        .then(function (data) {
            alerts.clear();
            listAccounts();
            $scope.loadAccount();

            if (data.message) {
                alerts.setAlert({
                    'alertMessage': 'Account was successfully deleted.',
                    'alertType': 'alert-success'
                });
            }
        }, function (reason) {
            console.log(reason);
            alerts.setAlert({
                'alertMessage': reason.message,
                'alertType': 'alert-danger'
            });
        });
    };
*/
}])

;
