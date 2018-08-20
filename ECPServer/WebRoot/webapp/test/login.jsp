<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
    
    <title>My JSP 'login.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
  </head>
  
  <body>
    
    验证码:<input  type="text" name="checkCode" id="checkCode"/>
    <img alt="验证码" id="imagecode" src="/ECPServer/servlet/ImageServlet"/>
    <a href="javascript:reloadCode();">看不清楚</a><br>
    <button id="submit">提交</button>
    <script type="text/javascript" src="../../js/common/jQuery1.7.js"></script>
  </body>
  
  <script>
  $(function(){
	  $("#checkCode").blur(function(){
		  $.ajax({  
	          url: '/ECPServer/servlet/LoginServlet' , 
	          //url:'http://125.216.242.9:8080/ECPServer/cloudmake/CloudMakeFileUpload',
	          data: {
	        	  checkCode:$("#checkCode").val()	  
	          },
	          type: 'POST',  
	          success: function (returndata) {  
	              alert(returndata);  
	          },  
	          error: function (returndata) {  
	              alert(returndata);  
	          }  
	     });  
	});
	  });
   function reloadCode()
     {
         var time=new Date().getTime();
         document.getElementById("imagecode").src="<%= request.getContextPath()%>/servlet/ImageServlet?d="+time;
     }
  </script>
  
</html>
