var bookings = bookings || {};

bookings.controller = {

	EventController : function ($scope) {
		$scope.addEvent = function() {
			message = {
				'organizer' : 	$scope.organizer,
			    'summary': 		$scope.summary, 
			    'maxAttendees': $scope.maxAttendees,
			    'startDate' : 	$scope.startDate,
			    'startTime' : 	$scope.startTime,
			    'endDate' : 	$scope.endDate,
			    'endTime' : 	$scope.endTime
			};
		};
		
		gapi.client.booking. addEvent(message).execute(function(resp) {
			console.log(resp);
		  if (!resp.code) {
		    bookings.print(resp);
		  }
		});
	}
};