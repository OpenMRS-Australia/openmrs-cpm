module.exports = function(config) {
  
  'use strict';

  config.set({
    basePath: '../../',
    frameworks: ['jasmine', 'requirejs'],
    files: [
      { pattern: 'main/webapp/resources/components/**/*.js', included: false, watched: true },
      { pattern: 'main/webapp/resources/js/**/*.js', included: false, watched: true },
      { pattern: 'test/webapp/unit/**/*Spec.js', included: false, watched: true },

      'test/webapp/test-main.js'
    ],
    exclude: [
      'main/webapp/resources/components/**/*Spec.js',
      'main/webapp/resources/js/main'
    ],
    reporters: ['progress'],
    port: 9876,
    runnerPort: 9100,
    colors: true,
    autoWatch: true,

    // Start these browsers, currently available:
    // - Chrome
    // - ChromeCanary
    // - Firefox
    // - Opera
    // - Safari (only Mac)
    // - PhantomJS
    // - IE (only Windows)
    browsers: ['Chrome'],
    captureTimeout: 60000,
    singleRun: false
  });
};