Application.Controllers.controller('MainCtrl', ['$scope', 'Account', 'sessionService',
        function($scope, Account, session) {

        $scope.account = new Account();
        $scope.accounts = Account.query();

        $scope.newAccount    = function() {
            $scope.account = new Account();
            $scope.editing = false;
        };

        $scope.activeAccount = function(account) {
            $scope.account = account;
            session.setAccount(account);
            $scope.editing = true;
        };

        $scope.save = function(account) {
            if ($scope.account.id) {
                Account.update({id: $scope.account.id}, $scope.account);
            } else {
                $scope.account.$save().then(function(response) {
                    $scope.accounts.push(response);
                });
            }
            $scope.editing = false;
            $scope.account = new Account();
        };

        $scope.delete = function(account) {
            Account.delete(account);
            _.remove($scope.accounts, account);
        };

    }
]);

//var app = angular.module('app', []);
app.controller('AppCtrl', function(){
    this.message = "Hello";
});