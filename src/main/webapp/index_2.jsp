<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html>
<html ng-app="studio-booking">
<head>
    <title>Bookings</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
	<!-- Optional theme -->

	<link rel="stylesheet" href="stylesheets/docs.css">
	<link rel="stylesheet" href="css/bootstrap-timepicker.css">
	<link rel="stylesheet" href="stylesheets/bootstrap-datepicker.css">
	<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
</head>
<body>
<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container">
            <a href="logout">logout</a>
        </div>
    </div>
</div>
<div class="container" data-ng-controller="bookings.controller.EventController" data-ng-init="$window = $service('$window');">

    <div id="outputLog"></div>

        <h2>List Event</h2>

		<form ng-submit="listEvents()">
			<button type="submit" class="btn btn-small">Submit</button>
	    </form>
	    <p>I have {{events.length}} events. They are:</p>
        <ul ng-repeat="event in events">
        	<li>{{event.summary}}  {{event.id}}</li>
        </ul>



    <h2>List Calendar</h2>

    <form ng-submit="listCalendars()">
		<button type="submit" class="btn btn-small">Submit</button>
    </form>
    <p>I have {{calendars.length}} calendar. They are:</p>
        <ul ng-repeat="calendar in calendars">
        	<li>{{calendar.description}}  {{calendar.id}}</li>
        </ul>

    <form role="form" id="addEvent" ng-submit="addEvent()">
        <h2>Add Event</h2>
        <div class="form-group">
        	<label class="col-lg-2 control-label" for="organizer">Organizer </label>
        	<input data-ng-model="organizer" class="form-control" id="organizer" />
        </div>
        <div class="form-group">
        	<label class="col-lg-2 control-label" for="summary">Summary </label>
        	<input data-ng-model="summary" class="form-control" id="summary" />
        </div>
        <div class="form-group">
        	<label class="col-lg-2 control-label" for="calendar">Calendar </label>
        	<input data-ng-model="summary" class="form-control" id="calendar" />
        	<select ng-model="eventsCalendar" ng-options="c.description for c in calendars"></select>
        </div>
        <div class="form-group">
        	<label class="col-lg-2 control-label" for="maxAttendees">Max Attendees </label>
        	<input data-ng-model="maxAttendees" class="form-control" id="maxAttendees" />
        </div>
        <div class="form-group">
        	<label class="col-lg-2 control-label" for="startDate">Start Date </label>
        	<input ng-model="startDate" data-date-format="dd/mm/yyyy" bs-datepicker class="form-control" id="startDate" />
        </div>
        <div class="form-group">
        	<label class="col-lg-2 control-label" for="startTime">Start Time </label>
        	<input data-ng-model="startTime" bs-timepicker  class="form-control" id="startTime" />
        </div>
        <div class="form-group">
        	<label class="col-lg-2 control-label" for="endDate">End Date </label>
        	<input data-ng-model="endDate" bs-datepicker data-date-format="dd/mm/yyyy" class="form-control" id="endDate" />
        </div>
        <div class="form-group">
        	<label class="col-lg-2 control-label" for="endTime">End Time </label>
        	<input data-ng-model="endTime" bs-timepicker class="form-control" id="endTime" value="00:00" />
        </div>
        <button type="submit" class="btn btn-small">Submit</button>
	</form>
	
	<form role="form" id="addEvent" ng-submit="addCalendar()">
        <h2>Add Calendar</h2>
        <div><span style="width: 90px; display: inline-block;">Owner name: </span><input id="name" /></div>
        <div><span style="width: 90px; display: inline-block;">Calendar description: </span>
        <input data-ng-model="description" id="description" /></div>
        <button type="submit" class="btn btn-small">Submit</button>
    </form>    
	

</div>


    <script src="//ajax.googleapis.com/ajax/libs/angularjs/1.0.6/angular.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.1/js/bootstrap.min.js"></script>
    <script src="js/angular-strap.min.js"></script>
    <script src="js/bootstrap-datepicker.js"></script>
    <script src="js/bootstrap-timepicker.js"></script>
    <script src="js/holder.js"></script>



    <script src="js/base.js"></script>


    <script type="text/javascript">

    	$('#startDate').datepicker({
    	      format:"DD, d MM yyyy"
    	});
    	$('#endDate').datepicker({format: 'DD, d MM yyyy'});
    	$('#startTime').timepicker({
    		minuteStep: 5,
            showInputs: false,
            disableFocus: true
    	});
    	$('#endTime').timepicker({
            minuteStep: 5,
            showInputs: false,
            disableFocus: true,
            showMeridian: false,
            defaultTime: 'value'
    	});

    	// Create a new module
    	var app = angular.module('studio-booking', ['$strap.directives']);

    	app.value('$strapConfig', {
    		datepicker: {
   		    format: 'M d, yyyy'
   		  }
   		});


    	//app.controller('bookings.controller.EventController', function($scope, $window, $location) {
    	  //  $scope.datepicker = {date: new Date("2012-09-01T00:00:00.000Z")};
    	//});

        function init() {
        	window.init();


        }
    </script>

    <script src="https://apis.google.com/js/client.js?onload=init"></script>
</body>
</html>
