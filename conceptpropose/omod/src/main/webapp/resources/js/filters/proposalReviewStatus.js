define([
    './index',
    'config'
  ],
  function(filters, config) {

    'use strict';

    function getConceptLink(conceptId) {
      return '<a onclick="var event = arguments[0] || window.event; event.stopPropagation();" href="' + config.contextPath + '/dictionary/concept.htm?conceptId=' + conceptId + '">' + conceptId + '</a>';
    }

    filters.filter('proposalReviewStatus', function() {
      return function(proposal) {
        switch (proposal.status) {

        case 'RECEIVED':
          return 'Open';

        case 'CLOSED_NEW':
          return 'New' + (proposal.conceptId ? ': ' + getConceptLink(proposal.conceptId) : '');

        case 'CLOSED_EXISTING':
          return 'Existing' + (proposal.conceptId ? ': ' + getConceptLink(proposal.conceptId) : '');

        case 'CLOSED_REJECTED':
          return 'Rejected';

        default:
          return '';
        }
      };
    });
  }
);