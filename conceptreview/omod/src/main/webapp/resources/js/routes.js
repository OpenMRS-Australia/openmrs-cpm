define([
    'js/app',
    'config'
  ],
  function (conceptreview, config) {

    'use strict';

      conceptreview.config(function($routeProvider){
      $routeProvider.
        when('/', {
          controller: 'ListIncomingProposalsCtrl',
          templateUrl: config.resourceLocation + '/partials/ListIncomingProposals.html'
        }).
        when('/edit/:proposalId', {
          controller: 'ReviewProposalCtrl',
          templateUrl: config.resourceLocation + '/partials/ReviewProposal.html'
        }).
        when('/edit/:proposalId/concept/:conceptId', {
          controller: 'ReviewConceptCtrl',
          templateUrl: config.resourceLocation + '/partials/ReviewConcept.html'
        });
    });
  }
);