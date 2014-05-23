
Application.Controllers.controller( 'accountsController', ['$rootScope', '$scope', 'alerts', 'accountService',
    function ($rootScope, $scope,  alerts, accountService) {

    // to set current account
    var listAccounts = function(){
        accountService.listWithoutUser()
        .then(function (data) {
            alerts.clear();
            console.log(data.items);
            $scope.accountForApp = data.items;

        }, function (reason) {
            alerts.setAlert({
                'alertMessage': "Server Error can't retrieve accounts",
                'alertType': 'alert-danger'});
        });
    };

    // to list accounts to super user
    $scope.loadAccount =  function(){
        console.log($scope.accountList);
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
