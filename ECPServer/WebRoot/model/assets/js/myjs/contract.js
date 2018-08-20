var probation = "1";//1代表合同模板有试用期字段，0代表没有。以下类似
var fildwork = "1";//实习
var insure_ratio = "1";//五险一金比例
var expire_date = "1";//合同有效期
var payment = "1";//薪资水平
var contract_background = "1";//合同北景
var contract_summary = "1";//合同简介
var job_name = "1";//工作岗位
var fine = "1";//违约金
var work_time = "1";//工作时间

function setProbation(){
	probation = "0";
	$("#probation").css("display","none");
}

function setContract_background(){
	contract_background = "0";
	$("#contract_background").css("display","none");
}

function setContract_summary(){
	contract_summary = "0";
	$("#contract_summary").css("display","none");
}

function setWork_time(){
	work_time = "0";
	$("#work_time").css("display","none");
}

function setJob_name(){
	job_name = "0";
	$("#job_name").css("display","none");
}

function setFine(){
	fine = "0";
	$("#fine").css("display","none");
}

function setFildwork(){
	fildwork = "0";
	$("#fildwork").css("display","none");
}

function setInsure_ratio(){
	insure_ratio = "0";
	$("#insure_ratio").css("display","none");
}

function setExpire_date(){
	expire_date = "0";
	$("#expire_date").css("display","none");
}

function setPayment(){
	payment = "0";
	$("#payment").css("display","none");
}

function createContractModel(){
	var contract_name = document.getElementById("contract_name").value;
	var contract_content1 = document.getElementById("contract_content").innerHTML;
	if(contract_name==""){
		alert("模板标题不能为空!");
		document.getElementById("contract_name").focus();
	}else if(contract_content1 == ""){
		alert("模板正文不能为空!");
		document.getElementById("contract_content").focus();
	}
	else{
        cVe.startReqByMsgHandle(cVeName,'','','reqCreateContractModel','resCreateContractModel','ECP.handle.ContractHandle.handleMsg');
	}
}

function reqCreateContractModel(){
	var req = {};
	req["op"] = "addContractTemplate";
	req["probation_start"] = probation;//试用期开始时间
	req["probation_end"] = probation;//试用期结束时间
	req["practice_begin"] = fildwork;//实习开始时间
	req["practice_end"] = fildwork;//实习结束时间
	req["insure_ratio"] = insure_ratio;//五险一金比例
	req["contract_start_time"] = expire_date;//合同生效时间
	req["contract_end_time"] = expire_date;//合同结束时间
	req["contract_background"] = contract_background;//合同背景
	req["contract_summary"] = contract_summary;//合同简介
	req["fine"] = fine;//违约金
	req["work_time"] = work_time;//工作时间
	req["job_name"] = job_name;//工作岗位
	req["payment"] = payment;//薪资
	req["contract_content"] = document.getElementById("contract_content").innerHTML;//合同正文
	req["contract_name"] = document.getElementById("contract_name").value;//合同名称
	req["org_id"] = cVeUti.Cookie.getCookie("org_id");//合同所属的公司id
	req["legal_rep"] = "1";
	req["license_no"] = "1";
	req["address"] = "1";
	req["contract_time"] = "1";
	req["user_id"] = "1";
	req["phone"] = "1";
	req["name"] = "1";
	req["id_cardno"] = "1";
	req["sex"] = "1";
	req["address"] = "1";
	req["partA_sig"] = "1";
	req["partA_date"] = "1";
	req["partB_sig"] = "1";
	req["partB_date"] = "1";
	var temp = JSON.stringify(req);
	temp = temp.replace(/&nbsp;/g," ");
	console.log(JSON.stringify(temp));
	cVe.setConn(cServerUri,"POST", true, temp);
	//cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}
function resCreateContractModel(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId];
	if(result.result=="success"){
		$("#deleteWork_time").css("display","none");
		$("#deleteJob_name").css("display","none");
		$("#deleteFine").css("display","none");
		$("#deleteContract_summary").css("display","none");
		$("#deleteContract_background").css("display","none");
		$("#deleteProbation").css("display","none");
		$("#deleteFildwork").css("display","none");
		$("#deleteInsure_ratio").css("display","none");
		$("#deleteExpire_date").css("display","none");	
		$("#deletePayment").css("display","none");
		document.getElementById("contract_name").style.border="none";
		document.getElementById("contract_content").style.border="none";
		document.getElementById("contract_content").contentEditable = false;
		document.getElementById("contract_name").readOnly=true;
		$("#goBack").css("display","inline-block");
		$("#saveContractModel").css("display","none");
		$("#cancel").css("display","none");
		msgbox("提示",1,"创建成功");

	}else{
		msgbox("提示",2,"创建失败");
	}
}

function updateContent(){
	
	//alert(probation);
	alert(document.getElementById("contract_content").innerHTML.length);
	alert(document.getElementById("contract_content").innerHTML);
	$("#deleteProbation").css("display","none");
	$("#deleteFildwork").css("display","none");
	$("#deleteInsure_ratio").css("display","none");
	$("#deleteExpire_date").css("display","none");	
	$("#deletePayment").css("display","none");
	document.getElementById("contract_content").style.border="none";

	//document.getElementById("contract_content").readOnly=true;
	document.getElementById("contract_name").readOnly=true;
	document.getElementById("contract_content").contentEditable = false;
}



function enableInput(){
	document.getElementById("contract_content").style.border="solid 1px green";
	document.getElementById("contract_content").innerHTML = "";
	document.getElementById("contract_name").style.border="solid 1px blue";
	document.getElementById("contract_content").contentEditable = true;
	$("#deleteProbation").css("display","inline-block");
	$("#deleteFildwork").css("display","inline-block");
	$("#deleteInsure_ratio").css("display","inline-block");
	$("#deleteExpire_date").css("display","inline-block");
	$("#deletePayment").css("display","inline-block");
	$("#deleteWork_time").css("display","inline-block");
	$("#deleteJob_name").css("display","inline-block");
	$("#deleteFine").css("display","inline-block");
	$("#deleteContract_summary").css("display","inline-block");
	$("#deleteContract_background").css("display","inline-block");
	$("#edit").css("display","none");
	$("#goBack").css("display","none");
	$("#saveContractModel").css("display","inline-block");
	$("#cancel").css("display","inline-block");
	document.getElementById("contract_name").readOnly=false;
}

function modelInfoList(){
	
	grid= $('#dataGrid').jqGrid({ 
		  //caption: "合同模板列表",
		  datatype:'local',  
		 // altRows: true,
		  colNames:['序号','模板名称','操作','模板编号'],
		  colModel:[  
		    
		   {id:'number',name:'number', width:'100%',align:'center'},  
		    {id:'contract_name',name:'contract_name',width:'100%',align:'center'},
		    {id:'operate',name:'operate', width:'100%',align:'center',formatter:displayButtons},
		    {id:'template_no',name:'template_no', key:true, width:'100%',align:'center',hidden:'true'}, 
		  ],  
		  pager:"#pager",   
		  sortname:'0', 
		  rowNum: 16,
		  viewrecords: true,  
		  height:'100%',  
		  autowidth: true,
		  align:'center',
		  shrinkToFit:true,
		});	
}
//var contract_name = "";
function displayButtons(cellValue,options,rowObject){
	var id = options.rowId;//这里的rowId是关键字
	var contract_name ="'"+ rowObject.contract_name+"'";
	var edit = '<button class=" btn btn-primary my-jqgrid-button"  onclick="getTemplate_no('+id+')">查看</button>'+
	           '<button class=" btn btn-danger my-jqgrid-button"  style="margin-left:15px;" onclick="deleteTemplate('+id+','+contract_name+')">删除</button>';
	return edit;
}

var template_no = "";
function deleteTemplate(id,contract_name){
	template_no = id;
	document.getElementById("delItem").innerHTML = contract_name;
	$("#confirmDel").modal('show');//删除提示
}
//确认删除
function confirmDelete(){
	$("#confirmDel").modal("hide");
	cVe.startReqByMsgHandle(cVeName,'','','reqDeleteTemplate','resDeleteTemplate','ECP.handle.ContractHandle.handleMsg');
}
function reqDeleteTemplate(){
	var req = {};
	req["template_no"] = template_no;
	req["op"] = "contractTemplateDelete";
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resDeleteTemplate(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId];
	if(result.result=="success"){
		getContractModelList();
		msgbox("提示",1,"删除成功");
		
				
	}else{
		msgbox("提示",2,result.resultTip);
	}
}
//获取合同编号，用于查看合同模板
function getTemplate_no(id){
	 cVeUti.Cookie.setCookie("template_no",id); 
	 window.location.href = "contractModel.html";
}
//获取当前企业的合同模板列表
function getContractModelList(){
	cVe.startReqByMsgHandle(cVeName,'','','reqGetContractModelList','resGetContractModelList','ECP.handle.ContractHandle.handleMsg');
}

function reqGetContractModelList(){
	var req = {};
	req["op"] = "contractTemplateList";
	req["org_id"] = cVeUti.Cookie.getCookie("org_id");	
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resGetContractModelList(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId].rows;
	if(result.length<=0){
		$('#table').css("display","none");
		$('#prompt_info').css("display","block");		
	}else{
		modelInfoList();
		$('#dataGrid').jqGrid("clearGridData");
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


function showContractModelDetail(){		   
        cVe.startReqByMsgHandle(cVeName,'','','reqShowContractModelDetail','resShowContractModelDetail','ECP.handle.ContractHandle.handleMsg');
}

function reqShowContractModelDetail(){
	var req = {};
	req["op"] = "contractTemplateDetail";
	req["template_no"] = cVeUti.Cookie.getCookie("template_no");
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resShowContractModelDetail(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId];
	if(result.result=="0"){
        var data = result.rows[0];
		document.getElementById("contract_content").innerHTML = data.contract_content;
		document.getElementById("contract_name").innerHTML = data.contract_name;
		document.getElementById("template_no").value = data.template_no;
		if(data.contract_background=="1"){
			$("#contractItems").append('<div class="my-contractItem-div1" id="contract_background">'
					+'<p class="my-contractItem-p1">合同背景：</p>'	
					+'<div>'	
					+'<textarea  readonly="readonly" placeholder="合同背景"  class="my-contractArea"style="float:left;margin-left:0px"></textarea>'
					+'</div>'	
				+'</div>');
		 }
		if(data.contract_summary=="1"){
			$("#contractItems").append('<div class="my-contractItem-div1" id="contract_summary">'	
				    +'<p class="my-contractItem-p1">合同简介：</p>'
				    +'<div>'
				    	+'<textarea readonly="readonly" placeholder="合同简介" class="my-contractArea"style="float:left;margin-left:0px"></textarea>'
				    +'</div>'		
				+'</div>');
		 }
		if(data.contract_start_time=="1"){
			$("#contractItems").append('<div class="my-contractItemShow-div" id="expire_date">'
					+'<p class="my-contractItem-p">合同生效时间：__________________&nbsp;&nbsp;&nbsp;合同结束时间：__________________</p>'
				+'</div>');
		 }
		if(data.probation_start=="1"){
			$("#contractItems").append('<div class="my-contractItemShow-div" id="probation">'
			+'<p class="my-contractItem-p">试用期开始时间：__________________&nbsp;&nbsp;&nbsp;试用期结束时间：__________________</p>'
			+'</div>');
		}
		if(data.practice_begin=="1"){
			$("#contractItems").append('<div class="my-contractItemShow-div" id="fildwork">'
			+'<p class="my-contractItem-p">实习开始时间：__________________&nbsp;&nbsp;&nbsp;实习结束时间：__________________</p>'
			+'</div>');
		}
		if(data.insure_ratio=="1"){
			$("#contractItems").append('<div class="my-contractItemShow-div"  id="insure_ratio">'
			+'<p class="my-contractItem-p">五险一金约定比例：__________________</p>'
			+'</div>');
		}
		if(data.payment=="1"){
			$("#contractItems").append('<div class="my-contractItemShow-div"  id="payment">'
			+'<p class="my-contractItem-p">薪资水平：__________________</p>'
			+'</div>');
		}
		if(data.work_time=="1"){
			$("#contractItems").append('<div class="my-contractItemShow-div"  id="work_time">'
					+'<p class="my-contractItem-p">工作时间：__________________</p>'
				+'</div>');
		}
		if(data.job_name=="1"){
			$("#contractItems").append('<div class="my-contractItemShow-div"  id="job_name">'
					+'<p class="my-contractItem-p">工作岗位：__________________</p>'
				+'</div>');
		}
		if(data.fine=="1"){
			$("#contractItems").append('<div class="my-contractItemShow-div"  id="fine">'
					+'<p class="my-contractItem-p">违约金：__________________</p>'
				+'</div>');
		}
		var Request = new Object();
		Request = GetRequest();
		var previous =Request["previous"];
		if(previous=="fromOrder"){
			$("#edit").css("display","none");
			$("#addContract").css("display","inline-block");
			$("#goBack").css("display","inline-block");
			$("#updateContractModel").css("display","none");
			$("#cancel").css("display","none");		
	    }
	}else{
		msgbox("提示",2,"查看失败");
	}
}

function enableModify(){
	document.getElementById("contract_content").style.border="solid 1px green";
	document.getElementById("contract_name").style.border="solid 1px blue";
	document.getElementById("contract_content").contentEditable = true;
	$("#edit").css("display","none");
	$("#goBack").css("display","none");
	$("#updateContractModel").css("display","inline-block");
	$("#cancel").css("display","inline-block");
	document.getElementById("contract_name").contentEditable = true;
}
function updateContractModel(){
	var contract_name = document.getElementById("contract_name").innerHTML;
	var contract_content = document.getElementById("contract_content").innerHTML;
	if(contract_name==""){
		alert("模板标题不能为空!");
		document.getElementById("contract_name").focus();
	}else if(contract_content == ""){
		alert("模板正文不能为空!");
		document.getElementById("contract_content").focus();
	}else{
		cVe.startReqByMsgHandle(cVeName,'','','reqUpdateContractModel','resUpdateContractModel','ECP.handle.ContractHandle.handleMsg');
	}
	 
}

function reqUpdateContractModel(){
	var req = {};
	req["op"] = "contractTemplateUpdate";
	req["template_no"] = document.getElementById("template_no").value;
	req["contract_content"] = document.getElementById("contract_content").innerHTML;
	req["contract_name"] = document.getElementById("contract_name").innerHTML;
	var temp = JSON.stringify(req);
	temp = temp.replace(/&nbsp;/g," ");
	console.log(JSON.stringify(temp));
	cVe.setConn(cServerUri,"POST", true, temp);
	
}

function resUpdateContractModel(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId];
	if(result.result=="success"){
		msgbox("提示",1,"修改成功");
		$("#edit").css("display","none");
		$("#goBack").css("display","inline-block");
		$("#updateContractModel").css("display","none");
		$("#cancel").css("display","none");
		document.getElementById("contract_content").style.border="none";
		document.getElementById("contract_name").style.border="none";
		document.getElementById("contract_content").contentEditable = false;
		document.getElementById("contract_name").contentEditable = false;
	}else{
		msgbox("提示",2,"修改失败");
	}
}

//选择合同模板时的预览功能
function previewContractTemplate(){
	var template_no = document.getElementById("contactModelName").value;
	if(template_no==""){
		alert("请选择模板");
	}else{
		 cVeUti.Cookie.setCookie("template_no",template_no);
		 var previous = "fromOrder";
		 var job_apply_id = document.getElementById("job_apply_id").value;
		 var apply_name = document.getElementById("name").innerHTML;
		 var job_name = document.getElementById("job_Name").innerHTML;
		 cVeUti.Cookie.setCookie("job_name",job_name);
		 cVeUti.Cookie.setCookie("apply_name",apply_name);//记住解决乱码问题，这只是临时使用
		// alert(job_name);
		 //var url = "contractModel.jsp?previous=" + previous +"&job_apply_id="+job_apply_id+"&apply_name="+apply_name+"&job_name="+job_name;
		 window.location.href = "contractModel.html?previous=" + previous +"&job_apply_id="+job_apply_id+"&apply_name="+apply_name+"&job_name="+job_name;
		 //window.open(encodeURI(url));
		 
	}
}

//生成合同
function addContract(){
	cVe.startReqByMsgHandle(cVeName,'','','reqAddContract','resAddContract','ECP.handle.ContractHandle.handleMsg');
}

function reqAddContract(){
	var Request = new Object();
	Request = GetRequest();
	var job_apply_id =Request["job_apply_id"];//求职ID
	var job_name =cVeUti.Cookie.getCookie("job_name");//求职者姓名 即乙方
	var apply_name =cVeUti.Cookie.getCookie("apply_name");
	var req = {};
	req["op"] = "contractAdd";
	req["partA"] =cVeUti.Cookie.getCookie("org_name");
	req["partB"] = apply_name;
	req["org_id"] = cVeUti.Cookie.getCookie("org_id");
	req["job_name"] = job_name;
	req["job_apply_id"] = job_apply_id;
	req["contract_modelno"] = cVeUti.Cookie.getCookie("template_no");
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resAddContract(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId];
	if(result.result=="success"){
		//msgbox("提示",1,"合同生成成功");
		//cVeUti.Cookie.getCookie("job_name");
		cVeUti.Cookie.delCookie("job_name");
		cVeUti.Cookie.delCookie("apply_name");
		var contract_id = result.contract_id;
		var template_no = cVeUti.Cookie.getCookie("template_no");
		//alert(contract_id);
		 window.location.href = "contract.html?contract_id="+contract_id+"&template_no="+template_no;
	}else{
		msgbox("提示",2,"合同生成失败");
	}
}

function getContract(){		   
    cVe.startReqByMsgHandle(cVeName,'','','reqGetContract','resGetContract','ECP.handle.ContractHandle.handleMsg');
}

function reqGetContract(){
	var Request = new Object();
	Request = GetRequest();
	var template_no = Request["template_no"];
	var req = {};
	req["op"] = "contractTemplateDetail";
	req["template_no"] = template_no;
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
	}

function resGetContract(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId];
	if(result.result=="0"){
	    var data = result.rows[0];
		document.getElementById("contract_content").innerHTML = data.contract_content;
		document.getElementById("contract_name").value = data.contract_name;
		//document.getElementById("template_no").value = data.template_no;
		if(data.probation_start!="1"){
			//$("#goBack").css("display","inline-block");
			$("#cProbation").css("display","none");
		}
		if(data.practice_begin!="1"){
			$("#cfildwork").css("display","none");
		}
		if(data.insure_ratio!="1"){
			$("#cCinsure_ratio").css("display","none");
		}
		if(data.payment!="1"){
			$("#cPayment").css("display","none");
		}
		if(data.contract_start_time!="1"){
			$("#cExpire_date").css("display","none");
		
	    }
		if(data.work_time!="1"){
			$("#cWork_time").css("display","none");
		
	    }
		if(data.job_name!="1"){
			$("#cJob_name").css("display","none");		
	    }
		if(data.fine!="1"){
			$("#cFine").css("display","none");		
	    }
		if(data.contract_background!="1"){
			$("#cContract_background").css("display","none");		
	    }
		if(data.contract_summary!="1"){
			$("#cContract_summary").css("display","none");		
	    }
		getContractDetail();
	}else{
		msgbox("提示",2,"查看失败");
	}
}

/*function enableEditContract(){
	$("#edit").css("display","none");
	$("#goBack").css("display","none");
	$("#updateContract").css("display","inline-block");
	$("#cancel").css("display","inline-block");
	document.getElementById("inPartA").readOnly=false;
	document.getElementById("inLicense_no").readOnly=false;
	document.getElementById("inATele_num").readOnly=false;
	document.getElementById("inPartB").readOnly=false;
	document.getElementById("inSex").readOnly=false;
	document.getElementById("inID_no").readOnly=false;
	document.getElementById("inBTele_num").readOnly=false;
	document.getElementById("inProbation_start").readOnly=false;
	document.getElementById("inProbation_end").readOnly=false;
	document.getElementById("inPractice_begin").readOnly=false;
	document.getElementById("inPractice_end").readOnly=false;
	document.getElementById("inCinsure_ratio").readOnly=false;
	document.getElementById("inPayment").readOnly=false;
	document.getElementById("inExpire_date").readOnly=false;
}*/

function getContractDetail(){		   
    cVe.startReqByMsgHandle(cVeName,'','','reqGetContractDetail','resGetContractDetail','ECP.handle.ContractHandle.handleMsg');
}

function reqGetContractDetail(){
	var Request = new Object();
	Request = GetRequest();
	var contract_id = Request["contract_id"];
	var req = {};
	req["op"] = "contractDetail";
	req["contract_id"] = contract_id;
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

var contract_status = "";//在编辑合同时用于判断当前合同是否已经发送给乙方
function resGetContractDetail(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId];
	if(result.result=="0"){
		var data = result.rows[0];
		 contract_status = data.contract_status;
		 var myContactb  = data.contactb;
		 var bsex = data.partb_sex;
		 var bid  = data.id_cardno;
		 var btele = data.partb_telenum;
		 if(data.contract_status=="签订中")
			{
				$("#contractSend").css("display","none");
				if(btele!=""&&bid!=""&&bsex!=""&&myContactb!="")
					{
						$("#sigConfirm").css("display","inline-block");
					}
			}
		 if(data.contract_status=="已签订")
			 {
			 	$("#contractSend").css("display","none");
			 	$("#edit").css("display","none");
			 	$("#sigConfirm").css("display","none");
			 	$("#updateContract").css("display","none");
			 	$("#cancel").css("display","none");
			 }
		document.getElementById("job_apply_id").value = data.job_apply_id;
		//alert(data.user_id);
		document.getElementById("user_id").value = data.user_id;
		//alert(document.getElementById("job_apply_id").value);
	    document.getElementById("partA").value = data.parta;
	    document.getElementById("partB").value = data.partb;
	    document.getElementById("inPartA").value = data.contacta;
		document.getElementById("inLicense_no").value = data.license_no;
		document.getElementById("inATele_num").value = data.parta_telenum;
		document.getElementById("inPartB").value = data.contactb;
		document.getElementById("inSex").value = data.partb_sex;
		document.getElementById("inBID_no").value = data.id_cardno;
		document.getElementById("inBTele_num").value = data.partb_telenum;
		document.getElementById("inProbation_start").value = data.probation_start;
		document.getElementById("inProbation_end").value = data.probation_end;
		document.getElementById("inPractice_begin").value = data.practice_begin;
		document.getElementById("inPractice_end").value = data.practice_end;
		document.getElementById("inCinsure_ratio").value = data.cinsure_ratio;
		document.getElementById("inPayment").value= data.payment;
		document.getElementById("contract_end_time").value = data.contract_end_time;
		document.getElementById("contract_start_time").value = data.contract_start_time;
		document.getElementById("fine").value= data.fine;
		document.getElementById("job_name").value= data.job_name;
		document.getElementById("work_time").value= data.work_time;
		document.getElementById("contract_background").value= data.contract_background;
		document.getElementById("contract_summary").value= data.contract_summary;
	}else{
		msgbox("提示",2,"信息加载失败");
	}
}

function updateContract(){
	var probation_start = document.getElementById("inProbation_start").value ;
	var probation_end = document.getElementById("inProbation_end").value ;
	var practice_begin = document.getElementById("inPractice_begin").value ;
	var practice_end = document.getElementById("inPractice_end").value ;
	var contract_end_time = document.getElementById("contract_end_time").value ;
	var contract_start_time = document.getElementById("contract_start_time").value ;
	var contacta = document.getElementById("inPartA").value ;
	var parta_telenum = document.getElementById("inATele_num").value ;
	//var probation_start = document.getElementById("inProbation_start").value ;
	//var probation_end = document.getElementById("inProbation_end").value ;
	//var practice_begin = document.getElementById("inPractice_begin").value ;
	//var practice_end = document.getElementById("inPractice_end").value ;
	var cinsure_ratio = document.getElementById("inCinsure_ratio").value ;
	var payment = document.getElementById("inPayment").value;
	//var contract_end_time = document.getElementById("contract_end_time").value ;
	//var contract_start_time = document.getElementById("contract_start_time").value ;
	var fine = document.getElementById("fine").value;
	var job_name = document.getElementById("job_name").value;
	var work_time = document.getElementById("work_time").value;
	var contract_background = document.getElementById("contract_background").value;
	var contract_summary = document.getElementById("contract_summary").value;
	var teleNumber = /^((13[0-9]{1})|159|153)+\d{8}$/;
	var fixedNumber = /^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/;
	var temp = document.getElementById("inATele_num");
	if(contract_status=="签订中")
	{
		$("#contractSend").css("display","none"); //若为签订中，则不能再发送合同
		/*//alert(document.getElementById("cProbation").style.display);		
		var contacta = document.getElementById("inPartA").value ;
		var parta_telenum = document.getElementById("inATele_num").value ;
		//var probation_start = document.getElementById("inProbation_start").value ;
		//var probation_end = document.getElementById("inProbation_end").value ;
		//var practice_begin = document.getElementById("inPractice_begin").value ;
		//var practice_end = document.getElementById("inPractice_end").value ;
		var cinsure_ratio = document.getElementById("inCinsure_ratio").value ;
		var payment = document.getElementById("inPayment").value;
		//var contract_end_time = document.getElementById("contract_end_time").value ;
		//var contract_start_time = document.getElementById("contract_start_time").value ;
		var fine = document.getElementById("fine").value;
		var job_name = document.getElementById("job_name").value;
		var work_time = document.getElementById("work_time").value;
		var contract_background = document.getElementById("contract_background").value;
		var contract_summary = document.getElementById("contract_summary").value;*/
		if(contacta=="")
			{
				msgbox("提示",3,"甲方联系人不能为空");
				return;
			}
		if(contacta.length>20)
			{
				alert("描述甲方联系人的字符个数不能超过20");
				return;
			}
		if(parta_telenum=="")
		{
			msgbox("提示",3,"甲方电话不能为空");
			return;
		}
		if(parta_telenum!=""&&!teleNumber.test(temp.value)&&!fixedNumber.test(temp.value))
		 {
	       alert("请输入有效的甲方联系电话号码！");
	       temp.focus();
	       return false;
	    }
		if(document.getElementById("cContract_background").style.display!="none"&&contract_background==""){
			msgbox("提示",3,"合同背景不能为空!");
			return;
		}
		if(document.getElementById("cContract_background").style.display!="none"&&contract_background.length>150){
			alert("合同背景所包含的字符个数不能超过150!");
			return;
		}
		if(document.getElementById("cContract_summary").style.display!="none"&&contract_summary==""){
			msgbox("提示",3,"合同简介不能为空");
			return;
		}
		if(document.getElementById("cContract_summary").style.display!="none"&&contract_summary.length>150){
			alert("合同简介所包含的字符个数不能超过150");
			return;
		}
		if(document.getElementById("cExpire_date").style.display!="none"&&(contract_end_time==""||contract_start_time=="")){
			msgbox("提示",3,"合同起止时间不能为空");
			return;
		}
		if(document.getElementById("cExpire_date").style.display!="none"&&(contract_end_time<=contract_start_time))
		{
			msgbox("提示",3,"合同结束时间应晚于生效时间");
			return;
		}
		if(document.getElementById("cProbation").style.display!="none"&&(probation_start==""||probation_end=="")){
			//$("#goBack").css("display","inline-block");
			msgbox("提示",3,"试用期不能为空");
			return;
		}
		if(document.getElementById("cProbation").style.display!="none"&&(probation_start>=probation_end)){
			//$("#goBack").css("display","inline-block");
			msgbox("提示",3,"试用期结束时间应晚于开始时间");
			return;
		}
		if(document.getElementById("cfildwork").style.display!="none"&&(practice_begin==""||practice_end=="")){
			msgbox("提示",3,"实习期不能为空");
			return;
		}
		if(document.getElementById("cfildwork").style.display!="none"&&(practice_begin>=practice_end)){
			msgbox("提示",3,"实习结束时间应晚于开始时间");
			return;
		}
		if(document.getElementById("cCinsure_ratio").style.display!="none"&&cinsure_ratio==""){
			msgbox("提示",3,"五险一金不能为空");
			return;
		}
		if(document.getElementById("cCinsure_ratio").style.display!="none"&&cinsure_ratio.length>40){
			alert("描述五险一金的字符个数不能超过40！");
			return;
		}
		if(document.getElementById("cPayment").style.display!="none"&&payment==""){
			msgbox("提示",3,"薪资不能为空");
			return;
		}
		if(document.getElementById("cPayment").style.display!="none"&&payment.length>40){
			alert("描述薪资水平的字符个数不能超过40！");
			return;
		}
		
		if(document.getElementById("cWork_time").style.display!="none"&&work_time==""){
			msgbox("提示",3,"工作时间不能为空");
			return;
		}
		if(document.getElementById("cWork_time").style.display!="none"&&work_time.length>40){
			alert("描述工作时间的字符个数不能超过40！");
			return;
		}
		if(document.getElementById("cJob_name").style.display!="none"&&job_name==""){
			msgbox("提示",3,"工作岗位不能为空");
			return;
		}
		if(document.getElementById("cJob_name").style.display!="none"&&job_name.length>40){
			alert("描述工作岗位的字符个数不能超过40！");
			return;
		}
		if(document.getElementById("cFine").style.display!="none"&&fine==""){
			msgbox("提示",3,"违约金不能为空");
			return;
		}
		if(document.getElementById("cProbation").style.display!="none"&&((probation_start!=""&&probation_start<contract_start_time)||(probation_end!=""&&contract_end_time<probation_end))){
			//$("#goBack").css("display","inline-block");
			msgbox("提示",3,"试用期时段应处于合同有效期时段内。");
			return;
		}
		if(document.getElementById("cfildwork").style.display!="none"&&((practice_begin!=""&&practice_begin<contract_start_time)||(practice_end!=""&&practice_end>contract_end_time))){
			msgbox("提示",3,"实习期时段应处于合同有效期时段内。");
			return;
		}
		if(document.getElementById("cfildwork").style.display!="none"&&document.getElementById("cProbation").style.display!="none"){
			if((practice_begin>=probation_start&&practice_begin<=probation_end)||(practice_end>=probation_start&&practice_end<=probation_end)){
				msgbox("提示",3,"实习期和试用期时段不能重叠。");
				return;
			}
		}
		if(document.getElementById("cFine").style.display!="none"&&fine.length>40){
			alert("描述违约金的字符个数不能超过40！");
			return;
		}
		cVe.startReqByMsgHandle(cVeName,'','','reqUpdateContract','resUpdateContract','ECP.handle.ContractHandle.handleMsg');
	}else
		{
			if(contacta.length>20)
			{
				alert("描述甲方联系人的字符个数不能超过20");
				return;
			}
			if(parta_telenum!=""&&!teleNumber.test(temp.value)&&!fixedNumber.test(temp.value))
			 {
		       alert("请输入有效的甲方联系电话！");
		       temp.focus();
		       return false;
		    }
			if(document.getElementById("cExpire_date").style.display!="none"&&(contract_end_time!=""&&contract_start_time!="")&&(contract_end_time<=contract_start_time))
			{
				msgbox("提示",3,"合同结束时间应晚于生效时间");
				return;
			}
			if(document.getElementById("cProbation").style.display!="none"&&(probation_start!=""&&probation_end!="")&&(probation_start>=probation_end)){
				//$("#goBack").css("display","inline-block");
				msgbox("提示",3,"试用期结束时间应晚于开始时间");
				return;
			}
			if(document.getElementById("cProbation").style.display!="none"&&((probation_start!=""&&probation_start<contract_start_time)||(probation_end!=""&&contract_end_time<probation_end))){
				//$("#goBack").css("display","inline-block");
				msgbox("提示",3,"试用期时段应处于合同有效期时段内。");
				return;
			}
			if(document.getElementById("cfildwork").style.display!="none"&&(practice_begin!=""&&practice_end!="")&&(practice_begin>=practice_end)){
				msgbox("提示",3,"实习结束时间应晚于开始时间");
				return;
			}
			if(document.getElementById("cfildwork").style.display!="none"&&((practice_begin!=""&&practice_begin<contract_start_time)||(practice_end!=""&&practice_end>contract_end_time))){
				msgbox("提示",3,"实习期时段应处于合同有效期时段内。");
				return;
			}
			if(document.getElementById("cfildwork").style.display!="none"&&document.getElementById("cProbation").style.display!="none"){
				if((practice_begin>=probation_start&&practice_begin<=probation_end)||(practice_end>=probation_start&&practice_end<=probation_end)){
					msgbox("提示",3,"实习期和试用期时段不能重叠。");
					return;
				}
			}
			if(document.getElementById("cContract_background").style.display!="none"&&contract_background.length>150){
				alert("合同背景所包含的字符个数不能超过150!");
				return;
			}
			if(document.getElementById("cContract_summary").style.display!="none"&&contract_summary.length>150){
				alert("合同简介所包含的字符个数不能超过150");
				return;
			}
			if(document.getElementById("cCinsure_ratio").style.display!="none"&&cinsure_ratio.length>40){
				alert("描述五险一金的字符个数不能超过40！");
				return;
			}
			if(document.getElementById("cPayment").style.display!="none"&&payment.length>40){
				alert("描述薪资水平的字符个数不能超过40！");
				return;
			}
			if(document.getElementById("cWork_time").style.display!="none"&&work_time.length>40){
				alert("描述工作时间的字符个数不能超过40！");
				return;
			}
			if(document.getElementById("cJob_name").style.display!="none"&&job_name.length>40){
				alert("描述工作岗位的字符个数不能超过40！");
				return;
			}		
			if(document.getElementById("cFine").style.display!="none"&&fine.length>40){
				alert("描述违约金的字符个数不能超过40！");
				return;
			}
		    cVe.startReqByMsgHandle(cVeName,'','','reqUpdateContract','resUpdateContract','ECP.handle.ContractHandle.handleMsg');
		}
}

function reqUpdateContract(){
	var Request = new Object();
	Request = GetRequest();
	var contract_id = Request["contract_id"];//求职ID
	var req = {};
	req["op"] = "contractUpdate";
	req["contract_id"] = contract_id;
	req["contacta"] = document.getElementById("inPartA").value;
	//alert(document.getElementById("inPartA").value);
	req["license_no"] = document.getElementById("inLicense_no").value;
	req["parta_telenum"] = document.getElementById("inATele_num").value;
    req["contactb"] = document.getElementById("inPartB").value;
	req["partb_sex"] = document.getElementById("inSex").value;
	req["id_cardno"] = document.getElementById("inBID_no").value ;
	req["partb_telenum"] = document.getElementById("inBTele_num").value;
	req["probation_start"] = document.getElementById("inProbation_start").value;
	req["probation_end"] = document.getElementById("inProbation_end").value;
	req["practice_begin"] = document.getElementById("inPractice_begin").value;
	req["practice_end"] = document.getElementById("inPractice_end").value;
	req["cinsure_ratio"] = document.getElementById("inCinsure_ratio").value;
	req["payment"] = document.getElementById("inPayment").value;
	req["contract_end_time"] = document.getElementById("contract_end_time").value;
	req["contract_start_time"] = document.getElementById("contract_start_time").value;
	req["fine"] = document.getElementById("fine").value;
	req["job_name"] = document.getElementById("job_name").value;
	req["work_time"] = document.getElementById("work_time").value;
	req["contract_background"] = document.getElementById("contract_background").value;
	req["contract_summary"] = document.getElementById("contract_summary").value;
	var reqStr = encodeURI(JSON.stringify(req)); 
	cVe.setConn(cServerUri,"POST", true, reqStr);
}

function resUpdateContract(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId];
	if(result.result=="success"){
	    msgbox("提示",1,"保存成功");
	    $("#edit").css("display","inline-block");
		$("#goBack").css("display","inline-block");
		$("#updateContract").css("display","none");
		$("#cancel").css("display","none");
		if(contract_status=="签订中")
		{
			var myContactb = document.getElementById("inPartB").value;
			var bsex = document.getElementById("inSex").value;
			var bid = document.getElementById("inBID_no").value;
			var btele = document.getElementById("inBTele_num").value;
			if(myContactb!=""&&bsex!=""&&bid!=""&&btele!="")
				{
					$("#sigConfirm").css("display","inline-block");
				}
			sendContractUpdateNotice();			 
		}else
			{
				$("#contractSend").css("display","inline-block");
			}
		document.getElementById("inPartA").readOnly=true;
		//document.getElementById("inLicense_no").readOnly=true;
		document.getElementById("inATele_num").readOnly=true;
		document.getElementById("inPartB").readOnly=true;
		document.getElementById("inSex").readOnly=true;
		document.getElementById("inBID_no").readOnly=true;
		document.getElementById("inBTele_num").readOnly=true;
		//document.getElementById("inProbation_start").readOnly=true;
		//document.getElementById("inProbation_end").readOnly=true;
		//document.getElementById("inPractice_begin").readOnly=true;
		//document.getElementById("inPractice_end").readOnly=true;
		document.getElementById("inCinsure_ratio").readOnly=true;
		document.getElementById("inPayment").readOnly=true;
		//document.getElementById("contract_end_time").readOnly=true;
		//document.getElementById("contract_start_time").readOnly=true;
		document.getElementById("fine").readOnly=true;
		document.getElementById("job_name").readOnly=true;
		document.getElementById("contract_background").readOnly=true;
		document.getElementById("contract_summary").readOnly=true;
		document.getElementById("work_time").readOnly=true;
		 $('#inPractice_begin').attr("disabled",true);//控制不能选择日期 disabled和readonly的区别
		 $('#inPractice_end').attr("disabled",true);
		 $('#inProbation_start').attr("disabled",true);
		 $('#inProbation_end').attr("disabled",true);
		 $('#contract_end_time').attr("disabled",true);
		 $('#contract_start_time').attr("disabled",true);
	}else{
		msgbox("提示",2,"保存失败");
	}
}
//推送合同更新通知
function sendContractUpdateNotice(){
	cVe.startReqByMsgHandle(cVeName,'','','reqSendContractUpdateNotice','resSendContractUpdateNotice','ECP.handle.common.CCommonHandle.handleMsg');				
}

function reqSendContractUpdateNotice(){
	var req = {};
	var org_name =  cVeUti.Cookie.getCookie("org_name");
	var contract_name = document.getElementById("contract_name").value;
	req["systemInfo"] = "您好，这里是"+org_name+"，合同："+contract_name+" 的内容有部分修改，请注意查看，谢谢！";
	//alert(req["systemInfo"]);
	req["toAccount"] = document.getElementById("user_id").value;
	req["fromAccount"] = cVeUti.Cookie.getCookie("org_id");
	req["chat_content"] = "您好，这里是"+org_name+"，合同："+contract_name+" 的内容有部分修改，请注意查看，谢谢！";
	req["user_id"] = document.getElementById("user_id").value;
	req["org_id"] = cVeUti.Cookie.getCookie("org_id");
	req["msg_type"] = "to";//表示从招聘端发出
	req["is_read"] = "已读";
	req["op"] = "webPush";
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resSendContractUpdateNotice(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var retData=retDataObj[cVe.cEioVeDataId];
	if(retData.result == "success"){
		//alert("发送成功");
	}else{
		alert("更新通知发送失败");
	}
}
function enableEditContract(){
	if(contract_status=="签订中")
	{
		var partB = document.getElementById("partB").value;
		var contract_name = document.getElementById("contract_name").value;
		document.getElementById("prompt_header").innerHTML = "'"+contract_name+"' 已经处于 '签订中' 状态，若需要编辑合同，请务必完善所有信息，并且编辑完成时我们将通知乙方 '"+partB+"' 关于合同的变动。";
		document.getElementById("prompt_content").innerHTML = "";
		$("#confirmSend").remove();
		//$("#confirmSend").css("display","none");
		if(typeof $("#enableUpdateButton").html()=="undefined"){
			$("#modal-footer").append('<button id="enableUpdateButton"type="button" class="btn btn-danger" onclick="confirmEnableEditContract()">确  定</button>');
		}
		$("#confirmModal").modal('show');		
	}else
		{
			confirmEnableEditContract();
		}
		
}
function confirmEnableEditContract()
{
	$("#sigConfirm").css("display","none");
	$("#confirmModal").modal('hide');
	$("#contractSend").css("display","none");
	$("#edit").css("display","none");
	$("#goBack").css("display","none");
	$("#updateContract").css("display","inline-block");
	$("#cancel").css("display","inline-block");
	document.getElementById("inPartA").readOnly=false;
	//document.getElementById("inLicense_no").readOnly=false;
	document.getElementById("inATele_num").readOnly=false;
	/*document.getElementById("inPartB").readOnly=false;
	document.getElementById("inSex").readOnly=false;
	document.getElementById("inBID_no").readOnly=false;
	document.getElementById("inBTele_num").readOnly=false;*/
	//document.getElementById("inProbation_start").readOnly=false;
	//document.getElementById("inProbation_end").readOnly=false;
	//document.getElementById("inPractice_begin").readOnly=false;
	//document.getElementById("inPractice_end").readOnly=false;
	document.getElementById("inCinsure_ratio").readOnly=false;
	document.getElementById("inPayment").readOnly=false;
	//document.getElementById("contract_end_time").readOnly=false;
	//document.getElementById("contract_start_time").readOnly=false;
	document.getElementById("fine").readOnly=false;
	document.getElementById("job_name").readOnly=false;
	document.getElementById("contract_background").readOnly=false;
	document.getElementById("contract_summary").readOnly=false;
	document.getElementById("work_time").readOnly=false;
	 $('#inPractice_begin').attr("disabled",false);//控制选择日期 disabled和readonly的区别
	  $('#inPractice_end').attr("disabled",false);
	  $('#inProbation_start').attr("disabled",false);
	  $('#inProbation_end').attr("disabled",false);
	  $('#contract_end_time').attr("disabled",false);
	  $('#contract_start_time').attr("disabled",false);
}
function contractInfoList(){
	
	grid= $('#dataGrid').jqGrid({ 
		  //caption: "合同模板列表",
		  datatype:'local',  
		 // altRows: true,
		  colNames:['序号','合同名称','乙方姓名','合同状态','操作','合同ID','模板编号'],
		  colModel:[  	    
		    {id:'number',name:'number', width:'100%',align:'center'},  
		    {id:'contract_name',name:'contract_name',width:'100%',align:'center'},
		    {id:'partb',name:'partb',width:'100%',align:'center'},
		    {id:'contract_status',name:'contract_status',width:'100%',align:'center'},
		    {id:'operate',name:'operate', width:'100%',align:'center',formatter:contractButtons},
		    {id:'contract_id',name:'contract_id', key:true, width:'100%',align:'center',hidden:'true'}, 
		    {id:'contract_modelno',name:'contract_modelno',  width:'100%',align:'center',hidden:'true'}, 
		  ],  
		  pager:"#pager",   
		  sortname:'0', 
		  rowNum: 16,
		  viewrecords: true,  
		  height:'100%',  
		  autowidth: true,
		  align:'center',
		  shrinkToFit:true,
		});	
}

function contractButtons(cellValue,options,rowObject){
	var id = options.rowId;//这里的rowId是关键字
	var contract_name ="'"+ rowObject.contract_name+"'";
	var contract_modelno ="'"+ rowObject.contract_modelno+"'";
	var edit = '<button class=" btn btn-primary my-jqgrid-button"  onclick="showContract('+id+','+contract_modelno+')">查看</button>'+
	           '<button class=" btn btn-danger my-jqgrid-button"  style="margin-left:15px;" onclick="deleteContract('+id+','+contract_name+')">删除</button>';
	return edit;
}

function showContract(id,contract_modelno){
	window.location.href = "contract.html?contract_id="+id+"&template_no="+contract_modelno;
}

var contract_id;
function deleteContract(id,contract_name){
		contract_id = id;
		document.getElementById("delItem").innerHTML = contract_name;
		$("#confirmDel").modal('show');//删除提示
}

//确认删除
function confirmDeleteContract(){
	cVe.startReqByMsgHandle(cVeName,'','','reqDeleteContract','resDeleteContract','ECP.handle.ContractHandle.handleMsg');
}
function reqDeleteContract(){
	var req = {};
	req["contract_id"] = contract_id;
	req["op"] = "contractDelete";
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resDeleteContract(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId];
	if(result.result=="success"){
		//getContractList();
		//window.location.reload();
		var url = window.location.pathname;
		var jsp = url.split("/");
		if(jsp[jsp.length-1]=="contractList.html")
		{
			getContractList();
		}else if(jsp[jsp.length-1]=="historyContractList.html")
			{
				getHistoryContractList();
			}
		$("#confirmDel").modal("hide");
		msgbox("提示",1,"删除成功");
				
	}else{
		msgbox("提示",2,"删除失败");
	}
}
//获取在签或编辑中的合同列表
function getContractList(){
	cVe.startReqByMsgHandle(cVeName,'','','reqGetContractList','resGetContractList','ECP.handle.ContractHandle.handleMsg');
}

function reqGetContractList(){
	var req = {};
	req["op"] = "contractList";
	req["org_id"] = cVeUti.Cookie.getCookie("org_id");
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resGetContractList()
{
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId];
	if(result.result=="0"){
		var data = result.rows;
		if(data.length<=0)
		{
			$('#contractTable').css("display","none");
			$('#prompt_info').css("display","block");
			$('#goBack').css("display","none");
		}else
			{
				contractInfoList();
				$('#dataGrid').jqGrid("clearGridData");
				for(var i=0;i<data.length;i++){
					data[i].number=i+1; 
				}
				var reader = {
				  page: 1 ,  
				  total: (data.length-1)/pageSize +1,  
				  records: data.length
				}; 				
				grid.setGridParam({data: data, localReader: reader}).trigger('reloadGrid');
			}		
	}
}
//获取历史合同列表
function getHistoryContractList(){
	cVe.startReqByMsgHandle(cVeName,'','','reqGetHistoryContractList','resGetHistoryContractList','ECP.handle.ContractHandle.handleMsg');
}

function reqGetHistoryContractList(){
	var req = {};
	req["op"] = "contractList";
	req["contract_status"] = "已签订";
	req["org_id"] = cVeUti.Cookie.getCookie("org_id");
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resGetHistoryContractList()
{
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId];
	if(result.result=="0"){
		var data = result.rows;
		if(data.length<=0)
		{
			$('#contractTable').css("display","none");
			$('#prompt_info').css("display","block");
			$('#goBack').css("display","none");
		}else
			{
				contractInfoList();
				$('#dataGrid').jqGrid("clearGridData");
				for(var i=0;i<data.length;i++){
					data[i].number=i+1; 
				}
				var reader = {
				  page: 1 ,  
				  total: (data.length-1)/pageSize +1,  
				  records: data.length
				}; 				
				grid.setGridParam({data: data, localReader: reader}).trigger('reloadGrid');
			}		
	}
}

function sendContract1()
{
	var partB = document.getElementById("partB").value;
	document.getElementById("prompt_header").innerHTML = "是否将以下合同发送给 "+partB+" ? 发送合同前请务必将信息填写完整！";
	document.getElementById("prompt_content").innerHTML = document.getElementById("contract_name").value;
	$("#confirmModal").modal('show');
	$("#enableUpdateButton").remove();
	if(typeof $("#confirmSend").html()=="undefined"){
		$("#modal-footer").append('<button id="confirmSend"type="button" class="btn btn-danger" onclick="confirmSendContract()">确  定</button>');
	}	
}

function confirmSendContract()
{
	var contacta = document.getElementById("inPartA").value ;
	var parta_telenum = document.getElementById("inATele_num").value ;
	var probation_start = document.getElementById("inProbation_start").value ;
	var probation_end = document.getElementById("inProbation_end").value ;
	var practice_begin = document.getElementById("inPractice_begin").value ;
	var practice_end = document.getElementById("inPractice_end").value ;
	var cinsure_ratio = document.getElementById("inCinsure_ratio").value ;
	var payment = document.getElementById("inPayment").value;
	var contract_end_time = document.getElementById("contract_end_time").value ;
	var contract_start_time = document.getElementById("contract_start_time").value ;
	var fine = document.getElementById("fine").value;
	var job_name = document.getElementById("job_name").value;
	var work_time = document.getElementById("work_time").value;
	var contract_background = document.getElementById("contract_background").value;
	var contract_summary = document.getElementById("contract_summary").value;
	if(contacta=="")
		{
		    $("#confirmModal").modal('hide');
			msgbox("提示",3,"甲方联系人不能为空");
			return;
		}
	if(parta_telenum=="")
	{
		$("#confirmModal").modal('hide');
		msgbox("提示",3,"甲方电话不能为空");
		return;
	}
	if(document.getElementById("cContract_background").style.display!="none"&&contract_background==""){
		$("#confirmModal").modal('hide');
		msgbox("提示",3,"合同背景不能为空");
		return;
	}
	if(document.getElementById("cContract_summary").style.display!="none"&&contract_summary==""){
		$("#confirmModal").modal('hide');
		msgbox("提示",3,"合同简介不能为空");
		return;
	}
	if(document.getElementById("cExpire_date").style.display!="none"&&(contract_end_time==""||contract_start_time=="")){
		$("#confirmModal").modal('hide');
		msgbox("提示",3,"合同起止时间不能为空");
		return;
	}
	if(document.getElementById("cProbation").style.display!="none"&&(probation_start==""||probation_end=="")){
		//$("#goBack").css("display","inline-block");
		$("#confirmModal").modal('hide');
		msgbox("提示",3,"试用期不能为空");
		return;
	}
	if(document.getElementById("cfildwork").style.display!="none"&&(practice_begin==""||practice_end=="")){
		$("#confirmModal").modal('hide');
		msgbox("提示",3,"实习期不能为空");
		return;
	}
	if(document.getElementById("cCinsure_ratio").style.display!="none"&&cinsure_ratio==""){
		$("#confirmModal").modal('hide');
		msgbox("提示",3,"五险一金不能为空");
		return;
	}
	if(document.getElementById("cPayment").style.display!="none"&&payment==""){
		$("#confirmModal").modal('hide');
		msgbox("提示",3,"薪资水平不能为空");
		return;
	}
	
	if(document.getElementById("cWork_time").style.display!="none"&&work_time==""){
		$("#confirmModal").modal('hide');
		msgbox("提示",3,"工作时间不能为空");
		return;
	}
	if(document.getElementById("cJob_name").style.display!="none"&&job_name==""){
		$("#confirmModal").modal('hide');
		msgbox("提示",3,"工作岗位不能为空");
		return;
	}
	if(document.getElementById("cFine").style.display!="none"&&fine==""){
		$("#confirmModal").modal('hide');
		msgbox("提示",3,"违约金不能为空");
		return;
	}
	cVe.startReqByMsgHandle(cVeName,'','','reqConfirmSendContract','resConfirmSendContract','ECP.handle.ContractHandle.handleMsg');
}

function reqConfirmSendContract()
{
   var Request = new Object();
   Request = GetRequest();
   var contract_id =Request["contract_id"];
   var req = {};
   req["op"] = "setContract_status";
   req["contract_id"] = contract_id;
   req["contract_status"] = "签订中";
   cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resConfirmSendContract()
{
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId];
	if(result.result=="0")
	{
		$('#confirmModal').modal('hide');
		msgbox("提示",1,"合同发送成功");
		//msgbox("提示",1,"合同发送成功");
		$("#contractSend").css("display","none");
		contract_status = "签订中";
		sendContractNotice();
	}else
		{
		    alert("发送失败");
		}
}

//推送合同
function sendContractNotice(){
	cVe.startReqByMsgHandle(cVeName,'','','reqSendContractNotice','resSendContractNotice','ECP.handle.common.CCommonHandle.handleMsg');				
}

function reqSendContractNotice(){
	var req = {};
	var org_name =  cVeUti.Cookie.getCookie("org_name");
	var contract_name = document.getElementById("contract_name").value;
	req["systemInfo"] = "您好，这里是"+org_name+"，我们已将合同："+contract_name+" 发送给你，请注意查看并及时填写好相关内容，谢谢！";
	//alert(req["systemInfo"]);
	req["toAccount"] = document.getElementById("user_id").value;
	req["fromAccount"] = cVeUti.Cookie.getCookie("org_id");
	req["chat_content"] = "您好，这里是"+org_name+"，我们已将合同："+contract_name+" 发送给你，请注意查看并及时填写好相关内容，谢谢！";
	req["user_id"] = document.getElementById("user_id").value;
	req["org_id"] = cVeUti.Cookie.getCookie("org_id");
	req["msg_type"] = "to";//表示从招聘端发出
	req["is_read"] = "已读";
	req["op"] = "webPush";
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resSendContractNotice(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var retData=retDataObj[cVe.cEioVeDataId];
	if(retData.result == "success"){
		
	}else{
		alert("通知发送失败");
	}
}

function sigContract()
{
	var partB = document.getElementById("partB").value;
	document.getElementById("prompt_header").innerHTML = "是否确定与  "+partB+"  签订该合同？签订的同时对应的求职申请将变为'历史'状态";
	document.getElementById("prompt_content").innerHTML = document.getElementById("contract_name").value;
	$("#enableUpdateButton").remove();
	$("#confirmModal").modal('show');
	if(typeof $("#confirmSig").html()=="undefined"){
		$("#modal-footer").append('<button id="confirmSig"type="button" class="btn btn-danger" onclick="confirmSigContract()">确  定</button>');
	}		
}

function confirmSigContract()
{
	cVe.startReqByMsgHandle(cVeName,'','','reqConfirmSigContract','resConfirmSigContract','ECP.handle.ContractHandle.handleMsg');					
}

function reqConfirmSigContract()
{
	 var Request = new Object();
     Request = GetRequest();
     var contract_id =Request["contract_id"];
     var req = {};
     req["op"] = "setContract_status";
     req["contract_id"] = contract_id;
     req["job_apply_id"] = document.getElementById("job_apply_id").value;
     req["contract_status"] = "已签订";
     cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));	
}

function resConfirmSigContract()
{
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId];
	if(result.result=="0")
	{
		$("#confirmModal").modal('hide');
		msgbox("提示",1,"合同签订成功");
		$("#sigConfirm").css("display","none");
		$("#edit").css("display","none");
		contract_status = "已签订";
		sendSigNotice();
	}else
		{
		    $("#confirmModal").modal('hide');
		    msgbox("提示",1,"合同签订失败");
		}
}


//推送合同签订通知
function sendSigNotice(){
	cVe.startReqByMsgHandle(cVeName,'','','reqSendSigNotice','resSendSigNotice','ECP.handle.common.CCommonHandle.handleMsg');				
}

function reqSendSigNotice(){
	var req = {};
	var org_name =  cVeUti.Cookie.getCookie("org_name");
	var contract_name = document.getElementById("contract_name").value;
	req["systemInfo"] = "您好，这里是"+org_name+"，我们已将合同："+contract_name+" 确认为已签订状态，请你查看，谢谢！";
	//alert(req["systemInfo"]);
	req["toAccount"] = document.getElementById("user_id").value;
	req["fromAccount"] = cVeUti.Cookie.getCookie("org_id");
	req["chat_content"] = "您好，这里是"+org_name+"，我们已将合同："+contract_name+" 确认为已签订状态，请你查看，谢谢！";
	req["user_id"] = document.getElementById("user_id").value;
	req["org_id"] = cVeUti.Cookie.getCookie("org_id");
	req["msg_type"] = "to";//表示从招聘端发出
	req["is_read"] = "已读";
	req["op"] = "webPush";
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resSendSigNotice(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var retData=retDataObj[cVe.cEioVeDataId];
	if(retData.result == "success"){
		//alert("发送成功");
	}else{
		alert("通知发送失败");
	}
}