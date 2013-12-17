
var bookings = bookings || {};

/**
 * Client ID of the application (from the APIs Console).
 * @type {string}
 */
bookings.CLIENT_ID =
    '389068630524-9rht9ebi7s7jpqos14a4im98iml6aet9.apps.googleusercontent.com';

/**
 * Scopes used by the application.
 * @type {string}
 */
bookings.SCOPES =
    'https://www.googleapis.com/auth/userinfo.email';

bookings.controller = {

	EventController : function ($scope, $window) {
		$scope.$window = $window;
		
		$scope.load_guestbook_lib = function() {
			gapi.client.load('booking', 'v1', function() {
				console.log('api loaded?');
				$scope.listCalendars();
				$scope.listEvents();
				
			}, '/_ah/api');
		};
	
		$scope.addEvent = function() {
			message = {
				'organizer' : 	$scope.organizer,
			    'summary': 		$scope.summary, 
			    'calendarId':	$scope.eventsCalendar.id,
			    'maxAttendees': $scope.maxAttendees,
			    'startDate' : 	$scope.startDate.toString().substr(0,15),
			    'startTime' : 	$scope.startTime.substr(0,5),
			    'endDate' : 	$scope.endDate.toString().substr(0,15),
			    'endTime' : 	$scope.endTime.substr(0,5)
			};
			console.log(message);
			console.log($scope.eventsCalendar);
			gapi.client.booking.calendar.addEvent(message).execute(function(resp) {
				console.log(resp);
			});
		};
		
		$scope.addCalendar = function() {
			message = {
				'description': 	$scope.description
			};
			console.log(message);
			gapi.client.booking.calendar.addCalendar(message).execute(function(resp) {
				console.log(resp);
			});
		};
		
		$scope.listCalendars = function() {
			gapi.client.booking.calendar.listCalendars().execute(function(resp) {
				console.log(resp);
			    $scope.calendars = resp.items;
			    $scope.$apply();
			});
		};
		
		$scope.listEvents = function() {
			message = {
				'calendarId': 	$scope.eventsCalendar.id
			};
			console.log(message);
			gapi.client.booking.calendar.listEvents(message).execute(function(resp) {
				console.log(resp);
			    $scope.events = resp.items;
			    $scope.$apply();
			});
		};
		
		$scope.datepicker = {date: new Date("2012-09-01")};
		$scope.timepicker = {};
		
		$window.init= function() {
			$scope.$apply($scope.load_guestbook_lib);
			  
			  console.log('init called');
			  
		};
		
		
	}
}; 

/**
 * Initializes the application.
 * @param {string} apiRoot Root of the API's path.
*/
bookings.init = function(apiRoot) {
  // Loads the OAuth and helloworld APIs asynchronously, and triggers login
  // when they have completed.
  var apisToLoad;
  console.log('booking');

  apisToLoad = 1; 
  $scope.$apply($scope.load_guestbook_lib);

}; 
