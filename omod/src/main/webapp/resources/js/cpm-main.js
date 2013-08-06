require(['config'], function(config) {
  requirejs.config({
    baseUrl: config.resourceLocation,
    paths: {
      'angular': 'lib/angular',
      'angular-resource': 'lib/angular-resource',
      'domReady': 'lib/domReady',
      'jquery': 'lib/jquery',
      'jquery-ui': 'lib/jquery-ui',
      'require': 'lib/require',
      'underscore': 'lib/underscore'
    },
    shim: {
      'underscore': {
        exports: '_'
      },
      'jquery-ui': ['jquery'],
      'angular': {
        deps: ['jquery'],
        exports: 'angular'
      },
      'angular-resource': ['angular']
    }
  });

  require(['js/cpm-bootstrap'], function () {
      // nothing to do here - see cpm-bootstrap.js
  });
});