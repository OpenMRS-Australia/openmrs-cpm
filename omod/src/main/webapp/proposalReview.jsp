<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/header.jsp" %>

<openmrs:require privilege="View Concepts" otherwise="/login.htm" redirect="/" />

<script data-main="${pageContext.request.contextPath}/moduleResources/cpm/app-review" src="${pageContext.request.contextPath}/moduleResources/cpm/require.js"></script>

<style>
.results tr:nth-child(odd) {
 background-color: #F3F7FB;
}
.results tr:hover {
 background-color: #F0E68C;
}
</style>

<div id="cpmapp" ng-view>Loading...</div>

<script>
define('config', [], function() {
  return {
    resourceLocation: '${pageContext.request.contextPath}/moduleResources/cpm',
    contextPath: '${pageContext.request.contextPath}'
  };
});

requirejs.config({
    paths: {
        'angular': 'lib/angular',
        'angular-resource': 'lib/angular-resource'
    },
    shim: {
        'jquery-ui': ['jquery'],
        'angular': {
        	deps: ['jquery'],
            exports: 'angular'
        },
        'angular-resource': ['angular']
    }
});
</script>

<%@ include file="/WEB-INF/template/footer.jsp" %>
