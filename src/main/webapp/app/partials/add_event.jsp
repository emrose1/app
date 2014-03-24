<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="content-header">
    <h1>Add Studio Session</h1>
</div> <!-- #content-header -->


<div id="content-container">

    <div class="portlet-content">


    <div class="row">
        <div class="col-sm-12">
            <h4>Calendar</h4>
            <select ng-change="loadCalendar()" id="calendar" class="form-control" ng-model="eventsCalendar" ng-options="c.description for c in calendars">
                <option>Loading</option>
            </select>
        </div>
    </div>

        <form role="form" id="addEvent" name="eventForm" ng-submit="addEvent()" novalidate>


            <div class="row">
                <div class="col-sm-5">
                    <h4>Date </h4>
                    <div class="input-group date ui-datepicker">
                        <input  type="text" class="form-control" name="startTime" id="startTime" datepicker-popup="dd-MM-yyyy"
                                ng-model="startTime"  datepicker-options="dateOptions" />
                        <span class="input-group-addon" ng-click="datePickerOpen('startTime')">
                            <i class="fa fa-calendar"></i>
                        </span>
                    </div>
                </div>
                 <div class="col-sm-4">
                    <h4>Instructor</h4>
                    <input type="text" name="organizer" data-ng-model="organizer" class="form-control" id="organizer" />
                </div>

                <div class="col-sm-3 form-group" ng-class="{ 'has-error' : eventForm.maxAttendees.$invalid && !eventForm.maxAttendees.$pristine }">
                    <h4>Max Attendees</h4>
                    <input type="text" name="maxAttendees" data-ng-model="maxAttendees" class="form-control" id="maxAttendees" required />
                    <p ng-show="eventForm.maxAttendees.$invalid && !eventForm.maxAttendees.$pristine" class="help-block">
                        Not valid number!
                    </p>
                </div>


            </div>

            <div class="row">

                <div class="col-sm-2">
                    <h4>Start Time </h4>
                    <div class="col-sm-2 input-group bootstrap-timepicker">
                        <div ng-model="startTime">
                            <timepicker hour-step="hstep" minute-step="mstep" show-meridian="false"></timepicker>
                        </div>
                    </div>
                </div>

                <div class="col-sm-2">
                    <h4>Duration </h4>
                    <div class="col-sm-2 input-group bootstrap-timepicker">
                        <div ng-model="duration">
                            <timepicker hour-step="hstep" minute-step="mstep" show-meridian="false"></timepicker>
                        </div>
                    </div>
                </div>

                <div class="col-sm-8">
                    <h4>Description</h4>
                    <textarea name="textarea-input" name="summary" data-ng-model="summary" id="summary" cols="10" rows="4" class="form-control">
                    </textarea>
                </div>

            </div> <!-- /.row -->

            <div class="row">
                <div class="col-sm-6">
                    <div class="form-group">
                        <select ng-model="eventCategory" id="select-input" class="form-control" name="eventCategory">
                            <option value="" disabled selected>Session Type</option>
                            <option ng-repeat="eventCategory in eventCategories">{{eventCategory.name}}</option>
                        </select>
                    </div>
                </div>

                <div class="col-sm-6">
                    <div class="form-group">
                        <select ng-model="eventAttribute" id="select-input" class="form-control" name="eventAttribute">
                            <option value="" disabled selected>Session Category</option>
                            <option ng-repeat="eventAttribute in eventAttributes">{{eventAttribute.name}}</option>
                        </select>
                    </div>
                </div>
            </div>

            <div class="row form-horizontal">
                <div class="col-sm-2">
                    <div class="checkbox">
                        <h4>
                            <input value="false" name="repeatEvent" type="checkbox" name="repeatEvent" 
                                ng-model="repeatEvent" ng-click="toggleRepeat()" checked>
                                Repeat Class
                        </h4>
                    </div>
                </div>

                <div class="col-sm-4" ng-show="repeatEvent">
                    <div class="form-group">
                        <select id="select-input" ng-model="eventRepeatType" class="form-control">
                            <option value="" disabled selected>Repeat Frequency</option>
                            <option ng-repeat="ert in eventRepeatTypes">{{ert}}</option>
                        </select>
                    </div>
                </div>

                 <div class="col-sm-6" ng-show="repeatEvent">
                    <div class="form-group">
                        <h4 class="col-md-6" for="finalDate" style="text-align:right; line-height:35px">Final Repeat Date</h4>

                        <div class="col-md-6">
                            <div class="input-group date ui-datepicker">
                                <input  type="text" class="form-control" name="finalRepeatEvent" id="finalRepeatEvent"
                                    datepicker-popup="dd-MM-yyyy" ng-model="finalRepeatEvent"  datepicker-options="dateOptions"/>
                                <span class="input-group-addon" ng-click="datePickerOpen('finalRepeatEvent')">
                                    <i class="fa fa-calendar"></i>
                                </span>
                            </div>
                        </div>
                    </div>
                </div>
            </div> <!-- /.row -->

            <div class="row">
                <div class="col-sm-12">
                    <button type="submit" class="btn btn-primary">Submit</button>
                </div>
            </div> <!-- /.row -->

        </form>
    </div>



    <div class="row">
        <div class="col-xs-3">
            <h3>Form</h3>
            <table class="table table-bordered">
                <tbody>
                    <tr>
                        <td ng-class="{ success: eventForm.$valid, danger: eventForm.$invalid }">Valid</td>
                    </tr>
                    <tr>
                        <td ng-class="{ success: eventForm.$pristine, danger: !eventForm.$pristine }">Pristine</td>
                    </tr>
                    <tr>
                        <td ng-class="{ success: eventForm.$dirty }">Dirty</td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="col-xs-3">
            <h3>Summary</h3>
            <table class="table table-bordered">
                <tbody>
                    <tr>
                        <td ng-class="{ success: eventForm.summary.$valid, danger: eventForm.summary.$invalid }">Valid</td>
                    </tr>
                    <tr>
                        <td ng-class="{ success: eventForm.summary.$pristine, danger: !eventForm.summary.$pristine }">Pristine</td>
                    </tr>
                    <tr>
                        <td ng-class="{ success: eventForm.summary.$dirty }">Dirty</td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="col-xs-3">
            <h3>Organizer</h3>
            <table class="table table-bordered">
                <tbody>
                    <tr>
                        <td ng-class="{ success: eventForm.organizer.$valid, danger: eventForm.organizer.$invalid }">Valid</td>
                    </tr>
                    <tr>
                        <td ng-class="{ success: eventForm.organizer.$pristine, danger: !eventForm.organizer.$pristine }">Pristine</td>
                    </tr>
                    <tr>
                        <td ng-class="{ success: eventForm.organizer.$dirty }">Dirty</td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="col-xs-3">
            <h3>Max Attendees</h3>
            <table class="table table-bordered">
                <tbody>
                    <tr>
                        <td ng-class="{ success: eventForm.maxAttendees.$valid, danger: eventForm.maxAttendees.$invalid }">Valid</td>
                    </tr>
                    <tr>
                        <td ng-class="{ success: eventForm.maxAttendees.$pristine, danger: !eventForm.maxAttendees.$pristine }">Pristine</td>
                    </tr>
                    <tr>
                        <td ng-class="{ success: eventForm.maxAttendees.$dirty }">Dirty</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>