<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/header.jsp" %>

<h1>Proposal List</h1>

<table>
	<tr>
		<th>Id</th>
		<th>Name</th>
	</tr>
	<c:forEach var="proposal" items="${proposalList}">
	<tr>
		<td>${proposal.id}</td>
		<td>${proposal.title}</td>
	</tr>
	</c:forEach>
</table>

<%@ include file="/WEB-INF/template/footer.jsp" %>
