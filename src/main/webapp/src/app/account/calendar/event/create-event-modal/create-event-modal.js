angular.module('application.account.calendar.event.createEventModal', [
	'application.constants.configuration',
	'checklist-model'
])

.controller('RepeatEventModalCtrl',[
	"$rootScope", "$scope", "$modalInstance",
	'EVENT_REPEAT_TYPES', "event",
  function($rootScope, $scope, $modalInstance, eventRepeatTypes, event) {
    $scope.formData = {};
    $scope.event = event;

    $scope.saveRepeatData = function () {
      $modalInstance.close($scope.formData);
    };

    $scope.cancel = function () {
      $modalInstance.dismiss('cancel');
    };

	$scope.daysOfWeek = [
		{id: 1, text: 'M'},
		{id: 2, text: 'T'},
		{id: 3, text: 'W'},
		{id: 4, text: 'T'},
		{id: 5, text: 'F'},
		{id: 6, text: 'S'},
		{id: 7, text: 'S'}
	];

	$scope.event = {
		repeatDaysOfWeek: []
	};
}]);