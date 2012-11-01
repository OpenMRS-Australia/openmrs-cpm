define(['angular-mocks', 'filters'], function() {
  'use strict';

  describe("Filter Spec", function() {

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
