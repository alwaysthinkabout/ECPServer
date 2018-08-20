<!DOCTYPE html>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
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
		<link href="../model/assets/css/mycss.css" rel="stylesheet" />
		<link href="../model/assets/img/title.png" rel="icon" type="image/x-icon" />
		<style>
			* {
				margin: 0;
				padding: 0;
			}
			
			.parentCls {
				width: 100%;
			}
			
			.auto-tip li {
				width: 100%;
				height: 110%;
				line-height: 100%;
				font-size: 100%;
				padding-top: 2%;
				padding-bottom: 2%;
				list-style-type: none;
				background: #FFF
			}
			
			.auto-tip li.hoverBg {
				background: #ddd;
				cursor: pointer;
			}
			
			.red {
				color: black;
			}
			/*下拉框补充*/
			
			.hidden {
				display: none;
			}
			
			.capslock {
				position: absolute;
				z-index: 4;
				display: inline;
				width: 100%;
				top: 32%;
				left: 96%;
				padding-left: 10%;
				height: 100%;
				line-height: 28%;
				overflow: hidden;
				background: #F9F900;
			}
			/* #pwdSpan{margin:0 0.5em;font-size:85.7%;} */
			
			.topArrow {
				width: 0px;
				height: 0px;
				border: solid 10%;
				border-color: #ffffff #ffffff #F9F900 #ffffff;
				display: inline-block;
				position: absolute;
				right: 220%;
				z-index: 4;
				top: 12%;
			}
		</style>

	</head>

	<body style="background-color: #FFFFFF;">
		<div class="container">
			<div class="row ">
				<div class="col-md-5 col-md-offset-3 col-sm-6 col-sm-offset-3 col-xs-10 col-xs-offset-1">
					<div class="panel-body">
						<div class="row text-center " style="padding-top:20%;">
							<div class="col-md-12">
								<img src="../model/assets/img/titlePic3.png" height="100%" width="100%" />
							</div>
						</div>
						<hr />
						<div class="form-group input-group" style="width:100%;">
							<span class="input-group-addon"><i class="fa fa-tag"  ></i></span>
							<div class="parentCls">
								<input type="text" class="form-control inputElem" id="uid" style="width:90%;height:50%;" placeholder="账号/邮箱" autocomplete="off" />
							</div>
						</div>
						<input class="form-control" style="display: none" />
						<div class="form-group input-group" style="width:100%;">
							<span class="input-group-addon"><i class="fa fa-lock"  ></i></span>
							<input id="password" class="form-control" style="width:90%;height:50%;" onfocus="this.type='password'" placeholder="密码" autocomplete="off" />
							<!-- <span class="capslock"><b></b>大小写锁定已打开</span> -->
							<div id="pwdSpan" style="display:none;">
								<div class="topArrow"></div>
								<span class="capslock">大写锁定已打开</span>
							</div>
						</div>
						<div class="form-group input-group" style="width:100%;padding-top: 3%;">
							<input type="text" name="checkCode" id="checkCode" class="form-control" style="width:80%;height:100%;" placeholder="验证码" />
							<span class="code_flag_right" id="right_code_flag">✔</span>
							<span class="code_flag_wrong" id="wrong_code_flag">✘</span>
							<!-- <i class="fa fa-check" id="checkIcon" style="display:none"></i> -->
							<input id="flag" value="0" type="hidden" />
							<!-- <span ><i class="fa fa-check"  ></i></span> -->
							<!-- <input type="submit" value="提交"> -->
							<!-- </form> -->
						</div>
						<div class="form-group" style="width:100%;">
							<img alt="验证码" id="imagecode" style="width:50%;height: 100%;" src="<%= request.getContextPath()%>/servlet/ImageServlet" />
							<a style="padding-left:15%;width:50%;" href="javascript:reloadCode();"> 看不清楚</a><br>
						</div>
						<div class="form-group" style="width:100%;">
							<label class="checkbox-inline">
                               <input type="checkbox" id="saveUserName" onclick="saveUserOrNot()"/> 记住我
                           </label>
							<span class="pull-right">
                                  <a href="findPassword.html" >忘记密码 ? </a> 
                           </span>
						</div>
						<div class="form-group input-group col-md-5" style="width:100%;height: 120%;">
							<button onclick="login()" id="login" class="btn btn-primary" style="width:100%;font-size:100%;">登 录 </button>
						</div>
						<hr />
						<a href="weChatRegister.jsp">没有账号？立即注册</a>
					</div>
				</div>
			</div>
		</div>
		<script src="../model/assets/js/jquery-1.10.2.js"></script>
		<!-- BOOTSTRAP SCRIPTS -->
		<script src="../model/assets/js/bootstrap.js"></script>
		<script src="../model/assets/js/ve1.js"></script>
		<script src="../model/assets/js/simpleUtil.js"></script>
		<script src="../js/wechat/login.js"></script>
		<script src="../model/assets/js/veHelper.js"></script>
		<script src="../model/assets/js/myjs/supplement.js"></script>
	</body>
	<script>
		/* if(cVeUti.Cookie.getCookie("user_id")=="")			
				{
					alert("没有访问权限，请先登录");
					//window.location.href="login.html";
				} */

		$(function() {
			$("#checkCode").blur(function() {
				$.ajax({
					url: '/ECPServer/servlet/LoginServlet',
					//url:'http://125.216.242.9:8080/ECPServer/cloudmake/CloudMakeFileUpload',
					data: {
						checkCode: $("#checkCode").val()
					},
					type: 'POST',
					error: function(returndata) {
						alert(returndata);
						/* $("#right_code_flag").css("display","none");
						$("#wrong_code_flag").css("display","inline-block"); */
					},
					success: function(returndata) {
						//    alert(returndata.length ==6);
						if(returndata.length == 6) {
							$("#wrong_code_flag").css("display", "none");
							$("#right_code_flag").css("display", "inline-block");
						} else {
							//alert("验证码错误！");
							$("#right_code_flag").css("display", "none");
							$("#wrong_code_flag").css("display", "inline-block");
						}
					},
				});
			});
			setUid();
		});

		function reloadCode() {
			var btn = document.getElementById("checkCode");
			btn.value = "";
			// btn.focus();
			$("#right_code_flag").css("display", "none");
			$("#wrong_code_flag").css("display", "none");
			var time = new Date().getTime();
			document.getElementById("imagecode").src = "<%= request.getContextPath()%>/servlet/ImageServlet?d=" + time;
		}
	</script>

</html>