<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login Page</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css ">
 <link rel="stylesheet"  type="text/css" href="CssFiles/FolderStyle.css">
 
</head>

<body>
	<h2>Login Page!Enter the Credentials</h2>
	<form  class="form-inline" action="LoginUser" method = "post">
	<div class="form-group">
    <label for="username">Username</label>
	<input class="form-control" type = "text" name="name" >
    <label for="exampleInputEmail1">Password</label>
	<input class="form-control" type="password" name="password">
	<input class="btn btn-default" type="submit" value="Login">
	</div>
		
	
	</form>
</body>
</html>