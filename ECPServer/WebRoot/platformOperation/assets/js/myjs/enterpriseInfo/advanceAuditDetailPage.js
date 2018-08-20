var Request = new Object();
Request = GetRequest();
var audit_id=Request["audit_id"];
var org_cert_id=Request["org_cert_id"];
var org_id;

$(document).ready(function(){
	getPrimaryAuditDetail();
});

function getPrimaryAuditDetail(){
	cVe.startReqByMsgHandle(cVeName,'','','reqGetPrimaryAuditDetail','resGetPrimaryAuditDetail','ECP.handle.platform.OrgAuditHandle.handleMsg');
}

function reqGetPrimaryAuditDetail(){
/*	var userId*/
	var req = {};
	req["op"] = "orgAuditDetail";
	req["audit_id"] = audit_id;
	req["org_cert_id"] = org_cert_id;
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resGetPrimaryAuditDetail(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId].data;
	if(result.length!=0){
		org_id=result.org_info_id;
		$("#audit_id").val(result.audit_id);
		$("#org_info_id").val(result.org_info_id);
		$("#org_cert_id").val(result.org_cert_id);
		$("#cert_type").text(result.cert_type);
		$("#cert_name").text(result.cert_name);
		$("#cert_id").text(result.cert_id);
		$("#auth_org").text(result.auth_org);
		$("#cert_date").text(result.cert_date);
		$("#cert_expire").text(result.cert_expire);
		$("#cert_status").text(result.cert_status);
		$("#cert_desc").text(result.cert_desc);
		$("#operation_reason").text(result.operation_reason);
		if(result.url==""){
			document.getElementById("previewContent").innerHTML="<h3 style='color:red;'>&nbsp;&nbsp;&nbsp;还未上传证明材料</h3>";
		}else{
			/*var src="http://222.201.145.241:8888"+result.url;
			var image=new Image(); 
			image.src=src;
			var height=image.height;
			var width=image.width;
			image.onload = function(){
				height=this.height;
				width=this.width;
				alert("图片的宽度为"+this.width+",长度为"+this.height);
				alert("image:"+image+",   height:"+height+"  width:"+width);
				
	//			document.getElementById("previewContent").innerHTML="<img src='"+src+"'>";
			};
			alert("image:"+image+",   height:"+height+"  width:"+width);
			document.getElementById("previewContent").innerHTML="<img src='"+src+"'>";*/
			
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
		};
	}
}

function goBack(){
	window.location.href="enterpriseAuditDetailPage.jsp?org_id="+org_id;
}

//图片预览
function preview(){
	$("#previewModel").modal('show');
}


/************************审核初级信息*************************/
function auditAdvanceInfo(){
	cVe.startReqByMsgHandle(cVeName,'','','reqAuditAdvanceInfo','resAuditAdvanceInfo','ECP.handle.platform.OrgAuditHandle.handleMsg');
}

function GetJsonObjectForAdvance() {
	var advanceInfo;
	advanceInfo=$("#advanceAuditForm").serializeArray();
	var theRequest = new Object();
	for(var i=0;i<advanceInfo.length;i++){
		theRequest[advanceInfo[i].name]=unescape(advanceInfo[i].value);
	}
	return theRequest;
}

function reqAuditAdvanceInfo(){
		var req = {};
		var advanceInfo = new Object();
		advanceInfo = GetJsonObjectForAdvance();
		req["op"] = "orgAuditUpdate";
		req["info_type"] = "org_cert";
		req["data"] = advanceInfo;
		var user_id=cVeUti.Cookie.getCookie("userId");
		req["user_id"] = user_id;
		cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
	}

function resAuditAdvanceInfo(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId].data;
	alert(result);
	window.location.href="enterpriseAuditDetailPage.jsp?org_id="+org_id;
}