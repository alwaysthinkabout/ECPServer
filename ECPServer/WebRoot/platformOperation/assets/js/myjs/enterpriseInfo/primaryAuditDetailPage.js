var Request = new Object();
Request = GetRequest();
var audit_id=Request["audit_id"];
var org_state_id=Request["org_state_id"];
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
	req["org_state_id"] = org_state_id;
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
		$("#org_state_id").val(result.org_state_id);
		$("#year").text(result.year);
		$("#employee_num").text(result.employee_num);
		$("#techstaff_num").text(result.techstaff_num);
		$("#revenue").text(result.revenue);
		$("#total_assets").text(result.total_assets);
		$("#total_indebt").text(result.total_indebt);
		$("#net_sales").text(result.net_sales);
		$("#current_assets").text(result.current_assets);
		$("#current_indebt").text(result.current_indebt);
		$("#operate_expense").text(result.operate_expense);
		$("#profit").text(result.profit);
		$("#loan_balance").text(result.loan_balance);
		$("#taxation").text(result.taxation);
		$("#rd_budget").text(result.rd_budget);
		/*$("#operation_reason").text(result.operation_reason);*/
		$("#operation_reason").text(result.content);
		if(result.urls==""){
			document.getElementById("previewContent").innerHTML="<h3 style='color:red;'>&nbsp;&nbsp;&nbsp;还未上传证明材料</h3>";
		}else{
			var urls=new Array();
			urls=(result.urls).split(",");
			var picCount=0;
			for(var i=0;i<urls.length;i++){
				var strs=new Array();
				var src="http://222.201.145.241:8888"+urls[i];
				var fileName=(urls[i]).substring(38);
				strs=fileName.split(".");
				var suffix=strs[strs.length-1];//文件后缀名
				//alert(suffix);
				if(suffix=="jpg"||suffix=="png"||suffix=="jpeg"){
					picCount++;
					var image=new Image(); 
					image.src=src;
					$("#lastDiv").after("<div class='form-group'><a onclick='preview("+picCount+")'><label>"+fileName+" >></label></a></div>");
					$("#lastDiv").after("<div class='modal fade' id='previewModel"+picCount+"' tabindex='-1' role='dialog' aria-labelledby='myModalLabel'>"
											+"<div class='modal-dialog'>"
												+"<div class='modal-content'> "					
													+"<div class='modal_form_content' id='previewContent"+picCount+"'></div>"
										+"</div></div></div>");
					document.getElementById("previewContent"+picCount).innerHTML="<img src='"+src+"' width=800px >";
				}else if(suffix=="doc"||suffix=="docx"||suffix=="pdf"||suffix=="xls"||suffix=="xlsx"||suffix=="txt"){
					var swfpath="ECPServer/download?name="+strs[0]+".swf^OrgFile";
					/*cVeUti.Cookie.setCookie("swfpath",swfpath);*/
					/*$("#lastDiv").after("<div class='form-group'><form name='viewForm' id='form_swf' action='../common/documentView.jsp' method='POST'>"  
	                +"<input type='submit' value='文件预览' class='BUTTON SUBMIT'/></form> </div>");*/
					/*$("#lastDiv").after("<div class='form-group'><a id='"+swfpath+"' onclick='fileReplace(this.id)'><label>"+fileName+"  >></label></a></div>");*/
					$("#lastDiv").after("<div class='form-group'><a id='"+swfpath+"' onclick='fileReplace(this.id)' href='../common/documentView.jsp' target='_blank'><label>"+fileName+"  >></label></a></div>");
				}
			}
		};
	}
}
/************************文件预览******************************/
function fileReplace(swfpath){
	cVeUti.Cookie.setCookie("swfpath",swfpath);
	/*alert(swfpath);*/
//	document.getElementById(swfpath).href("../common/documentView.jsp");
//	window.location.href="../common/documentView.jsp";
}
/************************图片预览******************************/
//图片预览
function preview(picCount){
	$("#previewModel"+picCount).modal('show');
}

function goBack(){
	window.location.href="enterpriseAuditDetailPage.jsp?org_id="+org_id;
}

/************************审核初级信息*************************/
function auditPrimaryInfo(){
	cVe.startReqByMsgHandle(cVeName,'','','reqAuditPrimaryInfo','resAuditPrimaryInfo','ECP.handle.platform.OrgAuditHandle.handleMsg');
}

function GetJsonObjectForPrimary() {
	var primaryInfo;
	primaryInfo=$("#primaryAuditForm").serializeArray();
	var theRequest = new Object();
	for(var i=0;i<primaryInfo.length;i++){
		theRequest[primaryInfo[i].name]=unescape(primaryInfo[i].value);
	}
	return theRequest;
}

function reqAuditPrimaryInfo(){
		var req = {};
		var primaryInfo = new Object();
		primaryInfo = GetJsonObjectForPrimary();
		req["op"] = "orgAuditUpdate";
		req["info_type"] = "org_state";
		req["data"] = primaryInfo;
		var user_id=cVeUti.Cookie.getCookie("userId");
		req["user_id"] = user_id;
		cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
	}

function resAuditPrimaryInfo(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId].data;
	alert(result);
	window.location.href="enterpriseAuditDetailPage.jsp?org_id="+org_id;
}