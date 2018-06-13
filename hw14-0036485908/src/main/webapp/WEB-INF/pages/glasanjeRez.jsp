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
	<h1>Voting results</h1>
	<p>Results are shown below:</p>

	<table border=1>
		<thead>
			<tr>
				<th>Voting option</th>
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
	<br>
	<br>
	<h1>Pie chart</h1>
	<img alt="Pie-chart" src="glasanje-grafika" width="400" height="400" />
	<br>
	<br>
	<h3>Download voting results</h3>
	<a href="glasanje-xls">Results are available here</a>
	<br>
	<br>
	<h3>References</h3>
	<ol>
		<c:forEach var="reference" items="${references}">
			<li><a href="${reference.value}">${reference.key}</a></li>

		</c:forEach>
	</ol>
</body>
</html>