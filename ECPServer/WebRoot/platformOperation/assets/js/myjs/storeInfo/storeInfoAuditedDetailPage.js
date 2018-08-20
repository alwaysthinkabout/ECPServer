var Request = new Object();
Request = GetRequest();
var audit_id=Request["audit_id"];
var user_id=cVeUti.Cookie.getCookie("userId");

$(document).ready(function(){
	getStoreInfoAuditDetail();
});

function getStoreInfoAuditDetail(){
	cVe.startReqByMsgHandle(cVeName,'','','reqGetStoreInfoAuditDetail','resGetStoreInfoAuditDetail','ECP.handle.platform.OrgAuditHandle.handleMsg');
}

function reqGetStoreInfoAuditDetail(){
/*	var userId*/
	var req = {};
	req["op"] = "getStoreInfoAuditDetail";
	req["audit_id"] = audit_id;
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resGetStoreInfoAuditDetail(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var baseInfo=retDataObj[cVe.cEioVeDataId].data.baseInfo;
	var storeInfo=retDataObj[cVe.cEioVeDataId].data.storeInfo;
	var qualificationInfo=retDataObj[cVe.cEioVeDataId].data.qualificationInfo;
	var collectionInfo=retDataObj[cVe.cEioVeDataId].data.collectionInfo;
	var audit_content=retDataObj[cVe.cEioVeDataId].data.audit_content;
	
	if(storeInfo.store_Name!=undefined){
		$("#noStoreInfoBody").addClass("hidden");
		$("#storeInfoBody").removeClass("hidden");
		var result=storeInfo;
		$("#store_Name").text(result.store_Name);
		$("#contact_Name").text(result.contact_Name);
		$("#contact_Phone").text(result.contact_Phone);
		$("#category").text(result.category);
		$("#storeAddress").text(result.storeAddress);
		$("#keyWord").text(result.keyWord);
		$("#introduction").text(result.introduction);
		/*$("#store_Logo").text(result.store_Logo);*/
		if(result.store_Logo==""){
			document.getElementById("LOGOPreviewContent").innerHTML="<h3 style='color:red;'>&nbsp;&nbsp;&nbsp;还未上传证明材料</h3>";
		}else{
			var src=result.store_Logo;
			var image=new Image(); 
			image.src=src;
			document.getElementById("LOGOPreviewContent").innerHTML="<img src='"+src+"' width=800px >";
		};
	}else{
		$("#noStoreInfoBody").removeClass("hidden");
		$("#storeInfoBody").addClass("hidden");
	}
	/*alert(qualificationInfo);*/
	if(qualificationInfo.identity_Name!=undefined){
		$("#qualificationInfoBody").removeClass("hidden");
		$("#noQualificationInfoBody").addClass("hidden");
		var result=qualificationInfo;
		/*$("#identity_Photo").text(result.identity_Photo);*/
		$("#identity_Name").text(result.identity_Name);
		$("#identity_Num").text(result.identity_Num);
		$("#license_Type").text(result.license_Type);
		/*$("#license_Photo").text(result.license_Photo);*/
		$("#license_Name").text(result.license_Name);
		$("#license_Num").text(result.license_Num);
		$("#license_Address").text(result.license_Address);
		$("#license_ExpiryDate").text(result.license_ExpiryDate);
		$("#permit_Name").text(result.permit_Name);
		$("#permit_Num").text(result.permit_Num);
		$("#permit_Address").text(result.permit_Address);
		$("#permit_ExpiryDate").text(result.permit_ExpiryDate);
		
		if(result.identity_Photo==""){
			document.getElementById("IDCardPreviewContent").innerHTML="<h3 style='color:red;'>&nbsp;&nbsp;&nbsp;还未上传证明材料</h3>";
		}else{
			var src=result.identity_Photo;
			var image=new Image(); 
			image.src=src;
			document.getElementById("IDCardPreviewContent").innerHTML="<img src='"+src+"' width=800px>";
		};
		
		if(result.license_Photo==""){
			document.getElementById("licensePhotoPreviewContent").innerHTML="<h3 style='color:red;'>&nbsp;&nbsp;&nbsp;还未上传证明材料</h3>";
		}else{
			var src=result.license_Photo;
			var image=new Image(); 
			image.src=src;
			document.getElementById("licensePhotoPreviewContent").innerHTML="<img src='"+src+"'  width=800px>";
		};
		
		if(result.permit_Photo==""){
			document.getElementById("permitPhotoPreviewContent").innerHTML="<h3 style='color:red;'>&nbsp;&nbsp;&nbsp;还未上传证明材料</h3>";
		}else{
			var src=result.permit_Photo;
			var image=new Image(); 
			image.src=src;
			document.getElementById("permitPhotoPreviewContent").innerHTML="<img src='"+src+"'  width=800px>";
		};
	}
	else{
		$("#noQualificationInfoBody").removeClass("hidden");
		$("#qualificationInfoBody").addClass("hidden");
	}
	
	if(collectionInfo.account_Name!=undefined){
		$("#collectionInfoBody").removeClass("hidden");
		$("#noCollectionInfoBody").addClass("hidden");
		var result=collectionInfo;
		$("#account_Type").text(result.account_Type);
		$("#account_Name").text(result.account_Name);
		$("#account_Num").text(result.account_Num);
		$("#account_Bank").text(result.account_Bank);
		$("#account_SubBank").text(result.account_SubBank);
	}else{
		$("#noCollectionInfoBody").removeClass("hidden");
		$("#collectionInfoBody").addClass("hidden");
	}
	
	$("#operation_reason").text(audit_content);
	$("#oper_user_name").text(baseInfo.oper_user_name);
	$("#oper_time").text(baseInfo.oper_time);
	$("#audit_user_name").text(baseInfo.audit_user_name);
	$("#audit_reason").text(baseInfo.audit_reason);
	$("#audit_time").text(baseInfo.audit_time);
	$("#audit_result").text(baseInfo.audit_result);
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
	window.location.href="storeInfoAuditedList.jsp";
};
