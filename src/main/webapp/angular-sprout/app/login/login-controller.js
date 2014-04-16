Application.Controllers.controller( 'login',
    function LoginController($scope, $rootScope, $location, AUTH_EVENTS, auth, session) {

    $scope.credentials = {
        username: '',
        password: ''
    };

    $scope.login = function (credentials) {
        $scope.loginSuccessMessage = false;
        $scope.loginErrorMessage = false;

        auth.login(credentials, $scope)
        .then(function (data) {
            $location.path('/about');
            $rootScope.$broadcast(AUTH_EVENTS.loginSuccess);
        }, function (reason) {
            console.log('Failed: ' + reason);
            $rootScope.$broadcast(AUTH_EVENTS.loginFailed);
        });
    };
})

;