var Request = new Object();
Request = GetRequest();
var org_id=Request["org_id"];

$(document).ready(function(){
	getEnterpriseAuditDetail();
});

function getEnterpriseAuditDetail(){
	cVe.startReqByMsgHandle(cVeName,'','','reqGetEnterpriseAuditDetail','resGetEnterpriseAuditDetail','ECP.handle.platform.OrgAuditHandle.handleMsg');
}

function reqGetEnterpriseAuditDetail(){
/*	var userId*/
	var req = {};
	req["op"] = "orgAuditWaitingList";
	req["org_id"] = org_id;
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resGetEnterpriseAuditDetail(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var registerInfo=retDataObj[cVe.cEioVeDataId].data.org_info;
	var primaryInfo=retDataObj[cVe.cEioVeDataId].data.org_state;
	var advanceInfo=retDataObj[cVe.cEioVeDataId].data.org_cert;
/*	alert(registerInfo.audit_id);*/
	
	if(registerInfo.audit_id!=undefined){
		$("#registerPanelBody").removeClass("hidden");
		$("#noRegisterPanelBody").addClass("hidden");
		var result=registerInfo;
		$("#org_id").val(result.org_id);
		$("#audit_id").val(result.audit_id);
		$("#org_info_id").val(result.org_info_id);
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
			/*var src="http://222.201.145.241:8888"+result.url;
			var image=new Image(); 
			image.src=src;
			document.getElementById("previewContent").innerHTML="<img src='"+src+"'>";*/
		};
	}else{
		$("#registerPanelBody").addClass("hidden");
		$("#noRegisterPanelBody").removeClass("hidden");
	}
	
	//初级待审核列表信息加载
	if(primaryInfo.length<=0){
//		$("#prompt_info").css("display","inline-block");//输入为空时
		$("#primaryTableDIV").css("display","none");
		$("#noPrimaryDIV").removeClass("hidden");
	}else{
//		$("#prompt_info").css("display","none");
		$("#noPrimaryDIV").addClass("hidden");
		primaryInfoAuditList();
		for(var i=0;i<primaryInfo.length;i++){
			primaryInfo[i].number=i+1; 
		}
		var reader_primary = {
		  page: 1 ,  
		  total: (primaryInfo.length-1)/pageSize +1,  
		  records: primaryInfo.length
		}; 				
		grid.setGridParam({data: primaryInfo, localReader: reader_primary}).trigger('reloadGrid');
	}
	//高级待审核列表信息加载
	if(advanceInfo.length<=0){
	//	$("#prompt_info").css("display","inline-block");//输入为空时
		$("#advanceTableDIV").css("display","none");
		$("#noAdvanceDIV").removeClass("hidden");
	}else{
	//	$("#prompt_info").css("display","none");
		$("#noAdvanceDIV").addClass("hidden");
		advanceInfoAuditList();
		for(var i=0;i<advanceInfo.length;i++){
			advanceInfo[i].number=i+1; 
		}
		var reader_advance = {
		  page: 1 ,  
		  total: (advanceInfo.length-1)/pageSize +1,  
		  records: advanceInfo.length
		}; 				
		grid.setGridParam({data: advanceInfo, localReader: reader_advance}).trigger('reloadGrid');
	}
}


/************************初级待审核信息列表******************************/
function primaryInfoAuditList(){
	grid= $('#primaryInfoAuditListTable').jqGrid({ 
		  //caption: "网点信息",
		  datatype:'local',  
		  //altRows: true,
		  colNames:['年度','员工数','营业额','操作时间','操作类型',''],
		  colModel:[  
		    {id:'year',name:'year',width:'100%',align:'center'},
		    {id:'employee_num',name:'employee_num',width:'100%',align:'center'},
		    {id:'revenue',name:'revenue', width:'100%',align:'center'},  
		    {id:'operation_time',name:'operation_time', width:'100%',align:'center'},
		    {id:'operation_type',name:'operation_type', width:'100%',align:'center'},
		    {id:'audit_id',name:'audit_id',key:true, width:'100%',align:'center',formatter:displayButtons_primary}
		  ],  
		  pager:"#pager_primary",  
		  sortname:'0',  
		  viewrecords: true,  
		  height:'100%',  
		  autowidth: true,
		  align:'center',
		  shrinkToFit:true,
		});	
}


function displayButtons_primary(cellValue,options,rowObject){
	audit_id = rowObject.audit_id;
	org_state_id=rowObject.org_state_id;
	var audit_primary_button = '<input type="button" value="审核" class=" btn btn-primary btn-sm" onclick="audit_primary('+audit_id+','+org_state_id+')"/>';
	return audit_primary_button;
}

function audit_primary(audit_id,org_state_id){
	window.location.href="primaryAuditDetailPage.jsp?audit_id="+audit_id+"&org_state_id="+org_state_id;
}

/************************高级待审核信息列表******************************/
function advanceInfoAuditList(){
	grid= $('#advanceInfoAuditListTable').jqGrid({ 
		  //caption: "网点信息",
		  datatype:'local',  
		  //altRows: true,
		  colNames:['证件种类','证件名称','操作时间','操作类型',''],
		  colModel:[  
		    {id:'cert_type',name:'cert_type',width:'100%',align:'center'},
		    {id:'cert_name',name:'cert_name',width:'100%',align:'center'},
		    {id:'operation_time',name:'operation_time', width:'100%',align:'center'},
		    {id:'operation_type',name:'operation_type', width:'100%',align:'center'},
		    {id:'audit_id',name:'audit_id',key:true, width:'100%',align:'center',formatter:displayButtons_advance}
		  ],  
		  pager:"#pager_advance",  
		  sortname:'0',  
		  viewrecords: true,  
		  height:'100%',  
		  autowidth: true,
		  align:'center',
		  shrinkToFit:true,
		});	
}


function displayButtons_advance(cellValue,options,rowObject){
	audit_id = rowObject.audit_id;
	org_cert_id=rowObject.org_cert_id;
	var audit_advance = '<input type="button" value="审核" class=" btn btn-primary my-jqgrid-button btn-sm"  onclick="audit_advance('+audit_id+','+org_cert_id+')"/>';
	return audit_advance;
}

function audit_advance(audit_id,org_cert_id){
	window.location.href="advanceAuditDetailPage.jsp?audit_id="+audit_id+"&org_cert_id="+org_cert_id;
}

/************************图片预览******************************/
//图片预览
function preview(){
	$("#previewModel").modal('show');
}

function goBack(){
	window.location.href="enterpriseInfoAuditList.jsp";
};

/************************审核注册级信息*************************/
function openRegisterAuditModel(){
	$("#registerAuditModel").modal('show');
}

function auditRegisterInfo(){
	cVe.startReqByMsgHandle(cVeName,'','','reqAuditRegisterInfo','resAuditRegisterInfo','ECP.handle.platform.OrgAuditHandle.handleMsg');
}

function GetJsonObjectForRegister() {
	var registerInfo;
	registerInfo=$("#registerInfoForm").serializeArray();
	var theRequest = new Object();
	for(var i=0;i<registerInfo.length;i++){
		theRequest[registerInfo[i].name]=unescape(registerInfo[i].value);
	}
	return theRequest;
}

function reqAuditRegisterInfo(){
		var req = {};
		var registerInfo = new Object();
		registerInfo = GetJsonObjectForRegister();
		req["op"] = "orgAuditUpdate";
		req["info_type"] = "org_info";
		var org_info_id = $("#org_info_id").val();
		req["org_info_id"] = org_info_id;
		req["data"] = registerInfo;
		var user_id=cVeUti.Cookie.getCookie("userId");
		req["user_id"] = user_id;
		cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
	}

function resAuditRegisterInfo(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId].data;
	alert(result);
	window.location.replace("enterpriseAuditDetailPage.jsp?org_id="+org_id);
}