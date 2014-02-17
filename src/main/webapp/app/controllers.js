'use strict';

/* Controllers */

var gapi = gapi || {};
gapi.client = gapi.client || {};


var controllers = angular.module('controllers', []);

controllers.controller('MainController', ['$scope', '$route', '$routeParams', '$location', '$window',
	function($scope, $route, $routeParams, $location, $window) {
		$scope.$route = $route;
		$scope.$location = $location;
		$scope.$routeParams = $routeParams;

		$scope.$window = $window;

		$window.initialise = function() {

			var apisToLoad = 1;
			var callback = function() {
				if(--apisToLoad === 0) {
					console.log('api loaded');
					$scope.$broadcast('EventLoaded');
				}
			}
			gapi.client.load('booking', 'v1', callback, '/_ah/api');
		};
	}
]);

controllers.controller('EventController', ['$scope', '$route', '$routeParams', '$location', '$filter',
	function($scope, $route, $routeParams, $location, $filter) {
		$scope.$route = $route;
		$scope.$location = $location;
		$scope.$routeParams = $routeParams;

		// startDate settings
		$scope.startTime = new Date();

		var d = new Date();
	    d.setHours(1);
	    d.setMinutes(0);
	    $scope.duration = d;

		$scope.hstep = 1;
		$scope.mstep = 15;

		$scope.options = {
			hstep: [1, 2, 3],
			mstep: [1, 5, 10, 15, 25, 30]
		};

		$scope.ismeridian = true;
		$scope.selectedWeekNumber = 0;
		$scope.calendarDates = {};

		var getDatesOfCurrentWeek = function() {
			// create array of dates for currently selected week
			for (var i = 1; i < 8; i++) {
				var curr = new Date();
				// get Monday (first working day) of current week then add number of days 1-7
				var dayOfWeek = curr.getDate() - curr.getDay() + i + ($scope.selectedWeekNumber * 7);
				// get its Date
				var dateOfWeek = new Date(curr.setDate(dayOfWeek));
				var shortDate = new Date(dateOfWeek.getFullYear(), dateOfWeek.getMonth(), dateOfWeek.getDate()).getTime();
				$scope.calendarDates[i] = {'fullDate' : dateOfWeek, 'shortDate' : shortDate};
			}
		};

		$scope.open = function($event) {
		    $event.preventDefault();
		    $event.stopPropagation();

		    $scope.opened = true;
		 };

		$scope.timetableNavigation = function(val) {
			$scope.selectedWeekNumber = $scope.selectedWeekNumber + val;
			getDatesOfCurrentWeek();
			$scope.listEvents();
		}


		$scope.addEvent = function() {
			var message = {
				'organizer' : $scope.organizer,
			    'summary': $scope.summary,
			    'calendarId': $scope.eventsCalendar.id,
			    'startDateTime' : $scope.startTime.toUTCString(),
			    'startDate' : new Date($scope.startTime.getFullYear(), $scope.startTime.getMonth(), $scope.startTime.getDate()).getTime(),
			    'duration' : $scope.duration.getHours() + ':' + $scope.duration.getMinutes(),
			    'maxAttendees': $scope.maxAttendees,
			    'categoryId' : "",
			    'attributeId' : "",
			    'repeatEvent' : $scope.repeatEvent,
				'eventRepeatType' : $scope.eventRepeatType,
				'finalRepeatEvent' : ""
			};
			console.log(message);
			gapi.client.booking.calendar.addEvent(message).execute(function(resp) {
				console.log(resp);
				init();
			});
		};

		$scope.addCalendar = function() {
			var message = {
				'description': $scope.description
			};
			console.log(message);
			gapi.client.booking.calendar.addCalendar(message).execute(function(resp) {
				console.log(resp);
			});
		};

		$scope.listCalendars = function() {
			gapi.client.booking.calendar.listCalendars().execute(function(resp) {
				console.log('list calendars');
				console.log(resp);

				$scope.listOwners();
			    $scope.calendars = resp.items;
			    $scope.eventsCalendar = $scope.calendars[1];

			    $scope.$apply();

			    getDatesOfCurrentWeek();
			    $scope.listEvents();
			    $scope.listEventAttributes();
			    $scope.listEventCategories();
			    $scope.listEventRepeatTypes();

			});
		};

		$scope.listOwners = function() {
			gapi.client.booking.calendar.listOwners().execute(function(resp) {
				console.log(resp);
			    /*$scope.events = resp.items;
			    $scope.$apply();*/
			});
		};

		$scope.listEvents = function() {
			var message = {
				'calendarId': $scope.eventsCalendar.id
			};
			console.log(message);
			gapi.client.booking.calendar.listEvents(message).execute(function(resp) {
				console.log(resp);
			    $scope.events = resp.items;
			    $scope.$apply();
			});
		};

		$scope.listEventAttributes = function() {
			var message = {
				'calendarId': $scope.eventsCalendar.id
			};
			gapi.client.booking.calendar.listEventAttributes(message).execute(function(resp) {
				console.log('Event Attributes: ');
				console.log(resp);
			    $scope.eventAttributes = resp.items;
			    $scope.$apply();
			});
		};

		$scope.listEventCategories = function() {
			var message = {
				'calendarId': $scope.eventsCalendar.id
			};
			gapi.client.booking.calendar.listEventCategories(message).execute(function(resp) {
				console.log('Event Categories: ');
				console.log(resp);
			    $scope.eventCategories = resp.items;
			    $scope.$apply();
			});
		};

		$scope.listEventRepeatTypes = function() {
			var message = {
				'calendarId' : $scope.eventsCalendar.id
			};
			gapi.client.booking.calendar.listEventRepeatTypes(message).execute(function(resp) {
				console.log('Event Repeat Type: ');
				console.log(resp);
			    $scope.eventRepeatType = resp.items;
			    $scope.$apply();
			});
		};

	    var init = function() {
	    	$scope.$on('EventLoaded', function() {
	    		console.log('list calendars');
				$scope.listCalendars();
			});
	    };

	    init();
	}
]);