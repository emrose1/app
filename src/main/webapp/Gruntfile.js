module.exports = function ( grunt ) {

  /**
   * Load required Grunt tasks. These are installed based on the versions listed
   * in `package.json` when you do `npm install` in this directory.
   */
  grunt.loadNpmTasks('grunt-contrib-clean');
  grunt.loadNpmTasks('grunt-contrib-copy');
  grunt.loadNpmTasks('grunt-contrib-jshint');
  grunt.loadNpmTasks('grunt-contrib-concat');
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-contrib-uglify');
  grunt.loadNpmTasks('grunt-contrib-less');
  grunt.loadNpmTasks('grunt-html2js');

  /**
   * Load in our build configuration file.
   */
  var userConfig = require( './build.config.js' );

  /**
   * This is the configuration object Grunt uses to give each plugin its
   * instructions.
   */
  var taskConfig = {
    /**
     * We read in our `package.json` file so we can access the package name and
     * version. It's already there, so we don't repeat ourselves here.
     */
    pkg: grunt.file.readJSON("package.json"),

    /**
     * The directories to delete when `grunt clean` is executed.
     */
    clean: [
      '<%= build_dir %>'
    ],

    less: {
      development: {
        files: {
          '<%= app_files.css %>': '<%= app_files.less %>'
        }
      }
    },

    /**
     * The `index` task compiles the `index.html` file as a Grunt template. CSS
     * and JS files co-exist here but they get split apart later.
     */
    index: {
      development: {
        dir: '<%= src_dir %>',
        files: [
          { src: ['<%= vendor_files.js %>', '<%= vendor_files.css %>'] },
          { src: ['<%= app_files.js %>', '<%= app_files.css %>'] }
        ],
        base_href: '<%= base_href.dev %>'
      },
      production: {
        dir: '<%= src_dir %>',
        files: [
          { src: ['<%= src_dir %>/vendor-combined.js', '<%= src_dir %>/app-combined.js', '<%= app_files.css %>'] }
        ],
        base_href: '<%= base_href.production %>'
      }
    },

    concat: {
      /**
       * The `build_css` target concatenates compiled CSS and vendor CSS
       * together.
       */
      build_vendor_css: {
         src: [
           '<%= vendor_files.css %>',
           '<%= build_dir %>/assets/<%= pkg.name %>-<%= pkg.version %>.css'
         ],
         dest: '<%= src_dir %>/vendor-combined.css'
      },
      /**
       * The `compile_js` target is the concatenation of our application source
       * code and all specified vendor source code into a single file.
       */
      compile_app_js: {
        src: [
          '<%= app_files.js %>'
        ],
        dest: '<%= src_dir %>/app-combined.js'
      },
      compile_vendor_js: {
        src: [
          '<%= vendor_files.js %>'
        ],
        dest: '<%= src_dir %>/vendor-combined.js'
      }
    },

    uglify: {
      compile: {
        files: {
          '<%= concat.compile_app_js.dest %>': '<%= concat.compile_app_js.dest %>',
          '<%= concat.compile_vendor_js.dest %>': '<%= concat.compile_vendor_js.dest %>'
        }
      }
      // options: {
      //   beautify: true
      // }
    },

    copy: {
      assets: {
        files: [
          {
            src: [ '**' ],
            dest: '<%= build_dir %>/assets/',
            cwd: 'src/assets',
            expand: true
          }
       ]
      },
      js: {
        files: [
          {
            src: [ 'vendor-combined.js', 'app-combined.js' ],
            dest: '<%= build_dir %>/',
            cwd: 'src',
            expand: true
          }
        ]
      },
      css: {
        files: [
          {
            src: [ 'vendor-combined.css', 'less/main.css' ],
            dest: '<%= build_dir %>/',
            cwd: 'src',
            expand: true
          }
        ]
      },
      html: {
        files: [
          {
            src: [ 'index.html' ],
            dest: '<%= build_dir %>/',
            cwd: 'src',
            expand: true
          }
        ]
      },
      templates: {
        files: [
          {
            src: [ '**/*tpl.html' ],
            dest: '<%= build_dir %>/',
            cwd: 'src',
            expand: true
          }
        ]
      }
    }
  };

  grunt.initConfig( grunt.util._.extend( taskConfig, userConfig ) );

  /**
   * The default task is to build for development
   */
  grunt.registerTask( 'default', [ 'build-development' ] );

  /**
   * Builds less files and generates an index.html file with all sources declared individually
   */
  grunt.registerTask( 'build-development', [
    'less:development', 'index:development'
  ]);

  /**
   * Builds less files concats and uglifies js and generates an index.html file with compressed/compiled sources.
   */
  grunt.registerTask( 'build-production', [
    'less:development', 'concat:compile_app_js', 'concat:compile_vendor_js', 'uglify', 'concat:build_vendor_css', 'index:production'
  ]);

  /**
   * Takes the compiled production build resources and moves them to a build folder
   */
  grunt.registerTask( 'production', [
    'build-production', 'copy'
  ]);

  /**
   * A utility function to get all app JavaScript sources.
   */
  function filterForJS ( files ) {
    return files.filter( function ( file ) {
      return file.match( /\.js$/ );
    });
  }

  /**
   * A utility function to get all app CSS sources.
   */
  function filterForCSS ( files ) {
    return files.filter( function ( file ) {
      return file.match( /\.css$/ );
    });
  }

  /**
   * The index.html template includes the stylesheet and javascript sources
   * based on dynamic names calculated in this Gruntfile. This task assembles
   * the list into variables for the template to use and then runs the
   * compilation.
   */
  grunt.registerMultiTask( 'index', 'Process index.html template', function () {
    var base_href = this.data.base_href;
    var dirRE = new RegExp( '^('+grunt.config('src_dir')+')\/', 'g' );

    var jsFiles = filterForJS( this.filesSrc ).map( function ( file ) {
      return file.replace( dirRE, '' );
    });

    var cssFiles = filterForCSS( this.filesSrc ).map( function ( file ) {
      return file.replace( dirRE, '' );
    });

    grunt.file.copy('src/index.html.template', this.data.dir + '/index.html', {
      process: function ( contents, path ) {
        return grunt.template.process( contents, {
          data: {
            scripts: jsFiles,
            styles: cssFiles,
            base_href: base_href
          }
        });
      }
    });
  });
};
