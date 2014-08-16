angular.module('application.controllers.menu', [])
.controller('menu', ['menu', '$scope', function(menu, $scope){
	$scope.items = menu.get();
}]);