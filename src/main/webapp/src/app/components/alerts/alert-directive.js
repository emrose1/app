angular.module('app.directives.alert', [])
.directive('alertModal', ['$parse', function($parse) {
    return {
        restrict: 'A',
        templateUrl: 'app/components/alerts/alert-modal.tpl.html',

        link: function(scope, elem, attrs) {
            var alertMessageAttr = attrs['alertinfo'];
            scope.alertMessage = null;

            scope.hideAlert = function() {
                scope.alertMessage = null;
                // Also clear the error message on the bound variable
                // Do this so that in case the same error happens again
                // the alert bar will be shown again next time
                $parse(alertMessageAttr).assign(scope, null);
            };

            scope.$watch(alertMessageAttr, function(newVal) {
                scope.alertMessage = newVal;
                if (newVal){
                    setTimeout(function(){
                        scope.hideAlert();
                        scope.$apply();
                    }, 4000);
                }
            });
        }
    };
}]);
