/**
 * bootstraps angular onto the window.document node
 * NOTE: the ng-app attribute should not be on the document when using ng.bootstrap
 */
define([
    'require',
    'angular',
    'js/cpm-app',
    'js/cpm-routes',
    'js/cpm-impl'
], function (require, angular) {
    //'use strict';

    /* place operations that need to initialize prior to app start here
     * using the `run` function on the top-level module
     */

    require(['domReady!'], function (document) {
        return angular.bootstrap(document, ['cpm']);
    });
});