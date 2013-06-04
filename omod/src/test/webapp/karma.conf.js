basePath = '../../';

files = [
  JASMINE,
  JASMINE_ADAPTER,
  REQUIRE,
  REQUIRE_ADAPTER,

  { pattern: 'main/webapp/resources/lib/**/*.js', included: false, watched: true },
  { pattern: 'main/webapp/resources/js/**/*.js', included: false, watched: true },
  { pattern: 'test/webapp/lib/**/*.js', included: false, watched: true },
  { pattern: 'test/webapp/unit/**/*Spec.js', included: false, watched: true },

  'test/webapp/test-main.js'
];

// list of files to exclude
exclude = [
  'main/webapp/resources/js/cpm-main',
  'main/webapp/resources/js/cpmr-main'
];

// test results reporter to use
// possible values: 'dots', 'progress', 'junit'
reporters = ['progress'];

// web server port
port = 9876;

// cli runner port
runnerPort = 9100;

// enable / disable colors in the output (reporters and logs)
colors = true;

// possible values: LOG_DISABLE || LOG_ERROR || LOG_WARN || LOG_INFO || LOG_DEBUG
logLevel = LOG_INFO;

autoWatch = true;

// Start these browsers, currently available:
// - Chrome
// - ChromeCanary
// - Firefox
// - Opera
// - Safari (only Mac)
// - PhantomJS
// - IE (only Windows)
browsers = ['Chrome'];

// If browser does not capture in given timeout [ms], kill it
captureTimeout = 60000;

// Continuous Integration mode
// if true, it capture browsers, run tests and exit
singleRun = false;