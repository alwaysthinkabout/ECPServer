var org_id = "";
var user_id = "";
$(function(){
	org_id=cVeUti.Cookie.getCookie("org_id");
	user_id=cVeUti.Cookie.getCookie("user_id");
	$("#user_id").val(user_id);
	$("#user_idAD").val(user_id);
	getStoreDetail();	
	if(org_id==""){
		$("#registerAddPanelBody").show();
		$("#updateRegister").hide();
	}else{
		$("#registerPanelBody").show();
	}
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
					document.getElementById('infoStateVE').innerHTML="审核"+level1.audit_state;
					document.getElementById('infoStateVD').innerHTML="审核"+level1.audit_state;
				}else{
					document.getElementById('infoStateVDSummary').innerHTML="修改内容审核"+level1.audit_state;
					document.getElementById('infoStateVESummary').innerHTML="修改内容审核"+level1.audit_state;
					document.getElementById('infoStateVEDetaile').innerHTML=level1.org_info;
					document.getElementById('infoStateVDDetaile').innerHTML=level1.org_info;
					document.getElementById('oper_timeVE').innerHTML=level1.ope_time;
					document.getElementById('oper_timeVD').innerHTML=level1.ope_time;
					$("#infoStateVDDetail_div").show();
					$("#infoStateVEDetaile_div").show();
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

function enterpriseRegisterFileUploadModel(){
	$("#enterpriseRegisterFileUpload_cert_id").val($("#license_noVE").text());
	$("#enterpriseRegisterFileUploadModel").show();
	$("#registerPanelBody").hide();
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
    if(cert_id=="")
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
function storeAddClick(){
	storeAdd();
}
function storeAdd(){
	var radioChecked= $('input:radio[name="type"]:checked').val();
	if(radioChecked=="enterprise"){
		var license_no=$("#license_no").val();
		var storeName=$("#storeName").val();
		var properties=$("#properties").val().length;
		var registerRegion=$("#registerRegion").val().length;
		var registerPerson = $("#registerPerson").val();
		var registerFund=$("#registerFund").val().length;
		var coreBusiness=$("#coreBusiness").val().length;
		var reg_auth=$("#reg_auth").val().length;
		var contact=$("#contact").val().length;
		var officialWebsite=$("#officialWebsite").val().length;
		var teleNumber = /^((13[0-9]{1})|159|153)+\d{8}$/;
		var fixedNumber = /^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/;
		var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		var temp = document.getElementById("contactEmail");    //对电子邮件的验证
		var storeNumber = document.getElementById("contactPhone");    //对电话验证		 
		if(license_no==""){
			alert("营业执照号不能为空");
			return false;
		} 
		if(storeName==""){
			alert("单位名称不能为空");
			return false;
		} 
		if(registerPerson=="")
		{
			alert("法定代表人不能为空！");
			return false;
		}
		 var reg = new RegExp("^[0-9]*$");
		 var staffAmount = document.getElementById("staffAmount");
		 if(!reg.test(staffAmount.value)){
			    alert("公司在职人数只能为数字!");
			    staffAmount.focus();
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
		var maxWorsNumbers = 30;//最大输入长度
		if(license_no.length>maxWorsNumbers||
		   storeName.length>maxWorsNumbers||
		   properties>maxWorsNumbers||
		   registerRegion>maxWorsNumbers||
		   registerPerson.length>maxWorsNumbers||
		   registerFund>maxWorsNumbers||
		   coreBusiness>maxWorsNumbers||
		   reg_auth>maxWorsNumbers||
		   contact>maxWorsNumbers||
		   officialWebsite>maxWorsNumbers||
		   staffAmount.value.length>maxWorsNumbers||
		   temp.value.length>maxWorsNumbers)
		{
			alert("每一个输入项的长度不能超过 " +maxWorsNumbers+ " !");
		}
		cVe.startReqByMsgHandle(cVeName,'','','reqStoreAdd','resStoreAdd','ECP.handle.OrganizationInfoHandle.handleMsg');
		
	}else{
			var license_no=$("#license_noAD").val();
			var storeName=$("#storeNameAD").val();
			var registerPersonAD = $("#registerPersonAD").val();
			var registerRegion = $("#registerRegionAD").val();
			var registerFund=$("#registerFundAD").val().length;
			var coreBusiness=$("#coreBusinessAD").val().length;
			var reg_auth=$("#reg_authAD").val().length;
			var contact=$("#contactAD").val().length;
			var fund_srcAD = $("#fund_srcAD").val().length;
			var holderAD = $("#holderAD").val().length;
			var officialWebsite=$("#officialWebsiteAD").val().length;
			if(license_no==""){
				alert("营业执照号不能为空");
				return false;
			}
			if(storeName==""){
				alert("单位名称不能为空");
				return false;
			}
			var aim=$("#aimAD").val();
			if(radioChecked=="department"&&aim==""){
				alert("宗旨不能为空！");
				return false;
			} 
			if(registerPersonAD=="")
			{
			    alert("法定代表人不能为空！");
			    return false;
			}
			var teleNumber = /^((13[0-9]{1})|159|153)+\d{8}$/;
			var fixedNumber = /^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/;
			var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
			var temp = document.getElementById("contactEmailAD");    //对电子邮件的验证
			var storeNumber = document.getElementById("contactPhoneAD");    //对电话验证
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
			var maxWorsNumbers1 = 100;
			var maxWorsNumbers = 30;//最大输入长度
			if(license_no.length>maxWorsNumbers||
			   storeName.length>maxWorsNumbers||
			   registerRegion.length>maxWorsNumbers||
			   registerPersonAD.length>maxWorsNumbers||
			   registerFund>maxWorsNumbers||
			   reg_auth>maxWorsNumbers||
			   contact>maxWorsNumbers||
			   officialWebsite>maxWorsNumbers||
			   temp.value.length>maxWorsNumbers||
			   fund_srcAD>maxWorsNumbers||
			   holderAD>maxWorsNumbers)
			{
				alert("除“宗旨”和“业务范围”以外，每一个输入项的长度不能超过 " +maxWorsNumbers+ " !");
				return false;
			}
			if(coreBusiness>maxWorsNumbers1||aim.length>maxWorsNumbers1)
			{
				alert("“宗旨”和“业务范围”的长度不能超过 " +maxWorsNumbers1+ " !");
				return false;
			}
			cVe.startReqByMsgHandle(cVeName,'','','reqStoreAdd','resStoreAdd','ECP.handle.OrganizationInfoHandle.handleMsg');
		}
		
}
function GetJsonObjectForRegisterAdd() {
	var radioChecked= $('input:radio[name="type"]:checked').val();
	var store;
	if(radioChecked=="enterprise"){
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


function reqStoreAdd(){
	var store = new Object();
	store = GetJsonObjectForRegisterAdd();
	var req = {};
	req["op"] = "storeAdd";
	req["store"] = store;
	req["user_id"] = cVeUti.Cookie.getCookie("user_id");
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resStoreAdd(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var resultTip=retDataObj[cVe.cEioVeDataId].resultTip;
	var result=retDataObj[cVe.cEioVeDataId].result;
	var data=retDataObj[cVe.cEioVeDataId].data;
	cVeUti.Cookie.setCookie("org_id",data);
	cVeUti.Cookie.setCookie("org_name",$("#storeName").val());
	org_id=data;
	alert(resultTip);
	if(result!="fail"){
		getStoreDetail();
		location.replace(location.href);
	}
}