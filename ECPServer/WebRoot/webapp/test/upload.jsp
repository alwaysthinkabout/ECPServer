<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件上传实例 </title>
</head>
<body>
<h1>文件上传实例 </h1>
<form method="post" id="uploadForm"  enctype="multipart/form-data">
	选择一个文件:
	<input type="file" name="fileData" /><br/>
	
	<input type="text" name="data" value="resumeFile,1000000025"/>
	<br/>
	<input type="button" id="upload" value="上传" />
</form>

<input type="button" id="deleteFile" value="删除文件" />

<a href="/ECPServer/download?name=test_20170508192356_adu.jpg&type=pictureFile" rel="nofollow">下载</a> <br/>  

<script type="text/javascript" src="../../js/common/jQuery1.7.js"></script>
<script>
$(function(){
	$("#upload").click(function(){
		var formData = new FormData($( "#uploadForm" )[0]); 
		
		$.ajax({  
	          url: '/ECPServer/UploadServlet' , 
	          //url:'http://125.216.242.9:8080/ECPServer/cloudmake/CloudMakeFileUpload',
	          type: 'POST',  
	          dataType:'text',
	          data: formData,  
	          async: false,  
	          cache: false,  
	          contentType: false,  
	          processData: false,  
	          success: function (returndata) {  
	              alert(returndata);  
	          },  
	          error: function (returndata) {  
	              alert(returndata);  
	          }  
	     });  
	});
});
</script>

</body>

</html>