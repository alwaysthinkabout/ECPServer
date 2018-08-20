<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <title>平台运营管理系统</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />

    <!-- BOOTSTRAP STYLES-->
    <link href="assets/css/bootstrap.css" rel="stylesheet" />
    <!-- FONTAWESOME STYLES-->
    <link href="assets/css/font-awesome.css" rel="stylesheet" />
    
    <link rel="stylesheet" href="assets/css/reset.css">
    <link rel="stylesheet" href="assets/css/supersized.css">
    <link rel="stylesheet" href="assets/css/style_login.css">
    
</head>
<body>
      <div class="page-container">
      	  <div class="box-bg" >
	          <div style="padding-top:50px;">
	          	  <div class="row" style="text-align: center;">
	          	  <h2>金英台运营管理系统</h2>
	          	  </div>
		          <form id="form_login">
		          	  <div class="form-group input-group" style="padding-top:30px;">
                           <span class="input-group-addon" ><i class="fa fa-tag"  ></i></span>
                           <input type="text" class="form-control" id="uid" placeholder="账号/邮箱" />
                      </div>
                      <div class="form-group input-group" style="padding-top:10px;">
                           <span class="input-group-addon"><i class="fa fa-lock"  ></i></span>
                           <input type="password" id="password" class="form-control" placeholder="密码" />
                      </div>
                      <div class="form-group input-group" style="width:400px; padding-top:10px;">
						   <input  type="text" name="checkCode" id="checkCode" class="form-control" style="width:110px;height:40px;" placeholder="验证码"/>
						   <span class="code_flag_right" id="right_code_flag">✔</span>
						   <span class="code_flag_wrong" id="wrong_code_flag">✘</span>
						   <!-- <i class="fa fa-check" id="checkIcon" style="display:none"></i> -->
						   <input id="flag" value="0" type="hidden"/>
						   <!-- <span ><i class="fa fa-check"  ></i></span> -->
						   <img alt="验证码" id="imagecode" style="padding-left:13%;width:40%;height:40px;" src="<%= request.getContextPath()%>/servlet/ImageServlet"/>
						   <a href="javascript:reloadCode();"> 看不清楚</a><br>
								    <!-- <input type="submit" value="提交"> -->
						    <!-- </form> -->
                      </div>
		          </form>
                  <div class="form-group input-group" style="width:400px;padding-left:95px;">
                      <label class="checkbox-inline">
                          <input type="checkbox" id="saveUserName" onclick="saveUserOrNot()"/> 记住我
                      </label>
                      <span class="pull-right">
                             <a href="content/common/findPasswordPage.jsp" >忘记密码 ? </a> 
                      </span>
                  </div>
	              <div class="row" style="text-align: center;">
	                <button onclick="login()" id="login" type="submit" >登 录 </button>
	              </div>
	              <div class="row" style="padding-top: 15px;padding-left:105px;">
	              	<a href="content/common/userRegisterPage.jsp" >没有账号？立即注册</a>
	              </div> 
	          </div>
          </div>
      </div>
      
    <!-- Javascript -->
    <script src="assets/js/jquery-1.11.1.min.js"></script>
    <script src="assets/js/jquery-1.8.2.min.js"></script>
    <script src="assets/js/supersized.3.2.7.min.js"></script>
    <script src="assets/js/supersized-init.js"></script>
    <script src="assets/js/scripts.js"></script>
    <script src="assets/js/myjs/login.js"></script>
    <script src="assets/js/ve1.js"></script>
    <script src="assets/js/veHelper.js"></script>
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
	          error: function (returndata) {
	              alert(returndata);
	              /* $("#right_code_flag").css("display","none");
	              $("#wrong_code_flag").css("display","inline-block"); */
	          },  
	          success: function (returndata) {
	      //    alert(returndata.length ==6);
	            if(returndata.length ==6){
	                $("#wrong_code_flag").css("display","none");
	            	$("#right_code_flag").css("display","inline-block");
	            }else{
	            	//alert("验证码错误！");
	            	$("#right_code_flag").css("display","none");
              		$("#wrong_code_flag").css("display","inline-block");
	            }
	          },  
	     });  
	});
	setUid();
 });   
   function reloadCode()
     {
         var time=new Date().getTime();
         document.getElementById("imagecode").src="<%= request.getContextPath()%>/servlet/ImageServlet?d="+time;
     }
  </script>
</html>
