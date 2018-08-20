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
		var fixedNumber = /^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{5,14}$/;
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
	       alert("请输入有效的电话号码！比如号码的有效性、不能包含空格等。");
	       storeNumber.focus();
	       return false;
		 }
		 if(temp.value!=""&&!myreg.test(temp.value))
		 {
	       alert("请输入有效的Email！");
	       temp.focus();
	       return false;
	    }		 
		var maxWorsNumbers = 50;//最大输入长度
		var maxWorsNumbers1 = 200;//最大输入长度
		if(license_no.length>maxWorsNumbers||
		   storeName.length>maxWorsNumbers||
		   properties>maxWorsNumbers||
		   registerRegion>maxWorsNumbers||
		   registerPerson.length>maxWorsNumbers||
		   registerFund>maxWorsNumbers||
		   reg_auth>maxWorsNumbers||
		   contact>maxWorsNumbers||
		   officialWebsite>maxWorsNumbers||
		   staffAmount.value.length>maxWorsNumbers||
		   temp.value.length>maxWorsNumbers)
		{
			alert("除经营范围外，其他每一项输入长度不能超过 " +maxWorsNumbers+ " !");
			return false;
		}
		if(coreBusiness > maxWorsNumbers1){
			alert("经营范围输入长度不能超过 " +maxWorsNumbers+ " !");
			return false;
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
			/*var reg = new RegExp("^[0-9]*$");
			var staffAmount = document.getElementById("staffAmountAD");
			if(!reg.test(staffAmount.value)){
			    alert("公司在职人数只能为数字!");
			    staffAmount.focus();
			    return;
			 }*/
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
			var maxWorsNumbers1 = 200;
			var maxWorsNumbers = 50;//最大输入长度
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

$(document).ready(
		
);

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
		/*$("#wzStep1").removeClass("cur");
		$("#numStep1").removeClass("cur");
		$("#registerAddPanelBody").hide();
		$("#wzStep2").addClass("cur");
		$("#numStep2").addClass("cur");
		$("#primaryAddPanelBody").show();
		$("#updatePrimaryButton").hide();*/
	}
}