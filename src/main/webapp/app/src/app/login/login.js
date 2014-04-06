angular.module( 'bookings.login', [
  'ui.state',
  'authService'
])

.config(function config( $stateProvider ) {
  $stateProvider.state( 'login', {
    url: '/login',
    views: {
      "main": {
        controller: 'LoginCtrl',
        templateUrl: 'login/login.tpl.html'
      }
    },
    data:{ pageTitle: 'Login' }
  });
})

.controller( 'LoginCtrl', function LoginController($scope, $rootScope, $location, AUTH_EVENTS, authService) {

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

        authService.login(credentials, $scope)
        .then(function (data) {
            $location.path('/home');
            $scope.$broadcast(AUTH_EVENTS.loginSuccess);
        }, function (reason) {
            console.log('Failed: ' + reason);
            $scope.$broadcast(AUTH_EVENTS.loginFailed);
        });
    };
})

;