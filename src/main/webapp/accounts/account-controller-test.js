/* jasmine specs for controllers go here */
describe('Account controllers', function() {

  beforeEach(function(){
    this.addMatchers({
      toEqualData: function(expected) {
        return angular.equals(this.actual, expected);
      }
    });
  });

  beforeEach(module('ngResource'));
  beforeEach(module('application.services'));
  beforeEach(module('application.controllers'));


  describe('MainCtrl', function(){
    var scope, $httpBackend, ctrl, mockAccountResource, mockSessionService, mockAccount,
        accountsDummyService = function() {
			var testData  = JSON.stringify(
			{
				items :
				[
					{
						id : "5383208929591296",
						name : "Testing Accounts2",
						accountSettings : {
							repeatEventFinalDate : "2019-12-31T23:59:00.000Z"
						}
					},
					{
						id : "6086896371367936",
						name : "Testing Accounts 6999",
						accountSettings : {
							repeatEventFinalDate : "2019-12-31T23:59:00.000Z"
						}
					}, {
						id : "6685030696878080",
						name : "Testing Account1",
						accountSettings : {
							repeatEventFinalDate : "2019-12-31T23:59:00.000Z"
						}
					}
				]
			});
			return testData;
		},

        account = {
			id: "6086896371367936",
			name: "Testing Accounts 6999",
			accountSettings : {
				repeatEventFinalDate : "2019-12-31T23:59:00.000Z"
			}
        },
        accountId = "6086896371367936";

    beforeEach(inject(function(_$httpBackend_, $rootScope, $controller, Account, sessionService) {
      $httpBackend = _$httpBackend_;
      $httpBackend.expectGET('http://localhost:8080/_ah/api/booking/v1/account').respond(accountsDummyService());

      scope = $rootScope.$new();
      ctrl = $controller('MainCtrl', {$scope: scope});
      mockAccountService = Account;
      mockSessionService = sessionService;
    }));


    it('should fetch accounts', function() {
		expect(scope.accounts).toEqualData([]);
		$httpBackend.flush();
		expect(scope.accounts).toEqualData(JSON.parse(accountsDummyService()).items);
		expect(scope.accounts.length).toEqual(3);
		expect(scope.accounts[0].id).toEqual("5383208929591296");
    });


    it('should update account', function() {
		scope.account = account;
		$httpBackend.expect('PUT', 'http://localhost:8080/_ah/api/booking/v1/account/' + account.id,
			account).respond(200, {active:true});
		scope.save(account);
		$httpBackend.flush();
		expect(scope.editing).toEqual(false);
		expect(scope.account).toEqualData({});
    });

    it('should save account', function() {
		scope.account = new mockAccountService();
		$httpBackend.expectPOST('http://localhost:8080/_ah/api/booking/v1/account').respond(200, {id: 1});
		scope.save(account);
		$httpBackend.flush();
		expect(scope.editing).toEqual(false);
		expect(scope.account).toEqualData({});
		expect(scope.accounts.length).toEqual(4);
    });

    it('should delete account', function() {
		$httpBackend.expect('DELETE', 'http://localhost:8080/_ah/api/booking/v1/account', undefined).respond(200, {active:true});
		scope.delete(undefined);
		$httpBackend.flush();
    });
  });
});