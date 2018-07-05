<!DOCTYPE html>
<html>
<head>
<title>Galerija</title>
  <link rel="stylesheet" href="main.css">
  <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="js/htmlescaping.js"></script>
<script type="text/javascript">
<!--
	$(document).ready(function() {
		$.get("rest/tags", function(data) {
			html = ""
			$.each(data, function(index, value) {
				html += "<input class='mybutton mybutton2' type='button' onclick='acquireThumbnail(this.value)'; value=" + value + " />";
			});
			$("#button").html(html);
		}); 
	});
	
function acquireThumbnail(name){
    $.ajax({
        type: "GET",
        url: "servlets/get-thumbnails",                
        dataType: "json",
        data: {"tag" : name},
        success:function(data){
        	html = ""
            if(data){
            	$.each(data, function(index, value) {
    				html += "<img class='thumbnail' src='servlets/display-thumbnail?name="+ value +"&size=" + "thumbnail" +"'  name=" + value + " onclick='displayImage(this.name)'; />";
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
        url: "rest/info/"+name,                
        dataType: "json",
        data: {"arg" : name},
        success:function(data){
        	html = ""
            if(data){
            		html += "<div class='w3-panel w3-leftbar w3-sand w3-xxlarge w3-serif'>"
            		html+="<div class='w3-tag w3-round w3-blue w3-border w3-border-white'>"
            		html+="name:"
            		html+="</div>"
            		html += data.name + "<br>"
            		html+="<div class='w3-tag w3-round w3-blue w3-border w3-border-white'>"
            		html+="description: "
            		html+="</div>"
                	html += data.description + "<br>"
                	html+="<div class='w3-tag w3-round w3-blue w3-border w3-border-white'>"
            		html+="tags: "
            		html+="</div>"
                    html += data.tags + "<br>"
                    html+="</div>"
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
	<h1 class="title">Welcome to the US Cities Gallery!</h1>


	<div id="button">&nbsp;</div>
	<br>
	<div id="thumbnail">&nbsp;</div>
	<br>
	<div id="picture">&nbsp;</div>


</body>
</html>