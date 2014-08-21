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


angular.module('application.account.calendar.event.createEvent', [])

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
                'calendars': {
                    templateUrl: 'app/account/calendar/calendar-dropdown.tpl.html',
                    controller: 'calendarCtrl'
                },
                'content': {
					templateUrl: 'app/account/calendar/event/create-event/create-event.tpl.html',
					controller: 'addEventCtrl'
                }
            }
        });
    }
])

.controller('addEventCtrl', ['$rootScope', '$scope',
	function($rootScope,$scope) {

	// ------------- DATE PICKER ------------------------- //

	$scope.today = function() {
		$scope.startDate = new Date();
	};
	$scope.today();

	$scope.clear = function () {
		$scope.startDate = null;
		$scope.startTime = null;
		$scope.endTime = null;
	};

	// Disable weekend selection
	$scope.disabled = function(date, mode) {
		return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
	};

	$scope.toggleMin = function() {
		$scope.minDate = $scope.minDate ? null : new Date();
	};
	$scope.toggleMin();

	$scope.openDatePicker = function($event) {
		$event.preventDefault();
		$event.stopPropagation();
		$scope.openedDatePicker = true;
	};

	$scope.dateOptions = {
		formatYear: 'yy',
		startingDay: 1
	};

	$scope.initDate = new Date('2016-15-20');
	$scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
	$scope.format = $scope.formats[0];


	// ------------- TIME PICKER ------------------------- //

	$scope.startTime = new Date();
	$scope.endTime = new Date();

	$scope.hstep = 1;
	$scope.mstep = 15;

	$scope.options = {
	hstep: [1, 2, 3],
	mstep: [1, 5, 10, 15, 25, 30]
	};

	$scope.ismeridian = true;
	$scope.toggleMode = function() {
		$scope.ismeridian = ! $scope.ismeridian;
	};

	$scope.update = function() {
		var d = new Date();
		d.setHours( 14 );
		d.setMinutes( 0 );
		$scope.mytime = d;
	};

	$scope.changed = function () {
		console.log('StartTime changed to: ' + $scope.startTime);
		console.log('EndTime changed to: ' + $scope.endTime);
	};



	// ------------- ALL DAY SWITCH ------------------------- //

	$scope.myOptions = ["Off", "On"];
    $scope.allDay = "Off";

    $scope.$watch('allDay', function(v){
        console.log('changed', v);
    });


}])
;