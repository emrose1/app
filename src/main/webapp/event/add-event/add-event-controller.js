Application.Controllers.controller('addEventCtrl', ['$rootScope', '$scope',
	function($rootScope,$scope) {

	$scope.time = new Date(); // (formatted: 10:20 AM)
	$scope.sharedDate = new Date(); // (formatted: 8/10/14 9:00 PM)

	$scope.button = {
		toggle: false,
		checkbox: {left: false, middle: true, right: false},
		radio: 'left'
	};


	// ------------- ALL DAY SWITCH ------------------------- //

	$scope.myOptions = ["Off", "On"];
    $scope.allDay = "Off";

    $scope.$watch('allDay', function(v){
        console.log('changed', v);
    });


}])
;