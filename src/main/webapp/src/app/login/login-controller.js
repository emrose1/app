angular.module('application.controllers.login', [])
.controller( 'login',
    function LoginController($scope, $rootScope, $location, AUTH_EVENTS, auth, session, alerts, accountService) {


    $scope.login = function (credentials) {
        $scope.loginSuccessMessage = false;
        $scope.loginErrorMessage = false;

        auth.login($scope)
        .then(function (data) {
            alerts.clear();
            $location.path('/about');
            $rootScope.$broadcast(AUTH_EVENTS.loginSuccess);
        }, function (reason) {
            alerts.setAlert({
                'alertMessage': "Your username and password don't match our records",
                'alertType': 'alert-danger'
            });
            $rootScope.$broadcast(AUTH_EVENTS.loginFailed);
        });
    };


    $scope.accountsList = false;

    accountService.listAccounts()
    .then(function (data) {
        alerts.clear();
        console.log(data);
        $scope.accounts = data.items;

    }, function (reason) {
        alerts.setAlert({
            'alertMessage': "Server Error can't retrieve accounts",
            'alertType': 'alert-danger'});
    });
})

;