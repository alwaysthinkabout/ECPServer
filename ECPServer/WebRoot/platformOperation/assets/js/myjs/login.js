/*function login(){
	window.location.href="content/common/homePage.jsp?";
}*/



function login(){
	if($("#uid").val()=="")
	{
		var btn = document.getElementById("uid"); 
		btn.focus();
		alert("账号不能为空");
	}else if($("#password").val()=="")
		{
			var btn = document.getElementById("password"); 
			btn.focus();
			alert("密码不能为空");
		}else if($("#checkCode").val()==""){
			//alert("验证码不能为空");
			$("#right_code_flag").css("display","none");
      		$("#wrong_code_flag").css("display","inline-block");
			var btn = document.getElementById("checkCode"); 
			btn.focus();
		}else{
			  $.ajax({  
		          url: '/ECPServer/servlet/LoginServlet' , 
		          //url:'http://125.216.242.9:8080/ECPServer/cloudmake/CloudMakeFileUpload',
		          data: {
		        	  checkCode:$("#checkCode").val()	  
		          },
		          type: 'POST',
		          error: function (returndata) {
		        	  $("#right_code_flag").css("display","none");
		        	  $("#wrong_code_flag").css("display","inline-block");
		              //alert(returndata);
		          },  
		          success: function (returndata) {
		      //    alert(returndata.length ==6);
		            if(returndata.length ==6){
		            	$("#checkIcon").show();
		            	$("#wrong_code_flag").css("display","none");
		            	$("#right_code_flag").css("display","inline-block");		            	
						$("#login").attr("disabled", true);
						cVe.startReqByMsgHandle(cVeName,'','','reqLogin','resLogin','ECP.handle.UserHandle.handleMsg');
		            }else{
		            	//alert("验证码错误");
		            	$("#wrong_code_flag").css("display","inline-block");
		            	$("#right_code_flag").css("display","none");
						var btn = document.getElementById("checkCode"); 
						btn.focus();
		            }
		          },  
		     });  
				if($("#checkIcon").is(":hidden")) 
				{ 
					alert("验证码错误");
					var btn = document.getElementById("checkCode"); 
					btn.focus();
				}else{
					$("#login").attr("disabled", true);
					cVe.startReqByMsgHandle(cVeName,'','','reqLogin','resLogin','ECP.handle.UserHandle.handleMsg');
				} 
			}
}

function reqLogin(){
	var req = {};
	req["user_type"] = "平台";
	req["op"] = "login";
	req["user_id"] = document.getElementById("uid").value;
	req["password"] = document.getElementById("password").value;
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resLogin(){
	$("#login").attr("disabled", false);
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId];
	if(result.result=="0"){
		//var org_id = result.data.org_id;
		var user_id = result.data.user_id;
		//var org_name = result.data.org_name;
		cVeUti.Cookie.setCookie("userId",user_id);
		/*cVeUti.Cookie.setCookie("nick_name",nick_name);*/
		window.location.href="content/common/homePage.jsp";
		//alert(org_id);
	//	window.location.href="unprocessed.jsp?org_id="+org_id;
		//window.location.href="unprocessed.jsp";
	}else{
		alert("用户名或密码错误");
		$("#wrong_code_flag").css("display","none");
    	$("#right_code_flag").css("display","none");
    	document.getElementById("checkCode").value = "";
    	var btn = document.getElementById("uid"); 
		btn.focus();
		reloadCode();
	}
}

document.onkeydown=function() { 
	var pathName = window.location.pathname;
	var len = pathName.split("/").length;
	//if(retData=="6"&& pathName.split("/")[len-1]!="login.html")
	if (event.keyCode == 13&& pathName.split("/")[len-1]=="login.jsp") {
		var input = document.getElementById("uid");
		var btn = document.getElementById("login"); 
		input.focus(); 
		btn.click(); 
    } 
};


function setUid(){	 
	 var name = cVeUti.Cookie.getCookie("loginUserName");
	 document.getElementById("uid").value = "";
	 document.getElementById("password").value="";
	 if(name != null && name != "") {
	  document.getElementById("password").type = "password";
	  var userInfo = name.split(";");
	  document.getElementById("uid").value = userInfo[0];      	 
	  document.getElementById("password").value = userInfo[1];
	  document.getElementById("saveUserName").checked = true;
	 } else {  
		 document.getElementById("uid").focus();
	 }	 
}

function saveUserOrNot()
{
	var isChecked = document.getElementById("saveUserName").checked;
	if(!isChecked)
	{
		cVeUti.Cookie.delCookie("loginUserName");
	}
}
