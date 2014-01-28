define([
    'require',
    'angular',
    'js/app',
    'js/routes',
    'js/impl'
  ],

  function (require, angular) {
    
    'use strict';

    /* place operations that need to initialize prior to app start here
     * using the `run` function on the top-level module
     */

    require(
      ['domReady!'],
      function (document) {
        return angular.bootstrap(document, ['conceptreview']);
      }
    );
  }
);
