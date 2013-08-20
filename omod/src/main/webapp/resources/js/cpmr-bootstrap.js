define([
    'require',
    'angular',
    'js/cpmr-app',
    'js/cpmr-routes',
    'js/cpmr-impl'
  ],

  function (require, angular) {
    
    'use strict';

    /* place operations that need to initialize prior to app start here
     * using the `run` function on the top-level module
     */

    require(['domReady!'], function (document) {
        return angular.bootstrap(document, ['cpm-review']);
    });
});