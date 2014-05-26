
Application.Controllers.controller( 'accountsController', ['$rootScope', '$scope', 'alerts', 'accountService', 'sessionService',
    function ($rootScope, $scope,  alerts, accountService, sessionService) {


    $scope.loadAccounts = function() {
        $scope.accounts = accountService.getAccounts();
        $scope.accountList = $scope.accounts[0];
        sessionService.setAccount($scope.accountList);
    };

    $scope.loadAccount =  function(){
        sessionService.setAccount($scope.accountList);
    };

    // to set current account
    var listAccounts = function(){
        accountService.listWithoutUser()
        .then(function (data) {
            alerts.clear();
            sessionService.setAccount(data.items[0]);
            accountService.setAccounts(data.items);
            $scope.loadAccounts();
        }, function (reason) {
            alerts.setAlert({
                'alertMessage': "Server Error can't retrieve accounts",
                'alertType': 'alert-danger'});
        });
    };

    listAccounts();

    // to set current account
    $scope.deleteAccount = function(accountToDelete){
        accountService.delete(accountToDelete)
        .then(function (data) {
            alerts.clear();
            listAccounts();
        }, function (reason) {
            console.log(reason);
            alerts.setAlert({
                'alertMessage': reason.message,
                'alertType': 'alert-danger'
            });
        });
    };

}])

;
