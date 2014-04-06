angular.module( 'bookings.login', [
  'ui.state',
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
        console.log('login success');
        $rootScope.loginSuccessMessage = true;
    };
    var loginFailed = function () {
        console.log('login failed');
        $rootScope.loginErrorMessage = true;
    };
})

/**
 * And of course we define a controller for our route.
 */
.controller( 'LoginCtrl', function LoginController($scope, $rootScope, AUTH_EVENTS, Session, $q) {
  $scope.credentials = {
    username: '',
    password: ''
  };

  $scope.loginErrorMessage = 'false';
  $scope.loginSuccessMessage = 'false';



        var init = function(credentials){

            var one = $q.defer();

            one.promise.then(function (data) {
                console.log(data);

                $rootScope.$broadcast(AUTH_EVENTS.loginSuccess);
            }, function (reason) {
                console.log('Failed: ' + reason);
                $rootScope.$broadcast(AUTH_EVENTS.loginFailed);
            });

            var request = gapi.client.booking.calendar.authUserSession({
                username: credentials.username,
                password: credentials.password
            });

            request.execute(function (resp) {
                $scope.$apply(function() {
                    if (resp && resp.user) {
                        Session.create(resp.user.id, resp.user.userType.type);
                        console.log('yeah');
                        one.resolve(resp);
                    } else {
                        one.reject('error');
                    }
                });

            });

            return one.promise;
        };

        $scope.login = function (credentials) {
            init(credentials);



            /*.then(function () {
             $rootScope.$broadcast(AUTH_EVENTS.loginSuccess);
             }, function () {
             $rootScope.$broadcast(AUTH_EVENTS.loginFailed);
             });*/
        };
})

;