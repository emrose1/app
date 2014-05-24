
Application.Controllers.controller( 'accountsController', ['$rootScope', '$scope', 'alerts', 'accountService', 'sessionService',
    function ($rootScope, $scope,  alerts, accountService, sessionService) {

    // to set current account
    var listAccounts = function(){
        accountService.listWithoutUser()
        .then(function (data) {
            alerts.clear();
            $scope.accountForApp = data.items;
            $scope.accountList = data.items[0];
            sessionService.setAccount($scope.accountList);
        }, function (reason) {
            alerts.setAlert({
                'alertMessage': "Server Error can't retrieve accounts",
                'alertType': 'alert-danger'});
        });
    };

    // to list accounts to super user
    $scope.loadAccount =  function(){
        sessionService.setAccount($scope.accountList);
        accountService.list($scope.accountList)
        .then(function (data) {
            alerts.clear();
            console.log(data.items);
            $scope.accounts = data.items;

        }, function (reason) {
            alerts.setAlert({
                'alertMessage': "Server Error can't retrieve accounts",
                'alertType': 'alert-danger'});
        });
    };

    listAccounts();

}])

;
