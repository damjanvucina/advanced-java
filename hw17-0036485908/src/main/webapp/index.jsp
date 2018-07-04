<!DOCTYPE html>
<html>
<head>
<title>Galerija</title>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="js/htmlescaping.js"></script>
<script type="text/javascript">
<!--
	$(document).ready(function() {
		$.get("servlets/get-tags", function(data) {
			html = ""
			$.each(data, function(index, value) {
				html += "<input type='button' onclick=dohvatiThumb(this.value); value=" + value + " />";
			});
			$("#button").html(html);
		}); 
	});
	
function dohvatiThumb(name){
    var abc='hello';                
    $.ajax({
        type: "GET",
        url: "servlets/get-thumbnails",                
        dataType: "json",
        data: {"tag" : name},
        success:function(data){
        	html = ""
            if(data){
            	$.each(data, function(index, value) {
    				html += "<img src='servlets/display-thumbnail?name="+ value +"' onclick='showFullSize(value)'  />";
    			});
            	$("#thumbnail").empty();
            	$("#thumbnail").html(html);
            }
        }

    })       
};

	function acquireThumbnail(name) {
		$.get("servlets/get-thumbnails?tag=" + name, function(data) {
			html = ""
			$.each(data, function(index, value) {
				html += "<img src='servlets/display-thumbnail?name="+ value +" onclick='showFullSize(value);' alt=" + value + " width='150' height='150' />";
			});
			$("#button").html(html);
		}); 
	}

	
//-->
</script>
</head>
<body>
	<h1>Welcome to the US Cities Gallery!</h1>


	<div id="button">&nbsp;</div>
	<br>
	<div id="thumbnail">&nbsp;</div>
	<br>
	<div id="picture">&nbsp;</div>


</body>
</html>