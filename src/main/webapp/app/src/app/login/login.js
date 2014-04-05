angular.module( 'bookings.login', [
  'ui.state',
  'plusOne',
  'authService'
])

/**
 * Each section or module of the site can also have its own routes. AngularJS
 * will handle ensuring they are all available at run-time, but splitting it
 * this way makes each module more "self-contained".
 */
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

.run(function($rootScope, AUTH_EVENTS) {

    $rootScope.loginSuccessMessage = false;
    $rootScope.loginErrorMessage = false;

    $rootScope.$on(AUTH_EVENTS.loginSuccess, loginSuccess);
    $rootScope.$on(AUTH_EVENTS.loginFailed, loginFailed);

    var loginSuccess = function () {
        $rootScope.loginSuccessMessage = true;
    };
    var loginFailed = function () {
        $rootScope.loginErrorMessage = true;
    };
})

/**
 * And of course we define a controller for our route.
 */
.controller( 'LoginCtrl', function LoginController($scope, $rootScope, AUTH_EVENTS, authService) {
  $scope.credentials = {
    username: '',
    password: ''
  };

  $scope.loginErrorMessage = 'false';
  $scope.loginSuccessMessage = 'false';

  $scope.login = function (credentials) {
    authService.login(credentials);
   /* .then(function () {
        $rootScope.$broadcast(AUTH_EVENTS.loginSuccess);
    }, function () {
        $rootScope.$broadcast(AUTH_EVENTS.loginFailed);
    });*/
  };
})

;