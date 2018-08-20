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
	req["op"] = "getEnterpriseInfoAuditedDetail";
	req["org_id"] = org_id;
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resGetEnterpriseAuditDetail(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var registerInfo=retDataObj[cVe.cEioVeDataId].data.org_info;
	var primaryInfo=retDataObj[cVe.cEioVeDataId].data.org_state;
	var advanceInfo=retDataObj[cVe.cEioVeDataId].data.org_cert;
	//alert(registerInfo.length);
	
	if(registerInfo.length<=0){
		$("#registerTableDIV").css("display","none");
		$("#noRegisterDIV").removeClass("hidden");
	}else{
		$("#noRegisterDIV").addClass("hidden");
		registerInfoAuditList();
		for(var i=0;i<registerInfo.length;i++){
			registerInfo[i].number=i+1; 
		}
		var reader_register = {
		  page: 1 ,  
		  total: (registerInfo.length-1)/pageSize +1,  
		  records: registerInfo.length
		}; 				
		grid.setGridParam({data: registerInfo, localReader: reader_register}).trigger('reloadGrid');
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

/************************注册级待审核信息列表******************************/
function registerInfoAuditList(){
	grid= $('#registerInfoAuditListTable').jqGrid({ 
		  //caption: "网点信息",
		  datatype:'local',  
		  //altRows: true,
		  colNames:['营业执照号','公司名称','审核时间','审核人','审核结果',''],
		  colModel:[  
		    {id:'license_no',name:'license_no',width:'100%',align:'center'},
		    {id:'org_name',name:'org_name',width:'100%',align:'center'},
		    {id:'audit_time',name:'audit_time', width:'100%',align:'center'},  
		    {id:'audit_user_id',name:'audit_user_id', width:'100%',align:'center'},
		    {id:'audit_result',name:'audit_result', width:'100%',align:'center'},
		    {id:'audit_id',name:'audit_id',key:true, width:'100%',align:'center',formatter:displayButtons_register}
		  ],  
		  pager:"#pager_register",  
		  sortname:'0',  
		  viewrecords: true,  
		  height:'100%',  
		  autowidth: true,
		  align:'center',
		  shrinkToFit:true,
		});	
}


function displayButtons_register(cellValue,options,rowObject){
	audit_id = rowObject.audit_id;
	org_state_id=rowObject.org_state_id;
	var audit_register_button = '<input type="button" value="查看" class=" btn btn-primary btn-sm" onclick="search_register('+audit_id+','+org_state_id+')"/>';
	return audit_register_button;
}

function search_register(audit_id,org_state_id){
	window.location.href="registerAuditedDetailPage.jsp?audit_id="+audit_id+"&org_state_id="+org_state_id;
}

/************************初级待审核信息列表******************************/
function primaryInfoAuditList(){
	grid= $('#primaryInfoAuditListTable').jqGrid({ 
		  //caption: "网点信息",
		  datatype:'local',  
		  //altRows: true,
		  colNames:['年度','员工数','营业额','审核时间','审核人','审核结果',''],
		  colModel:[  
		    {id:'year',name:'year',width:'100%',align:'center'},
		    {id:'employee_num',name:'employee_num',width:'100%',align:'center'},
		    {id:'revenue',name:'revenue', width:'100%',align:'center'},  
		    {id:'audit_time',name:'audit_time', width:'100%',align:'center'},
		    {id:'audit_user_id',name:'audit_user_id', width:'100%',align:'center'},
		    {id:'audit_result',name:'audit_result', width:'100%',align:'center'},
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
	var search_primary_button = '<input type="button" value="查看" class=" btn btn-primary btn-sm" onclick="search_primary('+audit_id+','+org_state_id+')"/>';
	return search_primary_button;
}

function search_primary(audit_id,org_state_id){
	window.location.href="primaryAuditedDetailPage.jsp?audit_id="+audit_id+"&org_state_id="+org_state_id;
}

/************************高级待审核信息列表******************************/
function advanceInfoAuditList(){
	grid= $('#advanceInfoAuditListTable').jqGrid({ 
		  //caption: "网点信息",
		  datatype:'local',  
		  //altRows: true,
		  colNames:['证件种类','证件名称','审核时间','审核人','审核结果',''],
		  colModel:[  
		    {id:'cert_type',name:'cert_type',width:'100%',align:'center'},
		    {id:'cert_name',name:'cert_name',width:'100%',align:'center'},
		    {id:'audit_time',name:'audit_time', width:'100%',align:'center'},
		    {id:'audit_user_id',name:'audit_user_id', width:'100%',align:'center'},
		    {id:'audit_result',name:'audit_result', width:'100%',align:'center'},
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
	var search_advance_button = '<input type="button" value="查看" class=" btn btn-primary my-jqgrid-button btn-sm"  onclick="search_advance('+audit_id+','+org_cert_id+')"/>';
	return search_advance_button;
}

function search_advance(audit_id,org_cert_id){
	window.location.href="advanceAuditedDetailPage.jsp?audit_id="+audit_id+"&org_cert_id="+org_cert_id;
}

/********************返回************************/
function goBack(){
	window.location.href="enterpriseInfoAuditedList.jsp";
}