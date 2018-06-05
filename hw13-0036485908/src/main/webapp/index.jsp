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

	<h3>Enter custom limits</h3>
	<form action="trigonometric" method="GET">
		Start angle:<br> <input type="number" name="a" min="0" max="360"
			step="1" value="0"><br> End angle:<br> <input
			type="number" name="b" min="0" max="360" step="1" value="360"><br>
		<input type="submit" value="Show Table"><input type="reset"
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

	<h1>XLS Generator</h1>
	<h3>a represents lower limit; its value must be between -100 and
		100</h3>
	<h3>b represents upper limit; its value must be between -100 and 100</h3>
	<h3>n represents numbers of sheets to be generated; its value must
		be between 1 and 5</h3>

	<form action="powers" method="GET">
		a:<br> <input type="number" name="a" step="1" value="1"><br>
		b:<br> <input type="number" name="b" step="1" value="100"><br>
		n:<br> <input type="number" name="n" step="1" value="3"><br>
		<input type="submit" value="Generate XLS"><input type="reset"
			value="Reset values">
	</form>
	<br>
	<br>
	<h1>App Lifetime</h1>
	<a href="appinfo.jsp">View app lifetime</a>
	<br><br>
	<h1>Bend Voting</h1>
	<a href="glasanje" action="GET">Vote for your favorite band!</a>
</body>
</html>