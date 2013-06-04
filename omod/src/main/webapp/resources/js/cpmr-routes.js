/**
 * Defines the main routes in the application.
 * The routes you see here will be anchors '#/' unless specifically configured otherwise.
 */

define(['js/cpmr-app', 'config'], function (cpmr, config) {
    'use strict';

    cpmr.config(['$routeProvider', function($routeProvider){
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
    }]);
});