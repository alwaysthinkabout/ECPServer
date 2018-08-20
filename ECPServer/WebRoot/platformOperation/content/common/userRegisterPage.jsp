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
<!--        CUSTOM BASIC STYLES
    <link href="../../assets/css/basic.css" rel="stylesheet" /> -->
    
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
                	<div class="col-md-3" style="display:inline-block;width:29%;"></div>
                    <div class="panel panel-default col-md-5" >
                        <div class="panel-body">
                            <div class="form-group row">
                            	<div class="col-md-12 row">
			                        <label><h2><b>用户注册</b></h2></label>
			                        <hr />
			                    </div>
			                    <div class="col-md-12 row">
			                        <h1><b></b></h1>
			                    </div>			                    
			                        <div >
				                        <div class="col-md-3"style="display:inline-block;width:17.5%;padding-right:5px;text-align:right;">
				                            <label>账号</label>
				                        </div>
				                        <div class="col-md-8" style="display:inline-block;width:60%;padding-right:5px">
				                            <input class="form-control" id="user_id" name="user_id" placeholder="账号" readonly="readonly" type="text" />
				                        </div>
				                        <div style="display:inline-block;width:8%;">
		                        			<button type="button" id="getUids"style="display:inline-block" class="btn btn-default" onclick="getUid()">获取账号</button>
		                       			</div>
			                        </div>
		                    </div>
		                    <div class="form-group row">
		                        <div class="col-md-3" style="display:inline-block;width:17.5%;padding-right:5px;text-align:right;">
		                            <label>密码</label>
		                        </div>
		                        <div class="col-md-7"style="display:inline-block;width:60%;padding-right:5px">
		                        	<input class="form-control" id="password" name="password" type="password" />
		                        </div>
		                    </div>
		                    <div class="form-group row">
		                        <div class="col-md-3" style="display:inline-block;width:17.5%;padding-right:5px;text-align:right;">
		                            <label>密码确认</label>
		                        </div>
		                        <div class="col-md-7"style="display:inline-block;width:60%;padding-right:5px">
		                        	<input class="form-control" id="password1" name="password1" type="password" />
		                        </div>
		                    </div>
		                    <div class="form-group row">
		                        <div>
			                        <div class="col-md-3" style="display:inline-block;width:17.5%;padding-right:5px;text-align:right;">
			                            <label>验证方式 </label>
			                        </div>
			                        <div class="col-md-8" style="display:inline-block;width:66.7%;padding-right:5px">
			                            <label><input id="verifyWay" name="verifyEamil" style="margin-right:5px;margin-left:60px"type="radio" value="eamil" onclick="selectWay('eamil')" />邮箱</label>
			                        	<label><input id="verifyWay" name="verifyTelephone" style="margin-right:5px;margin-left:50px" type="radio" value="telephone" onclick="selectWay('telephone')"/>手机 </label>
			                        </div>
		                        </div>
		                    </div>
			                <div class="form-group row" id="verify_email"style="display:none">
			                     <div class="col-md-3"style="display:inline-block;width:17.5%;padding-right:5px;text-align:right;">
			                        <label>邮箱</label>
			                     </div>
			                     <div class="col-md-8" style="display:inline-block;width:60%;padding-right:5px">
			                        <input class="form-control" style="display:inline-block;" id="email" name="email" type="text" />
			                     </div> 
			                     <div style="display:inline-block;width:8%;">       
			                        <button type="button"  class="btn btn-default" onclick="showCodeInput('email')">验证</button>
			                     </div>         
			                </div>
		                    <div class="form-group row" id="verify_telephone"style="display:none">
		                        <div class="col-md-3"style="display:inline-block;width:17.5%;padding-right:5px;text-align:right;">
		                            <label>手机号码</label>
		                        </div>
		                        <div class="col-md-8" style="display:inline-block;width:60%;padding-right:5px">
		                        	<input class="form-control" style="display:inline-block" id="telephone_num" name="telephone_num" type="text" />
		                        </div>
		                        <div style="display:inline-block;width:8%;">
		                        	<button type="button" style="display:inline-block" class="btn btn-default" onclick="showCodeInput('telephone')">验证</button>
		                        </div>
		                    </div>
		                    <div class="form-group row" id="verify_code"style="display:none">
		                        <div class="col-md-3" style="display:inline-block;width:17.5%;padding-right:5px;text-align:right;">
		                            <label>验证码</label>
		                        </div>
		                        <div class="col-md-8" style="display:inline-block;width:60%;padding-right:5px">
		                        	<input class="form-control" style="display:inline-block" id="code" name="code" type="text" />
		                        	<!-- <span class="flag_wrong" id="code_flag">✘</span> -->
		                        </div>
		                        
		                    </div>
		                    <hr />
		                    <div class="form-group row" style="text-align:center">
						        <!-- <div class="col-md-4"></div> -->
						        <button type="button" class="btn btn-info" onclick="userRegister()">注 册</button>
						        <button type="button" class="btn btn-danger" style="margin-left:10px;"onclick="goBack()">取 消</button>
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
		function userRegister(){		
		   var nick_name = document.getElementById("user_id").value;
		   var password = document.getElementById("password").value;
		   var password1 = document.getElementById("password1").value;
		   if(nick_name=="")
		   {
		       alert("请获取账号");
		       return;
		   }
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
		   if(document.getElementById("code").style.display=="none"||document.getElementById("code").value=="")
		   {
		       alert("请完成验证");
		       return;
		   }
		   if(verify_code==document.getElementById("code").value ){
				var password=document.getElementById("password").value;
				var password1=document.getElementById("password1").value;
				if(password!=password1){
					alert("两次密码输入不一致，请重新输入！");
					$("#password").val("");
					$("#password1").val("");
				}else{
					cVe.startReqByMsgHandle(cVeName,'','','reqRegister','resRegister','ECP.handle.UserHandle.handleMsg');
				}
		   }else{
			   alert("验证码输入错误！");
		       //msgbox("提示",3,"验证码输入错误");
		   }
		}
		
		function reqRegister(){
			var req = {};
			req["user_type"] = "平台";
			req["op"] = "register";
			req["user_id"] = document.getElementById("user_id").value;
			req["password"] = document.getElementById("password").value;
			if(select =="0")
			{
			    req["newEmail"] = document.getElementById("email").value;
			    req["phone"] = "";
			}else if(select="1")
			        {
			             req["phone"] = document.getElementById("telephone_num").value;
			             req["email"] = "";
			        }
			cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
		}
		
		function resRegister(){
			var retData =cVe.XHR.responseText;
			var retDataObj=JSON.parse(retData);
			var result=retDataObj[cVe.cEioVeDataId];
			if(result.result=="0"){
				alert(result.msg+"，请牢记您的账号："+document.getElementById("user_id").value+"，您可用账号或邮箱登录。");
				window.location.href="../../login.jsp";
			}else{
				alert("邮箱不可重复注册，请输入新的邮箱。");
				document.getElementById("code").value = "";
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
		
		//选择验证方式
		var select = "";//0代表邮件，1代表电话
		function selectWay(way){
		    if(way=="eamil"){
		        select = "0";
		        document.getElementById("telephone_num").value = "";
		        document.getElementById("email").value = "";
		        $("#verify_email").css("display","block");
		        $("#verify_telephone").css("display","none");
		        $("#verify_code").css("display","none");
		    }
		    if(way=="telephone"){
		        /*  select = "1";
		         document.getElementById("telephone_num").value = "";
		         document.getElementById("email").value = "";		        
		         $("#verify_telephone").css("display","block");
		         $("#verify_email").css("display","none");
		         $("#verify_code").css("display","none"); */
		         alert("手机验证暂未开通，请用邮箱验证。");
		         select = "0";
		         $("input[name='verifyEamil']").eq(0).attr("checked","checked");
		         $("input[name='verifyTelephone']").eq(0).removeAttr("checked");
		         document.getElementById("telephone_num").value = "";
		         document.getElementById("email").value = "";
		         $("#verify_email").css("display","block");
		         $("#verify_telephone").css("display","none");
		         $("#verify_code").css("display","none");
		    }
		}
		
		function showCodeInput(way){
		    
		     if(way=="email"){
		         var inputValue = document.getElementById("email").value;
		         if(inputValue==""){
		             alert("请填入邮箱");
		         }
		         else{
		             verifyBeforeRegiste();  
		         }
		     }else if(way=="telephone"){
		         var inputValue = document.getElementById("telephone_num").value;
                 var mobile=/^((13[0-9]{1})|159|153)+\d{8}$/;
                 if(inputValue==""){		             
		        	 alert("请填入手机号");
		         }else if(!mobile.test(inputValue)){		        	 
	        			 alert("请填入正确的手机号");
		         }else{
		            document.getElementById("code").value = "";
		            $("#verify_code").css("display","block");   
		         }
		      }
		}
		
		function verifyBeforeRegiste(){	
			 var temp = document.getElementById("email");    //对电子邮件的验证
			 var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
			 if(!myreg.test(temp.value))
			 {
		        alert("请输入有效的E_mail！");
		        temp.focus();
	            return false;
             }
			 else{
		         cVe.startReqByMsgHandle(cVeName,'','','reqVerifyBeforeRegiste','resVerifyBeforeRegiste','ECP.handle.common.CCommonHandle.handleMsg');
			 }
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
		
		function getUid(){		     
		         cVe.startReqByMsgHandle(cVeName,'','','reqGetUid','resGetUid','ECP.handle.UserHandle.handleMsg');
		}
		
		function reqGetUid(){
		    var req = {};
		    req["op"] = "getUserIds";
		    //req["email"] = document.getElementById("email").value;
		    cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
		}
		
		function resGetUid(){
			var retData =cVe.XHR.responseText;
			var retDataObj=JSON.parse(retData);
			var result=retDataObj[cVe.cEioVeDataId];
			if(result.user_ids.length>=0){
				var user_ids = document.getElementById("ids");
				user_ids.options.length=0;
				//contactModelNameList.options.add(new Option("请选择",""));
				for(var i = 0; i<result.user_ids.length; i++){
					var retData =result.user_ids[i];
						user_ids.options.add(new Option(retData,retData));
				}
				$("#chooseUid").modal('show'); 
			}else{
				msgbox("提示",3,"账号获取失败");
			}
		}
		
		function confirmUid()
		{
		    document.getElementById("user_id").value = document.getElementById("ids").value;
		    document.getElementById("getUids").innerHTML = "重新获取";
		    $("#chooseUid").modal('hide');
		}
	</script>
</content>