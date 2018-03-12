<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
 <link rel="stylesheet"  type="text/css" href="CssFiles/FolderStyle.css">
<title>Folder Directory</title>
</head>
<body>
	<form  action='EditFolder' method='post'>
		<span class="inputClass">Edit Folder:</span> 
		<input class="formEdit" type="text" name="name" value="${fileName}">
		<input type="hidden" name="folderId" value="${currentId}">
		<input class="buttonEdit" type="submit" value="EDIT">
		</form>
		
</body>
</html>