<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<script src="assets/js/myjs/browser.js"></script>
    <!-- BOOTSTRAP STYLES-->
    <link href="assets/css/bootstrap.css" rel="stylesheet" />
    <!-- FONTAWESOME STYLES-->
    <link href="assets/css/font-awesome.css" rel="stylesheet" />
    <link href="assets/css/mycss.css" rel="stylesheet" />
    <link href="assets/img/title.png" rel="icon" type="image/x-icon" />
    <style>
    	  *{margin:0;padding:0;}   
		 .parentCls{width:200px;}  
		 .auto-tip li{width:100%;height:22px;line-height:22px;font-size:14px;list-style-type:none; background:#FFF;}  
		 .auto-tip li.hoverBg{background:#ddd;cursor:pointer;}  
		 .red{color:black;}  /*下拉框补充*/
		 .hidden {display:none;}
    	 .capslock {
		    position: absolute;
		    z-index: 4;
		    display: inline;
		    width: 116px;
		    top: 32px;
		    left: 96px;
		    padding-left: 10px;
		    height: 26px;
		    line-height: 28px;
		    overflow: hidden;
		    background: #F9F900;
		}
		/* #pwdSpan{margin:0 0.5em;font-size:85.7%;} */
		.topArrow{
			width:0px;
			height:0px;
			border:solid 10px;
			border-color:#ffffff  #ffffff #F9F900 #ffffff ;
			display:inline-block;
			position:absolute;
			right: 220px;
		    z-index: 4;
		    top: 12px;
		}
		
		
    </style>

</head>
<body style="background-color: #FFFFFF;">
    <div class="container">
         <div class="row ">
              <div class="col-md-5 col-md-offset-3 col-sm-6 col-sm-offset-3 col-xs-10 col-xs-offset-1">
                   <div class="panel-body">   
	                   	<div class="row text-center " style="padding-top:120px;">
				            <div class="col-md-12">
				                <img src="assets/img/titlePic3.png" height="140"/>
				            </div>
	       				</div>                             
                      <hr />
                      <div class="form-group input-group ">
                           <span class="input-group-addon"><i class="fa fa-tag"  ></i></span>
                           <div class="parentCls">
                          	 <input type="text" class="form-control inputElem" id="uid" style="width:385px;height:40px;" placeholder="账号/邮箱" autocomplete="off"  />
                           </div>                          
                      </div>
                      <input class="form-control"  style="display: none"/>	
                      <div class="form-group input-group"style="position:relative">
                           <span class="input-group-addon"><i class="fa fa-lock"  ></i></span>
                           <input  id="password" class="form-control" style="width:390px;height:40px;" onfocus="this.type='password'" placeholder="密码"  autocomplete="off" />
                           <!-- <span class="capslock"><b></b>大小写锁定已打开</span> -->
                           <div id="pwdSpan" style="display:none;">
	                           <div class="topArrow"></div>
	                           <span  class="capslock">大写锁定已打开</span>
                           </div>
                      </div>                 
                      <div class="form-group input-group" style="width:450px;">
						   <input  type="text" name="checkCode" id="checkCode" class="form-control" style="width:150px;height:40px;" placeholder="验证码"/>
						   <span class="code_flag_right" id="right_code_flag">✔</span>
						   <span class="code_flag_wrong" id="wrong_code_flag">✘</span>
						   <!-- <i class="fa fa-check" id="checkIcon" style="display:none"></i> -->
						   <input id="flag" value="0" type="hidden"/>
						   <!-- <span ><i class="fa fa-check"  ></i></span> -->
						   <img alt="验证码" id="imagecode" style="padding-left:20%;width:45%;height:40px;" src="<%= request.getContextPath()%>/servlet/ImageServlet"/>
						   <a href="javascript:reloadCode();"> 看不清楚</a><br>
								    <!-- <input type="submit" value="提交"> -->
						    <!-- </form> -->
                      </div>
                      <div class="form-group" style="width:420px;">
                           <label class="checkbox-inline">
                               <input type="checkbox" id="saveUserName" onclick="saveUserOrNot()"/> 记住我
                           </label>
                           <span class="pull-right">
                                  <a href="findPassword.html" >忘记密码 ? </a> 
                           </span>
                       </div>
                       <div class="form-group input-group col-md-5">
                           <button onclick="login()" id="login" class="btn btn-primary" style="width:430px;height:40px;font-size:20px;">登 录 </button>
                       </div>    
                       <hr />
                       <a href="userRegisterPage.html" >没有账号？立即注册</a> 
                   </div>
               </div>
        </div>
    </div>
     <script src="assets/js/jquery-1.10.2.js"></script>
    <!-- BOOTSTRAP SCRIPTS -->
    <script src="assets/js/bootstrap.js"></script>
    <script src="assets/js/ve1.js"></script>
    <script src="assets/js/veHelper.js"></script>
    <script src="assets/js/simpleUtil.js"></script>
    <script src="assets/js/myjs/login.js"></script>
    <script src="assets/js/myjs/supplement.js"></script>
</body>
 <script>
 /* if(cVeUti.Cookie.getCookie("user_id")=="")			
		{
			alert("没有访问权限，请先登录");
			//window.location.href="login.html";
		} */
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
      	 var btn = document.getElementById("checkCode");
      	 btn.value = ""; 
		// btn.focus();
		 $("#right_code_flag").css("display","none");
      	 $("#wrong_code_flag").css("display","none");
         var time=new Date().getTime();
         document.getElementById("imagecode").src="<%= request.getContextPath()%>/servlet/ImageServlet?d="+time;
     }
  </script>
</html>
