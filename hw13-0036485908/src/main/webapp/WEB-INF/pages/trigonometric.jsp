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
	<h1>Trigonometry table</h1>
	<p>Results are shown below:</p>

	<br>
	<br>
	<a href="./index.jsp">Go to homepage</a>
	<br>

	<table border=2>
		<thead>
			<tr>
				<th>Number</th>
				<th>Sine</th>
				<th>Cosine</th>
			</tr>
		</thead>

		<tbody>
			<c:forEach var="angle" items="${angles}">
				<tr>
					<td align="center">${angle.value}</td>
					<td align="center">${angle.sin}</td>
					<td align="center">${angle.cos}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>