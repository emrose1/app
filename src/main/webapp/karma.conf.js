// Karma configuration
// Generated on Wed Jun 04 2014 20:24:17 GMT+0100 (BST)

module.exports = function(config) {
  config.set({

    // base path that will be used to resolve all patterns (eg. files, exclude)
    basePath: '',


    // frameworks to use
    // available frameworks: https://npmjs.org/browse/keyword/karma-adapter
    frameworks: ['jasmine'],


    // list of files / patterns to load in the browser
    files: [
        'lib/bower_components/angular/angular.min.js',
        'lib/bower_components/angular-resource/angular-resource.min.js',
        'lib/bower_components/angular-mocks/angular-mocks.js',
        'lib/bower_components/angular-ui-router/release/angular-ui-router.js',

        'lib/lodash.js',
        'lib/localization/localize.js',
        'application/application.js',
        'application/configuration-constants.js',


        'authentication/session-service.js',
        'authentication/auth-service.js',
        'accounts/account-resource.js',
        'accounts/accounts-service.js',
        'accounts/account-controller.js',
        'accounts/account-controller-test.js',

        'alerts/alert-directive.js',
        'alerts/alert-service.js'


    ],


    // list of files to exclude
    exclude: [

    ],


    // preprocess matching files before serving them to the browser
    // available preprocessors: https://npmjs.org/browse/keyword/karma-preprocessor
    preprocessors: {

    },


    // test results reporter to use
    // possible values: 'dots', 'progress'
    // available reporters: https://npmjs.org/browse/keyword/karma-reporter
    reporters: ['progress'],


    // web server port
    port: 9876,


    // enable / disable colors in the output (reporters and logs)
    colors: true,


    // level of logging
    // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
    logLevel: config.LOG_INFO,


    // enable / disable watching file and executing tests whenever any file changes
    autoWatch: true,


    // start these browsers
    // available browser launchers: https://npmjs.org/browse/keyword/karma-launcher
    browsers: ['Chrome'],


    // Continuous Integration mode
    // if true, Karma captures browsers, runs the tests and exits
    singleRun: false
  });
};
