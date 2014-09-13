/*@Named("account_id") Long accountId,
@Named("calendar_id") Long calendarId,
@Named("repeatEvent") String repeatEvent,
@Named("eventRepeatType") String eventRepeatType,
@Named("eventRepeatInterval") Integer repeatInterval,
@Named("finalRepeatDate") String finalRepeatDate,
@Named("eventRepeatCount") Integer eventRepeatCount,
@Named("repeatDaysOfWeek") Integer[] repeatDaysOfWeek,
@Named("excludeDays") String[] excludeDays,
@Named("title") String title,
@Named("startDateTime") String startDateTime,
@Named("endDateTime") String endDateTime,
@Named("maxAttendees") Integer maxAttendees,
@Named("instructor_id") Long instructorId,
@Named("eventCategory") Long eventCategory,
@Named("eventAttribute") Long eventAttribute*/


angular.module('application.account.calendar.event.createEvent', [
	'application.account.calendar.event.service',
	'application.account.calendar.event.createEventModal'
])

.config([
  '$stateProvider',
  function config( $stateProvider ) {
	$stateProvider
		.state('addevent', {
			url: '/addevent',

			views: {
				'accounts': {
					templateUrl: 'app/account/account-dropdown.tpl.html',
					controller: 'AccountCtrl'
				},
				'content': {
					templateUrl: 'app/account/calendar/event/create-event/create-event.tpl.html',
					controller: 'addEventCtrl'
				}
			}
		});
	}
])

.controller('addEventCtrl', ['$rootScope', '$scope', '$modal', 'Calendar', 'Event', 'sessionService',
	function($rootScope, $scope, $modal, Calendar, Event, session) {

	// ------------- DATE PICKER ------------------------- //

	$scope.clear = function () {
		$scope.startDateTime = null;
		$scope.endDateTime = null;
	};

	$scope.openDatePicker = function($event) {
		$event.preventDefault();
		$event.stopPropagation();
		$scope.openedDatePicker = true;
	};

	$scope.dateOptions = {
		formatYear: 'yy',
		startingDay: 1
	};

	$scope.formats = ['dd MMMM yyyy', 'dd/MM/yyyy', 'dd.MM.yyyy', 'shortDate'];
	$scope.format = $scope.formats[0];


	// ------------- TIME PICKER ------------------------- //

	$scope.hstep = 1;
	$scope.mstep = 15;
	$scope.ismeridian = true;

	// ------------- EVENT ------------------------- //

	$scope.newEvent = function() {
        $scope.event = new Event();
		$scope.event.repeatEvent = "false";
		$scope.event.eventRepeatType = "";
		$scope.event.eventRepeatInterval = 0;
		$scope.event.finalRepeatDate = 0;
		$scope.event.eventRepeatCount = 0;
		$scope.event.repeatDaysOfWeek = [];
		$scope.event.excludeDays = [];

		$scope.startDateTime = new Date().setMinutes(0);
		$scope.endDateTime = new Date().setMinutes(0);
    };

    $scope.editEvent = function(event) {
        $scope.event = event;
        $scope.editing = true;
    };

	$scope.saveEvent = function(event) {
		if (event.id) {
			event.$update({calendar_id: session.getCalendar()}, function(){
				//getEvents();
			});
		} else {
			event.startDateTime = $scope.startDateTime.getTime();
			event.endDateTime = $scope.endDateTime.getTime();
			console.log(event.startDateTime + " " + event.endDateTime);
			event.$save({account_id: session.getAccount(), calendar_id: event.calendar_id}, function(){
				//getEvents();
			});
		}
		$scope.newEvent();
	};

	$scope.openRepeatEventModal = function (startDateTime) {
		$scope.modalInstance = $modal.open({
			templateUrl: 'app/account/calendar/event/create-event-modal/create-event-modal.tpl.html',
			scope: $scope,
			controller: 'RepeatEventModalCtrl',
			resolve: {
				eventTime: function () {
					return startDateTime;
				}
			}
		});
		$scope.modalInstance.result.then(function (formData) {
			console.log($scope.event);
			$scope.event = _.assign($scope.event, formData);
			console.log($scope.event);
		}, function () {
			//reset repeat
		});
	};

	$scope.newEvent();

    $rootScope.$on('accountLoaded', function(event, args) {

	});

}])
;