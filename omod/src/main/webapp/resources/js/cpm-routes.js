/**
 * Defines the main routes in the application.
 * The routes you see here will be anchors '#/' unless specifically configured otherwise.
 */

define(['js/cpm-app', 'config'], function (cpm, config) {
    'use strict';

    cpm.config(['$routeProvider', function($routeProvider) {
        $routeProvider.
            when('/', {
                controller: 'ListProposalsCtrl', 
                templateUrl: config.resourceLocation + '/partials/ListProposals.html'
            }).
            when('/edit', {
                controller: 'EditProposalCtrl', 
                templateUrl: config.resourceLocation + '/partials/EditProposal.html'
            }).
            when('/edit/:proposalId', {
                controller: 'EditProposalCtrl', 
                templateUrl: config.resourceLocation + '/partials/EditProposal.html'
            }).
            when('/settings', {
                controller: 'SettingsCtrl', 
                templateUrl: config.resourceLocation + '/partials/Settings.html'
            });
    }]);
});