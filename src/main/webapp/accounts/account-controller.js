Application.Controllers.controller('MainCtrl', ['$rootScope', '$scope', 'Account', 'sessionService',
        function($rootScope, $scope, Account, session) {

        console.log('account logged');

        $scope.account = new Account();
        $scope.accounts = Account.query();

        $scope.newAccount = function() {
            $scope.account = new Account();
            $scope.editing = false;
        };

        $scope.activeAccount = function(account) {
            $scope.account = account;
            $scope.editing = true;
            console.log(account);
            session.setAccount(account.id);
        };

        $scope.save = function() {
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
            console.log(account);
            _.remove($scope.accounts, account);
        };

        $scope.changeAccount = function() {
            session.setAccount($scope.selectedAccount);
        };

    }
]);

