var Request = new Object();
Request = GetRequest();
var audit_id=Request["audit_id"];
var user_id=cVeUti.Cookie.getCookie("userId");

$(document).ready(function(){
	getRoleInfoAuditDetail();
});

function getRoleInfoAuditDetail(){
	cVe.startReqByMsgHandle(cVeName,'','','reqGetRoleInfoAuditDetail','resGetRoleInfoAuditDetail','ECP.handle.platform.RoleAuditHandle.handleMsg');
}

function reqGetRoleInfoAuditDetail(){
/*	var userId*/
	var req = {};
	req["op"] = "getRoleInfoAuditDetail";
	req["audit_id"] = audit_id;
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resGetRoleInfoAuditDetail(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId].data[0];
	//alert(result.oper_user_name);
	$("#user_name").text(result.oper_user_name);
	$("#identity").text(result.identity);
	$("#role_type").text(result.role_type);
	$("#role_limits").text(result.role_limits);
	if(result.idCard==""){
		document.getElementById("IDCardPreviewContent").innerHTML="<h3 style='color:red;'>&nbsp;&nbsp;&nbsp;还未上传证明材料</h3>";
	}else{
		var src="http://222.201.145.241:8888"+result.idCard;
		var image=new Image(); 
		image.src=src;
		document.getElementById("IDCardPreviewContent").innerHTML="<img src='"+src+"'>";
	};
	$("#audit_user_id").val(user_id);
	$("#audit_id").val(result.audit_id);
	$("#oper_reason").text(result.oper_reason);
	$("#oper_time").text(result.oper_time);
	$("#oper_type").text(result.oper_type);
	$("#oper_user_name").text(result.oper_user_name);
	$("#audit_content").text(result.audit_content);
}


/************************审核角色申请信息*************************/
function auditRoleInfo(){
	cVe.startReqByMsgHandle(cVeName,'','','reqAuditRoleInfo','resAuditRoleInfo','ECP.handle.platform.RoleAuditHandle.handleMsg');
}

function GetJsonObjectForRole() {
	var storeInfo;
	storeInfo=$("#roleInfoForm").serializeArray();
	var theRequest = new Object();
	for(var i=0;i<storeInfo.length;i++){
		theRequest[storeInfo[i].name]=unescape(storeInfo[i].value);
	}
	return theRequest;
}

function reqAuditRoleInfo(){
		var req = {};
		var registerInfo = new Object();
		registerInfo = GetJsonObjectForRole();
		req["op"] = "getRoleInfoAudit";
		req["data"] = registerInfo;
		cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
	}

function resAuditRoleInfo(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId].resultTip;
	alert(result);
	window.location.href="roleInfoAuditList.jsp";
}

/************************图片预览******************************/
//图片预览
function previewIdentityPhoto(){
	$("#IDCardPreviewModel").modal('show');
}

function goBack(){
	window.location.href="roleInfoAuditList.jsp";
};
