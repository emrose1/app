<%@ page contentType="text/html;charset=UTF-8" language="java" %>
event

controller: {{name}}<br />
Event Id: {{params.eventId}}<br />

<div id="content-header">
	<h1>Add Event</h1>
</div> <!-- #content-header -->


<div id="content-container">

	<div class="portlet-content">
		<form role="form" id="addEvent" name="eventForm" ng-submit="addEvent()">


			<div class="row">
				<div class="col-sm-3">
		        	<h4>Start Date </h4>
		        	<div class="input-group date ui-datepicker">
			            <input  type="text" class="form-control" name="startTime" id="startTime" datepicker-popup="dd-MM-yyyy"
				            	ng-model="startTime"  datepicker-options="dateOptions" />
		 				<span class="input-group-addon" ng-click="datePickerOpen('startTime')">
		 					<i class="fa fa-calendar"></i>
		 				</span>
			        </div>
			   	</div>

			    <div class="col-sm-3">
					<h4>Start Time </h4>
					<div class="col-sm-2 input-group bootstrap-timepicker">
						<div ng-model="startTime">
					        <timepicker hour-step="hstep" minute-step="mstep" show-meridian="false"></timepicker>
					  	</div>
					</div>
				</div>

				<div class="col-sm-3">
					<h4>Duration </h4>
					<div class="col-sm-2 input-group bootstrap-timepicker">
						<div ng-model="duration">
					        <timepicker hour-step="hstep" minute-step="mstep" show-meridian="false"></timepicker>
					  	</div>
					</div>
				</div>
			</div>



			<div class="row">
				<div class="col-sm-3">
					<h4>Teacher</h4>
					<input type="text" data-ng-model="organizer" class="form-control" id="organizer" />
				</div>

				<div class="col-sm-3">
					<h4>Lesson</h4>
					<input type="text" data-ng-model="summary" class="form-control" id="summary" />
				</div>

				<div class="col-sm-3">
					<h4>Max Attendees</h4>
					<input type="text" data-ng-model="maxAttendees" class="form-control" id="maxAttendees" />
				</div>
			</div>



		    </div> <!-- /.row -->

		    <div class="row">
		    	<div class="col-sm-6">
				    <div class="form-group">
						<label for="select-input">Event Categories</label>
						<select ng-model="eventCategory" id="select-input" class="form-control">
							<option ng-repeat="eventCategory in eventCategories">{{eventCategory.name}}</option>
						</select>
					</div>
				</div>

				<div class="col-sm-6">
				    <div class="form-group">
						<label for="select-input">Event Attributes</label>
						<select ng-model="eventAttribute" id="select-input" class="form-control">
							<option ng-repeat="eventAttribute in eventAttributes">{{eventAttribute.name}}</option>
						</select>
					</div>
				</div>
			</div>

		    <div class="row">
		    	<div class="col-sm-4">
		        	<label for="repeatEvent">Repeat Event </label>

		        	<div class="checkbox">
						<label>
						    <input value="false" type="checkbox" name="repeatEvent" ng-model="repeatEvent" class="" checked>
						    Repeat Event
						</label>
					</div>
			   	</div>

			   	<div class="col-sm-4">
				    <div class="form-group">
						<label for="select-input">Repeat Event Type</label>
						<select id="select-input" ng-model="eventRepeatType" class="form-control">
						<option ng-repeat="ert in eventRepeatTypes">{{ert}}</option>
						</select>
					</div>
				</div>

				 <div class="col-sm-4">
					<label for="finalDate">Final Repeat Date </label>
						<div class="input-group date ui-datepicker">
				            <input  type="text" class="form-control" name="finalRepeatEvent" id="finalRepeatEvent"
				            	datepicker-popup="dd-MM-yyyy" ng-model="finalRepeatEvent"  datepicker-options="dateOptions"/>
				            <span class="input-group-addon" ng-click="datePickerOpen('finalRepeatEvent')">
				            	<i class="fa fa-calendar"></i>
				            </span>

				        </div>
				 </div>
		    </div>

		    <div class="row">
		        <div class="col-sm-12">
					<button type="submit" class="btn btn-primary">Submit</button>
				</div>
	        </div> <!-- /.row -->

	    </form>


	<div class="row">
		<div class="col-sm-12">
			<h4>Add Event</h4>
			<select ng-change="loadCalendar()" id="calendar" class="form-control" ng-model="eventsCalendar" ng-options="c.description for c in calendars">
				<option>Loading</option>
			</select>
		</div>
	</div>
		<h2>List Event</h2>

		<form ng-submit="listEvents()">
			<button type="submit" class="btn btn-small">Submit</button>
	    </form>
	    <p>I have {{events.length}} events. They are:</p>
	    <!-- <ul ng-repeat="event in events">
	    	<li>{{event.summary}}  {{event.id}} {{event.startDate}} {{event.startDateTime}}</li>
	    </ul> -->


			<h2>List Calendar</h2>

	    <form ng-submit="listCalendars()">
			<button type="submit" class="btn btn-small">Submit</button>
	    </form>
	    <p>I have {{calendars.length}} calendar. They are:</p>
	    <ul ng-repeat="calendar in calendars">
	    	<li>{{calendar.description}}  {{calendar.id}}</li>
	    </ul>

	</div>


	<div class="portlet-header">
		<h3>
			<i class="fa fa-calendar"></i>
			Add Event
		</h3>
	</div>

	<div class="portlet-content">

	<div class="row">
		<div class="col-md-8">

			<!-- <div ng-repeat="day in calendarDates" ng-show="$first">

			    <h4 class="heading">
					Week starting {{day.fullDate | date: 'EEEE, d MMMM,y'}}
				</h4>
			</div> -->
			<h4 class="heading">Studion Schedule</h4>
		</div>

		<div class="col-md-4">
			<span ng-click="timetableNavigation(-1)" class="fc-button fc-button-prev fc-state-default fc-corner-left" unselectable="on" >
				<span class="fc-text-arrow">���</span>
			</span>

			<span ng-click="timetableNavigation(1)" class="fc-button fc-button-next fc-state-default fc-corner-right" unselectable="on">
				<span class="fc-text-arrow">���</span>
			</span>
		</div>
	</div>

		<div class="row">
			<div class="col-md-12">

			<table class="table table-bordered">
					<thead>
						<tr>
							<th>Start time</th>
							<th>Lesson</th>
							<th>Teacher</th>
							<th>Number of spaces</th>
							<th>Duration</th>
							<th></th>
							<th></th>
							<th></th>
						</tr>
					</thead>

					<tbody ng-repeat="day in calendarDates | orderBy:day">
						<tr>
							<td colspan="8"><h4 class="heading">{{day.fullDate | date: 'EEEE, d MMMM,y'}}</h4></td>
						</tr>
						<tr ng-repeat="event in events | filter : day.shortDate | orderBy:event.eventItemDetails.summary:reverse" >
							<td>{{event.startDateTime| date: 'h:mma'}}</td>
							<td>{{event.eventItemDetails.summary}}</td>
							<td>{{event.eventItemDetails.organizer}}</td>

							<td>{{event.eventItemDetails.maxAttendees}}</td>
							<td>{{event.eventItemDetails.duration | date: 'h:mma'}}</td>
							<td>{{event.eventItemDetails.eventAttribute.name}}</td>

							<td>{{event.eventItemDetails.eventCategory.name}}</td>
							<td>
								<span class="label label-primary">Approved</span>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>

	</div>

</div>
