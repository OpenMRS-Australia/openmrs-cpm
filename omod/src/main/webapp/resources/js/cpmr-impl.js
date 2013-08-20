/**
 * This is where you add new components to the application
 * you don't need to sweat the dependency order (that is what RequireJS is for)
 */
define([
    'js/controllers/ListIncomingProposalsCtrl',
    'js/controllers/ReviewProposalCtrl',
    'js/controllers/ReviewConceptCtrl',
    'js/controllers/SearchConceptDialogCtrl',
    'js/directives/cpmMenu',
    'js/directives/jqueryUiDialog',
    'js/directives/searchConceptDialog',
    'js/filters/proposalReviewStatus',
    'js/filters/dashOnNull',
    'js/filters/boolToYesOrNo',
    'js/services/menu',
    'js/services/services'
], function() {
});