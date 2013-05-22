<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/header.jsp" %>

<openmrs:require privilege="View Concepts" otherwise="/login.htm" redirect="/" />

<link href="${pageContext.request.contextPath}/moduleResources/cpm/css/cpm.css" type="text/css" rel="stylesheet">

<script data-main="${pageContext.request.contextPath}/moduleResources/cpm/js/app-review" src="${pageContext.request.contextPath}/moduleResources/cpm/lib/require.js"></script>

<div id="cpmapp" ng-view>Loading...</div>

<script>
    define('config', [], function() {
      return {
        resourceLocation: '${pageContext.request.contextPath}/moduleResources/cpm',
        contextPath: '${pageContext.request.contextPath}'
      };
    });

    requirejs.config({
        baseUrl: '${pageContext.request.contextPath}/moduleResources/cpm',
        paths: {
            'angular': 'lib/angular',
            'angular-resource': 'lib/angular-resource',
            'jquery': 'lib/jquery',
            'jquery-ui': 'lib/jquery-ui'
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