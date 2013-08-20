// to run karma:
// $ karma start

var config = {
  resourceLocation: '/base/main/webapp/resources',
  contextPath: '/openmrs'
};

define('config', [], function() { return config; })

require([
    'base/main/webapp/resources/js/requireJS-config'
  ],

  function() {

    'use strict';

    var tests = Object.keys(window.__karma__.files).filter(function (file) {
      return /Spec\.js$/.test(file);
    });

    require.config({
      baseUrl: config.resourceLocation,
      paths: {
        'angular-mocks': '../../../test/webapp/lib/angular-mocks'
      },
      shim: {
        'angular-mocks': ['angular']
      },
      deps: tests,
      callback: window.__karma__.start
    });
  }
);