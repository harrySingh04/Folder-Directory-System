<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Sign up Page</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css ">
 <link rel="stylesheet"  type="text/css" href="CssFiles/FolderStyle.css">
 <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script>
$(function(){
    // Add your code here
    
    $("#username").blur(function(){
    	$( '#usernameStatus' ).html("");
    	var name=  $(this).val(); 
    	//document.write(name);
	     $.ajax({
	           url: "AjaxUserCheck",
	           data: {
	              "name" : name
	           },
	           success: function(data){
					if(data=='true')
						{
						$( '#usernameStatus' ).html("");
						
						}
					else
						{
						
						$( '#usernameStatus' ).html("Username Alreay exist.Please select another username");	
							
						}
	           }
	       });
    });
});
</script>
 
 
</head>
<body>
	<h2> Welcome to User Registration Page</h2>
	<form  class="form-inline" action="SignUp" method = "post">
	<div class="form-group">
    <label for="username">Username</label>
	<input id="username" class="form-control" type = "text" name="name" >
    <label for="exampleInputEmail1">Password</label>
	<input class="form-control" type="password" name="password">
	<button class="btn btn-default" id="signup">Sign up</button>
	<p id="usernameStatus"></p>
	</div>

	</form>
</body>
</html>