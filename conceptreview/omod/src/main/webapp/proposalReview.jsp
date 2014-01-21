<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/header.jsp" %>

<openmrs:require privilege="View Concepts" otherwise="/login.htm" redirect="/" />

<link href="${pageContext.request.contextPath}/moduleResources/conceptreview/css/cpm.css" type="text/css" rel="stylesheet">

<script data-main="${pageContext.request.contextPath}/moduleResources/conceptreview/js/cpmr-main" src="${pageContext.request.contextPath}/moduleResources/conceptreview/components/requirejs/require.js"></script>

<div id="conceptreview" ng-view>Loading...</div>

<script>
    define('config', [], function() {
      return {
        resourceLocation: '${pageContext.request.contextPath}/moduleResources/conceptreview',
        contextPath: '${pageContext.request.contextPath}'
      };
    });
</script>

<%@ include file="/WEB-INF/template/footer.jsp" %>