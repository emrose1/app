describe("AppCtrl", function(){
	var appCtrl, $httpBackend, $scope;
	beforeEach(module('app'));

	beforeEach(inject(function($injector){
		injector    = $injector;
		$rootScope  = injector.get('$rootScope');
		$controller = injector.get('$controller');

		//$httpBackend = injector.get('$httpBackend');

		$scope  = $rootScope.$new();

		appCtrl = $controller('AppCtrl', {
			$scope: $scope
		});

	}));

	describe("AppCtrl", function(){
		it("test", function(){
			//expect(appCtrl).toBeDefined();
		});
	});
});