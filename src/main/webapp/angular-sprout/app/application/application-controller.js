Application.Controllers.controller( 'application', ['$scope', '$location','$window', 'auth', 'USER_ROLES',
    function ($scope, $location, $window, auth, USER_ROLES) {

    $scope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams){
        /*if ( angular.isDefined( toState.data.pageTitle ) ) {
            $scope.pageTitle = toState.data.pageTitle + ' | bookings' ;
        }*/
    });

    console.log('hello');

    $scope.currentUser = null;
    $scope.userRoles = USER_ROLES;
    $scope.isAuthorized = auth.isAuthorized;

    $scope.$window = $window;

    $window.initialise = function() {
        var apisToLoad = 1;
        var callback = function() {
            if(--apisToLoad === 0) {
                console.log('api loaded');
                $scope.$broadcast('EventLoaded');
                $scope.is_backend_ready = true;
            }
        };
        gapi.client.load('booking', 'v1', callback, 'http://localhost:8080/_ah/api');
    };

}])
;
