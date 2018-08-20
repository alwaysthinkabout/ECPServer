var Request = new Object();
Request = GetRequest();
var audit_id=Request["audit_id"];
var user_id=cVeUti.Cookie.getCookie("userId");

$(document).ready(function(){
	getJobHunterInfoAuditDetail();
});

function getJobHunterInfoAuditDetail(){
	cVe.startReqByMsgHandle(cVeName,'','','reqGetJobHunterInfoAuditDetail','resGetJobHunterInfoAuditDetail','ECP.handle.platform.JobHunterAuditHandle.handleMsg');
}

function reqGetJobHunterInfoAuditDetail(){
/*	var userId*/
	var req = {};
	req["op"] = "getJobHunterInfoAuditDetail";
	req["audit_id"] = audit_id;
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resGetJobHunterInfoAuditDetail(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var jobHunterInfo=retDataObj[cVe.cEioVeDataId].data.jobHunterInfo;
	var auditInfo=retDataObj[cVe.cEioVeDataId].data.auditInfo;
	if(jobHunterInfo!=undefined){
		var result=jobHunterInfo;
		$("#birth").text(result.birth);
		$("#content").text(result.content);
		$("#degree").text(result.degree);
		$("#email").text(result.email);
		$("#id_cardno").text(result.ID_cardNo);
		$("#name").text(result.name);
		$("#phone").text(result.phone);
		$("#prof_title").text(result.title);
		$("#sex").text(result.sex);
	}
	if(auditInfo!=undefined){
		var result=auditInfo;
		$("#audit_user_id").val(user_id);
		$("#audit_id").val(result.audit_id);
		$("#audit_reason").text(result.audit_reason);
		$("#ope_time").text(result.ope_time);
		$("#ope_type").text(result.ope_type);
		/*alert($("#audit_user_id").val());*/
	}
}

/************************图片预览******************************/
//图片预览
function preview(){
	$("#previewModel").modal('show');
}

function goBack(){
	window.location.href="jobHunterInfoAuditList.jsp";
};


/************************审核求职者信息*************************/
function auditJobHunterInfo(){
	cVe.startReqByMsgHandle(cVeName,'','','reqAuditJobHunterInfo','resAuditJobHunterInfo','ECP.handle.platform.JobHunterAuditHandle.handleMsg');
}

function GetJsonObjectForJobHunter() {
	var jobHunterInfo;
	jobHunterInfo=$("#jobHunterInfoForm").serializeArray();
	var theRequest = new Object();
	for(var i=0;i<jobHunterInfo.length;i++){
		theRequest[jobHunterInfo[i].name]=unescape(jobHunterInfo[i].value);
	}
	return theRequest;
}

function reqAuditJobHunterInfo(){
	var req = {};
	var jobHunterInfo = new Object();
	jobHunterInfo = GetJsonObjectForJobHunter();
	req["op"] = "updateJobHunterInfoAudit";
	req["data"] = jobHunterInfo;
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
	}

function resAuditJobHunterInfo(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId].data;
	alert(result);
	window.location.href="jobHunterInfoAuditList.jsp";
}
