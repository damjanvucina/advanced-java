<%@ page import="java.util.Date,java.util.Calendar"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	StringBuilder sb = new StringBuilder();
	long birth = (Long) session.getServletContext().getAttribute("birth");
	long lifetime = System.currentTimeMillis() - birth;
	long miliseconds = lifetime % 1000;
	long seconds = (lifetime / 1000) % 60;
	long minutes = (lifetime / (1000 * 60)) % 60;
	long hours = (lifetime / (1000 * 60 * 60)) % 24;
	long days = (lifetime / (1000 * 60 * 60 * 24)) % 365;
	long years = lifetime / (1000 * 60 * 60 * 24 * 365);

	if (years > 0) {
		sb.append(years + " years, ");
	}
	if (days > 0) {
		sb.append(days + " days, ");
	}
	if (hours > 0) {
		sb.append(hours + " hours, ");
	}
	if (minutes > 0) {
		sb.append(minutes + " minutes, ");
	}
	if (seconds > 0) {
		sb.append(seconds + " seconds, ");
	}
	if (miliseconds > 0) {
		sb.append(miliseconds + " miliseconds, ");
	}
	sb.delete(sb.lastIndexOf(","), sb.length());
	
	String total = sb.toString();
%>
<html>
<body bgcolor="${pickedBgCol}">
	<h1>Application info</h1>
	<p>App has been up for <%=total%></p>

	<br>
	<br>
	<a href="./index.jsp">Go to homepage</a>
	<br>
</body>

</html>