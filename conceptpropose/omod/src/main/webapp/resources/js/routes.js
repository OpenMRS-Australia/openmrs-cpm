define([
    'js/app',
    'config'
  ],
  function (cpm, config) {

    'use strict';

    cpm.config(function($routeProvider) {
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
    });
  }
);