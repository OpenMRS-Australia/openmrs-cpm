define([
    'js/app',
    'config'
  ],
  function (conceptreview, config) {

    'use strict';

    conceptreview.config(function($routeProvider) {
      $routeProvider.
        when('/', {
          controller: 'ListIncomingProposalsCtrl',
          templateUrl: config.resourceLocation + '/partials/ListIncomingProposals.html'
        }).
        when('/completed', {
          controller: 'ListCompletedProposalsCtrl',
          templateUrl: config.resourceLocation + '/partials/ListCompletedProposals.html'
        }).
        when('/deleted', {
          controller: 'ListDeletedProposalsCtrl',
          templateUrl: config.resourceLocation + '/partials/ListDeletedProposals.html'
        }).
        when('/edit/:proposalId', {
          controller: 'ReviewProposalCtrl',
          templateUrl: config.resourceLocation + '/partials/ReviewProposal.html'
        }).
        when('/edit/:proposalId/concept/:conceptId', {
          controller: 'ReviewConceptCtrl',
          templateUrl: config.resourceLocation + '/partials/ReviewConcept.html'
        }).
        when('/deleted/:proposalId', {
          controller: 'ReviewDeletedProposalCtrl',
          templateUrl: config.resourceLocation + '/partials/ReviewDeletedProposal.html'
        }).
        when('/deleted/:proposalId/concept/:conceptId', {
          controller: 'ReviewConceptOfDeletedProposalCtrl',
          templateUrl: config.resourceLocation + '/partials/ReviewConceptOfDeletedProposal.html'
        });
    });
  }
);