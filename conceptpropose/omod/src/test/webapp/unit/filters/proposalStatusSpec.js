define(['angular-mocks', 'js/filters/proposalStatus'], function() {
  'use strict';

  describe('proposalStatusFilter Spec', function() {

    var filter;

    beforeEach(module('conceptpropose.filters'));

    beforeEach(inject(function(_proposalStatusFilter_) {
      filter = _proposalStatusFilter_;
    }));


    it('should convert DRAFT to Draft', function() {
      expect(filter('DRAFT')).toBe('Draft');
    });

    it('should convert SUBMITTED to Submitted', function() {
      expect(filter('SUBMITTED')).toBe('Submitted');
    });

  });
});