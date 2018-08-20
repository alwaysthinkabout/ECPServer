var Request = new Object();
Request = GetRequest();
var org_id=Request["org_id"];

var orgMsgCounter=0;
var jobHunterMsgCounter=0;
var storeMsgCounter=0;


$(document).ready(function(){
	getMsgCounters();
});

/****************获取企业待审核信息条数*********************/
function getMsgCounters()
{
	cVe.startReqByMsgHandle(cVeName,'','','reqGetMsgCounters','resGetMsgCounters','ECP.handle.platform.MessageHandle.handleMsg');
}

function reqGetMsgCounters()
{
	var req = {};
	req["op"] = "msgCounters";
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resGetMsgCounters()
{
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId];
	orgMsgCounter=result.orgMsgCounter;
	
	if(result.orgMsgCounter>=0)
	{
		$("#orgMsgCounter").css("display","inline-block");
		document.getElementById("orgMsgCounter").innerHTML = result.orgMsgCounter;
		$("#jobHunterMsgCounter").css("display","inline-block");
		document.getElementById("jobHunterMsgCounter").innerHTML = result.jobHunterMsgCount;
		$("#storeMsgCounter").css("display","inline-block");
		document.getElementById("storeMsgCounter").innerHTML = result.storeMsgCount;
		$("#roleMsgCounter").css("display","inline-block");
		document.getElementById("roleMsgCounter").innerHTML = result.roleMsgCount;
	}
	setTimeout("getMsgCounters()",60*1000);//每分钟轮询一次
}
