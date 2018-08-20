<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>平台运营管理系统</title>
    <!-- BOOTSTRAP STYLES-->
    <link href="../../assets/css/bootstrap.css" rel="stylesheet" />
    <!-- FONTAWESOME STYLES-->
    <link href="../../assets/css/font-awesome.css" rel="stylesheet" />
    
    <link rel="stylesheet" href="../../assets/css/bootstrap.cosmo.css">
    <link rel="stylesheet" href="../../assets/css/font-awesome.css">
    <link rel="stylesheet" href="../../assets/css/main.css">
    <link rel="stylesheet" href="../../assets/css/darkblue.css">
    <link rel="stylesheet" href="../../assets/css/style.css">
    
    <style>
        .modal_form_select_mul{height:120px!important;width:314px;outline: none !important;}
    </style>
</head>
<body>
<div class="navbar navbar-default top-bar" role="navigation">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <!-- responsive menu bar icon -->
        <a href="javascript:void(0);" class="hidden-md hidden-lg main-nav-toggle">
            <i class="fa fa-bars"></i>
        </a>
        <!-- end responsive menu bar icon -->
        <a class="navbar-brand" href="#">
            <img src="../../assets/img/logo4.png" alt='金英台运营管理系统' class="hidden-xs-my">
            <span>金英台运营管理系统</span>
        </a>
    </div>
</div><!-- /top -->
    <div id="wrapper">
        <div id="page-wrapper">
            <div id="page-inner">
                <div class="row">
                    <div class="col-md-12" style="display:inline-block;padding-top:5%;">
                        <h1><b></b></h1>
                    </div>
                <!-- /. ROW  -->
               <div class="row">
                <div class="col-md-12" >
                <form id="userRegisterForm">
                	<div class="row"></div>
                	<div class="col-md-3"></div>
                    <div class="panel panel-default col-md-5">
                        <div class="panel-body">
                            <div class="form-group row">
                            	<div class="col-md-12 row">
			                        <label><h2><b>忘记密码</b></h2></label>
			                        <hr />
			                    </div>
			                    <div class="col-md-12 row">
			                        <h1><b></b></h1>
			                    <div id="firstStep">
				                    <div class="form-group row" id="verify_email">
				                     <div class="col-md-3"style="display:inline-block;width:17.5%;padding-right:5px;text-align:right;">
				                        <label>邮箱</label>
				                     </div>
				                     <div class="col-md-8 parentCls" style="display:inline-block;width:66.7%;padding-right:5px">
				                        <input class="form-control inputElem" style="display:inline-block;" id="email" name="email" type="text" autocomplete="off"/>
				                     </div> 
				                     <div style="display:inline-block;width:8%;">       
				                        <button type="button"  class="btn btn-default" onclick="showCodeInput()">验证</button>
				                     </div>         
				                </div>
				                <div class="form-group row" id="verify_code"style="display:none">
			                        <div class="col-md-3" style="display:inline-block;width:17.5%;padding-right:5px;text-align:right;">
			                            <label>验证码</label>
			                        </div>
			                        <div class="col-md-8" style="display:inline-block;width:66.7%;padding-right:5px">
			                        	<input class="form-control" style="display:inline-block" id="code" name="code" type="text" />
			                        	<!-- <span class="flag_wrong" id="code_flag">x</span> -->
			                        </div>	                        
			                    </div>
		                    </div>
		                    <div id="nextStep"  style="display:none">
			                    <div class="form-group row">
			                        <div class="col-md-3" style="display:inline-block;width:17.5%;padding-right:5px;text-align:right;">
			                            <label>重置密码</label>
			                        </div>
			                        <div class="col-md-8"style="display:inline-block;width:66.7%;padding-right:5px;position:relative">
			                        	<input class="form-control" id="password" name="password" type="password" />
			                        	<div id="pwdSpan" style="display:none;">
				                           <div class="topArrow"></div>
				                           <span  class="capslock">大写锁定已打开</span>
			                           </div>
			                        </div>
			                    </div>
			                    <div class="form-group row">
			                        <div class="col-md-3" style="display:inline-block;width:17.5%;padding-right:5px;text-align:right;position:relative">
			                            <label>密码确认</label>
			                        </div>
			                        <div class="col-md-8"style="display:inline-block;width:66.7%;padding-right:5px">
			                        	<input class="form-control" id="password1" name="password1" type="password" />
			                        	<div id="pwdSpan1" style="display:none;">
				                           <div class="topArrow"></div>
				                           <span  class="capslock">大写锁定已打开</span>
			                            </div>
			                        </div>
			                    </div>	
		                    </div>	                    			                
		                    <hr />
		                    <div class="form-group row" style="text-align:center">
						        <!-- <div class="col-md-4"></div> -->
						        <button type="button" class="btn btn-info" id="nextStep_btn"onclick="nextStep()">下一步</button>
						        <button type="button" class="btn btn-primary" style="display:none;" id="setNewPassword_btn"onclick="setNewPassword()">确 定</button>
						        <button type="button" class="btn btn-danger" style="margin-left:10px;"onclick="goBack()">取 消</button>
				            </div>
		                    </div>
                        </div>
                       </div>
                      </div>
                    </form>
                </div>
            </div>
            </div>
            <!-- /. PAGE INNER  -->
        </div>
        <!-- /. PAGE WRAPPER  -->
    </div>
    <!-- /. WRAPPER  -->
    <!-- SCRIPTS -AT THE BOTOM TO REDUCE THE LOAD TIME-->
    <!-- JQUERY SCRIPTS -->
    <!-- modal start  -->
    <div class="modal fade" id="chooseUid" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document" style="padding-left:10%;padding-top:10%;">
				<div class="modal-content" style="width:600px;"> 					
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<p class="modal-title" id="myModalLabel">选择您的账号</p>
					</div>
					<div class="modal_form_content">
						<p style="font-size: 20px;margin-left:2%;color:red; ">请从以下账号中选择一个作为您的登录账号：</p>
					    	<div>
					    		<!-- <span class="lab_text text-right"></span> -->
					    		<select style="margin-left:13%;"class="modal_form_select_mul" multiple="multiple" id="ids"></select>
					    	</div>
						<div class="modal-footer">
					    <button type="button" class="btn  btn-default" data-dismiss="modal" >取  消</button>
						<button type="button" class="btn btn-danger"  onclick="confirmUid()">确 定</button>
						</div>
					</div>
					</div>
			</div>
		</div>
    <!-- modal end-->
    <script src="../../assets/js/jquery-1.11.1.min.js"></script>
    <!-- BOOTSTRAP SCRIPTS -->
    <script src="../../assets/js/bootstrap.js"></script>
    <!-- METISMENU SCRIPTS -->
    <script src="../../assets/js/simpleUtil.js"></script>
    
</body>
</html>
<content tag="scripts">
<script src="../../assets/js/ve1.js"></script>
<script src="../../assets/js/veHelper.js"></script>
<script src="../../assets/js/simpleUtil.js"></script>
<script>
	    var verify_code = "";
	    function nextStep()
	    {
	    	 var code = document.getElementById("code").value;
	    	 var temp = document.getElementById("email");    //对电子邮件的验证
	         var inputValue = temp.value;
			 var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
			 if(inputValue==""){
	             alert("请填入邮箱");
	             temp.focus();
	             return;
	         }else if(!myreg.test(temp.value))
			 {
		        alert("请输入有效的E_mail！");
		        temp.focus();
	            return false;
             }
	    	if(document.getElementById("verify_code").style.display=="none")
    		{
	    		alert("请完成验证！");
    		    return;
    		}
	    	if(code=="")
    		{
    		    alert("请填入验证码！");
    		    document.getElementById("code").focus();
    		}else if(verify_code!=code)
    			{
    			   alert("验证码不正确！");
    			   document.getElementById("code").focus();
    			}else
    				{
    				 $("#nextStep").css("display","block");
    				 $("#firstStep").css("display","none");
    				 $("#nextStep_btn").css("display","none");
    				 $("#setNewPassword_btn").css("display","inline-block");
    				}
    }
	    
		function setNewPassword(){		
		   var password = document.getElementById("password").value;
		   var password1 = document.getElementById("password1").value;
		   if(password=="")
		   {
		       alert("请填写密码");
		       return;
		   }		   
			var flag_pwd = 0;
			var chineseWord = 0;
			var numReg = /^[0-9]+$/;
			var charReg = /^[a-z]+$/;
			var bCharReg = /^[A-Z]+$/;
			var chineseReg  =/[^\u0000-\u00FF]/;
			for (var i = 0; i < password.length; i++) { 
				if(chineseReg.test(password.substr(i, 1))){
					chineseWord +=1;
					break;
				}
			} 
			if(chineseWord==1){
			    alert("密码不能包含中文");
			    return;
			}
			var specialReg  =/[`~!@#$%^&*_+<>{}\/'[\]]/m;
			for (var i = 0; i < password.length; i++) { 
				if(numReg.test(password.substr(i, 1))){
					flag_pwd +=1;
					break;
				}
			} 
			for (var i = 0; i < password.length; i++) { 
				if(charReg.test(password.substr(i, 1))){
					flag_pwd +=1;
					break;
				}
			} 
			
			for (var i = 0; i < password.length; i++) { 
				if(bCharReg.test(password.substr(i, 1))){
					flag_pwd +=1;
					break;
				}
			} 
			
			for (var i = 0; i < password.length; i++) { 
				if(specialReg.test(password.substr(i, 1))){
					flag_pwd +=1;
					break;
				}
			} 
			if(password.length>20||password.length<6||flag_pwd<2)
			{
			   alert("密码长度限于6至20之间，且密码至少由大小写、数字和特殊字符中的2种组成！");
			   return;
			}
		   if(password1=="")
		   {
		       alert("请确认密码");
		       return;
		   }
		   if(password!=password1)
		   {
			   alert("两次密码输入不一致！");
		       return;
		   }		  
		  cVe.startReqByMsgHandle(cVeName,'','','reqSetNewPassword','resSetNewPassword','ECP.handle.UserHandle.handleMsg');			
		}
		
		function reqSetNewPassword(){
			var req = {};
			req["user_type"] = "机构";
			req["op"] = "findPassword";
			req["newEmail"] = document.getElementById("email").value;
			req["password"] = document.getElementById("password").value;
			cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
		}
		
		function resSetNewPassword(){
			var retData =cVe.XHR.responseText;
			var retDataObj=JSON.parse(retData);
			var result=retDataObj[cVe.cEioVeDataId];
			if(result.result=="0"){
				alert("密码修改成功，友情提示：您的账号为 "+result.user_id);
				window.location.href="../../login.jsp";
			}else{
				alert(result.msg);
				document.getElementById("code").value = "";
				document.getElementById("password").value = "";
				document.getElementById("password1").value = "";
				$("#nextStep_btn").css("display","inline_block");
				 $("#setNewPassword_btn").css("display","none");
				$("#nextStep").css("display","none");
				$("#firstStep").css("display","block");
				$("#verify_code").css("display","none");
				var btn = document.getElementById("email"); 
				btn.focus();
				//window.location.href="userRegisterPage.jsp";
				//$("#chooseUid").modal('show');
			}
		}
	
		function goBack(){
			window.location.href="../../login.jsp";
		}
		
		function showCodeInput(){		         
		         var temp = document.getElementById("email");    //对电子邮件的验证
		         var inputValue = temp.value;
				 var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
				 if(inputValue==""){
		             alert("请填入邮箱");
		             return;
		         }else if(!myreg.test(temp.value))
				 {
			        alert("请输入有效的E_mail！");
			        temp.focus();
		            return false;
	             }else{
		             verifyBeforeRegiste();  
		         }
		}
		
		function verifyBeforeRegiste(){	
		         cVe.startReqByMsgHandle(cVeName,'','','reqVerifyBeforeRegiste','resVerifyBeforeRegiste','ECP.handle.common.CCommonHandle.handleMsg');
		}
		
		function reqVerifyBeforeRegiste(){
		    var req = {};
		    req["op"] = "email_verify";
		    req["email"] = document.getElementById("email").value;
		    cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
		}
		
		function resVerifyBeforeRegiste(){
			var retData =cVe.XHR.responseText;
			var retDataObj=JSON.parse(retData);
			var result=retDataObj[cVe.cEioVeDataId];
			if(result.result=="success"){
				verify_code = result.identify_check;
				alert("已将验证码发送至您的邮箱，请将验证码填入相应位置。");
				document.getElementById("code").value = "";
		        $("#verify_code").css("display","block"); 
			}else{
				alert("验证失败，请确认邮箱是否输入正确！");
			}
		}
		document.onkeydown=function(event) { 
			/*var pathName = window.location.pathname;
			var len = pathName.split("/").length; */
			var hasFocus = $("#password").is(':focus');//当前光标是否处于密码输入框内
			var hasFocus1 = $("#password1").is(':focus');
			if(event.keyCode==20&&hasFocus&&document.getElementById("pwdSpan").style.display=="inline")
			{
				document.getElementById("pwdSpan").style.display="none";
			}else if(event.keyCode==20&&hasFocus1&&document.getElementById("pwdSpan1").style.display=="inline")
			{
				document.getElementById("pwdSpan1").style.display="none";
			}
		};
		$("#password").blur(function()
				{
					document.getElementById("pwdSpan").style.display="none";
				});
		$("#password1").blur(function()
				{
					document.getElementById("pwdSpan1").style.display="none";
				});
		function  detectCapsLock(event){
		    var e = event||window.event;
		    //var o = e.target||e.srcElement;
		    var oTip = document.getElementById("pwdSpan");
		    var oTip1 = document.getElementById("pwdSpan1");
		    var hasFocus = $("#password").is(':focus');//当前光标是否处于密码输入框内
			var hasFocus1 = $("#password1").is(':focus');
		    var keyCode  =  e.keyCode||e.which; // 按键的keyCode 
		    var isShift  =  e.shiftKey ||(keyCode  ==   16 ) || false ; // shift键是否按住
		     if (
		     ((keyCode >=   65   &&  keyCode  <=   90 )  &&   !isShift) // Caps Lock 打开，且没有按住shift键 
		     || ((keyCode >=   97   &&  keyCode  <=   122 )  &&  isShift)// Caps Lock 打开，且按住shift键
		     ){
		    	 if(hasFocus)
		    		 {
		    		 	oTip.style.display = 'inline';
		    		 }else if(hasFocus1){
		    			 oTip1.style.display = 'inline';
		    		 }
		      }else{
		    	  if(hasFocus)
		    		 {
		    		 	oTip.style.display = 'none';
		    		 }else if(hasFocus1){
		    			 oTip1.style.display = 'none';
		    		 }
		    	  } 
		}
		document.getElementById('password').onkeypress = detectCapsLock;
		document.getElementById('password1').onkeypress = detectCapsLock;
	</script>
</content>