function reqLogin()
{
	var req = {};
	req["userName"] = exText("userName");
	req["password"] = exText("password");
	req["op"] = "login";
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resLogin()
{	
	alert("尝试登录");
	
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var retData=retDataObj[cVe.cEioVeDataId];
	if(retData.msg == "success"){
		}
}

function login()
{   
	event.preventDefault();
	cVe.startReqByMsgHandle(cVeName, '','','reqLogin', 'resLogin', 'ECP.handle.UserHandle.handleMsg');	
}