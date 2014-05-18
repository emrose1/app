
Application.Controllers.controller( 'accountsController', ['$rootScope', '$scope', 'alerts', 'accountService',
    function ($rootScope, $scope,  alerts, accountService) {

    console.log('listing accounts');

    var listAccounts = function(){
        accountService.list()
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
    };

    $rootScope.$on('AccountLoaded', listAccounts);

}])

;
