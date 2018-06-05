<%@ page import="java.util.Date,java.util.Calendar"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
</head>
<body bgcolor="${pickedBgCol}">
	<h1>Band voting results</h1>
	<p>Results are shown below:</p>

	<br>
	<br>
	<a href="./index.jsp">Go to homepage</a>
	<br>

	<table border=2>
		<thead>
			<tr>
				<th>Band</th>
				<th>Number of Votes</th>
			</tr>
		</thead>

		<tbody>
			<c:forEach var="result" items="${results}">
				<tr>
					<td align="center">${result.key}</td>
					<td align="center">${result.value}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>