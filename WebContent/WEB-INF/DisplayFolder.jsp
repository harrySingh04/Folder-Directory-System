<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css ">
<link rel="stylesheet"  type="text/css" href="CssFiles/FolderStyle.css">

<title>Folder Directory</title>

<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script>
$(function(){
    // Add your code here
    
    $("#share").click(function(){
    	
    	 // var folderId = $("#fileId").val();
		  var userToShare = prompt("Enter the username to share the file with:");
		  var row = $(this).closest("tr");
	      $.ajax({
	           url: "AjaxFileShare",
	           data: {
	        	   "id" : row.attr("data-entry-id"),
	               "userName" : userToShare
	           },
	           success: function(){
	               
	           }
	       });
    });
    
    $("#delete").click(function()
    {
    	
    	// var folderId = $("#fileId").val();
    	 var row = $(this).closest("tr");
    	 //document.write(folderId);
     $.ajax({
    		 
    		 url : "AjaxDelete",
    		 data : {
    			 
    			 "id" : row.attr("data-entry-id")
    			 
    		 },
    		 
    		 success : function()
    		 {
    			 
    			 row.remove();
    		 }
    	 });
    	 
    	
    });
      
});
</script>
</head>
<body>
	
	<c:if test="${currentId == 0}">
	
	<div id="addDiv" class="div1">
	 <span>[</span><a href="AddNewFolder?currentId=${currentId}">New Folder</a>
	<%-- <span>[</span><a id="add" href="#">New Folder</a>--%>
	<span>|</span>
	<a href="UploadFile?folderId=${currentId}">UploadFile</a>
	<span>]</span>
	<span><a href="SharedFiles?userId=${userId}&currentId=${currentId}">Shared Files</a></span>
	</div>
	
	</c:if>
	
	
	<c:if test="${currentId != 0}">
	
	<div class="div1">
	
	<a href="FolderDisplay?currentId=${parentId}">..</a>
	
		
		<span>\</span> ${currentFileName}
		<span>[</span>
		<a href="AddNewFolder?currentId=${currentId}">New Folder</a>
		<span>|</span>
		<a href="UploadFile?folderId=${currentId}">UploadFile</a>
		<span>]</span>
		<span><a href="SharedFiles?userId=${userId}&currentId=${currentId}">Shared Files</a></span>
	</div>
	
	</c:if>
	
	<a class="logout" href="LogoutHome">Logout</a>
	<br/>
	<br/>
	<br/>
	<br/>
	
	
	<table class="table table-hover" >
	
	<tr><th>Name</th>
		<th>Date</th>
		<th>Size</th>
		<th>Operations</th>
	</tr>
	
	
	<c:forEach items="${file}" var="f" varStatus="status">
	
	<tr id="tableRow" data-entry-id="${f.id}">

	<c:if test="${f.getParent()==currentId}">
	
	<td>
	
	<c:if test="${f.isFolder()}">
		<img src="folder_green.png"><a href="FolderDisplay?currentId=${f.getId()}">${f.getName()}</a>
	
	</c:if>
	
	<c:if test="${!f.isFolder()}">
		<img src="file_extension_txt.png"><a href="DownloadFile?currentId=${f.getId()}">${f.getName()}</a>
	</c:if>
	
	</td>
	
	
	<td>
	<fmt:formatDate value="${f.getDate()}"
        pattern="MM/dd/YYYY hh:mm a" />
    </td>
	<td>
	
	<c:if test="${!f.isFolder()}">
	
	
	<c:if test="${f.getSize()>='1024'}">
	<fmt:formatNumber value="${f.getSize()/1024}" minFractionDigits="0" maxFractionDigits = "0" /> KB
	</c:if>
	<c:if test="${f.getSize()<'1024'}">
	${f.getSize()} B
	</c:if>
	</c:if>
	</td>
	
	<td>
	<img src="edit_button.png"><a href="EditFolder?folderId=${f.getId()}">Rename</a>
	<span>|</span>
	<img src="delete.png"><a id="delete" href="#">Delete</a>
	
	<c:if test="${!f.isFolder()}">
	<span>|</span>
	<a id="share" href="#" >Share File</a>
	</c:if>


	</td>
	</c:if>
	</tr>
	</c:forEach>
	</table>
</body>
</html>