//EIO 抽注

var cVe= new EIO.ve();
var cVei = new EIO.vei();
var cVeUti = new EIO.veUti();
var cVeName="VeDemo1";//定义本应用的Ve引擎名称，用户自定义，用作服务按的消息处理调度
var cServerUri="/ECPServer/EIOServletMsgEngine";
var cMsgConfigure;
var pageSize = 15;

//lwj 引入的全局变量
var num_xy = 2;//x,y坐标显示的时候乘以 2 
var mag_top = "20px";//磁条位置,写死的
var card_x = 856;//卡面宽度
var card_y = 540;//卡面高度

function exText(id){
	return $("#"+id).val();
}

function inText(id, value){
	$("#"+id).val(value);
}

function exRadio(name){
	var result =$("input[name='"+name+"']:checked").val();
	if(result==undefined) result = "";
	return result;
}

function inRadio(name, value){
	$("input[name='"+name+"'][value='"+value+"']").attr("checked",true);
}

function exSelect(id){
	/*if($("#"+id).val()==='') return "";*/
	return $("#"+id).val();
}

function inSelect(id, value){
	$("#"+id+" option[value='"+value+"']").attr("selected",true);
}

function exCheckbox(name){
	var result="";
	$("input[name='"+name+"']:checked").each(
		function(){ 
			result+=$(this).val()+","; 
		}
	);
	if(result!="") result=result.substring(0, result.length-1);
	return result;

}

function inCheckbox(name,value){
	var vals = value.split(",");
	for(var key in vals){
		var val = vals[key];
		$("input[name='"+name+"'][value='"+val+"']").attr("checked",true);
	}
}

//author: meizz 
Date.prototype.Format = function (fmt) { 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

function parseDate(ds){
	ds= ds.split(".")[0];
	ds = ds.replace(/-/g, '/');
	ds = ds.replace('T', ' ');
	ds = ds.replace(/(\+[0-9]{2})(\:)([0-9]{2}$)/, ' UTC\$1\$3');
	d = new Date(ds);
	return d;
}

function exDate(id){
	if($("#"+id).val() == "") return "";
	var date = parseDate($("#"+id).val());
	return date.Format("yyyy-MM-dd hh:mm:ss");
}

function inDate(id, value){
	$("#"+id).val(parseDate(value).Format("yyyy-MM-dd"));
	$("#"+id+"_picker").datetimepicker("update");
}

function inDatetime(id, value){
	$("#"+id).val(parseDate(value).Format("yyyy-MM-dd hh:mm:ss"));
	$("#"+id+"_picker").datetimepicker('update');
}

function getParam() {
	var url = location.search; //获取url中"?"符后的字串
	var theRequest = {};
	if (url.indexOf("?") != -1) {
		var str = url.substr(1);
		strs = str.split("&");
		for(var i = 0; i < strs.length; i ++) {
			theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
		}
	}
	return theRequest;
}

function getUserId(){
	var cLoginPermission =cVeUti.readLoginPermit();
	return cLoginPermission["cUserid"];
}

function getUserName(){
	var cLoginPermission =cVeUti.readLoginPermit();
	return cLoginPermission["cUsername"];
}

/*
menu start
*/
var page_template;
function loadMenu(){
	var template = $("<div>");
	page_template = template;
	template.load('/Fastcard/html/common/template.html',function(){
		
		//load content_title
		$(".content_title").append(template.find(".content_title").html());
		$("#user").text(cVeUti.readLoginPermit()["cUsername"]);
		
		//load footer
		$(".footer").append(template.find(".footer").html());
		
		//loadMenu2(template);
		loadMenu1(template);
	});

	//$(".page_left #wrapper").load('/Fastcard/html/common/menu.html');
}

//由于请求菜单时会和其他数据请求共用EIO的cVe.XHR.responseText，所以这里用了jquery发请求
//并根据返回结果生成菜单树
//必须用$。ajax,ie8不支持$.post
function loadMenu1(template){
	$.ajax({   
        type: "POST",   
        url: cServerUri,   
        data: {
			'VeName':cVeName,
			'VEReqMsgType':'',
			'VEReqMsgName':'',
			'MsgHandle':'com.fastcard.handle.user.MenuHandle.handleMsg',
			'EIOVEDATA':"{'userid':"+getUserId()+",'op':'menuGet'}"
		},   
        dataType: "text",   
        contentType: "application/x-www-form-urlencoded;charset=utf-8",   
        async:false,   
        success: function (data) {
        	var retDataObj=JSON.parse(data);
        	
        	var retData=retDataObj[cVe.cEioVeDataId];
			var menuTree = {};
			menuTree[0] ={'name':'','url':'','parent_id':-1,'sons':[], isOpen:false};
			for(var key in retData.rows){
				var menuOri=retData.rows[key];
				var menuDest = {};
				menuDest.name = menuOri.right_name;
				menuDest.url = menuOri.right_url;
				if(menuOri.parent_id!='')
					menuDest.parent_id = menuOri.parent_id;
				else menuDest.parent_id=0;
				menuDest.sons=[];
				menuDest.isOpen=false;
				menuTree[menuOri.right_id] = menuDest;
			}
			for(var key in menuTree){
				if(isNaN(key)) continue;
				var curMenu = menuTree[key];
				if(key!=0) menuTree[curMenu.parent_id].sons.push(key);
			}
			
			//计算已经展开的菜单项
			var url = location.href;
			var temps = url.split("/Fastcard/html/");
			if(temps.length<2) return;
			else url=temps[1];
			var openId;
			for(var key in menuTree){
				var menu = menuTree[key];
				if(menu.url==url) openId=key;
			}
			if(openId!=undefined){
				while(openId!=-1){
					menuTree[openId].isOpen=true;
					openId= menuTree[openId].parent_id;
				}
			}
			
			var menu = loadMenuNode(template,menuTree,0);
			$('.page_left #wrapper').append(menu);
			$.getScript('/Fastcard/js/common/nav.js');
			
			//当点击菜单展开时收起同级菜单
			$('.navigation a').click(function(){
				if(!$(this).hasClass('in')){
					var lis=$(this).parent().siblings();
					lis.find("ul.in").slideUp(300);
					lis.find("ul.in").removeClass("in");
					lis.find("a.in").removeClass("in");
				}
			});
			
			//填写面包屑
			loadBreadCrumb(menuTree);
			
			//alert($('.navigation').html());
		},   
        error: function (data, status, e) {   
            alert("错误！" + e);   
        }   

    });
}
/*
function reqMenuGet(){
	var req={};
	req["userid"]=getUserId() ;		
	req["op"]="menuGet";
    cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
};

function resMenuGet(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var retData=retDataObj[cVe.cEioVeDataId];
	//console.log(retData);
	
	var menuTree = {};
	menuTree[0] ={'name':'','url':'','parent_id':-1,'sons':[]};
	for(var key in retData.rows){
		var menuOri=retData.rows[key];
		var menuDest = {};
		menuDest.name = menuOri.right_name;
		menuDest.url = menuOri.right_url;
		if(menuOri.parent_id!='')
			menuDest.parent_id = menuOri.parent_id;
		else menuDest.parent_id=0;
		menuDest.sons=[];
		menuTree[menuOri.right_id] = menuDest;
	}
	for(var key in menuTree){
		var curMenu = menuTree[key];
		if(key!=0) menuTree[curMenu.parent_id].sons.push(key);
	}
	
	
	var menu = loadMenuNode(page_template,menuTree,0).html();
	$('.page_left #wrapper').append(menu);
	$.getScript('/Fastcard/js/common/nav.js');

};

//用eio请求菜单，50ms后才开始请求，避开和其他请求的冲突
function loadMenu2(template){		
	window.setTimeout(function(){
		cVe.startReqByMsgHandle(cVeName,'','','reqMenuGet','resMenuGet','com.fastcard.handle.user.MenuHandle.handleMsg');		
	},50);	
}
*/
//递归生成菜单树
function loadMenuNode(template,menuTree,node){
	var cur;
	//container
	if(menuTree[node].parent_id == -1){
		cur = $(template.find(".container").html());
	}
	else if(menuTree[node].parent_id == 0){
		//topHasNoSon
		if(menuTree[node].sons.length==0){
			cur = $(template.find(".topHasNo").html());
		}
		//topHasSon
		else{
			cur = $(template.find(".topHas").html());
		}
	}
	else{
		//sonHasNoSon
		if(menuTree[node].sons.length==0){
			cur = $(template.find(".sonHasNo").html());
		}
		//sonHasSon
		else{
			cur = $(template.find(".sonHas").html());
		}
	}
	cur.find('span').text(menuTree[node].name);
	if(menuTree[node].url!=='') cur.find('a').attr('href','/Fastcard/html/'+menuTree[node].url);
	if(menuTree[node].isOpen){
		cur.find('a').addClass("in");
		cur.find('ul').addClass("in");	
		cur.find('ul').css("display","block");
		if(menuTree[node].sons.length==0) cur.find('span').addClass("cur");
	}
	var contain=cur.find('.nav-stacked');
	
	
	
	for(var key in menuTree[node].sons){	
		if(isNaN(key)) continue;
		var son_id = menuTree[node].sons[key];
		
		var son = loadMenuNode(template,menuTree,son_id);
		contain.append(son);
	}
	return cur;
		
}

function loadBreadCrumb(menuTree){
	//计算url，如：user/log/logOperation.html
	var url = location.href;
	var temps = url.split("/Fastcard/html/");
	if(temps.length<2) return;
	else url=temps[1];
	
	
	//计算对应的叶子节点
	var leaf;
	for(var key in menuTree){
		if(menuTree[key].url==url) {
			leaf=key;
			break;
		}
	}
	if(leaf==undefined) return;
	
	//计算出由根节点开始路径
	var crumbs = [];
	var index = leaf;
	while(menuTree[index].parent_id!= -1){
		crumbs.unshift(index);
		index = menuTree[index].parent_id;
	}
	
	
	var breads = $(".content_title .pull-left");
	for(var i =0;i<crumbs.length;i++){
		var theUrl=menuTree[crumbs[i]].url;
		var bread;
		if(theUrl=='') bread = menuTree[crumbs[i]].name;
		else bread='<a href="/Fastcard/html/'+menuTree[crumbs[i]].url+'">'+menuTree[crumbs[i]].name+'</a>';
		
		
		
		if(i<crumbs.length-1)
			bread+='<i class="icon-caret-right"></i>';
		breads.append(bread);
	}
	
}

/*
menu end
*/

function logout(){
	cVeUti.Cookie.delCookie("lastOperTime");
	cVe.startReqByMsgHandle(cVeName,'','','reqLogOutLog','resLogOutLog','com.fastcard.handle.user.UserHandle.handleMsg');
	cVeUti.Cookie.delCookie('VeLoginPermit');
	window.location.href="/Fastcard/index.html";
	
}

function reqLogOutLog(){
	var req = {};
	req["op"] = "logoutLog";
	//req["flag"] = "permission";
	req["userid"]=getUserId();
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resLogOutLog(){	
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var retData=retDataObj[cVe.cEioVeDataId];
	if(retData.msg != "success"){ 		
		msgbox("提示",2,"注销失败");
		return;
	}	
}
/*
$("body").click(function(){
	//alert(1);
	//cVe.startReqByMsgHandle(cVeName,'','','reqJudgeLogin','resJudgeLogin','com.fastcard.handle.user.UserHandle.handleMsg');
	var basePath = '';
	var pathName = window.location.pathname.substring(1);//substring(1)去掉打头的/
	//alert(pathName);
	var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/'));//substring(0, pathName.indexOf('/'))获取从下标0开始到第一个/位置之间的字符
	//alert(webName);
	if(webName == "") {
		basePath=window.location.protocol + '//' +window.location.host;
		}else{
			basePath =window.location.protocol + '//' + window.location.host + '/' +webName; 
			}
	
 	var id = getUserId();
	var time = new Date().getTime(); 
	//alert(''+basePath+'/servlet/JudgeLoginServlet?currentTime='+time);
	$.ajax({
		url : ''+basePath+'/servlet/JudgeLoginServlet?currentTime='+time,
		secureuri : false,
		async:false,
		//fileElementId : 'add_scriptFile'+(upload_file+1),
		dataType : 'json',
		data : {
			userid:id
		},
		success : function(data, status) {
			judgeLogin(data);
		},
		error : function(data, status, e) {
			//alert('上传出错');
			//msgbox("提示","2","上传出错！");
		}
	});
	//alert(2);
});
*/
function judgeLogin(data){
	if(data.msg == "success"){ 		
		/*$("body").append('<div id="curtain"></div>');
		$("body").append('<div class="my_modal" id="changePassword">'+
				'<div class="modal-dialog" role="document">'+
						'<div class="modal-header alert-title">掉线提示</div>'+
						'<p class="alert-content"><span>对不起，您的账号在'+data.ip+'被登录</span></p>'+
						'<div class="modal_form_content">'+
							'<button type="button" class="btn btn-danger" style="width:100px" data-toggle="modal" data-backdrop="static" onclick="confirmClose();">确认</button>'+
						'</div>'+
				'</div>'+
			'</div>');
		$("#curtain").css("display","block");
		$("#changePassword").css("display","block");
		
		$("#curtain").css("display","none");
		$("#changePassword").css("display","none");
		*/
		alert("该用户在'"+data.ip+"'被登录");
		cVeUti.Cookie.delCookie('VeLoginPermit');
		$.cookie("lastOperTime","",{ expires: -1 });
		window.location.href="/Fastcard/index.html";
	
	}	
}
/*
function reqJudgeLogin(){
	var req = {};
	req["op"] = "judgeLogin";
	//req["flag"] = "permission";
	req["userid"]=getUserId();
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resJudgeLogin(){	
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var retData=retDataObj[cVe.cEioVeDataId];
	if(retData.msg == "success"){ 		
		$("body").append('<div id="curtain"></div>');
		$("body").append('<div class="my_modal" id="changePassword">'+
				'<div class="modal-dialog" role="document">'+
						'<div class="modal-header alert-title">掉线提示</div>'+
						'<p class="alert-content"><span>对不起，您的账号在'+retData.ip+'被登录</span></p>'+
						'<div class="modal_form_content">'+
							'<button type="button" class="btn btn-danger" style="width:100px" data-toggle="modal" data-backdrop="static" onclick="confirmClose();">确认</button>'+
						'</div>'+
				'</div>'+
			'</div>');
		$("#curtain").css("display","block");
		$("#changePassword").css("display","block");
	}	
}
*/
function confirmClose(){
	$("#curtain").css("display","none");
	$("#changePassword").css("display","none");
	cVeUti.Cookie.delCookie('VeLoginPermit');
	$.cookie("lastOperTime","",{ expires: -1 });
	window.location.href="/Fastcard/index.html";
 }
function scheduledLogout(){
	var url = location.href;
	var temps = url.split("/Fastcard/html/");
	if(temps.length<2) return;
	else url=temps[1];
	if(url.indexOf("login") > 0) return;
	var min=JSON.parse(cVeUti.Cookie.getCookie("param")).session_expire;
	if(!min) min=30;
	if(!cVeUti.Cookie.getCookie("lastOperTime") || new Date().getTime()-cVeUti.Cookie.getCookie("lastOperTime")<(min *60 * 1000)){
		cVeUti.Cookie.setCookie("lastOperTime",new Date().getTime());
	}else{
		msgbox("提示",2,"登陆超时，请重新登录");
		setTimeout(logout,1500);
	}
}

//消息弹出框
$("body").append('<div id="msgbox"></div>');
$("body").append('<div class="msgbox"></div>');
function msgbox(title,type,message){//title:alert框标题（统一用”提示“）；type 1：操作成功，2：操作失败，3：警告；message:内容（比如：制卡成功)
	 $(".msgbox").empty();
	if(type == 1){
	   srcName="/Fastcard/images/alertSuccess.png";
   }else if(type == 2){
	   srcName="/Fastcard/images/alertFail.png";
   }else if(type == 3){
	   srcName="/Fastcard/images/alertWarn.png";
   }else{
	   return;
	   }
	$(".msgbox").append('<div class="modal-dialog" role="document">'+
				'<div class="modal-header alert-title">'+title+'：</div>'+
				'<div class="modal_form_content">'+
					'<p class="alert-content"><img src="'+srcName+'"/><span class="alert-msg">'+message+'</span></p>'+
					'<button type="button" class="alert-modal-close btn  btn-default" onclick="msgboxClose();">确定</button>'+
					'</div>'+
		'</div>');
	$("#msgbox").css("display","block");
	$(".msgbox").css("display","block");
}

$(function(){
	var retData = cVeUti.Cookie.getCookie('userStatus');
	//alert(getUserName());
	var pathName = window.location.pathname;
	var len = pathName.split("/").length;
	if(retData=="6"&& pathName.split("/")[len-1]!="login.html"){
	$("body").append('<div id="curtain"></div>');
	$("body").append('<div class="my_modal" id="changePassword">'+
			'<div class="modal-dialog" role="document">'+
					'<div class="modal-header alert-title">请设置新密码</div>'+
					'<div class="modal_form_content">'+
						'<div>'+
		    	    '<label class="flag_div">'+ 
			    	'<span class="lab_text text-right">新密码<i class="red">*</i>：</span>'+
			    	'<input  class="modal_form_input"  id="newPassword" type="password" style="width:220px"/>'+
			    	'<span class="flag_wrong" id="newPassword_flag">x</span>'+					   			    	
			    	'</label>'+
			    '</div>'+
			    '<div>'+
		    	    '<label class="flag_div">'+ 
			    	'<span class="lab_text text-right">确认新密码<i class="red">*</i>：</span>'+
			    	'<input  class="modal_form_input"  id="renewPassword" type="password" style="width:220px" />'+
			    	'<span class="flag_wrong" id="renewPassword_flag">x</span>'+					   			    	
			    	'</label>'+
			    '</div>'+
						'<button type="button" class="btn btn-danger" style="width:100px" data-toggle="modal" data-backdrop="static" onclick="firstResetPassword();">确认</button>'+
					'</div>'+
			'</div>'+
		'</div>');
	$("#curtain").css("display","block");
	$("#changePassword").css("display","block");
	}
});
$("#newPassword").blur(function(){
	if(!exText("newPassword")){
		$("#newPassword_flag").css("display","inline-block");//输入为空时
	}else{
		$("#newPassword_flag").css("display","none");
	}
});

$("#renewPassword").blur(function(){
	if(!exText("renewPassword")){
		$("#renewPassword_flag").css("display","inline-block");//输入为空时
	}else{
		$("#renewPassword_flag").css("display","none");
	}
});

function firstResetPassword(){
	if(!exText("newPassword")){
		$("#newPassword_flag").css("display","inline-block");//输入为空时
	}

	if(!exText("renewPassword")){
		$("#renewPassword_flag").css("display","inline-block");//输入为空时
	}
	
	if(!exText("renewPassword")||!exText("newPassword")){
		return;
	}
if(exText("newPassword")!=exText("renewPassword"))
	{
	   msgbox("提示",3,"两次密码不一致");
	   document.getElementById("newPassword").value="";
	   document.getElementById("renewPassword").value="";
	   return;
	}
var newPassword = document.getElementById("newPassword").value;
var userName = getUserName();
if(newPassword.length>20||newPassword.length<6)
{
   msgbox("提示",3,"密码长度限于6到20之间");
   return;
}

if(newPassword==userName){
	msgbox("提示",3,"密码和用户名不能相同");
	   return;
}
var flag_pwd = 0;
var numReg = /^[0-9]+$/;
var charReg = /^[a-z]+$/;
var bCharReg = /^[A-Z]+$/;
var specialReg  =/[`~!@#$%^&*_+<>{}\/'[\]]/m;
for (var i = 0; i < newPassword.length; i++) { 
	if(numReg.test(newPassword.substr(i, 1))){
		flag_pwd +=1;
		break;
	}
} 
for (var i = 0; i < newPassword.length; i++) { 
	if(charReg.test(newPassword.substr(i, 1))){
		flag_pwd +=1;
		break;
	}
} 

for (var i = 0; i < newPassword.length; i++) { 
	if(bCharReg.test(newPassword.substr(i, 1))){
		flag_pwd +=1;
		break;
	}
} 

for (var i = 0; i < newPassword.length; i++) { 
	if(specialReg.test(newPassword.substr(i, 1))){
		flag_pwd +=1;
		break;
	}
} 
if(flag_pwd<3){
	msgbox("提示",3,"密码必须最少由大小写、数字和特殊字符中的三种组成");
	return;
}
cVe.startReqByMsgHandle(cVeName,'','','reqFirstResetPassword','resFirstResetPassword','com.fastcard.handle.user.UserHandle.handleMsg');
}

function reqFirstResetPassword(){
	var req = {};
	req["op"] = "firstResetPassword";
	req["newPassword"] = exText("newPassword");
	req["userid"] = getUserId();
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resFirstResetPassword(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var retData=retDataObj[cVe.cEioVeDataId];
	if(retData.msg == "success"){ 
		cVeUti.Cookie.setCookie("userStatus",'1');
		changeActivate_code();
        msgbox("提示",1,"修改密码成功");
        $("#curtain").css("display","none");
    	$("#changePassword").css("display","none");
		 document.getElementById("newPassword").value="";
		 document.getElementById("renewPassword").value="";
	}else if(retData.msg == "repeatedPwd"){
		msgbox("提示",3,"密码不能和原来密码相同");
	}else{
		msgbox("提示",2,"操作失败");
	}
}

function changeActivate_code(){
cVe.startReqByMsgHandle(cVeName,'','','reqChangeActivate_code','resChangeActivate_code','com.fastcard.handle.user.UserHandle.handleMsg');
}

function reqChangeActivate_code(){
	var req = {};
	req["op"] = "changeActivate_code";
	//req["newPassword"] = exText("newPassword");
	req["userid"] = getUserId();
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resChangeActivate_code(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var retData=retDataObj[cVe.cEioVeDataId];
	if(retData.msg == "success"){ 
		cVeUti.Cookie.setCookie("userStatus",'1');
        $("#curtain").css("display","none");
    	$("#changePassword").css("display","none");
		 document.getElementById("newPassword").value="";
		 document.getElementById("renewPassword").value="";
	}
}
	
function msgboxClose(){
	$("#msgbox").css("display","none");
	$(".msgbox").css("display","none");
}
//msgbox("提示",1,"操作成功");
//msgbox("提示",2,"操作失败");
//msgbox("提示",3,"请至少选择一条操作记录");

function showHint(container,text){
	$("#"+container).css("display","inline-block");
	$("#"+container).text(text);
}

function hideHint(container){
	$("#"+container).css("display","none");
	$("#"+container).text("");
}

$(function(){
	//menu_flag点击事件
	$('.menu_flag').click(function(){
		if($(this).hasClass('toLeft')){
			$(this).removeClass('toLeft').addClass('toRight');
			$('.page_left').hide(400);
			$('.page_content').animate({'margin-left':'0px'},400);
			$('#gridData').setGridWidth($(window).width()-22.1)
		}
		else{
			$(this).removeClass('toRight').addClass('toLeft');
			$('.page_left').show(400);
			$('.page_content').animate({'margin-left':'250px'},400);
			$('#gridData').setGridWidth($(window).width()-272.1)
		}
	});
}) ;


//输入合法性检测方法--只能输入数字、字母和汉字  和空格
//@author--lwj
function inputLegitimacy(id){
	var pattern = new RegExp(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5\ ]/g);
	var value = $("#"+id).val();
//	value = value.replace(/\s/g,"");//去掉所有空格
	if( pattern.test( value ) ){
		msgbox("提示","3","不能输入特殊字符！");
	}
	value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5\ ]/g,'');
	$("#"+id).val( value );
}

//输入合法性检测方法--只能输入数字、字母和汉字 和这些符号（。，、；：？！“”） 以及空格
//@author--lwj
function inputLegitimacy_symbol(id){
	var pattern = new RegExp(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5\.\,\;\:\?\!\"\"\。\，\；\：\？\！\“\”\、\s]/g);
	var value = $("#"+id).val();
//	value = value.replace(/\s/g,"");//去掉所有空格
	if( pattern.test( value ) ){
		msgbox("提示","3","不能输入特殊字符！");
	}
	value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5\.\,\;\:\?\!\"\"\。\，\；\：\？\！\“\”\、\s]/g,'');
	$("#"+id).val( value );
}

//输入合法性检测方法--只能输入数字、字母和空格
//@author--lwj
function inputNumberString(id){
	var pattern = new RegExp(/[^\a-\z\A-\Z0-9\ ]/g);
	var value = $("#"+id).val();
//	value = value.replace(/\s/g,"");//去掉所有空格
	if( pattern.test( value ) ){
		msgbox("提示","3","只能输入数字和字母！");
	}
	value=value.replace(/[^\a-\z\A-\Z0-9\ ]/g,'');
	$("#"+id).val( value );
}


//输入合法性检测方法--input只能输入数字、字母和汉字 ,textarea只能输入数字、字母和汉字 和这些符号（。，、；：？！“”）
//@author--hj
function inputLegitimacy_hj(id,id2){
	if(typeof $("input[id="+id+"]").val()!=="undefined"){//如果是input输入
		var pattern = new RegExp(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g);
		var value = $("#"+id).val();
		value = value.replace(/\s+/g,"");//去掉所有空格
		if( pattern.test( value ) ){
			$("#"+id2).css("display","block");
			return 0;
		}else{
			$("#"+id2).css("display","none");
			return 1;
		}
	}else if(typeof $("textarea[id="+id+"]").val()!=="undefined"){//如果是textarea文本输入
		var pattern = new RegExp(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5\.\,\;\:\?\!\"\"\。\，\；\：\？\！\“\”\、]/g);
		var value = $("#"+id).val();
		value = value.replace(/\s+/g,"");//去掉所有空格 
		if( pattern.test( value ) ){
			$("#"+id2).css("display","block");
			return 0;
		}else{
			$("#"+id2).css("display","none");
			return 1;
		}
	}		
}


//输入合法性检测方法--只能输入数字
//@author--hj
function inputNumber_hj(id,id2){
	var pattern = new RegExp(/[^0-9]/g);
	var value = $("#"+id).val();
	value = value.replace(/\s+/g,"");//去掉所有空格
	if( pattern.test( value ) ){
		$("#"+id2).css("display","block");
		return 0;
	}else{
		$("#"+id2).css("display","none");
		return 1;
	}
}

