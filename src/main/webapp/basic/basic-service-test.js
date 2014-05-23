describe('basicService tests', function (){
  var basicService;

  // excuted before each "it" is run.
  beforeEach(function (){

    // load the module.
    module('myApp');

    // inject your service for testing.
    // The _underscores_ are a convenience thing
    // so you can have your variable name be the
    // same as your injected service.
    inject(function(_basicService_) {
      basicService = _basicService_;
    });
  });

  // check to see if it has the expected function
  it('should have an exciteText function', function () {
    expect(angular.isFunction(basicService.exciteText)).toBe(true);
  });

  // check to see if it does what it's supposed to do.
  it('should make text exciting', function (){
    var result = basicService.exciteText('bar');
    expect(result).toBe('bar!!!');
  });
});