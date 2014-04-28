
Application.Controllers.controller( 'accountsController', ['$scope', 'alerts', 'accountService',
    function ($scope,  alerts, accountService) {

    accountService.listAccounts()
    .then(function (data) {
        alerts.clear();
        console.log(data.items);
        $scope.accounts = data.items;

    }, function (reason) {
        alerts.setAlert({
            'alertMessage': "Server Error can't retrieve accounts",
            'alertType': 'alert-danger'});
    });

    $scope.loadAccount = function(){
        console.log($scope.accountList.id);
    };

}])

;
