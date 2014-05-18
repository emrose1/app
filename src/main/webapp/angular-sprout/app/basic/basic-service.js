angular.module('myApp', []);
angular.module('myApp').factory('basicService', function(){
  return {
    exciteText: function(msg) {
      return msg + '!!!'
    }
  };
});