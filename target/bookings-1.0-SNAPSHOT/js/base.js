


/** samples namespace for DevRel sample code. */
var bookings = bookings || {};



/**
 * Client ID of the application (from the APIs Console).
 * @type {string}
 */
bookings.CLIENT_ID =
    '389068630524-9rht9ebi7s7jpqos14a4im98iml6aet9.apps.googleusercontent.com';

/**
 * Scopes used by the application.
 * @type {string}
 */
bookings.SCOPES =
    'https://www.googleapis.com/auth/userinfo.email';

/**
 * Whether or not the user is signed in.
 * @type {boolean}
 */
bookings.signedIn = false;

/**
 * Loads the application UI after the user has completed auth.
 */
bookings.userAuthed = function() {
  var request = gapi.client.oauth2.userinfo.get().execute(function(resp) {
    if (!resp.code) {
      bookings.signedIn = true;
      document.getElementById('signinButton').innerHTML = 'Sign out';
      document.getElementById('authedGreeting').disabled = false;
    }
  });
};

/**
 * Handles the auth flow, with the given value for immediate mode.
 * @param {boolean} mode Whether or not to use immediate mode.
 * @param {Function} callback Callback to call on completion.
 */
bookings.signin = function(mode, callback) {
  gapi.auth.authorize({client_id: bookings.CLIENT_ID,
      scope: bookings.SCOPES, immediate: mode},
      callback);
};


/**
 * Prints a greeting to the greeting log.
 * param {Object} greeting Greeting to print.
 */
bookings.print = function(greeting) {
  var element = document.createElement('div');
  element.classList.add('row');
  element.innerHTML = greeting.summary;
  $('#outputLog').append(element);
};

/**
 * Lists events via the API.
 */
bookings.listEvent = function() {
  gapi.client.booking.listEvents().execute(
      function(resp) {
        if (!resp.code) {
          resp.items = resp.items || [];
          for (var i = 0; i < resp.items.length; i++) {
            booking.print(resp.items[i]);
          }
        }
      }
  );
};

/**
 * Creates an Event
 * @param {string} summary of event.
 */


/**
 * Creates a Calendar
 * @param {string} summary of event.
 */
bookings.addCalendar = function(
    description) {
	console.log(description);
  gapi.client.booking.calendar.add({
      //'name': name,
      'description': description
    }).execute(function(resp) {
    	console.log(resp);
      if (!resp.code) {
        bookings.print(resp);
      }
    });
};


/**
 * Enables the button callbacks in the UI.
 */

	/*$('#addEvent').submit(function(e) {
		e.preventDefault();
		bookings.addEvent(	
			$('#organizer').val(),
			$('#summary').val(),
			$('#maxAttendees').val(),
			$('#startDate').val(),
			$('#startTime').val(),
			$('#endDate').val(),
			$('#endTime').val()
		);
	});*/
  
	$('#addCalendar').click(function() {
		bookings.addCalendar(
			//document.getElementById('name').value);
			$('#description').val()
		);
	});
	
	
bookings.controller = {

		EventController : function ($scope, $window) {
			$scope.$window = $window;
			$scope.addEvent = function() {
				message = {
					'organizer' : 	$scope.organizer,
				    'summary': 		$scope.summary, 
				    'maxAttendees': $scope.maxAttendees,
				    'startDate' : 	$scope.startDate.toString().substr(0,15),
				    'startTime' : 	$scope.startTime.substr(0,5),
				    'endDate' : 	$scope.endDate.toString().substr(0,15),
				    'endTime' : 	$scope.endTime.substr(0,5)
				};
				console.log(message);
				gapi.client.booking.calendar.addEvent(message).execute(function(resp) {
					console.log(resp);
				  if (!resp.code) {
				    bookings.print(resp);
				  }
				});
			};
			
			$scope.listCalendars = function() {
				gapi.client.booking.calendar.listCalendars().execute(function(resp) {
					console.log(resp);
				    $scope.calendars = resp.items;
				    $scope.$apply();
				});
			};
			
			$scope.listEvents = function() {
				gapi.client.booking.calendar.listEvents().execute(function(resp) {
					console.log(resp);
				    $scope.events = resp.items;
				    $scope.$apply();
				});
			};
				
			$scope.load_guestbook_lib = function() {
				gapi.client.load('booking', 'v1', function() {
			    /*$scope.is_backend_ready = true;
			    $scope.list();*/
			  }, '/_ah/api');
			};
			
			 $scope.datepicker = {date: new Date("2012-09-01")};
			 $scope.timepicker = {};
			
			$window.init= function() {
			  $scope.$apply($scope.load_guestbook_lib);
			  
			};
		
			/*gapi.client.booking.addEvent(message).execute(function(resp) {
			console.log(resp);
		  if (!resp.code) {
		    bookings.print(resp);
		  }
		});*/
	}
}; 







/**
 * Initializes the application.
 * @param {string} apiRoot Root of the API's path.
*/
bookings.init = function(apiRoot) {
  // Loads the OAuth and helloworld APIs asynchronously, and triggers login
  // when they have completed.
  var apisToLoad;
  var callback = function() {
    if (--apisToLoad == 0) {

    }
  };

  apisToLoad = 1; // must match number of calls to gapi.client.load()
  /*gapi.client.load('helloworld', 'v1', callback, apiRoot);
  gapi.client.load('booking', 'v1', callback, apiRoot);
  gapi.client.load('oauth2', 'v2', callback);*/
  
  $scope.$apply($scope.load_guestbook_lib);
}; 
