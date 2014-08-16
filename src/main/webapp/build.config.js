/**
 * This file/module contains all configuration for the build process.
 */
module.exports = {
  /**
   * The `build_dir` folder is where our projects are compiled during
   * development folder is where our app resides once it's
   * completely built.
   */
  build_dir: 'build',
  src_dir: 'src',
  base_href: {
    dev: '/',
    production: '/'
  },
  app_files: {
    js: [
      'src/**/*.js',
      '!src/**/*.spec.js',
      '!src/assets/**/*.js',
      '!src/menu/**/*.js',
      '!src/login/**/*.js',
      '!src/bower_components/**/*.js',
      '!src/app-combined.js',
      '!src/vendor-combined.js'
    ],


    html: [ 'index.html' ],
    less: 'src/less/main.less',
    css: 'src/less/main.css'
  },

  /**
   * This is the same as `app_files`, except it contains patterns that
   * reference vendor code (`vendor/`) that we need to place into the build
   * process somewhere. While the `app_files` property ensures all
   * standardized files are collected for compilation, it is the user's job
   * to ensure non-standardized (i.e. vendor-related) files are handled
   * appropriately in `vendor_files.js`.
   *
   * The `vendor_files.js` property holds files to be automatically
   * concatenated and minified with our project source files.
   *
   * The `vendor_files.css` property holds any CSS files to be automatically
   * included in our app.
   *
   * The `vendor_files.assets` property holds any assets to be copied along
   * with our app's assets. This structure is flattened, so it is not
   * recommended that you use wildcards.
   */
  vendor_files: {
    js: [
      // Include dependencies (from bower)
      'src/bower_components/angular/angular.js',
      'src/bower_components/angular-resource/angular-resource.js',
      'src/bower_components/angular-bootstrap/ui-bootstrap.js',
      'src/bower_components/angular-bootstrap/ui-bootstrap-tpls.js',
      'src/bower_components/angular-ui-router/release/angular-ui-router.js',
      'src/bower_components/restangular/dist/restangular.js',
      'src/bower_components/lodash/dist/lodash.js'
    ],
    css: [
    ],
    assets: [
    ]
  },
};
