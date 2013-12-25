/*function Ctrl($scope) {
  $scope.templates =
    [ { name: 'template1.html', url: 'template1.html'}
    , { name: 'template2.html', url: 'template2.html'} ];
  $scope.template = $scope.templates[0];
}*/

angular.module('ngToggle', [])
.controller('AppCtrl',['$scope', function($scope){
	
	$scope.templates =
	    [ { name: 'template1.html', url: 'template1.html'}
	    , { name: 'template2.html', url: 'template2.html'} ];
	$scope.template = $scope.templates[0];
	
    $scope.custom = true;
}]);