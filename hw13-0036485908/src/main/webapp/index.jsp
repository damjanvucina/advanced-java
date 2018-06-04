<%@ page import="java.util.Date,java.util.Calendar"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<body bgcolor="${pickedBgCol}">
	<h1>Redirect to color choosing page</h1>
	<a href="./colors.jsp">Choose color</a>
	<br>
	<br>

	<h1>Trigonometry</h1>
	<a href="./trigonometric?a=0&b=90" action="GET">See basic
		trigonometric angles table</a>
	<br>
	<br>

	<h3>Form</h3>
	<form action="trigonometric" method="GET">
		Početni kut:<br> <input type="number" name="a" min="0" max="360"
			step="1" value="0"><br> Završni kut:<br> <input
			type="number" name="b" min="0" max="360" step="1" value="360"><br>
		<input type="submit" value="Tabeliraj"><input type="reset"
			value="Reset">
	</form>

	<h1>A funny story</h1>
	<a href="funny" action="GET">A funny story</a>
	<br>
	<br>
	<h1>Operating systems distribution</h1>
	<a href="reportImage" action="GET">Distribution info</a>
	<br>
	<br>

</body>
</html>