require([
    'config'
  ],
  function(config) {

    'use strict';

    requirejs.config({
      baseUrl: config.resourceLocation,
      paths: {
        'angular': 'components/angular/angular',
        'angular-resource': 'components/angular-resource/angular-resource',
        'domReady': 'components/requirejs-domready/domReady',
        'jquery': 'components/jquery/jquery',
        'jquery-ui': 'lib/jquery-ui/ui/jquery-ui',
        'require': 'components/requirejs/require',
        'underscore': 'components/underscore/underscore'
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
  }
);