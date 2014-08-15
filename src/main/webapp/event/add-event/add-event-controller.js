Application.Controllers.controller('addEventCtrl', ['$rootScope', '$scope',
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

	$scope.openStartDate = function($event) {
		$event.preventDefault();
		$event.stopPropagation();
		$scope.openedStartDate = true;
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