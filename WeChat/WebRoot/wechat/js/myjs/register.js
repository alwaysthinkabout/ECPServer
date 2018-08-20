function userRegister(){
	var user_name = document.getElementById("user_name").value;
	var password = document.getElementById("password").value;
	var rePassword = document.getElementById("rePassword").value;
	var picture = document.getElementById("imgOne").value;
	if(user_name == "" || password == "" || rePassword == ""){
		alert("信息不能为空");
		return;
	}
	if(password != rePassword){
		alert("密码确认不正确");
		return;
	}
	if(picture == ""){
		alert("请选择要上传的头像。");
		return;
	}
	var data = {
			handle:"wechat.handle.UserHandle.handleMsg",
			reqData : {
				user_name : user_name,
				password : password,
				op : "register" 
			}
		};
	$.ajax({
		url:"/WeChat/servlet/WeChatRouter",
		type : 'post',
		data:JSON.stringify(data),  			
		//contentType: "application/json; charset=utf-8",
		//dataType : 'json',   			
		success : function(result){
			var data = JSON.parse(result);
			if(data.result == "success"){
				uploadImage(data.user_id);
				alert("注册成功，请登录。");
				window.location.href = "login.html";
			}else if(data.result == "fail"){
				alert(data.resultTip);
			}
			
		}
	});
}

function goBack(){
	window.location.href = "login.html";
}

/** 
* 从 file 域获取 本地图片 url 
*/ 
function getFileUrl(sourceId) { 
	var url; 
	if (navigator.userAgent.indexOf("MSIE")>=1) { // IE 
		url = document.getElementById(sourceId).value; 
	 } else if(navigator.userAgent.indexOf("Firefox")>0) { // Firefox 
		url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0)); 
	 } else if(navigator.userAgent.indexOf("Chrome")>0) { // Chrome 
		url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0)); 
	 } 
	return url; 
} 

/** 
* 将本地图片 显示到浏览器上 
*/ 
function preImg(sourceId, targetId) { 
	var url = getFileUrl(sourceId); 
	var imgPre = document.getElementById(targetId); 
	imgPre.src = url;
	$("#imgPre").show();
} 
function uploadImage(id){
	document.getElementById("fileName").value = id;
	var formData = new FormData($( "#imageUploadForm" )[0]);
	$.ajax({   
      url: '/WeChat/ImageUploadServlet',
      type: 'POST',  
      dataType:'text',
      data: formData,   
      cache: false,  
      contentType: false,  
      processData: false,  
      success: function (returndata) { 
    	  alert(returndata);
      },  
      error: function (returndata) {  
          alert(returndata);  
      }  
 });  
}