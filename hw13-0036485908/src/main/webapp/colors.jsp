<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
   <body bgcolor="${pickedBgCol}">
		<h1>Color choosing page</h1>
		
			<a href="/webapp2/setcolor?color=white">WHITE</a><br>
			<a href="/webapp2/setcolor?color=red">RED</a><br>
			<a href="./setcolor?color=green"> GREEN</a><br>
			<a href="./setcolor?color=cyan">CYAN</a><br>
   </body>
</html>