var Request = new Object();
Request = GetRequest();
var uid=Request["uid"];
var user_id=cVeUti.Cookie.getCookie("userId");

$(document).ready(function(){
	getStoreInfoAuditDetail();
});

function getStoreInfoAuditDetail(){
	cVe.startReqByMsgHandle(cVeName,'','','reqUserInfoDetail','resUserInfoDetail','ECP.handle.platform.UserHandle.handleMsg');
}

function reqUserInfoDetail(){
/*	var userId*/
	var req = {};
	req["op"] = "getUserInfoDetail";
	req["id"] = uid;
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resUserInfoDetail(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var data=retDataObj[cVe.cEioVeDataId].data;
	/*$("#user_image").src=data.picture;*/
	document.getElementById("user_image").innerHTML="<img src='"+data.picture+"' width='100%'>";
	$("#uid").text(data.uid);
	$("#nick_name").text(data.nick_name);
	$("#user_sex").text(data.user_sex);
	$("#user_type").text(data.user_type);
	$("#user_state").text(data.user_state);
	$("#mail").text(data.mail);
	$("#user_phone").text(data.user_phone);
	$("#introduce").text(data.introduce);
}
/************************图片预览******************************/
//图片预览
function previewStoreLogo(){
	$("#LOGOPreviewModel").modal('show');
}

function previewIdentityPhoto(){
	$("#IDCardPreviewModel").modal('show');
}

function previewLicensePhoto(){
	$("#licensePhotoPreviewModel").modal('show');
}

function previewPermitPhoto(){
	$("#permitPhotoPreviewModel").modal('show');
}

function goBack(){
	window.location.href="userInfoList.jsp";
};
