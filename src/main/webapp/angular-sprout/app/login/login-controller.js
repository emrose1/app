Application.Controllers.controller( 'login',
    function LoginController($scope, $rootScope, $location, AUTH_EVENTS, auth, session, alerts) {

    $scope.credentials = {
        username: '',
        password: ''
    };

    $scope.login = function (credentials) {
        $scope.loginSuccessMessage = false;
        $scope.loginErrorMessage = false;

        auth.login(credentials, $scope)
        .then(function (data) {
            alerts.clear();
            $location.path('/about');
            $rootScope.$broadcast(AUTH_EVENTS.loginSuccess);
        }, function (reason) {
            alerts.setAlert({
                'alertMessage': "Your username and password don't match our records",
                'alertType': 'alert-danger'});
            $rootScope.$broadcast(AUTH_EVENTS.loginFailed);
        });
    };
})

;