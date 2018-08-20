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
	req["op"] = "getJobHunterInfoAuditedDetail";
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
	//	$("#oper_user_name").text(result.org_user_id);
		$("#oper_time").text(result.ope_time);
		$("#audit_user_name").text(result.audit_user_id);
		$("#audit_reason").text(result.audit_reason);
		$("#audit_time").text(result.audit_time);
		$("#audit_result").text(result.audit_result);
		/*$("#ope_time").text(result.ope_time);
		$("#ope_type").text(result.ope_type);*/
		/*alert($("#audit_user_id").val());*/
	}
}

/************************图片预览******************************/
//图片预览
function preview(){
	$("#previewModel").modal('show');
}

function goBack(){
	window.location.href="jobHunterInfoAuditedList.jsp";
};
