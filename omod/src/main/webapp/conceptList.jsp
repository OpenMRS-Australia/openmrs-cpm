<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/header.jsp" %>

<h1>Concept List</h1>

<table>
	<tr>
		<th>Name</th>
		<th>Id</th>
	</tr>
	<c:forEach var="concept" items="${conceptList}">
	<tr>
		<td>${concept.name}</td>
		<td>${concept.conceptId}</td>
	</tr>
	</c:forEach>
</table>

<%@ include file="/WEB-INF/template/footer.jsp" %>
