<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/header.jsp" %>

<ul id="menu">
    <li class="first"><a href="${pageContext.request.contextPath}/admin">Admin</a></li>
    <li><a href="${pageContext.request.contextPath}/module/cpm/proposals.list#edit">Create Proposal</a></li>
    <li><a href="${pageContext.request.contextPath}/module/cpm/proposals.list">Monitor Proposals</a></li>
    <li><a href="${pageContext.request.contextPath}/module/cpm/proposalReview.list">Incoming Proposals</a></li>
    <li><a href="${pageContext.request.contextPath}/module/cpm/proposals.list#settings">Settings</a></li>
</ul>

<openmrs:require privilege="View Concepts" otherwise="/login.htm" redirect="/" />

<script data-main="${pageContext.request.contextPath}/moduleResources/cpm/app.js" src="${pageContext.request.contextPath}/moduleResources/cpm/require.js"></script>

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
