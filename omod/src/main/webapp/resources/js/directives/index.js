/** attach directives to this module 
 * if you get 'unknown {x}Provider' errors from angular, be sure they are
 * properly referenced in one of the module dependencies in the array.
 **/
define(['angular'], function (angular) {
    'use strict';

    return angular.module("cpm.directives", []);
});