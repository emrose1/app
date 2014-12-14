angular.module('app.services.alerts', [])
.factory('alerts', function() {
  return {
    alertMessage: null,
    setAlert: function(msg) {
      this.alertMessage = msg;
    },
    clear: function() {
      this.alertMessage = null;
    }
  };
});