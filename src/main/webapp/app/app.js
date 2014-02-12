
var app = angular.module('bookingsApp', [
    'controllers',
    'ngRoute',
    'ui.bootstrap'
]);

//This configures the routes and associates each route with a view and a controller
app.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider
        .when('/users',
            {
                //controller: 'UsersController',
                templateUrl: 'app/partials/users.html'
            })
        //Define a route that has a route parameter in it (:customerID)
        .when('/users/:userId',
            {
                //controller: 'UserDetailsController',
                templateUrl: 'app/partials/userDetails.html'
            })
        //Define a route that has a route parameter in it (:customerID)
        .when('/calendars',
            {
                //controller: 'CalendarController',
                templateUrl: 'app/partials/calendar.html'
            })
        .when('/event/:eventId',
            {
                controller: 'EventController',
                templateUrl: 'app/partials/event.html'
            })
        .otherwise({
            redirectTo: '/calendars'
        });
}]);

