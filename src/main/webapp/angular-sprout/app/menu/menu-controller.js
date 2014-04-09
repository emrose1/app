Application.Controllers.controller('menu', ['menu', '$scope', function(menu, $scope){
    menu.get()
        .then(function (data) {
            $scope.items = data;
        });
}]);
