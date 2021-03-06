/*@Named("account_id") Long accountId,
@Named("calendar_id") Long calendarId,
@Named("repeatEvent") String repeatEvent, <- boolean
@Named("eventRepeatType") String eventRepeatType, <- WEEKLY
@Named("eventRepeatInterval") Integer repeatInterval, <- min 1
@Named("finalRepeatDate") String finalRepeatDate, <- null or Long
@Named("eventRepeatCount") Integer eventRepeatCount, <- 0 ++ number
@Named("repeatDaysOfWeek") Integer[] repeatDaysOfWeek, <- [] Array of Strings
@Named("excludeDays") String[] excludeDays, <- []
@Named("title") String title,
@Named("startDateTime") String startDateTime, <- date.getTime()
@Named("endDateTime") String endDateTime, <- date.getTime()
@Named("maxAttendees") Integer maxAttendees,
@Named("instructor_id") Long instructorId,
@Named("eventCategory") Long eventCategory,
@Named("eventAttribute") Long eventAttribute*/
//4749890231992320

angular.module('app.account.schedule.event.createEvent')

.config([
  '$stateProvider', '$validationProvider', function config( $stateProvider, $validationProvider) {

    $validationProvider.showSuccessMessage = false; // or true(default)

    $validationProvider.setErrorHTML(function (msg) {
        return  "<label class=\"control-label has-error\">" + msg + "</label>";
    });

	$stateProvider.state('addevent', {
		url: '/addevent',
		controller: 'AddEventCtrl',
		templateUrl: 'app/schedule/event/event.tpl.html',
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

/*	$stateProvider.state('editevent', {
		url: '/addevent/{id}',
		controller: 'AddEventCtrl',
		templateUrl: 'app/account/schedule/event/event.tpl.html',
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
	});*/
	}
])

.controller('AddEventCtrl', ['$rootScope', '$scope', '$injector', '$state', '$modal', 'Calendar', 'Event', 'sessionService', 'alerts',
	function($rootScope, $scope, $injector, $state, $modal, Calendar, Event, session, alerts) {


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
		$scope.event.repeatEvent = false;
		$scope.event.eventRepeatType = "";
		$scope.event.eventRepeatInterval = 1;
		$scope.event.finalRepeatDate = null;
		$scope.event.eventRepeatCount = 0;
		$scope.event.repeatDaysOfWeek = [];
		$scope.event.excludeDays = [];

		$scope.startDateTime = getNewEventTime.startTime();
		$scope.endDateTime = getNewEventTime.endTime();

    };

    $scope.editEvent = function(event) {
        $scope.event = event;
        $scope.editing = true;
    };


    // ---------- FORM -------------------------- //

    var validateDate = function(date) {
    	if ( Object.prototype.toString.call(date) === "[object Date]" ) {
			if ( isNaN( date.getTime() ) ) {  // d.valueOf() could also work
			return false
			}
			else {
				return true
			}
		}
		else {
			return false
		}
    };

    var saveEvent = function() {
    	$scope.event.startDateTime = $scope.startDateTime.getTime();
		$scope.event.endDateTime = $scope.endDateTime.getTime();


		$scope.event.$save({account_id: session.getAccount(), calendar_id: $scope.event.calendar_id}, function(){
			alerts.setAlert({
	            'alertMessage': "You're session " +  $scope.event.title + " has been added",
	            'alertType': 'alert-success'
	        });
			$state.transitionTo('schedule');
		});

	};

    var $validationProvider = $injector.get('$validation');

    $scope.form = {
        requiredCallback: 'required',
        checkValid: $validationProvider.checkValid,
        submit: function () {
        	if (validateDate($scope.startDateTime) && validateDate($scope.endDateTime)) {
	        	saveEvent();
        	} else {
        		alerts.setAlert({
		            'alertMessage': "Please enter a valid date",
		            'alertType': 'alert-warning'
		        });
        	}
        },
        reset: function () {
        }
    };

// ---- Repeat Event

	$scope.$watch('event.repeatEvent', function(repeatEvent) {
     	var result = repeatEvent ? $scope.openRepeatEventModal() : false;
    });

	$scope.openRepeatEventModal = function () {
		$scope.modalInstance = $modal.open({
			templateUrl: 'app/schedule/event/repeat-event-modal/repeat-event-modal.tpl.html',
			scope: $scope,
			controller: 'RepeatEventModalCtrl',
			resolve: {
				eventTime: function () {
					return $scope.startDateTime;
				}
			}
		});
		$scope.modalInstance.result.then(function (formData) {
			$scope.event = _.assign($scope.event, formData);
		}, function () {
			//reset repeat
		});
	};

// ---- Add attributes
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
						//$scope.reload();

			console.log($scope.event);
			$scope.event = _.assign($scope.event, formData);
			console.log($scope.event);
		}, function () {
			//reset repeat
		});
	};*/
	var init = $state.params.id ? $scope.editEvent() : $scope.newEvent();

}])
;