var user_id=cVeUti.Cookie.getCookie("userId");

$(document).ready(function(){
	getUserInfo();
});

/****************获取个人信息*********************/
function getUserInfo()
{
	cVe.startReqByMsgHandle(cVeName,'','','reqGetUserInfo','resGetUserInfo','ECP.handle.platform.UserHandle.handleMsg');
}

function reqGetUserInfo()
{
	var req = {};
	req["op"] = "getUserInfo";
	req["user_id"] = user_id;
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resGetUserInfo()
{
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId].data[0];
	$("#userId").text(result.user_id);
	$("#user_name").text(result.user_name);
	$("#role_type").text(result.role_type);
	$("#role_limits").text(result.role_limits);
}

/****************修改邮箱********************/
function mailModelShow(){
	$("#mailModel").modal('show');
}

function verifyBeforeUpdateFirst(){	
    var temp =   document.getElementById("originalEmail");//对电子邮件的验证
    var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
    if(temp.value==""){
        alert("请输入邮箱!");
    }else if(!myreg.test(temp.value)){
        alert("请输入有效的E_mail！");
       temp.focus();
    }else{
          cVe.startReqByMsgHandle(cVeName,'','','reqVerifyBeforeUpdateFirst','resVerifyBeforeUpdateFirst','ECP.handle.common.CCommonHandle.handleMsg');  
    } 	     
}

function reqVerifyBeforeUpdateFirst(){
   var req = {};
   req["op"] = "email_verify";
   req["flag"] = "confirm";
   req["email"] = document.getElementById("originalEmail").value;
   req["user_id"] = cVeUti.Cookie.getCookie("user_id");
   cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resVerifyBeforeUpdateFirst(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId];
	if(result.result=="success"){
		verify_code = result.identify_check;
		document.getElementById("code").value = "";
       $("#verify_code").css("display","block"); 
       document.getElementById("code").focus();
       alert("我们已将验证码发送至您的邮箱，请注意查看。");
	}else if(result.result=="notExist"){
		alert("该邮箱不是本账户的注册邮箱！请重新输入。");
		 $("#verify_code").css("display","none");
		 document.getElementById("originalEmail").focus();
	}else{
	    alert("邮箱验证失败，请检查邮箱是否输入正确。");
	}
}
function verifyBeforeUpdateSecond(){	
    var temp =   document.getElementById("newEmail");//对电子邮件的验证
    var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
    if(temp.value==""){
            alert("请输入邮箱!");
    }else if(!myreg.test(temp.value))
	 {
        alert("请输入有效的E_mail！");
        temp.focus();
    }else{
        cVe.startReqByMsgHandle(cVeName,'','','reqVerifyBeforeUpdateSecond','resVerifyBeforeUpdateSecond','ECP.handle.common.CCommonHandle.handleMsg');  
    } 	     
   
}

function reqVerifyBeforeUpdateSecond(){
   var req = {};
   req["op"] = "email_verify";
   req["email"] = document.getElementById("newEmail").value;
   cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resVerifyBeforeUpdateSecond(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId];
	if(result.result=="success"){
		verify_code = result.identify_check;
		document.getElementById("code").value = "";
       $("#verify_code").css("display","block"); 
       document.getElementById("code").focus();
       alert("我们已将验证码发送至您的邮箱，请注意查看。");
	}else{
		alert("邮箱验证失败，请检查邮箱是否输入正确。");
	}
}

function nextStep()
{
    var code = document.getElementById("code").value;
	 var temp = document.getElementById("originalEmail");    //对电子邮件的验证
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
	    alert("验证码不能为空！");
	    document.getElementById("code").focus();
	}else if(verify_code!=code)
		{
		   alert("验证码不正确！");
		   document.getElementById("code").focus();
		}else
			{
			 $("#nextStep_btn").css("display","none");
			 $("#confirmUpdate").css("display","inline-block");
			 $("#originalMail1").css("display","none");
			 $("#newMail1").css("display","block");
			 $("#alterEmail").css("display","inline-block");
			 document.getElementById("code").value = "";
			 $("#verify_code").css("display","none");
			}
}

function alterEmail()
{
    var email =  document.getElementById("newEmail").value;
    var code =  document.getElementById("code").value;
    if(user_id=="")
    {
        alert("请输入账号!");
        return;
    }
    if(email=="")
    {
        alert("请输入邮箱!");
        return;
    }
    if(document.getElementById("verify_code").style.display=="none")
    {
        alert("请完成验证!");
        return;
    }
    if(code=="")
    {
        alert("验证码不能为空");
    }
    if(code!=verify_code)
    {
        alert("验证码不正确!");
        return;
    }
    cVe.startReqByMsgHandle(cVeName,'','','reqUpdateEmail','resUpdateEmail','ECP.handle.UserHandle.handleMsg');
}

function reqUpdateEmail()
{
    var req = {};
    req["uid"] = user_id;
    req["newEmail"] = document.getElementById("newEmail").value;
    req["op"] = "updateAccount";
    cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resUpdateEmail()
{
    var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var retData=retDataObj[cVe.cEioVeDataId];
	if(retData.result == "0")
	{
		cVeUti.Cookie.delCookie("user_id");
	    alert("账户设置保存成功，请重新登录。");
	    window.location.href="../../login.jsp";
	}else{
        alert(retData.msg);
	}
}

/****************修改密码********************/
function passwordModelShow(){
	$("#passwordModel").modal('show');
}

function alterPassword(){
   var password =  document.getElementById("password").value;
   var newPassword = document.getElementById("newPassword").value;
   var reNewPassword = document.getElementById("reNewPassword").value;
   if(user_id=="")
   {
       alert("账号不能为空！");
       return;
   }
   if(password=="")
   {
       alert("原密码不能为空！");
       return;
   }
   if(newPassword=="")
   {
       alert("新密码不能为空！");
       return;
   }
   if(newPassword==password)
   {
       alert("新密码不能和原密码相同！");
       return;
   }
   if(reNewPassword=="")
   {
       alert("请确认密码！");
       return;
   }
   if(newPassword!=reNewPassword)
   {
       alert("密码确认不一致!");
       return;
   }
   if(newPassword.length>20||newPassword.length<6)
	{
	   alert("新密码长度限于6至20之间！");
   return;
}
var flag_pwd = 0;
var chineseWord = 0;
var numReg = /^[0-9]+$/;
var charReg = /^[a-z]+$/;
var bCharReg = /^[A-Z]+$/;
var chineseReg  =/[^\u0000-\u00FF]/;
for (var i = 0; i < newPassword.length; i++) { 
	if(chineseReg.test(newPassword.substr(i, 1))){
		chineseWord +=1;
		break;
	}
} 
if(chineseWord==1){
    alert("新密码不能包含中文！");
    return;
}
var specialReg  =/[`~!@#$%^&*_+<>{}\/'[\]]/m;
for (var i = 0; i < newPassword.length; i++) { 
	if(numReg.test(newPassword.substr(i, 1))){
		flag_pwd +=1;
		break;
	}
} 
for (var i = 0; i < newPassword.length; i++) { 
	if(charReg.test(newPassword.substr(i, 1))){
		flag_pwd +=1;
		break;
	}
} 

for (var i = 0; i < newPassword.length; i++) { 
	if(bCharReg.test(newPassword.substr(i, 1))){
		flag_pwd +=1;
		break;
	}
} 

for (var i = 0; i < newPassword.length; i++) { 
	if(specialReg.test(newPassword.substr(i, 1))){
		flag_pwd +=1;
		break;
	}
} 
if(flag_pwd<2){
	alert("新密码必须至少由大小写、数字和特殊字符中的2种组成");
		return;
	}
   
   cVe.startReqByMsgHandle(cVeName,'','','reqUpdatePassword','resUpdatePassword','ECP.handle.UserHandle.handleMsg');
}

function reqUpdatePassword()
{
    var req = {};
    req["uid"] = user_id;
    req["password"] = document.getElementById("password").value;
    req["newPassword"] = document.getElementById("newPassword").value;
    req["op"] = "updatePass";		            
    cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resUpdatePassword()
{
    var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var retData=retDataObj[cVe.cEioVeDataId];
	if(retData.result == "0")
	{
		cVeUti.Cookie.delCookie("user_id");
	    alert("账户设置保存成功，请重新登录。");
	    window.location.href="../../login.jsp";
	}else{
	       alert(retData.msg);
	}
}

/****************返回首页********************/
function goBack(){
	window.location.href="../../content/common/homePage.jsp";
}


