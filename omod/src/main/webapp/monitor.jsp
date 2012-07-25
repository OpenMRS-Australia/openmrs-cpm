<%@ include file="/WEB-INF/template/include.jsp" %>

<openmrs:message var="pageTitle" code="cpm.monitor.title" scope="page"/>

<%@ include file="/WEB-INF/template/header.jsp" %>

<openmrs:require privilege="View Concepts" otherwise="/login.htm"
	redirect="/dictionary/index.htm" />

<h2><openmrs:message code="cpm.monitor.title" /></h2>

<%@ include file="/WEB-INF/template/footer.jsp" %>