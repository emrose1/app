
var app = angular.module('bookingsApp', [
    'controllers',
    'ngRoute',
    'ui.bootstrap',
    'ui.bootstrap.datepicker'
]);

//This configures the routes and associates each route with a view and a controller
app.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider
        .when('/users',
            {
                //controller: 'UsersController',
                templateUrl: 'partials/users.html'
            })
        .when('/addevent',
            {
                controller: 'EventController',
                templateUrl: 'partials/add_event.html'
            })
        //Define a route that has a route parameter in it (:customerID)
        .when('/users/:userId',
            {
                //controller: 'UserDetailsController',
                templateUrl: 'partials/userDetails.html'
            })
        //Define a route that has a route parameter in it (:customerID)
        .when('/calendars',
            {
                //controller: 'CalendarController',
                templateUrl: 'partials/calendar.html'
            })
        .when('/event/:eventId',
            {
                controller: 'EventController',
                templateUrl: 'partials/event.html'
            })
        .otherwise({
            redirectTo: '/calendars'
        });
}]);

