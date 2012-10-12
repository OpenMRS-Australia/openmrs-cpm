'use strict';

describe('Concept Proposal Module', function() {

  // This should fail on IE7
  it('should redirect from monitor.list to monitor.list#/', function() {
    browser().navigateTo('monitor.jsp');
    expect(browser().location().url()).toBe('/');
  });


  describe('List proposals view', function() {

    it('should navigate to the edit screen after clicking on "Add proposal"', function() {

      // todo

    });

  });

});
