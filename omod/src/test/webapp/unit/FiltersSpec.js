define(['angular-mocks', 'ListConceptProposalsCtrl'], function() {
  'use strict';

  describe("ListConceptProposals Controller Spec", function() {

    var filter;

    beforeEach(module('cpm.filters'));

    beforeEach(inject(function(_proposalStatusFilter_) {
      filter = _proposalStatusFilter_;
    }));


    it("should convert DRAFT to Draft", function() {
      expect(filter('DRAFT')).toBe('Draft');
    });

    it("should convert SUBMITTED to Submitted", function() {
      expect(filter('SUBMITTED')).toBe('Submitted');
    });

  });
});
