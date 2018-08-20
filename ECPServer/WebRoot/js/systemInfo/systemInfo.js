function reqPush()
{
	var req = {};
	req["systemInfo"] = exText("systemInfo");
	req["toAccount"] = exText("toAccount");
	req["fromAccount"] = "106";
	req["op"] = "push";
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resPush()
{	
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var retData=retDataObj[cVe.cEioVeDataId];
	if(retData.result == "success"){
		alert("推送成功");
		}
}

function push(){
	event.preventDefault();
	cVe.startReqByMsgHandle(cVeName, '','','reqPush', 'resPush', 'ECP.handle.common.CCommonHandle.handleMsg');	
}