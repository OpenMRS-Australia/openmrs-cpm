<%@ include file="/WEB-INF/template/include.jsp" %>

<openmrs:message var="pageTitle" code="cpm.titlebar" scope="page"/>

<%@ include file="/WEB-INF/template/header.jsp" %>

<openmrs:require privilege="View Concepts" otherwise="/login.htm" redirect="/" />

<script data-main="/openmrs/moduleResources/cpm/cpm.js" src="/openmrs/moduleResources/cpm/require.js"></script>

<div id="cpmapp" ng-view>Loading...</div>

<script>

define('config', [], function() {
  return {
    resourceLocation: '/openmrs/moduleResources/cpm'
  };
});

requirejs.config({
    shim: {
        'jquery-ui': ['jquery'],
        'angular': {
            exports: 'angular'
        }
    }
});

require(['angular', 'cpm', 'directives', 'filters', 'ListConceptProposalsCtrl', 'EditConceptProposalCtrl', 'SearchConceptsDialogCtrl'], function(angular) {
  var appRoot = document.getElementById("cpmapp");
  angular.element(appRoot).ready(function() {
    angular.bootstrap(appRoot, ["cpm"]);
  });
});
</script>


<%@ include file="/WEB-INF/template/footer.jsp" %>
