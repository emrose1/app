
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
                templateUrl: 'app/partials/users.html'
            })
        .when('/addevent',
            {
                controller: 'EventController',
                templateUrl: 'app/partials/add_event.jsp'
            })
        //Define a route that has a route parameter in it (:customerID)
        .when('/users/:userId',
            {
                //controller: 'UserDetailsController',
                templateUrl: 'app/partials/userDetails.jsp'
            })
        //Define a route that has a route parameter in it (:customerID)
        .when('/calendars',
            {
                controller: 'AccountController',
                templateUrl: 'app/partials/calendar.jsp'
            })
        .when('/event/:eventId',
            {
                controller: 'EventController',
                templateUrl: 'app/partials/event.jsp'
            })
        .otherwise({
            redirectTo: '/calendars'
        });
}]);

