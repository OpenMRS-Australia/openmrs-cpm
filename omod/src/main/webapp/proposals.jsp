<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/header.jsp" %>

<openmrs:require privilege="View Concepts" otherwise="/login.htm" redirect="/" />

<script data-main="/openmrs/moduleResources/cpm/app.js" src="/openmrs/moduleResources/cpm/require.js"></script>

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
        	  deps: ['jquery'],
            exports: 'angular'
        },
        'angular-resource': ['angular']
    }
});

require(['app'], function() {});
</script>

<%@ include file="/WEB-INF/template/footer.jsp" %>
