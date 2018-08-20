
function getStoreDetail(){
	cVe.startReqByMsgHandle(cVeName,'','','reqGetStoreDetail','resGetStoreDetail','ECP.handle.OrganizationInfoHandle.handleMsg');
}

var Request = new Object();
Request = GetRequest();
var storeId=Request["storeId"];
var cert_id = null;//证件ID，用于删除证件

$(document).ready(function(){
	getStoreDetail();
});

function reqGetStoreDetail(){
/*	var userId*/
	var req = {};
	req["op"] = "orgAllDetail";
	req["storeId"] = org_id;
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resGetStoreDetail(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId].data.level1[0];
	$("#storeId").val(result.org_id);
	$("#storeNameF").text(result.org_name);
	$("#license_noF").text(result.license_no);
	$("#registerRegionF").text(result.reg_address);
	$("#registerPersonF").text(result.legal_rep);
	$("#registerTimeF").text(result.reg_time);
	$("#workPlaceF").text(result.site);
	$("#registerFundF").text(result.reg_capital);
	$("#coreBusinessF").text(result.business_scope);
	$("#propertiesF").text(result.org_type);
	$("#staffAmountF").text(result.staffcount);	
	$("#officialWebsiteF").text(result.website);
	document.getElementById('officialWebsiteF').href=result.website;
/*	$("#contacts").text(result.site);//noData
	$("#phone").text(result.site);//noData
	$("#email").text(result.site);//noData
*/	
	var level2=retDataObj[cVe.cEioVeDataId].data.level2;
	if(level2.length==0){
		$("#registerStoreUpdateButton").after('<button type="button" class="btn btn-success " id="primaryStoreAddPageButton" onclick="primaryStoreAddPage()">添加初级信息</button>');		
		$("#primaryStoreAddPageButton").after('<button type="button" class="btn btn-danger " onclick="deleteStore()">删除注册级信息</button> ');

	}else{
		var primaryStoreData=level2[0];
		/*alert(primaryStoreData);*/
		$("#registerPanel").after('<div class="panel panel-default" id="primaryPanel">'
                +'<div class="panel-heading" id="primaryPanelHead" onclick="primaryPanelToggle()">'
                +'<h3>初级信息</h3></div>'
                +'<div class="panel-body" id="primaryPanelBody">'
                +'<div class="row" >'
                +'<div class="col-md-12">'
                +'<form id="primaryStoreForm">'
                +'<div class="form-group row">'
                +'<div class="col-md-2">'
                +'<label>营业执照号：</label></div>'
                +'<div class="col-md-4">'
                +'<input class="form-control" id="org_id" name="org_id" type="hidden" />'
                +'<label>'+primaryStoreData.license_no+'</label></div>'
                +'<div class="col-md-2">'
                +'<label>年度：</span></label></div>'
                +'<div class="col-md-4">'
                +'<label >'+primaryStoreData.year+'</label></div></div>'
                +'<div class="form-group row">'
                +'<div class="col-md-2">'
                +' <label>员工数：</label></div>'
                +'<div class="col-md-4">'
                +'<label >'+primaryStoreData.employee_num+'</label></div>'
                +'<div class="col-md-2">'
                +'<label>技术人员数量：</label></div>'
                +'<div class="col-md-4">'
                +'<label >'+primaryStoreData.techstaff_num+'</label></div></div>'
                +'<div class="form-group row">'
                +'<div class="col-md-2">'
                +'<label>营业额：</label></div>'
                +'<div class="col-md-4">'
                +'<label >'+primaryStoreData.revenue+'</label></div>'
                +'<div class="col-md-2">'
                +'<label>资产总额：</label></div>'
                +'<div class="col-md-4">'
                +'<label >'+primaryStoreData.total_assets+'</label></div></div>'
                +'<div class="form-group row">'
                +'<div class="col-md-2">'
                +'<label>负债总额：</label></div>'
                +'<div class="col-md-4">'
                +'<label >'+primaryStoreData.total_indebt+'</label></div>'
                +'<div class="col-md-2">'
                +'<label>净销售额：</label></div>'
                +'<div class="col-md-4">'
                +'<label >'+primaryStoreData.net_sales+'</label></div></div>'
                +'<div class="form-group row">'
                +'<div class="col-md-2">'
                +'<label>流动资产：</label></div>'
                +' <div class="col-md-4">'
                +'<label >'+primaryStoreData.current_assets+'</label></div>'
                +'<div class="col-md-2">'
                +'<label>流动负债：</label></div>'
                +'<div class="col-md-4">'
                +'<label >'+primaryStoreData.current_indebt+'</label></div></div>'
                +'<div class="form-group row">'
                +'<div class="col-md-2">'
                +'<label>营业费用：</label></div>'
                +'<div class="col-md-4">'
                +'<label >'+primaryStoreData.operate_expense+'</label></div>'
                +'<div class="col-md-2">'
                +'<label>利润：</label></div>'
                +'<div class="col-md-4">'
                +'<label >'+primaryStoreData.profit+'</label></div></div>'
                +'<div class="form-group row">'
                +'<div class="col-md-2">'
                +'<label>贷款余额：</label></div>'
                +'<div class="col-md-4">'
                +'<label >'+primaryStoreData.loan_balance+'</label></div>'
                +'<div class="col-md-2">'
                +'<label>纳税：</label></div>'
                +'<div class="col-md-4">'
                +'<label />'+primaryStoreData.taxation+'</label></div></div>'
                +'<div class="form-group row">'
                +'<div class="col-md-2">'
                +'<label>研发经费金额：</label></div>'
                +'<div class="col-md-4">'
                +'<label >'+primaryStoreData.rd_budget+'</label></div></div>'
		                     
                +'<div class="form-group row">'
                +'<div style="padding-left:400px;padding-top:50px;"> '
                +'<button type="button" class="btn btn-success " id="superStoreAddPageButton" onclick="superStoreAddPage()" >添加高级信息</button>'
                +'<button type="button" class="btn btn-info " id="primaryStoreUpdatePage" onclick="primaryStoreUpdate()" >修改初级信息</button>' 
                +'<button type="button" class="btn btn-danger " id="primaryStoreDeletePage"  onclick="primaryStoreDelete()" >删除初级信息</button> '
                +'</div></div></form></div></div></div></div> ');
		var level3 = retDataObj[cVe.cEioVeDataId].data.level3;
		if(level3.length!=0){
			$("#superStoreAddPageButton").css("display","none");
			$("#primaryStoreDeletePage").css("display","none");	
			$("#primaryPanel").after('<div class="panel panel-default" id="advancedPanel">'
	                +'<div class="panel-heading" id="advancedPanelHead" onclick="advancedPanelToggle()">'
	                +'<h3>高级信息</h3></div>'
	                +'<div class="panel-body" id="advancedPanelBody">'
	                +'<div class="row" >'
	                +'<div class="col-md-12">'
	                +'<div id="table" class="panel panel-primary" >'
	                +'<div class="panel-heading" style="text-align:center;font-size:14px;">高级信息列表</div>'
	                +'<div id="table">'
					+'<table class="table table-hover" id="dataGrid" > </table>'
	                +'<div id= "pager"></div>'
	                +'</div>'
	                +'</div>' 
	                +'<button type="button" class="btn btn-success " style="margin-left:420px" onclick="superStoreAddPage()" >上传文件</button>'
	                +'</div></div></div></div></div>');
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
	}
}


function certInfoList(){
	
	grid= $('#dataGrid').jqGrid({ 
		  //caption: "网点信息",
		  datatype:'local',  
		  altRows: true,
		  colNames:['序号','证件名称','证件状态','发证日期','有效期','操作','证件编号',],
		  colModel:[  
		    
		   {id:'number',name:'number', width:'100%',align:'center'},  
		    {id:'cert_name',name:'cert_name',width:'100%',align:'center'},
		    {id:'cert_status',name:'cert_status', width:'100%',align:'center'},  
		    {id:'cert_date',name:'cert_date', width:'100%',align:'center'},
		    {id:'cert_expire',name:'cert_expire', width:'100%',align:'center'},
		   /* {id:'apply_status_code',name:'apply_status_code', width:'100%',align:'center',hidden:'true'},*/
		    {id:'operate',name:'operate', width:'100%',align:'center',formatter:displayButtons},

		    {id:'cert_id',name:'cert_id', key:true, width:'100%',align:'center',hidden:true}
		  ],  
		  pager:"#pager",  
		 // rowNum: pageSize,  
		  sortname:'0',  
		  viewrecords: true,  
		  height:'100%',  
		  autowidth: true,
		  align:'center',
		 //multiselect: true,
		  shrinkToFit:true,		
		});	
}

function displayButtons(cellValue,options,rowObject){
	//var id = options.rowId;//这里的rowId是关键值，不是行号   
	cVeUti.Cookie.setCookie("cert_id",options.rowId);
	var edit = '<button class=" btn btn-primary my-jqgrid-button"  onclick="deleteCert()">删除</button>';
	return edit;
}

function deleteCert(){
	//cert_id=id;
	//cVeUti.Cookie.setCookie("cert_id");
	//alert(cert_id);
	cVe.startReqByMsgHandle(cVeName,'','','reqDeleteCert','resDeleteCert','ECP.handle.OrganizationInfoHandle.handleMsg');
}

function reqDeleteCert(){
	var req={};
	req["op"] = "orgCertDelete";
	req["cert_id"] = cVeUti.Cookie.getCookie("cert_id");;
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
		window.location.reload();
    }
}
function storeUpdate(){
	 /*var license_no = document.getElementById('license_no').value;
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
	 if(registerTime>=curTime)
	 {
	     alert("注册时间不能晚于今天！");
	     return;
	 }
	 var inputValue = document.getElementById("contactPhone").value;
     var mobile=/^((13[0-9]{1})|159|153)+\d{8}$/;
      if(!mobile.test(inputValue)){		        	 
			 alert("请填入有效的手机号");
      }
	 var temp = document.getElementById("contactEmail");    //对电子邮件的验证
	 var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	 if(temp.value!=""&&!myreg.test(temp.value))
	 {
        alert("请输入有效的Email！");
        temp.focus();
        return false;
     }*/
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

function GetJsonObject() {
	var store=$("#storeSettingForm").serializeArray();
	var theRequest = new Object();
	for(var i=0;i<store.length;i++){
		theRequest[store[i].name]=unescape(store[i].value);
	}
	return theRequest;
}


function reqStoreUpdate(){
	var store = new Object();
	store = GetJsonObject();
	var req = {};
	req["op"] = "storeUpdate";
	req["store"] = store;
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resStoreUpdate(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var resultTip=retDataObj[cVe.cEioVeDataId].resultTip;
	alert(resultTip);
	location.replace(location.href);
}

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