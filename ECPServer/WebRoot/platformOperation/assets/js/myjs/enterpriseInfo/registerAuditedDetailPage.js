var Request = new Object();
Request = GetRequest();
var audit_id=Request["audit_id"];
var org_id;

$(document).ready(function(){
	getRegisterAuditDetail();
});

function getRegisterAuditDetail(){
	cVe.startReqByMsgHandle(cVeName,'','','reqGetRegisterAuditDetail','resGetRegisterAuditDetail','ECP.handle.platform.OrgAuditHandle.handleMsg');
}

function reqGetRegisterAuditDetail(){
/*	var userId*/
	var req = {};
	req["op"] = "getRegisterInfoDetail";
	req["audit_id"] = audit_id;
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resGetRegisterAuditDetail(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId].data;
	if(result.length!=0){
		org_id=result.org_id;
		if(result.aim==""){
			$("#departmentRegisterDIV").addClass("hidden");
			$("#enterpriseRegisterDIV").removeClass("hidden");
			$("#license_no").text(result.license_no);
			$("#org_name").text(result.org_name);
			$("#org_type").text(result.org_type);
			$("#reg_address").text(result.reg_address);
			$("#legal_rep").text(result.legal_rep);
			$("#reg_capital").text(result.reg_capital);
			$("#reg_time").text(result.reg_time);
			$("#business_scope").text(result.business_scope);
			$("#staffcount").text(result.staffcount);
			$("#reg_auth").text(result.reg_auth);
		}else{
			$("#enterpriseRegisterDIV").addClass("hidden");
			$("#enterpriseRegisterDIV").removeClass("hidden");
			$("#license_noD").text(result.license_no);
			$("#org_nameD").text(result.org_name);
			$("#aimD").text(result.aim);
			$("#coreBusinessD").text(result.business_scope);
			$("#registerRegionD").text(result.reg_address);
			$("#registerPersonD").text(result.registerPerson);
			$("#fundSrcD").text(result.fund_src);
			$("#registerFundD").text(result.legal_rep);
			$("#holderD").text(result.holder);
			$("#regAuthD").text(result.reg_auth);
		}
		$("#contact").text(result.contact);
		$("#contact_phone").text(result.contact_phone);
		$("#contact_email").text(result.contact_email);
		$("#website").text(result.website);
		$("#oper_user_name").text(result.org_user_id);
		$("#oper_time").text(result.operation_time);
		$("#audit_user_name").text(result.audit_user_id);
		$("#audit_reason").text(result.audit_reason);
		$("#audit_time").text(result.audit_time);
		$("#audit_result").text(result.audit_result);
		
		document.getElementById('website').href=result.website;
		$("#operation_reason").text(result.content);
		if(result.url==""){
			document.getElementById("previewContent").innerHTML="<h3 style='color:red;'>&nbsp;&nbsp;&nbsp;还未上传证明材料</h3>";
		}else{
			var strs=new Array();
			var src="http://222.201.145.241:8888"+result.url;
			var fileName=(result.url).substring(38);
			strs=fileName.split(".");
			var suffix=strs[strs.length-1];//文件后缀名
			//alert(suffix);
			if(suffix=="jpg"||suffix=="png"||suffix=="jpeg"){
				var image=new Image(); 
				image.src=src;
				$("#lastDiv").after("<div class='form-group'><a onclick='preview()' ><label>"+fileName+" >></label></a></div>");
				document.getElementById("previewContent").innerHTML="<img src='"+src+"' width=800px >";
			}else if(suffix=="doc"||suffix=="docx"||suffix=="pdf"||suffix=="xls"||suffix=="xlsx"||suffix=="txt"){
				var swfpath="ECPServer/download?name="+strs[0]+".swf^OrgFile";
				cVeUti.Cookie.setCookie("swfpath",swfpath);
				/*$("#lastDiv").after("<div class='form-group'><form name='viewForm' id='form_swf' action='../common/documentView.jsp' method='POST'>"  
                +"<input type='submit' value='文件预览' class='BUTTON SUBMIT'/></form> </div>");*/
				$("#lastDiv").after("<div class='form-group'><a href='../common/documentView.jsp' target='_blank'><label>"+fileName+"  >></label></a></div>");
			}
		}
	}
}

/************************图片预览******************************/
//图片预览
function preview(){
	$("#previewModel").modal('show');
}

function goBack(){
	window.location.href="enterpriseAuditedDetailPage.jsp?org_id="+org_id;
}