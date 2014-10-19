angular.module('application.account.calendar.event.createEventModal', [
	'application.constants.configuration',
	'checklist-model'
])

/*
@Named("eventRepeatType") String eventRepeatType, x
@Named("eventRepeatInterval") Integer repeatInterval, x
@Named("finalRepeatDate") String finalRepeatDate,
@Named("eventRepeatCount") Integer eventRepeatCount,
@Named("repeatDaysOfWeek") Integer[] repeatDaysOfWeek, x
*/

.controller('RepeatEventModalCtrl',[
	"$rootScope", "$scope", "$modalInstance", "$filter", "eventTime",
  function($rootScope, $scope, $modalInstance, $filter, eventTime) {

	$scope.formData = {
		repeatDaysOfWeek: [new Date(eventTime).getUTCDay()],
		eventRepeatCount: null,
		finalRepeatDate: 0
	};

	$scope.daysOfWeek = [
		{'id': 1, 'value': 'Monday'},
		{'id': 2, 'value': 'Tuesday'},
		{'id': 3, 'value': 'Wednesday'},
		{'id': 4, 'value': 'Thursday'},
		{'id': 5, 'value': 'Friday'},
		{'id': 6, 'value': 'Saturday'},
		{'id': 0, 'value': 'Sunday'}
	];

	$scope.eventRepeatTypes = [
		{ 'id' : 'DAILY', 'name' : 'Daily', 'shortname' : 'Days' },
		{ 'id' : 'WEEKLY', 'name' : 'Weekly', 'shortname' : 'Weeks' },
		{ 'id' : 'MONTHLY', 'name' : 'Monthly', 'shortname' : 'Months' },
		{ 'id' : 'YEARLY', 'name' : 'Yearly', 'shortname' : 'Years' }
	];

	var setSummary = function() {
		var repeatDaysOfWeek = _.map($filter('filter')($scope.daysOfWeek, {checked: true}), 'value');
		$scope.summary = $filter('lowercase')($scope.formData.eventRepeatType) + ' on ' + repeatDaysOfWeek.join(", ");
	}

	$scope.setRepeatDays = function() {
		$scope.formData.repeatDaysOfWeek = _.map($filter('filter')($scope.daysOfWeek, {checked: true}), 'id');
		setSummary();
    };

    var checkTodaysAsRepeatDay = function() {
    	var dayOfWeek = _.find($scope.daysOfWeek, { 'id':  new Date(eventTime).getUTCDay()});
		var i = _.indexOf($scope.daysOfWeek, dayOfWeek);
		$scope.daysOfWeek[i].checked = true;
    };

	var init = function() {
		$scope.formData.repeatInterval = 1;
		$scope.formData.eventRepeatType = $scope.eventRepeatTypes[1].id;

		$scope.startDate = new Date(eventTime);
		$scope.repeatType = $scope.eventRepeatTypes[1].shortname;
		$scope.repeatEndType = "never";

		checkTodaysAsRepeatDay();
		setSummary();
	};

	init();

	$scope.save = function () {
     	$modalInstance.close($scope.formData);
    };

    $scope.cancel = function () {
      $modalInstance.dismiss('cancel');
    };

	$scope.setRepeatType = function(repeatType){
		var repeatItem = _.find($scope.eventRepeatTypes, function(repeat) {
			return repeat.id === repeatType;
		});
		$scope.repeatType = repeatItem.shortname;
	};

	var initFinalDate = function() {
		var initDate = new Date(eventTime);
		var increment = initDate.getMonth() + 2;
		return initDate.setMonth(increment);
	}

	$scope.setRepeatEndType = function(repeatEndType) {
		if (repeatEndType === 'never') {
			$scope.formData.eventRepeatCount = null;
			$scope.formData.finalRepeatDate = null;

		} else if (repeatEndType === 'after') {
			$scope.formData.finalRepeatDate = null;
			$scope.formData.eventRepeatCount = 35;

		} else if (repeatEndType === 'on') {

			$scope.formData.finalRepeatDate = initFinalDate();
			$scope.formData.eventRepeatCount = null;
		}
	};


	/* Date Picker */

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


}]);