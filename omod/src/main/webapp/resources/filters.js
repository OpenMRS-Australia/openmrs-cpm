define(['angular', 'config'], function(angular, config) {

  function getConceptLink(conceptId) {
      return '<a onclick="var event = arguments[0] || window.event; event.stopPropagation();" href="' + config.contextPath + '/dictionary/concept.htm?conceptId=' + conceptId + '">' + conceptId + '</a>';
  }

  var filters = angular.module('cpm.filters', []);

  filters.filter('proposalStatus', function() {
    return function(input) {
      switch (input) {

        case 'DRAFT':
          return 'Draft';

        case 'SUBMITTED':
          return 'Submitted';

        default:
          return '';
      }
    };
  });

  filters.filter('proposalReviewStatus', function() {
    return function(proposal) {
      switch (proposal.status) {

        case 'RECEIVED':
          return 'Open';

        case 'CLOSED_NEW':
          return 'New: ' + getConceptLink(proposal.conceptId);

        case 'CLOSED_EXISTING':
          return 'Existing: ' + getConceptLink(proposal.conceptId);

        case 'CLOSED_REJECTED':
          return 'Rejected';

        default:
          return '';
      }
    }
  })

  return filters;
});
