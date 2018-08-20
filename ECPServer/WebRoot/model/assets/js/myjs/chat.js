var messageNum = 0;
//单独聊天
function sendMessagePer(){	
	if(document.getElementById('perContent').value=="")
	{
		msgbox("提示",3,"消息不能为空");
	}else
		{
		cVe.startReqByMsgHandle(cVeName,'','','reqSendMessagePer','resSendMessagePer','ECP.handle.common.CCommonHandle.handleMsg');
		}
}

function reqSendMessagePer(){
	var req = {};
	//req["systemInfo"] = document.getElementById('perContent').value;
	req["msg"] = document.getElementById('perContent').value;
	req["toAccount"] = document.getElementById('user_id').value;
	req["fromAccount"] = cVeUti.Cookie.getCookie("org_id");
	req["fromName"] = cVeUti.Cookie.getCookie("org_name");
	req["org_name"] = cVeUti.Cookie.getCookie("org_name");
	req["chat_content"] = document.getElementById('perContent').value;
	req["user_id"] = document.getElementById('user_id').value;
	req["org_id"] = cVeUti.Cookie.getCookie("org_id");
	req["msg_type"] = "to";//表示从招聘端发出
	req["is_read"] = "已读";
	//req["chat_time"] = myTime;
	req["op"] = "webChat";
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resSendMessagePer(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var retData=retDataObj[cVe.cEioVeDataId];
	if(retData.result == "success"){
		/*$("#chatContent").append(
				'<div class="chat-widget-name-left">'
	            +'<h4>Amanna Seiar</h4></div>'
				+'<div id="chatItem'+messageNum+'" class="chat-widget-right"style="text-align:center;margin-bottom:18px;"></div>');
		document.getElementById('chatItem'+messageNum+'').innerHTML=document.getElementById('perContent').value;
		messageNum++;*/
		$("#chatContent").append(
				'<li style="list-style-type: none" id="myLi'+messageNum+'"><p class="time"><span id="time'+messageNum+'"></span></p>'+
                '<div class="main" style="text-align:right;width:100%;padding-right:40px">'+
                '<div class="rightText"><p style="display:inline-block;margin-bottom:0px;margin-top:5px;line-height:20px"id="chatItem'+messageNum+'"></p><div class="rightArrow"></div>'+
                '</div>'+
                '<div style="display:inline-block;float:right;position:absolute">'+
                    '<img class="avatar" width="30" height="30" src="assets/img/chatImage/myPic1.jpg">'+
                '</div>'+
             '</div>'+
       '</li> ');
		document.getElementById('chatItem'+messageNum+'').innerHTML=document.getElementById('perContent').value;
		document.getElementById('time'+messageNum+'').innerHTML = getNowFormatDate();
		//cVe.startReqByMsgHandle(cVeName,'','','reqSendMessage','resSendMessage','ECP.handle.common.CCommonHandle.handleMsg');	
		messageNum++;
		var e=document.getElementById("chatContent");//使滚动条置底
		e.scrollTop=e.scrollHeight;
		document.getElementById('perContent').value = "";
	}else{
		msgbox("提示",3,"失败");
	}
}
//聊天界面发送消息
function sendMessage(){
	if(document.getElementById('content').value=="")
	{
	    //msgbox("提示",3,"消息不能为空");
		alert("消息不能为空！");
	}else
		{
		    cVe.startReqByMsgHandle(cVeName,'','','reqSendMessage','resSendMessage','ECP.handle.common.CCommonHandle.handleMsg');				
		}
}

function reqSendMessage(){
	/*var myDate = new Date();
	var myTime = myDate.toLocaleString( );*/
	var req = {};
	req["msg"] = document.getElementById('content').value;
	req["toAccount"] = user_id;
	req["fromAccount"] = cVeUti.Cookie.getCookie("org_id");
	req["fromName"] = cVeUti.Cookie.getCookie("org_name");
	req["org_name"] = cVeUti.Cookie.getCookie("org_name");
	req["chat_content"] = document.getElementById('content').value;
	//req["user_id"] = user_id;
	req["user_id"] = user_id;
	req["org_id"] = cVeUti.Cookie.getCookie("org_id");
	req["msg_type"] = "to";//表示从招聘端发出
	//req["chat_time"] = myTime;
	req["is_read"] = "已读";
	req["op"] = "webChat";
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resSendMessage(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var retData=retDataObj[cVe.cEioVeDataId];
	if(retData.result == "success"){
		$("#messageList").append(
				'<li style="display:block" id="myLi'+messageNum+'"><p class="time"><span id="time'+messageNum+'"></span></p>'+
                '<div class="main" style="text-align:right;width:100%;padding-right:40px">'+
                '<div class="rightText"><p style="display:inline-block;margin-bottom:0px;margin-top:5px;line-height:20px"id="chatItem'+messageNum+'"></p><div class="rightArrow"></div>'+
                '</div>'+
                '<div style="display:inline-block;float:right;position:absolute">'+
                    '<img class="avatar" style="display:inline-block;position:absolute" width="30" height="30" src="assets/img/chatImage/myPic1.jpg">'+
                '</div>'+
             '</div>'+
       '</li> ');
		document.getElementById('chatItem'+messageNum+'').innerHTML=document.getElementById('content').value;
		document.getElementById('time'+messageNum+'').innerHTML = getNowFormatDate();
		messageNum++;
		var e=document.getElementById("messageBox");//使滚动条置底
		e.scrollTop=e.scrollHeight;
		document.getElementById('content').value = "";
		/*--------将该好友放在第一个--------*/
		var tag_li = document.getElementById("friendsList").getElementsByTagName("li");
		if(tag_li[0].id!=user_id)//如果该好友不是第一个，就放到列表中的第一位去
		{			
			var tag_p = document.getElementById(user_id).getElementsByTagName("p");
			var tag_span = document.getElementById(user_id).getElementsByTagName("span");
			$("#"+user_id+"").remove();
			$("#friendsList").prepend(
				'<li class="active" id = "'+user_id+'" onclick="getRecord(this.id)">'+
				    '<img class="avatar" width="30" height="30"  src="assets/img/chatImage/candidate.jpg">'+ 
				        '<p class="name" id="'+tag_p[0].id+'">'+tag_p[0].innerHTML+'</p>'+
				        '<span class="per-counter"id="'+tag_span[0].id+'" style="display:none;"></span>'+
				'</li>');
			$("#"+user_id+"").css("background-color","rgb(167, 187, 201)");
		}
	}else{
		alert("失败");
	}
}
//获取当前时间
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();
    return currentdate;
}

function getFriendsList()
{
	document.getElementById("content").value = "";
	$("#sendMessage").attr({"disabled":"disabled"});
	$("#content").attr({"disabled":"disabled"});
	// $("#sendMessage").removeAttr("disabled");
	cVe.startReqByMsgHandle(cVeName,'','','reqGetFriendsList','resGetFriendsList','ECP.handle.WebChatRecordHandle.handleMsg');	
}

function reqGetFriendsList()
{
    var req = {};
    req["op"] = "getFriendsList";
    req["org_id"] = cVeUti.Cookie.getCookie("org_id");
    //req["org_id"] = "pctest";
    cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}
var friendsNum = 0;//好友个数
//var cUser_id = "";
function resGetFriendsList()
{
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var retData=retDataObj[cVe.cEioVeDataId];
	if(retData.result == "0"&&retData.rows.length>0){
		var friendsList = retData.rows;
		var nick_name = friendsList[0].nick_name;
		var uid = friendsList[0].uid;
		if(nick_name!="")
		{
			document.getElementById('myUser_name').innerHTML=nick_name;	
		}else
			{
				document.getElementById('myUser_name').innerHTML=uid;
			}
		
		for(var i=0; i<friendsList.length; i++)
		{
			
			if(typeof $("#"+friendsList[i].user_id+"").html()=="undefined")
			{
				friendsNum++;
				$("#friendsList").append(
						'<li class="active" id = "'+friendsList[i].user_id+'" onclick="getRecord(this.id)">'+
						    '<img class="avatar" width="30" height="30"  src="assets/img/chatImage/candidate.jpg">'+ 
						        '<p class="name" id="friend'+friendsNum+'"></p>'+
						        '<span class="per-counter"id="messageCounter'+friendsNum+'" style="display:none;"></span>'+
						'</li>');
				if(friendsList[i].name!="")
				{
				    document.getElementById('friend'+friendsNum+'').innerHTML=friendsList[i].name;
				}else
					{
					    document.getElementById('friend'+friendsNum+'').innerHTML = friendsList[i].user_id;
					}
				
				if(friendsList[i].msgCounter>0)
					{
					    $("#messageCounter"+friendsNum+"").css("display","inline-block");
					    document.getElementById('messageCounter'+friendsNum+'').innerHTML = friendsList[i].msgCounter;
					}
			}
			if(friendsList[i].is_read=="未读")
			{
		        $("#"+friendsList[i].user_id+"").css("background","#88CACB");		
			}
		}
		
	}/*else{
		alert("您当前还没有任何聊天好友。");
	}*/
	getLeaveMessageCounter();
}

//获取对应好友的聊天记录
var user_id = "";//对应好友的id
var cUser_id = "";
function getRecord(id)
{   
	$("#messageInputArea").css("display","block");
	$("#messageBox").css("display","block");
	$("#leaveMessageBox").css("display","none");
	user_id = id;
	if(cUser_id!=user_id)
	{
	    document.getElementById('content').value = "";
	    cUser_id =user_id;
	}
	cVe.startReqByMsgHandle(cVeName,'','','reqGetRecord','resGetRecord','ECP.handle.WebChatRecordHandle.handleMsg');	
}

function reqGetRecord()
{
  var req = {};
  req["op"] = "perUserChat_recodDetail";
  req["user_id"] = user_id;
  req["org_id"] = cVeUti.Cookie.getCookie("org_id");
  cVe.setConn(cServerUri,"POST", true, JSON.stringify(req)); 
}
//var recordNum = 0;//消息条数
function resGetRecord()
{
	if(typeof $("#chatItem0").html()!="undefined")
	{
	    for(var i=0;i<=messageNum;i++)
	    	$("#myLi"+i+"").remove();
	    messageNum=0;
	}
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var retData=retDataObj[cVe.cEioVeDataId];
	if(retData.result == "0"){
		var tag_li = document.getElementById("friendsList").getElementsByTagName("li");
		for(var i=0;i<tag_li.length;i++)
		{
			$("#"+tag_li[i].id+"").css("background-color","hsla(0,0%,100%,.1)");
		}
		$("#"+user_id+"").css("background-color","rgb(167, 187, 201)");
		var tag_span = document.getElementById(user_id).getElementsByTagName("span");//获取当前好友的未读提示的id
		$("#"+tag_span[0].id+"").css("display","none");		 
		var curCounter = document.getElementById(tag_span[0].id).innerHTML;
		var allCounter = cVeUti.Cookie.getCookie("msgCounters");
		var n = allCounter - curCounter;
		if(n>=0){
			cVeUti.Cookie.setCookie("msgCounters",n);
		}else{
			cVeUti.Cookie.setCookie("msgCounters","");
		}		
		document.getElementById(tag_span[0].id).innerHTML = "";//清除未读提示
		
		$("#sendMessage").removeAttr("disabled");
		//$("#content").removeAttr("disabled");
		document.getElementById("content").disabled = false;
		document.getElementById('content').focus();//获取光标
		var recordList = retData.rows;
		if(recordList[0].name!="")
		{
			document.getElementById("headerName").innerHTML=recordList[0].name;
		}else
			{
				document.getElementById("headerName").innerHTML=recordList[0].user_id;
			}
		
		document.getElementById("user_id").value=recordList[0].user_id;
		for(var i=0; i<recordList.length; i++)
		{
			if(recordList[i].msg_type=="to")
			{
				$("#messageList").append(
						'<li style="display:block" id="myLi'+messageNum+'"><p class="time"><span id="time'+messageNum+'"></span></p>'+
                        '<div class="main" style="text-align:right;width:100%;padding-right:40px">'+
                        '<div class="rightText"><p style="display:inline-block;margin-bottom:0px;margin-top:5px;line-height:20px"id="chatItem'+messageNum+'"></p><div class="rightArrow"></div>'+
                        '</div>'+
                        //'<div style="display:inline-block;float:right;position:absolute">'+
                            '<img class="avatar" style="display:inline-block;position:absolute" width="30" height="30" src="assets/img/chatImage/myPic1.jpg">'+
                        //'</div>'+
                     '</div>'+
               '</li> ');
			   document.getElementById('chatItem'+messageNum+'').innerHTML=recordList[i].chat_content;
			   document.getElementById('time'+messageNum+'').innerHTML=recordList[i].chat_time.split(".")[0];
			   messageNum++;
			}else if(recordList[i].msg_type=="from")
				{
					$("#messageList").append(
							'<li id="myLi'+messageNum+'"><p class="time"><span id="time'+messageNum+'"></span></p>'+
                            '<div class="main"><img class="avatar" width="30" height="30" src="assets/img/chatImage/candidate.jpg">'+                        
                            '<div class="leftText"><div class="leftArrow"></div><p style="line-height:20px;margin-top:5px;margin-bottom:0px;margin-right:0px;"id="chatItem'+messageNum+'"></p>'+
                             '</div>'+
                        '</div>'+
                  '</li>');
				   document.getElementById('chatItem'+messageNum+'').innerHTML=recordList[i].chat_content;
				   document.getElementById('time'+messageNum+'').innerHTML=recordList[i].chat_time.split(".")[0];
				   messageNum++;
				}
			
		}
		
	}/*else{
		msgbox("提示",2,"获取聊天列表失败");
	}*/
	var e=document.getElementById("messageBox");//使滚动条置底
	e.scrollTop=e.scrollHeight;
}
//求职详情聊天功能获取聊天记录
function getPerRecord(id)
{   
	user_id = id;
	cVe.startReqByMsgHandle(cVeName,'','','reqGetPerRecord','resGetPerRecord','ECP.handle.WebChatRecordHandle.handleMsg');	
}

function reqGetPerRecord()
{
  var req = {};
  req["op"] = "perUserChat_recodDetail";
  req["user_id"] = user_id;
  req["org_id"] = cVeUti.Cookie.getCookie("org_id");
  cVe.setConn(cServerUri,"POST", true, JSON.stringify(req)); 
}
//var recordNum = 0;//消息条数
function resGetPerRecord()
{
	if(typeof $("#chatItem0").html()!="undefined")
	{
	    for(var i=0;i<=messageNum;i++)
	    	$("#myLi"+i+"").remove();
	    messageNum=0;
	}
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var retData=retDataObj[cVe.cEioVeDataId];
	if(retData.result == "0"){
		var recordList = retData.rows;
		//document.getElementById("headerName").innerHTML=recordList[0].user_name;
		if(recordList.length>0)
		{
		    $("#initInfo").css("display","none");
		}
		for(var i=0; i<recordList.length; i++)
		{
			if(recordList[i].msg_type=="to")
			{
				$("#chatContent").append(
						'<li style="list-style-type: none" id="myLi'+messageNum+'"><p class="time"><span id="time'+messageNum+'"></span></p>'+
                        '<div class="main" style="text-align:right;width:100%;padding-right:40px">'+
                        '<div class="rightText"><p style="display:inline-block;margin-bottom:0px;margin-top:5px;line-height:20px"id="chatItem'+messageNum+'"></p><div class="rightArrow"></div>'+
                        '</div>'+
                        '<div style="display:inline-block;float:right;position:absolute">'+
                            '<img class="avatar" width="30" height="30" src="assets/img/chatImage/myPic1.jpg">'+
                        '</div>'+
                     '</div>'+
               '</li> ');
			   document.getElementById('chatItem'+messageNum+'').innerHTML=recordList[i].chat_content;
			   document.getElementById('time'+messageNum+'').innerHTML=recordList[i].chat_time.split(".")[0];
			   messageNum++;
			}else if(recordList[i].msg_type=="from")
				{
					$("#chatContent").append(
							'<li style="list-style-type: none" id="myLi'+messageNum+'"><p class="time"><span id="time'+messageNum+'"></span></p>'+
                            '<div class="main"><img class="avatar" width="30" height="30" src="assets/img/chatImage/candidate.jpg">'+                        
                            '<div class="leftText"><div class="leftArrow"></div><p style="line-height:20px;margin-top:5px;margin-bottom:0px;margin-right:0px;"id="chatItem'+messageNum+'"></p>'+
                             '</div>'+
                        '</div>'+
                  '</li>');
				   document.getElementById('chatItem'+messageNum+'').innerHTML=recordList[i].chat_content;
				   document.getElementById('time'+messageNum+'').innerHTML=recordList[i].chat_time;
				   messageNum++;
				}
			var e=document.getElementById("chatContent");//使滚动条置底
			e.scrollTop=e.scrollHeight;
			//document.getElementById('perContent').value = "";
		}
		var n = retData.msgCounter//当前用户的未读信息条数
		var allCounter = cVeUti.Cookie.getCookie("msgCounters");
		if(allCounter - n > 0)
		{
			cVeUti.Cookie.setCookie("msgCounters",allCounter - n);
			$("#messageCounter").css("display","inline-block");
			document.getElementById("messageCounter").innerHTML = cVeUti.Cookie.getCookie("msgCounters");	
		}else{
			cVeUti.Cookie.setCookie("msgCounters",0);
			$("#messageCounter").css("display","none");
			 document.getElementById("messageCounter").innerHTML = 0;
		}
	}
	/*var e=document.getElementById("messageBox");//使滚动条置底
	e.scrollTop=e.scrollHeight;*/
}
//获取留言
var leaveMessageNum = 0;
function getLeaveMessage()
{
	$("#messageInputArea").css("display","none");
	$("#messageBox").css("display","none");
	$("#leaveMessageBox").css("display","block");
	document.getElementById('headerName').innerHTML = "留言板";
	if(document.getElementById('leaveMessageCounter').innerHTML!=0)
	{
		var orgMsgCounters = cVeUti.Cookie.getCookie("msgCounters");
		var leaveMessageCounter = document.getElementById('leaveMessageCounter').innerHTML;
		cVeUti.Cookie.setCookie("msgCounters",orgMsgCounters-leaveMessageCounter);
		$("#leaveMessageCounter").css("display","none");
	    document.getElementById('leaveMessageCounter').innerHTML = 0;
	}
	
	cVe.startReqByMsgHandle(cVeName,'','','reqGetLeaveMessage','resGetLeaveMessage','ECP.handle.WebChatRecordHandle.handleMsg');	
}

function reqGetLeaveMessage()
{
	var req = {};
	req["op"] = "getLeaveMessage";
	req["org_id"] = cVeUti.Cookie.getCookie("org_id");
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req)); 
}

function resGetLeaveMessage()
{
	if(typeof $("#leaveLi0").html()!="undefined")
	{
	    for(var i=0;i<leaveMessageNum;i++)
	    	$("#leaveLi"+i+"").remove();
	    leaveMessageNum=0;
	}
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var retData=retDataObj[cVe.cEioVeDataId].data;
	if(retData.length>0)
	{
		for(var i=0;i<retData.length;i++)
		{
			$("#leaveMessageList").append(
				'<li id="leaveLi'+leaveMessageNum+'"style="background-color: rgb(167, 187, 201);border-radius:6px;padding:8px;margin-top:5px;">'+
            	'<p ><span >姓名：</span><span id="leave_Name'+leaveMessageNum+'"></span></p>'+
            	'<p ><span >岗位：</span></span><span id="leave_jobName'+leaveMessageNum+'"></span></p>'+                                                     	
            	'<p ><span >时间：</span><span id="leave_time'+leaveMessageNum+'"></span></p>'+
            	'<p ><span >内容：</span><span id="leave_content'+leaveMessageNum+'"></span></p>'+
            '</li>');
			if(retData[i].name=="")
			{
				document.getElementById('leave_Name'+leaveMessageNum+'').innerHTML = retData[i].uid;
			}else{
				document.getElementById('leave_Name'+leaveMessageNum+'').innerHTML = retData[i].name;
			}		   
		   document.getElementById('leave_jobName'+leaveMessageNum+'').innerHTML = retData[i].job_name;
		   document.getElementById('leave_time'+leaveMessageNum+'').innerHTML = retData[i].leave_time;
		   document.getElementById('leave_content'+leaveMessageNum+'').innerHTML = retData[i].leave_content;
		   leaveMessageNum++;
	    }
		var e=document.getElementById("leaveMessageBox");//使滚动条置底
		e.scrollTop=e.scrollHeight;
	}
	
}
//获取未读留言信息的条数
function getLeaveMessageCounter()
{
	cVe.startReqByMsgHandle(cVeName,'','','reqGetLeaveMessageCounter','resGetLeaveMessageCounter','ECP.handle.WebChatRecordHandle.handleMsg');	
}

function reqGetLeaveMessageCounter()
{
	var req = {};
	req["op"] = "leaveMessageCounter";
	req["org_id"] = cVeUti.Cookie.getCookie("org_id");
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req)); 
}

function resGetLeaveMessageCounter()
{
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var retData=retDataObj[cVe.cEioVeDataId].leaveMessageCounter;
	if(retData>0)
	{	
	   $("#leaveMessageCounter").css("display","block");
	   document.getElementById('leaveMessageCounter').innerHTML = retData;   
	}	
}