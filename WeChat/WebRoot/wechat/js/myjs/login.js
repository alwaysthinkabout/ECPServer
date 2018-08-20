function login(){
	var user_name = document.getElementById("user_name").value;
	var password = document.getElementById("password").value;
	if(user_name == "" || password == ""){
		alert("信息不能为空");
		return;
	}
	var data = {
			handle:"wechat.handle.UserHandle.handleMsg",
			reqData : {
				user_name : user_name,
				password : password,
				op : "login" 
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
				var user_info = data.user_info.user_name + ',' + data.user_info.user_id + ',' + data.user_info.picture;
				setCookie("user_info", user_info);
				window.location.href = "homePage.html";
			}else{
				alert("用户名或密码错误！");
			}
			
		}
	});
}