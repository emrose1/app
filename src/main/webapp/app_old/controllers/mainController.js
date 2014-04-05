'use strict';

/* Controllers */

var controllers = angular.module('controllers', []);

controllers.controller('MainCntl', ['$scope', '$route', '$routeParams', '$location',
  function($scope, $route, $routeParams, $location) {
    $scope.$route = $route;
  	$scope.$location = $location;
  	$scope.$routeParams = $routeParams;
  }]);