<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
 <link rel="stylesheet"  type="text/css" href="CssFiles/FolderStyle.css">
<title>Folder Directory</title>
</head>
<body>

	<form action="AddNewFolder" method="post">
	<span  class="inputClass">New Folder: </span><input class="formEdit" type="text" name="name">
	<input type="hidden" name="currentId" value="${currentId}">
	<input class="buttonEdit" type='submit' value='Create'>
	</form>
</body>
</html>