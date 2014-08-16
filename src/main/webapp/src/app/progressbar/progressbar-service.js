angular.module('application.services.progressBar', [])
.factory('progressbarService', function() {
  return {
    loadingProgress: null,
    setProgressBar: function(msg) {
      this.loadingProgress = msg;
    },
    clear: function() {
      this.loadingProgress = null;
    }
  };
});