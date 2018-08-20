function nextStep1to2Add(){
	storeAdd();
}
function storeAddClick(){
	storeAdd();
}


function nextStep1to2(){
	if(levelflag){
		$("#nextStep2to3Add").hide();
		$("#nextStep2to3").show();
	}else{
		$("#nextStep2to3Add").show();
		$("#nextStep2to3").show();
//		$("#nextStep2to3").hide();
	}
	$("#wzStep1").removeClass("cur");
	$("#numStep1").removeClass("cur");
	$("#registerPanelBody").hide();
	$("#wzStep2").addClass("cur");
	$("#numStep2").addClass("cur");
	$("#primaryPanelBody").show();
}

var primaryFlag;
function nextStep1to2Add2(){
	$("#wzStep1").removeClass("cur");
	$("#numStep1").removeClass("cur");
	$("#registerAddPanelBody").hide();
	$("#registerPanelBody").hide();
	$("#wzStep2").addClass("cur");
	$("#numStep2").addClass("cur");
	$("#primaryPanelBody").show();
	$("#updatePrimaryButton").hide();
	if(primaryFlag){
		$("#addNewPrimaryInfoButton").hide();
		$("#primaryListInfoTable").show();
	}else{
		$("#primaryListInfoTable").hide();
		$("#addNewPrimaryInfoButton").show();
	}
	var obj_primary = $("#dataGrid_primary").jqGrid("getRowData");
	var date=new Date;
	var year=date.getFullYear();
	var recentYears = "";
	//alert(obj_primary[0].year);
	for(var j=1;j<=3;j++)
	{
		var flag = 0;
		for(var i=0;i<obj_primary.length;i++)
		{
			if(obj_primary[i].year==year-j)
				{
					flag = 1;
					break;
				}
		}
		if(flag==0&&j!=3)
		{
			recentYears += year-j+","; 
		}
		if(flag==0&&j==3)
		{
			recentYears += year-j; 
		}
	}
	if(recentYears!="")
	{
	    alert("您可添加"+recentYears+"年度信息来提高您的资料完整度。")
	}
}

/*function prevStep2to1ForAdd(){
	location.replace(location.href);
}
*/

function prevStep2to1(){
	/*location.replace(location.href);*/
	/*getStoreDetail();*/
	$("#wzStep2").removeClass("cur");
	$("#numStep2").removeClass("cur");
	$("#primaryPanelBody").hide();
	$("#primaryAddPanelBody").hide();
	$("#wzStep1").addClass("cur");
	$("#numStep1").addClass("cur");
	$("#registerPanelBody").show();
}

/*function nextStep2to3Add(){
	primaryStoreAdd();
	$("#wzStep2").removeClass("cur");
	$("#numStep2").removeClass("cur");
	$("#primaryPanelBody").hide();
	$("#primaryAddPanelBody").hide();
	$("#wzStep3").addClass("cur");
	$("#numStep3").addClass("cur");
	$("#advancedPanelBody").show();
}*/

function nextStep2to3(){
	$("#wzStep2").removeClass("cur");
	$("#numStep2").removeClass("cur");
	$("#primaryPanelBody").hide();
	$("#wzStep3").addClass("cur");
	$("#numStep3").addClass("cur");
	$("#advancedPanelBody").show();
}

function prevStep3to2(){
	$("#nextStep2to3Add").addClass("hidden");
	/*getStoreDetail();*/
	$("#wzStep3").removeClass("cur");
	$("#numStep3").removeClass("cur");
	$("#advancedPanelBody").hide();
	$("#wzStep2").addClass("cur");
	$("#numStep2").addClass("cur");
	$("#primaryPanelBody").show();
}

function selectType(type){
    if(type=="enterprise"){
    	$("#enterpriseAddPanel").show();
    	$("#departmentAddPanel").hide();
    }
    if(type=="department"){
    	$("#enterpriseAddPanel").hide();
    	$("#departmentAddPanel").show();
    }
}

function updateRegisterTab(){
	if(document.getElementById("infoStateVEDetaile_div").style.display!="none")
	{
		var infoState1 = document.getElementById("infoStateVESummary").innerHTML;
		var infoState2 = document.getElementById("infoStateVDSummary").innerHTML;
		if(infoState1 == "修改内容待审核"||infoState2 == "修改内容待审核")
		{
		    alert("由于您的注册级信息近期被修改，现处于待审核状态，在审核完成之前禁止修改。");
		    return;
		}
	}else
		{
			var infoState1 = document.getElementById("infoStateVE").innerHTML;
			var infoState2 = document.getElementById("infoStateVD").innerHTML;
			if(infoState1 == "待审核"||infoState2 == "待审核")
			{
			    alert("您的注册级信息处于待审核状态，禁止修改。");
			    return;
			}
		}
	
	$("#registerPanelBody").hide();
	$("#registerAddPanelBody").show();
	if(aimFlag==""){
		$("#enterpriseAddPanel").show();
		$("#departmentAddPanel").hide();
	}else{
		$("#enterpriseAddPanel").hide();
		$("#departmentAddPanel").show();
	}
}

function updatePrimaryTab(){
	$("#primaryPanelBody").hide();
	$("#primaryAddPanelBody").show();
	$("#findRegisterForUpdateButton").hide();
	$("#addAdvancedButton").hide();
	$("#updatePrimaryButton").show();
}

var org_id;
var user_id;
var levelflag;
$(document).ready(function(){
	org_id=cVeUti.Cookie.getCookie("org_id");	
	user_id=cVeUti.Cookie.getCookie("user_id");
	$("#enterpriseRegisterFileUpload_user_id").val(user_id);
	$("#user_id").val(user_id);
	$("#user_idAD").val(user_id);
	/*$("#storeIdAD").val(org_id);*/
	getStoreDetail();	
	if(org_id==""){
		$("#registerAddPanelBody").show();
		$("#findForPrimary").hide();
		$("#nextStep1to2Add").show();
		$("#updateRegister").hide();
	}else{
		$("#registerPanelBody").show();
	}
	/*var Request = new Object();
	Request = GetRequest();
	var id =Request["id"];
	if(id!="undefined"){
		if(id=="1"){
			//document.getElementById("nextStep1to2AddForFind").click();
			//$("#registerPanelBody").hide();
			//$("#primaryPanelBody").show();
			nextStep1to2Add2();
		}else if(id=="2"){
			nextStep2to3();
			$("#wzStep1").removeClass("cur");
			$("#numStep1").removeClass("cur");
			$("#registerAddPanelBody").hide();
			$("#registerPanelBody").hide();
		}
	}*/
});

function getStoreDetail(){
	cVe.startReqByMsgHandle(cVeName,'','','reqGetStoreDetail','resGetStoreDetail','ECP.handle.OrganizationInfoHandle.handleMsg');
}

function reqGetStoreDetail(){
/*	var userId*/
	var req = {};
	req["op"] = "orgAllDetail";
	req["storeId"] = org_id;
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

var aimFlag;
var liscenceNo;
function resGetStoreDetail(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	
	var level1=retDataObj[cVe.cEioVeDataId].data.level1;
	if(level1.length==0){
		$("#updateRegister").hide();
		$("#departmentAddPanel").hide();
	}else{
		$("#nextStep1to2Add").hide();
		$("#typeRadios").hide();
		var result=retDataObj[cVe.cEioVeDataId].data.level1;
		if(result.aim==""){
			$("#enterpriseViewPanel").show();
			$("#departmentViewPanel").hide();
		}else{
			$("#enterpriseViewPanel").hide();
			$("#departmentViewPanel").show();
		}
		if(level1.audit_state=="通过"){
			document.getElementById('infoStateVE').innerHTML="审核"+level1.audit_state;
			document.getElementById('infoStateVD').innerHTML="审核"+level1.audit_state;
		}else if(level1.audit_state=="不通过"){ 
				if(level1.operation_type!="修改")
				{
					/*document.getElementById('infoStateVE').innerHTML="审核"+level1.audit_state;
					document.getElementById('infoStateVD').innerHTML="审核"+level1.audit_state;*/
					$("#vdReason").show();
					$("#infoStateVD").hide();
					$("#veReason").show();
					$("#infoStateVE").hide();
					document.getElementById('veAuditState').innerHTML="审核"+level1.audit_state;
					document.getElementById('veAudit_reason').innerHTML=level1.audit_reason;
					document.getElementById('vdAuditState').innerHTML="审核"+level1.audit_state;
					document.getElementById('vdAudit_reason').innerHTML=level1.audit_reason;
				}else{
					document.getElementById('infoStateVDSummary').innerHTML="修改内容审核"+level1.audit_state;
					document.getElementById('infoStateVESummary').innerHTML="修改内容审核"+level1.audit_state;
					document.getElementById('infoStateVEDetaile').innerHTML=level1.org_info;
					document.getElementById('infoStateVDDetaile').innerHTML=level1.org_info;
					document.getElementById('oper_timeVE').innerHTML=level1.ope_time;
					document.getElementById('oper_timeVD').innerHTML=level1.ope_time;
					document.getElementById('audit_reasonVE').innerHTML=level1.audit_reason;
					document.getElementById('audit_reasonVD').innerHTML=level1.audit_reason;
					$("#infoStateVDDetail_div").show();
					$("#infoStateVEDetaile_div").show();
					$("#audit_reasonVE_div").show();
					$("#audit_reasonVD_div").show();
					$("#infoStateVE_div").hide();
					$("#infoStateVD_div").hide();
				}
			}else{
					if(level1.operation_type!="修改")
					{
						document.getElementById('infoStateVE').innerHTML=level1.audit_state;
						document.getElementById('infoStateVD').innerHTML=level1.audit_state;
					}else{
						document.getElementById('infoStateVDSummary').innerHTML="修改内容"+level1.audit_state;
						document.getElementById('infoStateVESummary').innerHTML="修改内容"+level1.audit_state;
						document.getElementById('infoStateVEDetaile').innerHTML=level1.org_info;
						document.getElementById('infoStateVDDetaile').innerHTML=level1.org_info;
						document.getElementById('oper_timeVE').innerHTML=level1.ope_time;
						document.getElementById('oper_timeVD').innerHTML=level1.ope_time;
						$("#infoStateVDDetail_div").show();
						$("#infoStateVEDetaile_div").show();
						$("#infoStateVE_div").hide();
						$("#infoStateVD_div").hide();
					}
			}
		
		$("#storeId").val(result.org_id);
		$("#storeIdForRegisterFind").val(result.org_id);
		$("#storeNameVE").text(result.org_name);
		$("#license_noVE").text(result.license_no);
		liscenceNo=result.license_no;
		$("#registerRegionVE").text(result.reg_address);
		$("#registerPersonVE").text(result.legal_rep);
		$("#registerTimeVE").text(result.reg_time);
		$("#workPlaceVE").text(result.site);
		$("#registerFundVE").text(result.reg_capital);
		$("#coreBusinessVE").text(result.business_scope);
		$("#propertiesVE").text(result.org_type);
		$("#staffAmountVE").text(result.staffcount);	
		$("#officialWebsiteVE").text(result.website);
		$("#reg_authVE").text(result.reg_auth);
		document.getElementById('officialWebsiteVE').href=result.website;
		$("#contactsVE").text(result.contact);
		$("#phoneVE").text(result.contact_phone);
		$("#emailVE").text(result.contact_email);
		
		
		$("#storeIdVD").val(result.org_id);
		$("#storeNameVD").text(result.org_name);
		$("#license_noVD").text(result.license_no);
		$("#registerRegionVD").text(result.reg_address);
		$("#registerPersonVD").text(result.legal_rep);
		$("#registerTimeVD").text(result.reg_time);
		$("#workPlaceVD").text(result.site);
		$("#registerFundVD").text(result.reg_capital);
		$("#coreBusinessVD").text(result.business_scope);
		$("#propertiesVD").text(result.org_type);
		$("#staffAmountVD").text(result.staffcount);	
		$("#officialWebsiteVD").text(result.website);
		document.getElementById('officialWebsiteVD').href=result.website;
		$("#contactsVD").text(result.contact);
		$("#phoneVD").text(result.contact_phone);
		$("#emailVD").text(result.contact_email); 
		$("#aimVD").text(result.aim);
		$("#fund_srcVD").text(result.fund_src);
		$("#holderVD").text(result.holder);
		$("#reg_authVD").text(result.reg_auth);
		if(result.url!=""){
			$("#enterpriseRegisterFileUploadDIV").empty();
			$("#departmentRegisterFileUploadDIV").empty();
			$("#enterpriseRegisterFileUploadDIV").append('<label id="enterpriseRegisterFileUploadLabel"><a target="_Blank" title="点击下载"' 
					+'href = "'+result.url+'">营业执照</a></label>'
					+'&nbsp;&nbsp;&nbsp<button id="enterpriseRegisterFileUploadButton" class="btn btn-danger" onclick="deleteLicenseFile('+result.cid+')" >删除</button>');
			$("#departmentRegisterFileUploadDIV").append('<label id="departmentRegisterFileUploadLabel"><a target="_Blank" title="点击下载"' 
					+'href = "'+result.url+'">营业执照</a></label>'
					+'&nbsp;&nbsp;&nbsp<button id="departmentRegisterFileUploadButton" class="btn btn-danger" onclick="deleteLicenseFile('+result.cid+')" >删除</button>');
		}else{
			$("#enterpriseRegisterFileUploadDIV").empty();
			$("#departmentRegisterFileUploadDIV").empty();
			$("#enterpriseRegisterFileUploadDIV").append('<button id="enterpriseRegisterFileUpload" type="button" class="btn btn-danger" onclick="enterpriseRegisterFileUploadModel()" >上传营业执照</button>');
			$("#departmentRegisterFileUploadDIV").append('<button id="departmentFileUpload" type="button" class="btn btn-danger" onclick="enterpriseRegisterFileUploadModel()" >上传营业执照</button>');
		}
		
		//修改页面赋值
		$("#storeId").val(result.org_id);
		$("#storeIdForRegisterUpdate").val(result.org_id);
		$("#storeName").val(result.org_name);
		$("#license_no").val(result.license_no);
		$("#registerRegion").val(result.reg_address);
		$("#registerPerson").val(result.legal_rep);
		$("#registerTime").val(result.reg_time);
		$("#workPlace").val(result.site);
		$("#registerFund").val(result.reg_capital);
		$("#coreBusiness").val(result.business_scope);
		$("#properties").val(result.org_type);
		$("#staffAmount").val(result.staffcount);
		$("#officialWebsite").val(result.website);
		$("#reg_auth").val(result.reg_auth);
		$("#contact").val(result.contact);
		$("#contactPhone").val(result.contact_phone);
		$("#contactEmail").val(result.contact_email);
		
		$("#storeIdAD").val(result.org_id);
		$("#storeNameAD").val(result.org_name);
		$("#license_noAD").val(result.license_no);
		$("#registerRegionAD").val(result.reg_address);
		$("#registerPersonAD").val(result.legal_rep);
		$("#registerTimeAD").val(result.reg_time);
		$("#workPlaceAD").val(result.site);
		$("#registerFundAD").val(result.reg_capital);
		$("#coreBusinessAD").val(result.business_scope);
		$("#propertiesAD").val(result.org_type);
		$("#staffAmountAD").val(result.staffcount);	
		$("#officialWebsiteAD").val(result.website);
		$("#contactsAD").val(result.contact);
		$("#phoneAD").val(result.contact_phone);
		$("#emailAD").val(result.contact_email); 
		$("#aimAD").val(result.aim);
		$("#fund_srcAD").val(result.fund_src);
		$("#holderAD").val(result.holder);
		$("#reg_authAD").val(result.reg_auth);
		aimFlag=result.aim;
	}
	
	var level2=retDataObj[cVe.cEioVeDataId].data.level2;
	if(level1!=""){
		$("#org_idP").val(level1.org_id);
		$("#license_noP").val(level1.license_no);
	}
	if(level2.length==0){
		primaryFlag=false;
		levelflag=false;
		$("#nextStep1to2AddForFind").show();
		$("#findForPrimary").hide();
	}else{
		primaryFlag=true;
		levelflag=true;
		$("#findForPrimary").show();
		/*$("#nextStep1to2AddForFind").hide();*/
		var primaryStoreData=level2;
		$('#dataGrid_primary').jqGrid("clearGridData");
		if(primaryStoreData.length<=0){
		/*	$("#prompt_info").css("display","inline-block");//输入为空时
*/			$("#primaryListInfoTable").css("display","none");
		}else{
			/*$("#prompt_info").css("display","none");*/
			primaryInfoList();
			for(var i=0;i<primaryStoreData.length;i++){
				primaryStoreData[i].number=i+1; 
			}
			var reader = {
			  page: 1 ,  
			  total: (primaryStoreData.length-1)/pageSize +1,  
			  records: primaryStoreData.length
			}; 				
			grid.setGridParam({data: primaryStoreData, localReader: reader}).trigger('reloadGrid');
		}
	}
		
	var level3 = retDataObj[cVe.cEioVeDataId].data.level3;
	if(level3.length==0){
		$("#findForAdvanced").hide();
		$("#nextStep2to3Add").show();
		
	}else{
		$("#addSuperInfo").css("display","none");
		$("#advancedPrompt").css("display","none");
		$("#findForAdvanced").show();
		$("#nextStep2to3Add").hide();
		
		$("#superStoreAddPageButton").css("display","none");
		$("#suerInfoTable").css("display","inline-block");
		certInfoList();
		var result=retDataObj[cVe.cEioVeDataId].data.level3;
		for(var i=0;i<result.length;i++){
			result[i].number=i+1; 
		}
		var reader = {
		  page: 1 ,  
		  total: (result.length-1)/pageSize +1,  
		  records: result.length
		}; 				
		grid.setGridParam({data: result, localReader: reader}).trigger('reloadGrid');
	}
	var Request = new Object();
	Request = GetRequest();
	var id =Request["id"];
	if(id!="undefined"){
		if(id=="1"){
			//document.getElementById("nextStep1to2AddForFind").click();
			//$("#registerPanelBody").hide();
			//$("#primaryPanelBody").show();
			nextStep1to2Add2();
		}else if(id=="2"){
			nextStep2to3();
			$("#wzStep1").removeClass("cur");
			$("#numStep1").removeClass("cur");
			$("#registerAddPanelBody").hide();
			$("#registerPanelBody").hide();
		}
	}
}

function superStoreAddPage(){
	$("#addSuperInfo").css("display","inline_block");
	$("#suerInfoTable").css("display","none");
	
	$("#cert_type").val("荣誉证书");
	$("#selectForNoFileDIV").hide();
	$("#liscenceFileTypeRadio").hide();
	$("#stateFileTypeRadio").hide();
	$("#liscenceWay1").removeAttr("checked");
    $("#liscenceWay2").removeAttr("checked");
    $("#stateWay1").removeAttr("checked");
    $("#stateWay2").removeAttr("checked");
    document.getElementById("cert_id").value = ""; 
    document.getElementById("cert_name").value = ""; 
    document.getElementById("auth_org").value = ""; 
    document.getElementById("cert_expire").value = ""; 
    document.getElementById("cert_date").value = ""; 
    //document.getElementById("cert_status").value = ""; 
    document.getElementById("cert_path").value = ""; 
    document.getElementById("AdvancedFileUpload_user_id").value = cVeUti.Cookie.getCookie("user_id");
}
function certInfoList(){
	
	grid= $('#dataGrid').jqGrid({ 
		  //caption: "网点信息",
		  datatype:'local',  
		  //altRows: true,
		  colNames:['证件种类','证件名称','证件状态','发证日期','有效期',/*'证件描述',*/'文件下载'/*,'认证状态'*/,'操作',],
		  colModel:[  
		    
		    {id:'cert_type',name:'cert_type', width:'100%',align:'center'},  
		    {id:'cert_name',name:'cert_name',width:'100%',align:'center'},
		    {id:'cert_status',name:'cert_status', width:'100%',align:'center'},  
		    {id:'cert_date',name:'cert_date', width:'100%',align:'center'},
		    {id:'cert_expire',name:'cert_expire', width:'100%',align:'center'},
		   /* {id:'apply_status_code',name:'apply_status_code', width:'100%',align:'center',hidden:'true'},*/
		    //{id:'cert_desc',name:'cert_desc', width:'100%',align:'center'},
		    {id:'fileDownload',name:'fileDownload', width:'100%',align:'center',formatter:fileDownload },
		    /*{id:'authState',name:'authState', width:'100%',align:'center' },*/
		    {id:'operate',name:'operate', width:'100%',align:'center',formatter:displayButtons}
		    /*{id:'cert_id',name:'cert_id', key:true, width:'100%',align:'center',hidden:true}*/
		  ],  
		  pager:"#pager",  
		  rowNum: 10,   
		  sortname:'0',  
		  viewrecords: true,  
		  height:'100%',  
		  autowidth: true,
		  align:'center',
		 //multiselect: true,
		  shrinkToFit:true,		
		});	
}

function fileDownload(cellValue,options,rowObject){
	
	var file = '<a target="_Blank" title="点击下载"style="color:#337ab7;" ' 
	+'href = "/ECPServer/download?type=OrgFile&name='+rowObject.cert_path+'">'+rowObject.cert_name+'</a>';
	return file;
}

function displayButtons(cellValue,options,rowObject){
	//var id = options.rowId;//这里的rowId是关键值，不是行号   
	//cVeUti.Cookie.setCookie("cert_id",options.rowId);
	var id = "'"+options.rowId+"'";
	var edit = '<button class=" btn btn-danger my-jqgrid-button"  onclick="deleteCert('+id+')">删除</button>';
	return edit;
}
var cert_id2 = "";
function deleteCert(id){
	//cert_id=id;
	//cVeUti.Cookie.setCookie("cert_id");
	//alert(cert_id);
	cert_id2 = id;
	cVe.startReqByMsgHandle(cVeName,'','','reqDeleteCert','resDeleteCert','ECP.handle.OrganizationInfoHandle.handleMsg');
}

function reqDeleteCert(){
	var req={};
	req["op"] = "orgCertDelete";
	req["cert_id"] = cert_id2;
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resDeleteCert(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId];
	//var data = result[0];删除企业高级注册信息失败
	if(result.result == "1"){ 		
		alert("删除失败");
		//window.location.reload();
    }else{
    	alert("删除成功");
//		window.location.reload();
//    	getStoreDetail();
//    	primaryListFresh();
    	advancedListFresh();
    }
}

/************************修改注册级信息*****************************/
function storeUpdate(){
	if(aimFlag==""){
		 var license_no = document.getElementById('license_no').value;
		 var org_name = document.getElementById('storeName').value;
		 var registerPerson = document.getElementById('registerPerson').value;
		 var registerTime = document.getElementById('registerTime').value;
		 var curTime = getNowFormatDate();
		 if(license_no=="")
		 {
		     alert("营业执照号不能为空！");
		     return;
		 }
		 if(org_name=="")
		 {
		     alert("单位名称不能为空！");
		     return;
		 }
		 if(registerPerson=="")
		 {
		     alert("法定代表人不能为空！");
		     return;
		 }
		/* var a = /^(\d{4})-(\d{2})-(\d{2})$/;
		 if ((registerTime!="")&&(!a.test(registerTime))) { 
			 alert("注册时间格式不正确，请选择日期。");
			 document.getElementById('registerTime').value = "";
			 return false;
		 } */
		 if(registerTime>curTime)
		 {
		     alert("注册时间不能晚于今天！");
		     return;
		 }
		 /*var inputValue = document.getElementById("contactPhone").value;
	     var mobile=/^((13[0-9]{1})|159|153)+\d{8}$/;
	     if(inputValue!==""&&!mobile.test(inputValue)){		        	 
				 alert("请填入有效的手机号");
				 return;
	     }*/
		// var teleNumber = /^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$/;
		 var teleNumber = /^((13[0-9]{1})|159|153)+\d{8}$/;
		 var fixedNumber = /^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/;
		 var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		 var temp = document.getElementById("contactEmail");    //对电子邮件的验证
		 var storeNumber = document.getElementById("contactPhone");    //对电话验证
		 var reg = new RegExp("^[0-9]*$");
		 var staffAmount = document.getElementById("staffAmount").value;
		 if(!reg.test(staffAmount)){
			    alert("公司在职人数只能为数字!");
			    return;
		 }
		 if(storeNumber.value!=""&&!teleNumber.test(storeNumber.value)&&!fixedNumber.test(storeNumber.value))
		 {
	       alert("请输入有效的电话号码！");
	       storeNumber.focus();
	       return false;
	    }
		 if(temp.value!=""&&!myreg.test(temp.value))
		 {
	       alert("请输入有效的Email！");
	       temp.focus();
	       return false;
	    }
		 
	}else{
		var license_noAD=$("#license_noAD").val();
		var storeNameAD=$("#storeNameAD").val();
		var aimAD=$("#aimAD").val();
		 var registerPersonAD = document.getElementById('registerPersonAD').value;
		if(license_noAD==""){
			alert("营业执照号不能为空！");
			return;
		}else if(storeNameAD==""){
			alert("单位名称不能为空！");
			return;
		}else if(aimAD==""){
			alert("宗旨不能为空");
			return;
		}else if(registerPersonAD=="")
			{
			    alert("法定代表人不能为空！");
			    return;
			}
		 var teleNumber = /^((13[0-9]{1})|159|153)+\d{8}$/;
		 var fixedNumber = /^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/;
		 var storeNumber = document.getElementById("contactPhoneAD");    //对电话验证
		 if(storeNumber.value!=""&&!teleNumber.test(storeNumber.value)&&!fixedNumber.test(storeNumber.value))
		 {
	       alert("请输入有效的电话号码！");
	       storeNumber.focus();
	       return false;
		 }
		 var temp = document.getElementById("contactEmailAD");    //对电子邮件的验证
		 var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		 if(temp.value!=""&&!myreg.test(temp.value))
		 {
	       alert("请输入有效的Email！");
	       temp.focus();
	       return false;
	    }
	}
	
	cVe.startReqByMsgHandle(cVeName,'','','reqStoreUpdate','resStoreUpdate','ECP.handle.OrganizationInfoHandle.handleMsg');
}
//获取当前日期
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    //var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
            /*+ " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();*/
    return currentdate;
}

function GetJsonObjectForRegister() {
	var store;
	if(aimFlag==""){
		store=$("#addStoreForm").serializeArray();
	}else{
		store=$("#addStoreDepartmentForm").serializeArray();
	}
	var theRequest = new Object();
	for(var i=0;i<store.length;i++){
		theRequest[store[i].name]=unescape(store[i].value);
	}
	return theRequest;
}

function reqStoreUpdate(){
	var store = new Object();
	store = GetJsonObjectForRegister();
	var req = {};
	req["op"] = "storeUpdate";
	req["store"] = store;
	req["user_id"] = cVeUti.Cookie.getCookie("user_id");
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resStoreUpdate(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var resultTip=retDataObj[cVe.cEioVeDataId].resultTip;
	alert(resultTip);
	location.replace(location.href);
}



/************************删除注册级信息*****************************/
function deleteStore(){
	cVe.startReqByMsgHandle(cVeName,'','','reqDeleteStore','resDeleteStore','ECP.handle.OrganizationInfoHandle.handleMsg');
}

function reqDeleteStore(){
	var req = {};
	req["op"] = "storeDelete";
	req["storeId"] = storeId;
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resDeleteStore(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var resultTip=retDataObj[cVe.cEioVeDataId].resultTip;
	alert(resultTip);
	location.replace("myStoreListPage.html");
}

/************************删除初级信息*****************************/
function primaryStoreDelete(){
	cVe.startReqByMsgHandle(cVeName,'','','reqDeletePrimaryStore','resDeletePrimaryStore','ECP.handle.OrganizationInfoHandle.handleMsg');
}

function reqDeletePrimaryStore(){
	var req = {};
	req["op"] = "orgStateDelete";
	req["org_id"] = storeId;
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resDeletePrimaryStore(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var resultTip=retDataObj[cVe.cEioVeDataId].resultTip;
	alert(resultTip);
	location.replace("myStoreListPage.html");
}

/***********************初级信息列表*********************************/
function primaryInfoList(){
	grid= $('#dataGrid_primary').jqGrid({ 
		  //caption: "网点信息",
		  datatype:'local',  
		 // altRows: true,
		  colNames:['年度','员工数','技术人员数','营业额','资产总额','审核材料','审核状态',''],
		  colModel:[  
		    {id:'year',name:'year', width:'80%',align:'center'},  
		    {id:'employee_num',name:'employee_num',width:'80%',align:'center'},
		    {id:'techStaff_num',name:'techStaff_num',width:'80%',align:'center'},
		    {id:'revenue',name:'revenue', width:'80%',align:'center'},
		    {id:'total_assets',name:'total_assets', width:'100%',align:'center'},
		    {id:'fileUpload',name:'fileUpload',width:'150%',align:'center',formatter:fileUploadAction},
		    {id:'audit_state',name:'audit_state', width:'100%',align:'center'},
		    {id:'id',name:'id',key:true, width:'100%',align:'center',formatter:primaryDisplayButtons}
		  ],  
		  pager:"#pager_primary",  
		  rowNum: 10,   
		  sortname:'year',  
		  viewrecords: true,  
		  height:'100%',  
		  autowidth: true,
		  align:'center',
		 //multiselect: true,
		  shrinkToFit:true,
		});	
}

//上传初级信息认证材料
function fileUploadAction(cellValue,options,rowObject){
	var id = options.rowId;
	var uri=rowObject.uri;
	var year=rowObject.year;
	//var cid=rowObject.cid;
	var fileUploadButton = "";
	var fileCounters = 0;
	if(uri!=""){
		fileCounters = uri.split(";").length;
	}
	if(uri==""){
		fileUploadButton = '<button class=" btn btn-primary" style="height: 25px;width:50%; line-height: 10px !important;" onclick="addStateFile('+id+','+fileCounters+')">上传认证材料</button>';
	}else{
		var url = uri.split(";");
		for(var i=0;i<url.length;i++)
		{
			fileUploadButton +='<div style="display:block"><a  target="_Blank" title="点击下载" style="color:#337ab7;" ' 
				+'href = "'+url[i].split(",")[0]+'">'+year+'年度企业认证材料'+(i+1)+'</a>'
				+'&nbsp<button class="btn btn-danger" style="height: 25px; line-height: 10px !important;" onclick="deleteStateFile('+id+','+url[i].split(",")[1]+')">删除</button></div> ';
		}
		if(fileCounters<3)
		fileUploadButton  += '<button class=" btn btn-primary" style="height: 25px; line-height: 10px !important;display: block;margin-left:25%;width:50%" onclick="addStateFile('+id+','+fileCounters+')">继续上传</button>';
	}
	return fileUploadButton;
}

function addStateFile(id,fileCounters){
	if(fileCounters==0){
		alert("在此您可上传企业年度信息的支撑材料，最多上传3份。");
	}else{
		alert("在此您可上传企业年度信息的支撑材料，您还可上传"+(3-fileCounters)+"份。");
	}
	
	document.getElementById("stateFileUpload_cert_expire").value =""; 
    document.getElementById("stateFileUpload_cert_date").value =""; 
    document.getElementById("stateFileUpload_cert_id").value =""; 
    document.getElementById("stateFileUpload_cert_path").value ="";
	$("#stateFileUpload_state_id").val(id);
	document.getElementById("stateUser_id").value = cVeUti.Cookie.getCookie("user_id");
	$("#stateFileUploadModel").modal('show');
	getPrimaryStoreDetailAnnual(id);
}

function stateFileUpload(){
	document.getElementById("stateFileUpload_cerLicense_no").value = cVeUti.Cookie.getCookie("org_id");
	var cert_name = document.getElementById("stateFileUpload_cert_name").value; 
    //document.getElementById("auth_org").value = ""; 
    var cert_expire = document.getElementById("stateFileUpload_cert_expire").value ; 
    var cert_date = document.getElementById("stateFileUpload_cert_date").value; 
    var cert_id = document.getElementById("stateFileUpload_cert_id").value ; 
    var cert_path = document.getElementById("stateFileUpload_cert_path").value;
    if(cert_name=="")
    {
        alert("证件名称不能为空！");
        return;
    }
    /*if(cert_id=="")
    {
        alert("证件编号不能为空！");
        return;
    }
    if(cert_date=="")
    {
        alert("发证日期不能为空！");
        return;
    }
    var a = /^(\d{4})-(\d{2})-(\d{2})$/;
	 if (!a.test(cert_date)) { 
		 alert("注册时间格式不正确，请选择日期。");
		 document.getElementById('stateFileUpload_cert_date').value = "";
		 return false;
	 } 
    if(cert_expire=="")
    {
        alert("证件有效期不能为空！");
        return;
    }
    */
    if(cert_path=="")
    {
        alert("请选择要上传的文件！");
        return;
    }
    /*document.getElementById("stateFileUpload_org_id").value = cVeUti.Cookie.getCookie("org_id"); */
	var formData = new FormData($( "#stateFileUploadForm" )[0]); 		
	$.ajax({  
          /* url: 'http://125.216.242.9:8080/ECPServer/job/OrgFileUpload',  */ 
          url: '/ECPServer/job/OrgFileUpload',
          type: 'POST',  
          dataType:'text',
          data: formData,  
          async: false,  
          cache: false,  
          contentType: false,  
          processData: false,  
          success: function (returndata) { 
        	  alert(returndata);
        	  $("#stateFileUploadModel").modal('hide');
          },  
          error: function (returndata) {  
              alert(returndata);  
          }  
     });  
	primaryListFresh();
	advancedListFresh();
}

function cancelForstateFileUpload(){
	$("#stateFileUploadModel").modal('hide');
}

function primaryDisplayButtons(cellValue,options,rowObject){
	var id = options.rowId;//这里的rowId是光见值，不是行号
	var audit_state = "'"+rowObject.audit_state+"'";
	var detail = '<button class=" btn btn-primary my-jqgrid-button"  onclick="showPriamryDetailInfo('+id+')">查看</button>';
	var del = '<button class=" btn btn-danger my-jqgrid-button"  onclick="primaryAnnualInfoDelete('+id+','+audit_state+')">删除</button>';
	return detail+" "+del;
}

var org_state_id;
function showPriamryDetailInfo(id){
	org_state_id=id;
	cVe.startReqByMsgHandle(cVeName,'','','reqPrimaryStoreShow','resPrimaryStoreShow','ECP.handle.OrganizationInfoHandle.handleMsg');
}

function reqPrimaryStoreShow(){
	var req = {};
	req["op"] = "orgStateDetailAnnual";
	req["id"] = org_state_id;
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}
function resPrimaryStoreShow(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var primaryStoreData=retDataObj[cVe.cEioVeDataId].data;
//	alert(data.employee_num);
	$("#license_noFP").text(primaryStoreData.license_no);
	$("#yearF").text(primaryStoreData.year);
	$("#employee_numF").text(primaryStoreData.employee_num);
	$("#techstaff_numF").text(primaryStoreData.techstaff_num);
	$("#revenueF").text(primaryStoreData.revenue);
	$("#total_assetsF").text(primaryStoreData.total_assets);
	$("#total_indebtF").text(primaryStoreData.total_indebt);
	$("#net_salesF").text(primaryStoreData.net_sales);
	$("#current_assetsF").text(primaryStoreData.current_assets);
	$("#current_indebtF").text(primaryStoreData.current_indebt);
	$("#operate_expenseF").text(primaryStoreData.operate_expense);	
	$("#profitF").text(primaryStoreData.profit);
	$("#loan_balanceF").text(primaryStoreData.loan_balance);
	$("#taxationF").text(primaryStoreData.taxation);
	$("#rd_budgetF").text(primaryStoreData.rd_budget);
	if(primaryStoreData.audit_state=="通过"){
		document.getElementById('infoStateYEAR').innerHTML="审核"+primaryStoreData.audit_state;
	}else if(primaryStoreData.audit_state=="未通过"){ 
			if(primaryStoreData.operation_type!="修改")
			{
				//document.getElementById('infoStateYEAR').innerHTML="审核"+primaryStoreData.audit_state;
				$("#yearReason").show();
				$("#infoStateYEAR").hide();
				document.getElementById('yearAuditState').innerHTML="审核"+primaryStoreData.audit_state;
				document.getElementById('yearAudit_reason').innerHTML=primaryStoreData.audit_reason;
			}else{
				document.getElementById('infoStateSummaryYEAR').innerHTML="修改内容审核"+primaryStoreData.audit_state;
				document.getElementById('infoStateDetaileYEAR').innerHTML=primaryStoreData.org_state_info;
				document.getElementById('oper_timeYEAR').innerHTML=primaryStoreData.ope_time;
				document.getElementById('audit_reasonYEAR').innerHTML=primaryStoreData.audit_reason;
				$("#infoStateDetaileYEAR_div").show();
				$("#audit_reasonYEAR_div").show();
				$("#infoStateYEAR_div").hide();
			}
		}else{
				if(primaryStoreData.operation_type!="修改")
				{
					document.getElementById('infoStateYEAR').innerHTML= primaryStoreData.audit_state;
				}else{
					document.getElementById('infoStateSummaryYEAR').innerHTML="修改内容"+primaryStoreData.audit_state;
					document.getElementById('infoStateDetaileYEAR').innerHTML=primaryStoreData.org_state_info;
					document.getElementById('oper_timeYEAR').innerHTML=primaryStoreData.ope_time;
					$("#infoStateDetaileYEAR_div").show();
					$("#infoStateYEAR_div").hide();
				}
		}
	
	/*if(primaryStoreData.operation_type!="新增")
	{
		document.getElementById('infoStateSummaryYEAR').innerHTML="修改内容"+primaryStoreData.audit_state;
		document.getElementById('infoStateDetaileYEAR').innerHTML=primaryStoreData.org_state_info;
		document.getElementById('oper_timeYEAR').innerHTML=primaryStoreData.ope_time;
		$("#infoStateDetaileYEAR_div").show();
		$("#infoStateYEAR_div").hide();
	}else{
		document.getElementById('infoStateYEAR').innerHTML=primaryStoreData.audit_state;
		$("#infoStateYEAR_div").show();
		$("#infoStateDetaileYEAR_div").hide();
	}*/
	$("#primaryDetailModel").modal('show');
}
/************************添加初级信息*****************************/
//var obj_primary = null;
function addNewPrimaryInfoPage(){
	//alert(obj.length);
	$("#primaryAddModel").modal('show');
}

function primaryInfoAdd(){
	var obj_primary = $("#dataGrid_primary").jqGrid("getRowData");
	//alert(obj_primary[0].year);
	var year=$("#yearA").val();
	if(year==""){
		alert("年度不能为空");
		return;
	}
	for(var i=0;i<obj_primary.length;i++)
	{
		if(year==obj_primary[i].year)
		{
			alert("'"+year+"'年度在列表中已经存在，请选择其他年度。");
			return;
		}
	}	
	var employee_num=$("#employee_numA").val();
	var techStaff_num=$("#techStaff_numA").val();
	if(employee_num==""){
		alert("员工数不能为空");
	}else if(techStaff_num==""){
		alert("技术人员数不能为空");
	}else if(parseInt(techStaff_num)>parseInt(employee_num)){
		alert("技术人员数不能超过员工数!");
	}else{
		cVe.startReqByMsgHandle(cVeName,'','','reqPrimaryStoreAdd','resPrimaryStoreAdd','ECP.handle.OrganizationInfoHandle.handleMsg');
	}
}

/*function GetJsonObject() {
	var store=$("#primaryStoreAddForm").serializeArray();
	var theRequest = new Object();
	for(var i=0;i<store.length;i++){
		theRequest[store[i].name]=unescape(store[i].value);
	}
	return theRequest;
}*/

function reqPrimaryStoreAdd(){
	$("#org_idForPrimaryAdd").val(cVeUti.Cookie.getCookie("org_id"));
	var store = new Object();
	store = GetJsonObjectForPrimary();
	var req = {};
	req["op"] = "orgStateAdd";
	req["state"] = store;
	req["user_id"] = cVeUti.Cookie.getCookie("user_id");
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resPrimaryStoreAdd(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var resultTip=retDataObj[cVe.cEioVeDataId].resultTip;
	alert(resultTip);
	$("#primaryAddModel").modal('hide');
	primaryListFresh();
	advancedListFresh();
}

/************************修改初级信息*****************************/
function primaryUpdateModelShow(){
	if(document.getElementById("infoStateDetaileYEAR_div").style.display!="none")
	{
		var infoState = document.getElementById("infoStateSummaryYEAR").innerHTML;
		if(infoState == "修改内容待审核")
		{
		    alert("由于该年度信息近期被修改，现处于待审核状态，在审核完成之前禁止修改。");
		    return;
		}
	}else
		{
			var infoState1 = document.getElementById("infoStateYEAR").innerHTML;
			if(infoState1 == "待审核")
			{
			    alert("该年度信息处于待审核状态，禁止修改。");
			    return;
			}
		}
	cVe.startReqByMsgHandle(cVeName,'','','reqPrimaryStoreShow','resPrimaryStoreUpdateShow','ECP.handle.OrganizationInfoHandle.handleMsg');
}
function resPrimaryStoreUpdateShow(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var primaryStoreData=retDataObj[cVe.cEioVeDataId].data;
//	alert(data.employee_num);
	//初级信息修改（已有信息加载）
	$("#id").val(primaryStoreData.id);
	$("#year").val(primaryStoreData.year);
	$("#employee_num").val(primaryStoreData.employee_num);
	$("#techStaff_num").val(primaryStoreData.techstaff_num);
	$("#revenue").val(primaryStoreData.revenue);
	$("#total_assets").val(primaryStoreData.total_assets);
	$("#total_indebt").val(primaryStoreData.total_indebt);
	$("#net_sales").val(primaryStoreData.net_sales);
	$("#current_assets").val(primaryStoreData.current_assets);
	$("#current_indebt").val(primaryStoreData.current_indebt);
	$("#operate_expense").val(primaryStoreData.operate_expense);	
	$("#profit").val(primaryStoreData.profit);
	$("#loan_balance").val(primaryStoreData.loan_balance);
	$("#taxation").val(primaryStoreData.taxation);
	$("#RD_budget").val(primaryStoreData.rd_budget);
	$("#primaryDetailModel").modal('hide');
	$("#primaryUpdateModel").modal('show');
}

function primaryStoreUpdate(){
//	var license_no=$("#license_no").val();
	var year=$("#year").val();
	var employee_num=$("#employee_num").val();
	var techStaff_num=$("#techStaff_num").val();
	/*if(license_no==""){
		alert("营业执照号不能为空");
	}else */
	if(year==""){
		alert("年度不能为空");
	}else if(employee_num==""){
		alert("员工数不能为空");
	}else if(techStaff_num==""){
		alert("技术人员数不能为空");
	}else if(parseInt(techStaff_num)>parseInt(employee_num)){
		alert("技术人员数不能超过员工数!");
	}else{
		cVe.startReqByMsgHandle(cVeName,'','','reqPrimaryStoreUpdate','resPrimaryStoreUpdate','ECP.handle.OrganizationInfoHandle.handleMsg');
	}
}

function GetJsonObjectForPrimary() {
	var state=$("#primaryStoreAddForm").serializeArray();
	var theRequest = new Object();
	for(var i=0;i<state.length;i++){
		theRequest[state[i].name]=unescape(state[i].value);
	}
	return theRequest;
}

function GetJsonObjectForPrimaryUpdate() {
	var state=$("#primaryStoreUpdateForm").serializeArray();
	var theRequest = new Object();
	for(var i=0;i<state.length;i++){
		theRequest[state[i].name]=unescape(state[i].value);
	}
	return theRequest;
}

function reqPrimaryStoreUpdate(){
	var state = new Object();
	$("#org_idForPrimaryUpdate").val(cVeUti.Cookie.getCookie("org_id"));
	state = GetJsonObjectForPrimaryUpdate();	
	var req = {};
	req["op"] = "orgStateUpdate";
	req["state"] = state;
	req["user_id"] = cVeUti.Cookie.getCookie("user_id");
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resPrimaryStoreUpdate(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var resultTip=retDataObj[cVe.cEioVeDataId].resultTip;
	alert(resultTip);
	$("#primaryUpdateModel").modal('hide');
	primaryListFresh();
}
/************************删除初级信息*****************************/
var primary_delete_id;
function primaryAnnualInfoDelete(id,audit_state){
	primary_delete_id=id;
	if(audit_state == "待审核"){
		alert("当前年度信息处于待审核状态，禁止删除。");
		return;
	}
	var msg = "您确定要删除该年度初级信息吗？"; 
	if (confirm(msg)==true){
		cVe.startReqByMsgHandle(cVeName,'','','reqPrimaryAnnualInfoDelete','resPrimaryAnnualInfoDelete','ECP.handle.OrganizationInfoHandle.handleMsg');
		return true; 
	}else{ 
		return false; 
	}
}

function reqPrimaryAnnualInfoDelete(){
	var req = {};
	req["op"] = "orgStateDelete";
	req["id"] = primary_delete_id;
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resPrimaryAnnualInfoDelete(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var resultTip=retDataObj[cVe.cEioVeDataId].resultTip;
	alert(resultTip);
	primaryListFresh();
}

/****************************初级信息列表更新***************************/
function primaryListFresh(){
	cVe.startReqByMsgHandle(cVeName,'','','reqPrimaryStoreFresh','resPrimaryStoreFresh','ECP.handle.OrganizationInfoHandle.handleMsg');
}

function reqPrimaryStoreFresh(){
	var req = {};
	//req["op"] = "orgStateDetail";
	req["op"] = "orgAllDetail";
	//req["org_id"] = org_id;
	req["storeId"] = org_id;
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resPrimaryStoreFresh(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var level2=retDataObj[cVe.cEioVeDataId].data.level2;;
	updatePrimaryGrid(level2);
}


function updatePrimaryGrid(level2){
	var primaryStoreData=level2;
	$('#dataGrid_primary').jqGrid("clearGridData");
	if(primaryStoreData.length<=0){
		$("#primaryListInfoTable").css("display","none");
	}else{
		/*$("#prompt_info").css("display","none");*/
		primaryInfoList();
		for(var i=0;i<primaryStoreData.length;i++){
			primaryStoreData[i].number=i+1; 
		}
		var reader = {
		  page: 1 ,  
		  total: (primaryStoreData.length-1)/pageSize +1,  
		  records: primaryStoreData.length
		}; 				
		grid.setGridParam({data: primaryStoreData, localReader: reader}).trigger('reloadGrid');
	}
	$("#addNewPrimaryInfoButton").hide();
	$("#primaryListInfoTable").show();
	$("#nextStep2to3").show();
}
/****************************高级信息列表更新***************************/
function advancedListFresh(){
	cVe.startReqByMsgHandle(cVeName,'','','reqAdvancedStoreFresh','resAdvancedStoreFresh','ECP.handle.OrganizationInfoHandle.handleMsg');
}

function reqAdvancedStoreFresh(){
	var req = {};
	req["op"] = "orgAllDetail";
	req["storeId"] = org_id;
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resAdvancedStoreFresh(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var level3=retDataObj[cVe.cEioVeDataId].data.level3;
	var level2=retDataObj[cVe.cEioVeDataId].data.level2;
	var level1=retDataObj[cVe.cEioVeDataId].data.level1;
	updateAdvancedGrid(level3);
	updatePrimaryGrid(level2);
	if(level1.url!=""){
		$("#enterpriseRegisterFileUploadDIV").empty();
		$("#departmentRegisterFileUploadDIV").empty();
		$("#enterpriseRegisterFileUploadDIV").append('<label id="enterpriseRegisterFileUploadLabel"><a target="_Blank" title="点击下载"' 
				+'href = "'+level1.url+'">营业执照</a></label>'
				+'&nbsp;&nbsp;&nbsp<button id="enterpriseRegisterFileUploadButton" class="btn btn-danger" onclick="deleteLicenseFile('+level1.cid+')" >删除</button>');
		$("#departmentRegisterFileUploadDIV").append('<label id="departmentRegisterFileUploadLabel"><a target="_Blank" title="点击下载"' 
				+'href = "'+level1.url+'">营业执照</a></label>'
				+'&nbsp;&nbsp;&nbsp<button id="departmentRegisterFileUploadButton" class="btn btn-danger" onclick="deleteLicenseFile('+level1.cid+')" >删除</button>');
	}else{
		$("#enterpriseRegisterFileUploadDIV").empty();
		$("#departmentRegisterFileUploadDIV").empty();
		$("#enterpriseRegisterFileUploadDIV").append('<button id="enterpriseRegisterFileUpload" type="button" class="btn btn-danger" onclick="enterpriseRegisterFileUploadModel()" >上传营业执照</button>');
		$("#departmentRegisterFileUploadDIV").append('<button id="departmentFileUpload" type="button" class="btn btn-danger" onclick="enterpriseRegisterFileUploadModel()" >上传营业执照</button>');
		
	}
}


function updateAdvancedGrid(level3){
	var advancedStoreData=level3;
	$('#dataGrid').jqGrid("clearGridData");
	if(advancedStoreData.length<=0){
		/*$("#findForAdvanced").hide();
		$("#nextStep2to3Add").show();*/
	}else{
		/*$("#prompt_info").css("display","none");*/
		certInfoList();
		for(var i=0;i<advancedStoreData.length;i++){
			advancedStoreData[i].number=i+1; 
		}
		var reader = {
		  page: 1 ,  
		  total: (advancedStoreData.length-1)/pageSize +1,  
		  records: advancedStoreData.length
		}; 				
		grid.setGridParam({data: advancedStoreData, localReader: reader}).trigger('reloadGrid');
		$("#advancedInformation").show();
		$("#nextStep2to3").show();
		
		$("#advancedPrompt").hide();
		$("#suerInfoTable").show();
	}
}

/****************************注册级文件上传*******************************************/
function enterpriseRegisterFileUploadModel(){
	$("#enterpriseRegisterFileUpload_cert_id").val($("#license_noVE").text());
	$("#enterpriseRegisterFileUploadModel").modal('show');
}
function enterpriseRegisterFileUpload(){
//	var 
	document.getElementById("enterpriseRegisterFileUpload_cerLicense_no").value = cVeUti.Cookie.getCookie("org_id"); 
    document.getElementById("enterpriseRegisterFileUpload_org_id").value = cVeUti.Cookie.getCookie("org_id"); 
	var cert_name = document.getElementById("enterpriseRegisterFileUpload_cert_name").value; 
    //document.getElementById("auth_org").value = ""; 
    var cert_expire = document.getElementById("enterpriseRegisterFileUpload_cert_expire").value ; 
    var cert_date = document.getElementById("enterpriseRegisterFileUpload_cert_date").value; 
    var cert_id = document.getElementById("enterpriseRegisterFileUpload_cert_id").value ; 
    var cert_path = document.getElementById("enterpriseRegisterFileUpload_cert_path").value;
    if(cert_name=="")
    {
        alert("文件名称不能为空！");
        return;
    }
    /*if(cert_id=="")
    {
        alert("文件编号不能为空！");
        return;
    }
    if(cert_date=="")
    {
        alert("发证日期不能为空！");
        return;
    }
    var a = /^(\d{4})-(\d{2})-(\d{2})$/;
	 if (!a.test(cert_date)) { 
		 alert("发证日期格式不正确，请选择日期。");
		 document.getElementById('enterpriseRegisterFileUpload_cert_date').value = "";
		 return false;
	 } 
    if(cert_expire=="")
    {
        alert("文件有效期不能为空！");
        return;
    }
   */
    if(cert_path=="")
    {
        alert("请选择要上传的文件！");
        return;
    }
	
	var formData = new FormData($( "#enterpriseRegisterFileUploadForm" )[0]); 		
	$.ajax({  
          /* url: 'http://125.216.242.9:8080/ECPServer/job/OrgFileUpload',  */ 
          url: '/ECPServer/job/OrgFileUpload',
          type: 'POST',  
          dataType:'text',
          data: formData,  
          async: false,  
          cache: false,  
          contentType: false,  
          processData: false,  
          success: function (returndata) { 
        	  alert(returndata);
        	  $("#enterpriseRegisterFileUploadModel").modal('hide');
        	  location.replace(location.href);
          },  
          error: function (returndata) {  
              alert(returndata);  
          }  
     });  
}

function cancelForEnterpriseRegisterFileUpload(){
	$("#enterpriseRegisterFileUploadModel").modal('hide');
}

function departmentRegisterFileUploadModel(){
	$("#enterpriseRegisterFileUpload_cert_id").val($("#license_noVD").text());
	$("#enterpriseRegisterFileUploadModel").modal('show');
}

//

$("#cert_type").change(function(){
	var value=$(this).children('option:selected').val();
	if(value=="身份认证"){
		$("#liscenceFileTypeRadio").show();
		$("#selectForNoFileDIV").hide();
		$("#stateFileTypeRadio").hide();
//		$("#selectForNoFile").options.length = 0; 
		document.getElementById("selectForNoFile").options.length = 0;
		$("#uploadType").val("");
		$("#cert_name").val("");
    	$("#cert_id").val("");
	}else if(value=="资质认证"){
		$("#liscenceFileTypeRadio").hide();
		$("#stateFileTypeRadio").show();
		$("#uploadType").val("");
		$("#cert_name").val("");
    	$("#cert_id").val("");
		
	}else{
		$("#liscenceFileTypeRadio").hide();
		$("#selectForNoFileDIV").hide();
		$("#stateFileTypeRadio").hide();
//		$("#selectForNoFile").options.length = 0; 
		document.getElementById("selectForNoFile").options.length = 0;
		$("#uploadType").val("");
		$("#cert_name").val("");
    	$("#cert_id").val("");
	}
//	alert(value);
});

function selectWay(way){
    if(way=="businessLicense"){
    	$("#AdvancedFileUpload_org_id").val(org_id);
    	$("#uploadType").val("orgInfo");
    	$("#cert_name").val("营业执照");
    	$("#cert_id").val(liscenceNo);
    }else{
    	$("#uploadType").val("");
    	$("#cert_name").val("");
    	$("#cert_id").val("");
    }
}

function selectStateWay(way){
	if(way=="state"){
		$("#uploadType").val("stateInfo");
		document.getElementById("selectForNoFile").options.length = 0;
		getInfoForPrimaryWithoutFile();
		$("#cert_id").val("");
	}else{
		document.getElementById("selectForNoFile").options.length = 0;
		$("#selectForNoFileDIV").hide();
		$("#uploadType").val("");
		$("#cert_name").val("");
		$("#cert_id").val("");
	}
}
$("#selectForNoFile").change(function(){
	var text=$(this).children('option:selected').text();
	$("#cert_name").val(text+"年企业资质状况认证");
});
/*******************获取未上传文件的初级信息年度和id*************************/
function getInfoForPrimaryWithoutFile(){
	cVe.startReqByMsgHandle(cVeName,'','','reqGetInfoForPrimaryWithoutFile','resGetInfoForPrimaryWithoutFile','ECP.handle.OrganizationInfoHandle.handleMsg');
}

function reqGetInfoForPrimaryWithoutFile(){
	var req = {};
	req["op"] = "stateNotFileList";
	req["org_id"] = org_id;
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resGetInfoForPrimaryWithoutFile(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var rows=retDataObj[cVe.cEioVeDataId].rows;
	if(rows.length>0){
		for(var i=0;i<rows.length;i++){
			$("#selectForNoFile").append('<option value="'+rows[i].id+'">'+rows[i].year+'</option>');
		}
		$("#selectForNoFileDIV").show();
		var yearForPrimaryWithoutFile=rows[0].year;
		$("#cert_name").val(yearForPrimaryWithoutFile+"年企业资质状况认证");
	}else{
		alert("企业年度资质状况认证全部上传完毕！");
		$("#stateWay2").removeAttr("checked");
		$("#stateWay1").attr('checked','true');
		$("#uploadType").val("");
	}
}


/*********************在注册级信息页面删除营业执照文件***********************/
var deleteCidForLicenseFile;
function deleteLicenseFile(cid){
	deleteCidForLicenseFile=cid;
	cVe.startReqByMsgHandle(cVeName,'','','reqDeleteLicenseFileForAdvanced','resDeleteLicenseFileForAdvanced','ECP.handle.OrganizationInfoHandle.handleMsg');
}

function reqDeleteLicenseFileForAdvanced(){
	var req = {};
	req["op"] = "deleteLicenseFile";
	req["org_id"] = org_id;
	req["cid"] = deleteCidForLicenseFile;
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resDeleteLicenseFileForAdvanced(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var resultTip=retDataObj[cVe.cEioVeDataId].resultTip;
	alert(resultTip);
}


/*******************在初级信息页面删除企业资质状况认证文件**********************/
var deleteCidForStateFile;
var deleteStateIdForStateFile;
function deleteStateFile(id,cid){
	deleteStateIdForStateFile=id;
	deleteCidForStateFile=cid;
	cVe.startReqByMsgHandle(cVeName,'','','reqDeleteStateFileForAdvanced','resDeleteStateFileForAdvanced','ECP.handle.OrganizationInfoHandle.handleMsg');
}

function reqDeleteStateFileForAdvanced(){
	var req = {};
	req["op"] = "deleteStateFile";
	req["state_id"] = deleteStateIdForStateFile;
	req["cid"] = deleteCidForStateFile;
	req["user_id"] = cVeUti.Cookie.getCookie("user_id");
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resDeleteStateFileForAdvanced(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var resultTip=retDataObj[cVe.cEioVeDataId].resultTip;
	alert(resultTip);
	primaryListFresh();
	advancedListFresh();
}