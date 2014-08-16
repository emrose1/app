angular.module('application.directives.progressBar', [])
.directive('progressBar', ['$parse', function($parse) {
  return {
    restrict: 'A',
    templateUrl: 'progressbar/progressbar-partial.html',

    link: function(scope, elem, attrs) {
      var progressBarAttr = attrs['progressinfo'];
      //scope.loadingProgress = null;

      scope.$watch(progressBarAttr, function(newVal) {
        console.log(newVal);
        scope.loadingProgress = newVal;
      });
      scope.hideProgressBar = function() {
        scope.loadingProgress = null;
        // Also clear the error message on the bound variable
        // Do this so that in case the same error happens again
        // the alert bar will be shown again next time
        $parse(progressBarAttr).assign(scope, null);
      };
    }
  };
}]);
