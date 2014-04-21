// <div alert-bar alertMessage="myMessageVar"></div>
Application.Directives.directive('alertModal', ['$parse', function($parse) {
  return {
    restrict: 'A',
    templateUrl: 'alerts/alert-modal-template.html',

    link: function(scope, elem, attrs) {
      console.log(attrs);
      var alertMessageAttr = attrs['alertinfo'];
      scope.alertMessage = null;

      scope.$watch(alertMessageAttr, function(newVal) {
        console.log(alertMessageAttr);
        console.log(newVal);
        scope.alertMessage = newVal;
      });
      scope.hideAlert = function() {
        scope.alertMessage = null;
        // Also clear the error message on the bound variable
        // Do this so that in case the same error happens again
        // the alert bar will be shown again next time
        $parse(alertMessageAttr).assign(scope, null);
      };
    }
  };
}]);
