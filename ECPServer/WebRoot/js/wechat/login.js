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
		            	/*$("#checkIcon").show();*/
		            	$("#wrong_code_flag").css("display","none");
		            	$("#right_code_flag").css("display","inline-block");		            	
		            	cVeUti.Cookie.delCookie("job_apply_id");	
						cVeUti.Cookie.delCookie("page_id");
						cVeUti.Cookie.delCookie("org_id");	
						cVeUti.Cookie.delCookie("org_name");
						cVeUti.Cookie.delCookie("nick_name");
						cVeUti.Cookie.delCookie("msgCounters");
						cVeUti.Cookie.delCookie("msgCounter");
						cVeUti.Cookie.delCookie("user_id");
						cVeUti.Cookie.delCookie("lastOperTime");
						cVeUti.Cookie.delCookie("integrity");
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
			}
}

function reqLogin(){
	var req = {};
	req["op"] = "wechatLogin";
	req["email"] = document.getElementById("uid").value;
	req["password"] = document.getElementById("password").value;
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resLogin(){
	$("#login").attr("disabled", false);
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId];
	
	
	if(result.result=="success"){
		//判断是否要记住当前用户名
		var isChecked = document.getElementById("saveUserName").checked;
		if(isChecked) {
			var userInfo = document.getElementById("uid").value + ";" +document.getElementById("password").value;
			cVeUti.Cookie.setCookie("loginUserName",userInfo,30);
		}else
			{
				cVeUti.Cookie.delCookie("loginUserName");
			}
		var user_id = result.user_id;
//		var nick_name = result.data.nick_name;
		cVeUti.Cookie.setCookie("user_id",user_id);
//		cVeUti.Cookie.setCookie("nick_name",nick_name);
		cVeUti.Cookie.setCookie("lastOperTime",new Date().getTime());
		var strUrl = window.location.href;
		if (strUrl.indexOf("?") != -1) {
			strUrl = strUrl.substr(strUrl.indexOf("?") + 1);
        	if(strUrl=="1"){
        		window.location.href="student.html";
        	}else if(strUrl=="2"){
     	  	 	window.location.href="org.html";
        	}else if(strUrl=="3"){
     		   	window.location.href="school.html";
        	}else{
        		window.location.href="student.html";
        	}
		}else{
			window.location.href="org.html";
		}
		//alert(org_id);
	//	window.location.href="unprocessed.jsp?org_id="+org_id;
		//window.location.href="unprocessed.jsp";
	}else{
		
		alert(result.msg);
    	document.getElementById("checkCode").value = ""; 
    	reloadCode();
    	var btn = document.getElementById("uid"); 
		btn.focus();				
		$("#wrong_code_flag").css("display","none");
    	$("#right_code_flag").css("display","none");
	}
}


document.onkeydown=function(event) { 
	var pathName = window.location.pathname;
	var len = pathName.split("/").length;
	if (event.keyCode == 13&& pathName.split("/")[len-1]=="login.jsp") {
		var input = document.getElementById("uid");
		var btn = document.getElementById("login"); 
		input.focus(); 
		btn.click(); 
		return;
    } 
	var hasFocus = $("#password").is(':focus');//当前光标是否处于密码输入框内
	if(event.keyCode==20&& pathName.split("/")[len-1]=="login.jsp"&&hasFocus&&document.getElementById("pwdSpan").style.display=="inline")
	{
		document.getElementById("pwdSpan").style.display="none";
	}
};
$("#password").blur(function()
		{
			document.getElementById("pwdSpan").style.display="none";
		});
function  detectCapsLock(event){
    var e = event||window.event;
    //var o = e.target||e.srcElement;
    var oTip = document.getElementById("pwdSpan");
    var keyCode  =  e.keyCode||e.which; // 按键的keyCode 
    var isShift  =  e.shiftKey ||(keyCode  ==   16 ) || false ; // shift键是否按住
     if (
     ((keyCode >=   65   &&  keyCode  <=   90 )  &&   !isShift) // Caps Lock 打开，且没有按住shift键 
     || ((keyCode >=   97   &&  keyCode  <=   122 )  &&  isShift)// Caps Lock 打开，且按住shift键
     ){oTip.style.display = 'inline';}
     else{oTip.style.display  =  'none';} 
}
document.getElementById('password').onkeypress = detectCapsLock;
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
	 } else {  document.getElementById("uid").focus();
	 }	 
}
/*$(function(){
	setUid();		
});*/

function saveUserOrNot()
{
	var isChecked = document.getElementById("saveUserName").checked;
	if(!isChecked)
	{
		cVeUti.Cookie.delCookie("loginUserName");
	}
}
	