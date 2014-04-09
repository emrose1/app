Application.Controllers.controller( 'application.login', function LoginController($scope, $rootScope, $location, AUTH_EVENTS, auth) {

    var loginSuccess = function () {
        console.log('login success');
        $scope.loginSuccessMessage = true;
    };
    var loginFailed = function () {
        console.log('login failed');
        $scope.loginErrorMessage = true;
    };

    $scope.$on(AUTH_EVENTS.loginSuccess, loginSuccess);
    $scope.$on(AUTH_EVENTS.loginFailed, loginFailed);

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
            $scope.$broadcast(AUTH_EVENTS.loginSuccess);
        }, function (reason) {
            console.log('Failed: ' + reason);
            $scope.$broadcast(AUTH_EVENTS.loginFailed);
        });
    };
})

;