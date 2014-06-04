
Application.Controllers.controller( 'personDetailsController', [
    '$rootScope', '$scope', 'alerts', 'personService', 'sessionService', '$stateParams',
    function ($rootScope, $scope,  alerts, personService, session, $stateParams) {

    $scope.person = {};

    var findPerson = function(){
        var message = {
            'person' : $stateParams.personId,
            'account' : session.getAccount().id
        };

        personService.find($stateParams.personId)
        .then(function (data) {
            alerts.clear();
            console.log(data);
            $scope.person = data;
        }, function (reason) {
            alerts.setAlert({
                'alertMessage': "Server Error can't retrieve person",
                'alertType': 'alert-danger'});
        });
    };

    findPerson();

}])

;
