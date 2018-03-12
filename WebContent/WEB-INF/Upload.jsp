<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
 
  <link rel="stylesheet"  type="text/css" href="CssFiles/FolderStyle.css">
<title>Folder Directory</title>
</head>
<body>
	
	<form  action='UploadFile' method='post' enctype='multipart/form-data'>
	<span class="inputClass" >File:</span> 
	<input class="formEdit" type='file' name = 'file'>
	<input type='hidden' name='folder' value="${currentId}">
	<input class="buttonEdit" type='submit' value='Upload'>
	</form>
		
</body>
</html>