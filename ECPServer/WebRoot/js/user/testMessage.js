function reqTest()
{
	var req = {};
	req["store_Name"] = exText("userName");
	req["contact_Name"] = exText("password");
	req["store_id"] = 10;
	req["identity_Photo"] = "photo_path";
	req["identity_Name"] ="";
	req["identity_Num"] = "";
	req["self_Delivery"] ="是";
	req["ordinary_Delivery"] = "否";
	req["sameDay_Delivery"] = "";
	req["timeLimit_Delivery"] ="";
	req["op"] = "setDelivery";
//	req["op"] = "setQualification";
//	req["op"] = "setStoreInfo";
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resTest()
{	
	alert("进入返回数据");	
	var retData =cVe.XHR.responseText;
//	alert(retData);
	var retDataObj=JSON.parse(retData);
//	alert("2");
	var retData=retDataObj[cVe.cEioVeDataId];
	if(retData.result == "1"){
		alert(retData.resultTip);
	}else{ 
		alert("错误："+retData.resultTip);
	}
}

function test()
{   
	event.preventDefault();
	cVe.startReqByMsgHandle(cVeName, '','','reqTest', 'resTest', 'ECP.handle.StoreHandle.handleMsg');	
}