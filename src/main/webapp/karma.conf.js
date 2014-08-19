module.exports = function ( karma ) {
  karma.set({
    /**
     * This is the list of file patterns to load into the browser during testing.
     */
    files: [

        // Include dependencies (from bower)
        // include devDependencies (from bower)

      'src/bower_components/angular/angular.js',
      'src/bower_components/angular-mocks/angular-mocks.js',
      'src/bower_components/angular-bootstrap/ui-bootstrap.js',
      'src/bower_components/angular-bootstrap/ui-bootstrap-tpls.js',
      'src/bower_components/angular-ui-router/release/angular-ui-router.js',
      'src/bower_components/angular-resource/angular-resource.js',
      'src/bower_components/lodash/dist/lodash.js',
      'src/app/**/*.js'
    ],
    frameworks: [ 'jasmine' ],
    plugins: [ 'karma-jasmine', 'karma-phantomjs-launcher' ],

    /**
     * How to report, by default.
     */
    reporters: 'dots',

    /**
     * On which port should the browser connect, on which port is the test runner
     * operating, and what is the URL path for the browser to use.
     */
    port: 9018,
    runnerPort: 9100,
    urlRoot: '/',

    /**
     * Disable file watching by default.
     */
    autoWatch: true,

    browsers: [
      'PhantomJS'
    ]
  });
};

