module.exports = function(grunt) {

  'use strict';

  grunt.loadNpmTasks('grunt-contrib-jshint');
  grunt.loadNpmTasks('grunt-karma');

  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),
    srcDir: 'conceptpropose/omod/src/main/webapp',
    testDir: 'conceptpropose/omod/src/test/webapp',
    jshint: {
      options: {
        jshintrc: '.jshintrc'
      },
      all: [
        'Gruntfile.js',
        '<%= srcDir %>/resources/js/{,**/}*.js'
      ]
    },
    karma: {
      options: {
        configFile: '<%= testDir %>/karma.conf.js'
      },
      unit: {
        runnerPort: 9101,
        background: true
      },
      continuous: {
        singleRun: true,
        browsers: ['PhantomJS']
      }
    }
  });

  grunt.registerTask('build', [
    'jshint',
    'karma:continuous'
  ]);

  grunt.registerTask('default', ['build']);
};