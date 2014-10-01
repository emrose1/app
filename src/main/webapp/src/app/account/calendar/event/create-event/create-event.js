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
//4749890231992320

angular.module('application.account.calendar.event.createEvent', [
	'application.account.calendar.event.createEventModal'
])

.config([
  '$stateProvider',
  function config( $stateProvider ) {
	$stateProvider
		.state('addevent', {
			url: '/addevent',
			controller: 'AddEventCtrl',
			templateUrl: 'app/account/calendar/event/create-event/create-event.tpl.html',
			resolve: {

				calendars: function($q, Calendar, sessionService) {
                	var deferred = $q.defer();
                	Calendar.query({'account_id' : sessionService.getAccount()}, function(data) {
						deferred.resolve(data);
					});
					return deferred.promise;
                },
                eventCategories: function($q, EventCategory, sessionService) {
                	var deferred = $q.defer();
                	EventCategory.query({'account_id' : sessionService.getAccount()}, function(data) {
						deferred.resolve(data);
					});
					return deferred.promise;
                },

                eventAttributes: function($q, EventAttribute, sessionService) {
                	var deferred = $q.defer();
                	EventAttribute.query({'account_id' : sessionService.getAccount()}, function(data) {
						deferred.resolve(data);
					});
					return deferred.promise;
                },

                instructors: function($q, Person, sessionService) {
                	var deferred = $q.defer();
                	Person.query({'account_id' : sessionService.getAccount()}, function(data) {
						deferred.resolve(data);
					});
					return deferred.promise;
                }

            }
		});
	}
])

.controller('AddEventCtrl', ['$rootScope', '$scope', '$state', '$modal', 'Calendar', 'Event', 'sessionService',
	function($rootScope, $scope, $state, $modal, Calendar, Event, session) {


	// ------------- DATE PICKER ------------------------- //

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

	// ------------- EVENT OPTIONS ------------------------- //

	$scope.reload = function() {
        $state.reload();
    };

    $scope.$state = $state;
    $scope.$watch('$state.$current.locals.globals.eventCategories', function (eventCategories) {
        $scope.eventCategorys = eventCategories;
    });

    $scope.$watch('$state.$current.locals.globals.eventAttributes', function (eventAttributes) {
        $scope.eventAttributes = eventAttributes;
    });

    $scope.$watch('$state.$current.locals.globals.instructors', function (instructors) {
        $scope.instructors = instructors;
    });

    $scope.$watch('$state.$current.locals.globals.calendars', function (calendars) {
        $scope.calendars = calendars;
    });

	// ------------- EVENT ------------------------- //

	var getNewEventTime = (function() {
		var currentHour = new Date(new Date().setMinutes(0));

		var getNextHour = function() {
			return new Date(currentHour.setHours(new Date(currentHour).getHours() + 1));
		};

		return {
			startTime : getNextHour,
			endTime : getNextHour
		}
	})();

	$scope.newEvent = function() {
        $scope.event = new Event();
		$scope.event.repeatEvent = "false";
		$scope.event.eventRepeatType = "";
		$scope.event.eventRepeatInterval = 0;
		$scope.event.finalRepeatDate = 0;
		$scope.event.eventRepeatCount = 0;
		$scope.event.repeatDaysOfWeek = [];
		$scope.event.excludeDays = [];


		$scope.startDateTime = getNewEventTime.startTime();
		$scope.endDateTime = getNewEventTime.endTime();

		$scope.showValidationErrors = false;
    };

    $scope.$watch('startDateTime', function () {
        console.log($scope.startDateTime);
        console.log($scope.endDateTime);
    });

    $scope.editEvent = function(event) {
        $scope.event = event;
        $scope.editing = true;
    };

	$scope.saveEvent = function(isValid) {

/*		$scope.event.startDateTime = $scope.startDateTime.getTime();
		$scope.event.endDateTime = $scope.endDateTime.getTime();

*/
		var date = new Date($scope.startDateTime);
		console.log(date);

		//if(isValid) {
			$scope.event.startDateTime = $scope.startDateTime.getTime();
			$scope.event.endDateTime = $scope.endDateTime.getTime();

			console.log($scope.event);


			$scope.event.$save({account_id: session.getAccount(), calendar_id: $scope.event.calendar_id}, function(){
				$scope.reload();
			});
					//}$scope.newEvent();
/*		} else {
			$scope.showValidationErrors = true;
		}
*/	};

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
/*
	$scope.openAddCategoryModal = function (startDateTime) {
		$scope.modalInstance = $modal.open({
			templateUrl: 'app/account/calendar/event-category/event-category.tpl.html',
			scope: $scope,
			controller: 'EventCategory',
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
	};*/

	$scope.newEvent();



}])
;