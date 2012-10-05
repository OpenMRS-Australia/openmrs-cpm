<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/header.jsp" %>

<h1>Concept List</h1>

<table>
	<tr>
		<th>email</th>
		<th>description</th>
	</tr>
	<c:forEach var="proposal" items="${proposalList}">
	<tr>
		<td>${proposal.email}</td>
		<td>${proposal.description}</td>
	</tr>
	</c:forEach>
</table>

<%@ include file="/WEB-INF/template/footer.jsp" %>
