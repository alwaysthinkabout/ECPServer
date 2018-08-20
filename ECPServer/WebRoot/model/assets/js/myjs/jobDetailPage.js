function getJobDetail(){
	cVe.startReqByMsgHandle(cVeName,'','','reqGetJobDetail','resGetJobDetail','ECP.handle.JobInfoHandle.handleMsg');
}

var Request = new Object();
Request = GetRequest();
var storeId=Request["storeId"];
var jobId=Request["jobId"];

$(document).ready(		
	getJobDetail()
);

function reqGetJobDetail(){
/*	var userId*/
	var req = {};
	req["op"] = "loadWorkInfo";
	req["jobId"] = jobId;
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resGetJobDetail(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId].job[0];
	$("#storeId").val(result.storeId);
	$("#jobId").val(result.jobId);
	$("#keywords").text(result.keywords);
	$("#jobLocation").text(result.jobLocation);
	$("#payment").text(result.payment);
	$("#jobId").text(result.jobId);
	$("#publishTime").text(result.publishTime);
	$("#jobCity").text(result.jobCity);
	$("#number").text(result.number);
	$("#endTime").text(result.endTime);
	$("#requirements").text(result.requirements);
	$("#jobName").text(result.jobName);
	$("#timeType").text(result.timeType);
	$("#description").text(result.description);
	//$("#jobTypeName").text(result.jobTypeName);
	var job_type_names = result.jobTypeName+" : "+result.job_stype_name;
	$("#jobTypeName").text(job_type_names);
	$("#other_payment").text(result.other_payment);
	$("#accommodation").text(result.accommodation);
	$("#degree").text(result.degree);
	$("#title").text(result.title);
	$("#payment_type").text(result.payment_type);
	/*$("#jobCode").text(result.jobCode);*/
	$("#storeId").text(result.storeId);
//	alert(result.org_name);
}



function jobUpdate(){
	cVe.startReqByMsgHandle(cVeName,'','','reqJobUpdate','resJobUpdate','ECP.handle.JobInfoHandle.handleMsg');
}

function GetJsonObject() {
	var job=$("#updateJobForm").serializeArray();
	var theRequest = new Object();
	for(var i=0;i<job.length;i++){
		theRequest[job[i].name]=unescape(job[i].value);
	}
	return theRequest;
}


function reqJobUpdate(){
	var job = new Object();
	job = GetJsonObject();
	var req = {};
	req["op"] = "updateWork";
	req["job"] = job;
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resJobUpdate(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var resultTip=retDataObj[cVe.cEioVeDataId].resultTip;
	alert(resultTip);
//	location.replace(location.href);
}