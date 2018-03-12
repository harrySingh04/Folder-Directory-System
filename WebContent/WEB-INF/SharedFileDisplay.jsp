<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Folder Directory</title>
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css ">
<link rel="stylesheet"  type="text/css" href="CssFiles/FolderStyle.css">
</head>
<body>
	
	<div class="div1">
	
	<a href="FolderDisplay?currentId=${currentId}">Back</a>
	
	</div>
	
	<a class="logout" href="LogoutHome">Logout</a>
	
	<br/>
	<br/>
	<br/>
	<br/>
	
	
	<table class="table table-hover" >
	
	<tr><th>Name</th>
		<th>Date</th>
		<th>Size</th>
	</tr>
	
	
	<c:forEach items="${file}" var="f" varStatus="status">
	
		<tr>
			<td>
				<img src="file_extension_txt.png"><a href="DownloadFile?currentId=${f.getId()}">${f.getName()}</a>
			</td>
	
			<td>
				<fmt:formatDate value="${f.getDate()}"
       				 pattern="MM/dd/YYYY hh:mm a" />
  		  </td>

			<td>	
				<c:if test="${f.getSize()>='1024'}">
					<fmt:formatNumber value="${f.getSize()/1024}" minFractionDigits="0" maxFractionDigits = "0" /> KB
				</c:if>
	
				<c:if test="${f.getSize()<'1024'}">
				${f.getSize()} B
				</c:if>
	
			</td>
	
		</tr>
	</c:forEach>
	</table>
</body>
</html>