<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/header.jsp" %>

<openmrs:require privilege="View Concepts" otherwise="/login.htm" redirect="/" />

<link href="${pageContext.request.contextPath}/moduleResources/cpm/css/cpm.css" type="text/css" rel="stylesheet"/>

<script data-main="${pageContext.request.contextPath}/moduleResources/cpm/js/cpm-main" src="${pageContext.request.contextPath}/moduleResources/cpm/lib/require.js"></script>

<div id="cpmapp" ng-view>Loading...</div>

<script>
    define('config', [], function() {
      return {
        resourceLocation: '${pageContext.request.contextPath}/moduleResources/cpm',
        contextPath: '${pageContext.request.contextPath}'
      };
    });
</script>


<%@ include file="/WEB-INF/template/footer.jsp" %>