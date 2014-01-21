<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/header.jsp" %>

<openmrs:require privilege="View Concepts" otherwise="/login.htm" redirect="/" />

<link href="${pageContext.request.contextPath}/moduleResources/conceptpropose/css/cpm.css" type="text/css" rel="stylesheet"/>

<script data-main="${pageContext.request.contextPath}/moduleResources/conceptpropose/js/main" src="${pageContext.request.contextPath}/moduleResources/conceptpropose/components/requirejs/require.js"></script>

<div id="cpmapp" ng-view>Loading...</div>

<script>
    define('config', [], function() {
      return {
        resourceLocation: '${pageContext.request.contextPath}/moduleResources/conceptpropose',
        contextPath: '${pageContext.request.contextPath}'
      };
    });
</script>


<%@ include file="/WEB-INF/template/footer.jsp" %>