angular.module('application.controllers.schedule', [])
.controller('scheduleCtrl', ['$rootScope', '$scope',
  function($rootScope,$scope) {

    $scope.selectedWeekNumber = 0;

    var formatShortDate = function(date) {
      var dd = date.getDate();
      var mm = date.getMonth()+1;//January is 0!
      var yyyy = date.getFullYear();
      var hours = date.getHours();
      var min = date.getMinutes();

      if(dd<10){dd='0'+dd;}
      if(mm<10){mm='0'+mm;}
      if(hours<10){hours='0'+hours;}
      if(min<10){min='0'+min;}

      return  yyyy + mm  + dd;
    };

    var getDatesOfCurrentWeek = function() {
      var calendarDates = {};
      // create array of dates for currently selected week
      for (var i = 1; i < 8; i++) {
        var curr = new Date();
        // get Monday (first working day) of current week then add number of days 1-7
        var dayOfWeek = curr.getDate() - curr.getDay() + i + ($scope.selectedWeekNumber * 7);
        var dateOfWeek = new Date(curr.setDate(dayOfWeek));

        var shortDate = formatShortDate(dateOfWeek).toString();
        calendarDates[i] = {'fullDate' : dateOfWeek, 'shortDate' : shortDate};
      }
      return calendarDates;
    };

    $rootScope.$on('eventsLoaded', function(event, args) {
        $scope.calendarDates = getDatesOfCurrentWeek();
    });

    $scope.timetableNavigation = function(val) {
      $scope.selectedWeekNumber = $scope.selectedWeekNumber + val;
      $scope.calendarDates = getDatesOfCurrentWeek();
    };

}])
;