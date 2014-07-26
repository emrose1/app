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
					$scope.is_backend_ready = true;
				}
			}
			gapi.client.load('booking', 'v1', callback, 'http://localhost:8080/_ah/api');
		};
	}
]);

controllers.controller('AccountController', ['$scope',
	function($scope) {
		$scope.listAccounts = function() {
			gapi.client.booking.calendar.listAccounts().execute(function(resp) {
				console.log('listed accounts');
				console.log(resp);

			});
		};

		var init = function() {
	    	$scope.$on('EventLoaded', function() {
	    		console.log('list accounts');
				$scope.listAccounts();
			});
	    };

	    init();
	}
]);


controllers.controller('EventController', ['$scope', '$route', '$routeParams', '$location', '$filter', '$timeout', '$rootScope',
	function($scope, $route, $routeParams, $location, $filter, $timeout, $rootScope) {
		$scope.$route = $route;
		$scope.$location = $location;
		$scope.$routeParams = $routeParams;

		$scope.eventRepeatType = false;

		// startDate settings


		$rootScope.datePickerOpen = function(id) {

			$timeout(function() {
				$("#"+id).focus();
			});
		};

		$scope.today = function() {
		    $scope.startTime = new Date();
			$scope.finalRepeatEvent = new Date();
		};

		$scope.today();

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

		/*$scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'shortDate'];
  		$scope.format = $scope.formats[0];
*/
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
		};

		$scope.addEvent = function() {
			console.log('add event');

			if ($scope.eventForm.$valid) {
				console.log('its valid');
				var message = {
					'organizer' : $scope.organizer,
					'summary': $scope.summary,
					'calendarId': $scope.eventsCalendar.id,
					'startDateTime' : formatDate($scope.startTime),
					'startDate' : new Date($scope.startTime.getFullYear(), $scope.startTime.getMonth(), $scope.startTime.getDate()).getTime(),
					'duration' : $scope.duration.getHours() + ':' + $scope.duration.getMinutes(),
					'maxAttendees': $scope.maxAttendees,
					'eventCategory' : $scope.eventCategory,
					'eventAttribute' : $scope.eventAttribute,
					'eventRepeatType' : $scope.eventRepeatType,
					'repeatEvent' : $scope.repeatEvent,
					'finalRepeatEvent' : formatDate($scope.finalRepeatEvent)
				};
				console.log(message);
				gapi.client.booking.calendar.addEvent(message).execute(function(resp) {
					console.log(resp);
					$scope.loadCalendar();
				});
			} else {
				console.log($scope.eventForm);
			}
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


		$scope.listAccounts = function() {
			gapi.client.booking.calendar.listAccounts().execute(function(resp) {
				console.log('listed accounts');
				console.log(resp);

				$scope.account = resp.items[0];

				console.log($scope.account);
				$scope.listCalendars();

			});
		};

		$scope.listCalendars = function() {
			console.log('list calendars called');
			var message = {
				'ownerId' : $scope.account.id
			};

			gapi.client.booking.calendar.listCalendars(message).execute(function(resp) {
				console.log(message);
				console.log('list calendars');
				console.log(resp);

			    $scope.calendars = resp.items;
			    $scope.eventsCalendar = $scope.calendars[1];

			    $scope.$apply();

			    $scope.loadCalendar();

			});
		};

		$scope.loadCalendar = function() {
			getDatesOfCurrentWeek();
		    $scope.listEvents();
		    $scope.listEventAttributes();
		    $scope.listEventCategories();
		    $scope.listEventRepeatTypes();
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
			    $scope.eventRepeatTypes = resp.items;
			    $scope.$apply();
			});
		};

		var formatDate = function(date) {
			var dd = date.getDate();
			var mm = date.getMonth()+1;//January is 0!
			var yyyy = date.getFullYear();
			var hours = date.getHours();
			var min = date.getMinutes();

			if(dd<10){dd='0'+dd}
			if(mm<10){mm='0'+mm}
			if(hours<10){hours='0'+hours}
			if(min<10){min='0'+min}

			return  hours + ":" + min + " " + dd + " " + mm + " " + yyyy;
		}

	    var init = function() {
	    	$scope.$on('EventLoaded', function() {
				$scope.listAccounts();
			});
	    };

	    init();
	}
]);
