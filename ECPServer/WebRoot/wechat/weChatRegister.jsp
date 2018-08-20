<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <!--
    	width=device-width ：表示宽度是设备屏幕的宽度
		initial-scale=1.0：表示初始的缩放比例
		minimum-scale=0.5：表示最小的缩放比例
		maximum-scale=2.0：表示最大的缩放比例
		user-scalable=yes：表示用户是否可以调整缩放比例
    -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
    <!-- 设定iphone端页面全屏 -->
    <meta name="apple-mobile-web-app-capable" content="yes" />    
    <!-- 取消数字被识别为电话号码 -->
	<meta name="format-detection" content="telephone=no" />
    <title>金英台系统</title>
    <!-- BOOTSTRAP STYLES-->
    <link href="../model/assets/css/bootstrap.css" rel="stylesheet" />
    <!-- FONTAWESOME STYLES-->
    <link href="../model/assets/css/font-awesome.css" rel="stylesheet" />
       <!--CUSTOM BASIC STYLES-->
    <link href="../model/assets/css/basic.css" rel="stylesheet" />
    <!--CUSTOM MAIN STYLES-->
    <link href="../model/assets/css/mycss.css" rel="stylesheet" />
    <!-- GOOGLE FONTS-->
    <!-- <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' /> -->
    
</head>
<body>
	
    <div id="wrapper">
        <div id="page-wrapper">
            <div id="page-inner">
                <div class="row">
                <div class="col-md-2"></div>
                    <div class="col-md-12">
                        <h1><b></b></h1>
                    </div>
                </div>
                <!-- /. ROW  -->
               <div class="row">
                <div class="col-md-12">
                <form id="userRegisterForm">
                	<div class="row"></div>
                	<div class="col-md-2"></div>
                    <div class="panel panel-default col-md-6">
                        <div class="panel-body">
                            <div class="form-group row">
                            	<div class="col-md-12 row">
			                        <label><h1><b>用户注册</b></h1></label>
			                        <hr />
			                    </div>
			                    <div class="col-md-12 row">
			                        <h1><b></b></h1>
			                    </div>			                    
			                        <div >
				                        <div style="display:inline-block;width:100%;text-align:left;">
				                            <label>账号</label>
				                        </div>
				                        <div  style="display:inline-block;width:60%">
				                            <input class="form-control" id="user_id" name="user_id" placeholder="账号" readonly="readonly" type="text" />
				                        </div>
				                        <div style="display:inline-block;width:30%;">
		                        			<button type="button" id="getUids"style="display:inline-block" class="btn btn-default" onclick="getUid()">获取账号</button>
		                       			</div>
			                        </div>
		                    </div>
		                    <div class="form-group row">
		                        <div style="display:inline-block;width:100%;text-align:left;">
		                            <label>用户名</label>
		                        </div>
		                        <div style="display:inline-block;width:75%">
		                        	<input class="form-control" id="username" name="username" type="text" />
		                        </div>
		                    </div>
		                    <div class="form-group row">
		                        <div style="display:inline-block;width:100%;text-align:left;">
		                            <label>密码</label>
		                        </div>
		                        <div style="display:inline-block;width:75%">
		                        	<input class="form-control" id="password" name="password" type="password" />
		                        </div>
		                    </div>
		                    <div class="form-group row">
		                        <div style="display:inline-block;width:100%;text-align:left;">
		                            <label>密码确认</label>
		                        </div>
		                        <div style="display:inline-block;width:75%">
		                        	<input class="form-control" id="password1" name="password1" type="password" />
		                        </div>
		                    </div>
		                    <div class="form-group row">
		                        <div>
			                        <div style="display:inline-block;width:100%;text-align:left;">
			                            <label>验证方式 </label>
			                        </div>
			                        <div style="display:inline-block;width:100%">
			                            	<label style="display:inline-block;width:30%"><input id="verifyWay" name="verifyWay" style="margin-right:5%;margin-left:15%"type="radio" value="eamil" onclick="selectWay('eamil')"/>邮箱</label>
			                        		<label style="display:inline-block;width:30%"><input id="verifyWay" name="verifyWay" style="margin-right:5%;margin-left:15%" type="radio" value="telephone" onclick="selectWay('telephone')"/>手机 </label>
			                        </div>
		                        </div>
		                    </div>
			                <div class="form-group row" id="verify_email"style="display:none">
			                     <div style="display:inline-block;width:100%;text-align:left;">
			                        <label>邮箱</label>
			                     </div>
			                     <div style="display:inline-block;width:60%">
			                        <input class="form-control" style="display:inline-block;" id="email" name="email" type="text" />
			                     </div> 
			                     <div style="display:inline-block;width:30%;">       
			                        <button type="button"  class="btn btn-default" onclick="showCodeInput('email')">验证</button>
			                     </div>         
			                </div>
		                    <div class="form-group row" id="verify_telephone"style="display:none">
		                        <div style="display:inline-block;width:100%;text-align:left;">
		                            <label>手机号码</label>
		                        </div>
		                        <div style="display:inline-block;width:60%">
		                        	<input class="form-control" style="display:inline-block" id="telephone_num" name="telephone_num" type="text" />
		                        </div>
		                        <div style="display:inline-block;width:30%;">
		                        	<button type="button" style="display:inline-block" class="btn btn-default" onclick="showCodeInput('telephone')">验证</button>
		                        </div>
		                    </div>
		                    <div class="form-group row" id="verify_code"style="display:none">
		                        <div style="display:inline-block;width:100%;text-align:left;">
		                            <label>验证码</label>
		                        </div>
		                        <div style="display:inline-block;width:66.7%;padding-right:5%">
		                        	<input class="form-control" style="display:inline-block" id="code" name="code" type="text" />
		                        	<span class="flag_wrong" id="code_flag">x</span>
		                        </div>
		                        
		                    </div>
		                    <hr />
		                    <div class="form-group row" style="text-align:center">
						        <!-- <div class="col-md-4"></div> -->
						        <button type="button" class="btn btn-info" onclick="userRegister()">注 册</button>
						        <button type="button" class="btn btn-danger" style="margin-left:10%;"onclick="javascript:history.back(-1);">取 消</button>
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
			<div class="modal-dialog" role="document">
				<div class="modal-content"> 					
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<p class="modal-title" id="myModalLabel">选择您的账号</p>
					</div>
					<div class="modal_form_content">
						<p style="font-size: 130%;margin-left:2%;color:red; ">请从以下账号中选择一个作为您的登录账号：</p>
					    	<div">
					    		<!-- <span class="lab_text text-right"></span> -->
					    		<select style="margin-left:13%; width: 60%;"class="modal_form_select_mul" multiple="multiple" id="ids"></select>
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
    <script src="../model/assets/js/jquery-1.10.2.js"></script>
    <!-- BOOTSTRAP SCRIPTS -->
    <script src="../model/assets/js/bootstrap.js"></script>
    <!-- METISMENU SCRIPTS -->

    <script src="../model/assets/js/simpleUtil.js"></script>
    
</body>
</html>
<content tag="scripts">
<script src="../model/assets/js/ve1.js"></script>
	 <script src="../model/assets/js/simpleUtil.js"></script>
	<script>
	
	    //EIO 抽注
		var cVe= new EIO.ve();
		var cVei = new EIO.vei();
		var cVeUti = new EIO.veUti();
		var cVeName="VeDemo1";//定义本应用的Ve引擎名称，用户自定义，用作服务按的消息处理调度
		var cServerUri="/ECPServer/EIOServletMsgEngine";
		var cMsgConfigure;
	    var verify_code = "";
		function userRegister(){
		
		   var nick_name = document.getElementById("user_id").value;
		   var password = document.getElementById("password").value;
		   var username = document.getElementById("username").value;
		   if(nick_name==""||password==""||username=="")
		   {
		       alert("信息不能为空");
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
		       alert("验证码输入错误");
		   }
		}
		
		function reqRegister(){
			var req = {};
			req["op"] = "getRegister";
			req["account"] = document.getElementById("user_id").value;
			req["password"] = document.getElementById("password").value;
			req["nickname"] = document.getElementById("username").value;
			if(select =="0")
			{
			    req["email"] = document.getElementById("email").value;
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
			if(result.result=="success"){
				alert("注册成功，请牢记您的登录账号："+document.getElementById("user_id").value);
				window.location.href="login.jsp";
			}else{
				alert(result.msg);
				//window.location.href="userRegisterPage.jsp";
//				$("#chooseUid").modal('show');
			}
		}
	
		function goBack(){
			window.location.href="login.jsp";
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
		         select = "1";
		         document.getElementById("telephone_num").value = "";
		         document.getElementById("email").value = "";		        
		         $("#verify_telephone").css("display","block");
		         $("#verify_email").css("display","none");
		         $("#verify_code").css("display","none");
		    }
		}
		
		function showCodeInput(way){
		    
		     if(way=="email"){
		         var inputValue = document.getElementById("email").value;
		         if(inputValue==""){
		             alert("请输入邮箱");
		         }
		         else{
		             verifyBeforeRegiste();  
		         }
		     }else if(way=="telephone"){
		         var inputValue = document.getElementById("telephone_num").value;
		         if(inputValue==""){
		             
		             alert("请输入手机号码");
		         }
		         else{
		            document.getElementById("code").value = "";
		            $("#verify_code").css("display","block");   
		         }
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
				document.getElementById("code").value = "";
		        $("#verify_code").css("display","block"); 
			}else{
				alert("邮箱验证失败");
				<!--msgbox("提示",3,"验证失败");-->
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
				alert("账号获取失败");
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