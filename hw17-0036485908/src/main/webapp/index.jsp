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
				html += "<input type='button' onclick='dohvatiThumb(this.value)'; value=" + value + " />";
			});
			$("#button").html(html);
		}); 
	});
	
function dohvatiThumb(name){
    $.ajax({
        type: "GET",
        url: "servlets/get-thumbnails",                
        dataType: "json",
        data: {"tag" : name},
        success:function(data){
        	html = ""
            if(data){
            	$.each(data, function(index, value) {
    				html += "<img src='servlets/display-thumbnail?name="+ value +"&size=" + "thumbnail" +"'  name=" + value + " onclick='displayImage(this.name)'; />";
    			});
            	$("#picture").empty();
            	$("#thumbnail").empty();
            	$("#thumbnail").html(html);
            }
        }

    })       
};

function displayImage(name){
    $.ajax({
        type: "GET",
        url: "servlets/display-image",                
        dataType: "json",
        data: {"arg" : name},
        success:function(data){
        	html = ""
            if(data){
            		html+="name:"
            		html += data.name + "<br>"
            		html+="description:"
                	html += data.description + "<br>"
            		html+="tags:"
                    html += data.tags + "<br>"
                    html += "<img src='servlets/display-thumbnail?name="+ data.name +"&size="+"image" +"' />";
    			
            	$("#picture").empty();
            	$("#picture").html(html);
            }
        },
        error:function(data,status,er) {
            alert("bug neki: "+name);
        }

    })       
};

	
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