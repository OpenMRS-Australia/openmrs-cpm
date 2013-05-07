define(function() {

    'use strict'

    describe('OpenMRS CPM', function() {

        it('should be read-only', function() {

            browser().navigateTo('/edit/1');

            expect(element('.name').attr('ng-disabled')).toBe('true');

        });
    });
});
