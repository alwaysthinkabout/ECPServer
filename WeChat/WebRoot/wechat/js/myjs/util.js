var url = window.location.pathname;
var currentHtml = url.split('/')[url.split('/').length-1];
var webSocketIp = "125.216.242.231";
var port = "8080";
var messageNum = 0;//记录聊天记录条数
var users;

/*
 * websocket
 */
var socket
if(currentHtml != "login.html" && currentHtml != "register.html"){
	socket = new WebSocket('ws://'+webSocketIp+':'+port+'/WeChat/websocketTest');
	socket.onopen = function(event){
		var data = {};
		if(currentHtml == "room.html"){
			var Request = new Object();
			Request = GetRequest();
			var room_id =Request["room_id"];
			document.getElementById("room_id").value = room_id;
			data["room_id"] = room_id;
		}else{
			data["room_id"] = "";
		}
		data["user_id"] = getUser_id();
		data["user_name"] = getUser_name();
		socket.send(JSON.stringify(data));
	}
	socket.onclose = function(event) { 
		socket.close();
	};
	socket.onmessage = function(event){
		var data = JSON.parse(event.data);
		if(currentHtml == "room.html"&&data.op!="sendToPerson"){
			if(data.msg == "online_count"){
				document.getElementById('online_count').innerHTML = data.online_count + "人在线 ";
				users = data.users;//本聊天室所有成员
			}else{
				$("#messageList").append('<li class="myMessageLi'+messageNum+'">'+
	    				'<p class="time"><span id="time'+messageNum+'"></span></p>'+
	                    '<div style="position:relative;margin-left:-1.5rem">'+
	                    	 '<div style="float:left;width:12%;">'+
	                    	 	'<img  style="border-radius:11rem"width="40rem" height="40rem" id = "userlogo'+messageNum+'" src="../assets/imgs/loginLogo.jpg">'+
	                    	 '</div>'+
	                    	 '<div style="float:left;width:75%;margin-left:1.5rem;margin-top:0;">'+	      
	                    	 	'<div>'+
	                    	 		'<span style="color:#7B7B7B" id="user_name'+messageNum+'">lady</span>'+
	                    	 	'</div>'+	                                       
		                        '<div class="leftText">'+
		                            '<div class="leftArrow"></div>'+
		                            '<p style="line-height:20px;margin-top:5px;margin-bottom:0px;"id="chatItem'+messageNum+'"></p>'+
		                        '</div>'+
	                         '</div>'+
	                         '<div style="clear:both"></div>'+
	                     '</div>'+
	    			'</li>');
			
				document.getElementById('chatItem'+messageNum+'').innerHTML = data.msg;
				document.getElementById('time'+messageNum+'').innerHTML = getTime();
				document.getElementById('user_name'+messageNum+'').innerHTML = data.user_name;
				if(data.picture != ""){
					document.getElementById('userlogo'+messageNum+'').src =  "../assets/usersImages/" + data.picture;
				}
				
				messageNum++;
				var e=document.getElementById("content");//使滚动条置底
				e.scrollTop=e.scrollHeight;
			}
		}else if(currentHtml == "chat.html"&& data.op == "sendToPerson"){
			$("#messageList").append('<li class="myMessageLi'+messageNum+'">'+
    				'<p class="time"><span id="time'+messageNum+'"></span></p>'+
                    '<div style="position:relative;margin-left:-1.5rem">'+
                    	 '<div style="float:left;width:12%;">'+
                    	 	'<img  style="border-radius:11rem"width="40rem" height="40rem" id = "userlogo'+messageNum+'" src="../assets/imgs/loginLogo.jpg">'+
                    	 '</div>'+
                    	 '<div style="float:left;width:75%;margin-left:1.5rem;margin-top:0;">'+	      
                    	 	'<div>'+
                    	 		'<span style="color:#7B7B7B" id="user_name'+messageNum+'">lady</span>'+
                    	 	'</div>'+	                                       
	                        '<div class="leftText">'+
	                            '<div class="leftArrow"></div>'+
	                            '<p style="line-height:20px;margin-top:5px;margin-bottom:0px;"id="chatItem'+messageNum+'"></p>'+
	                        '</div>'+
                         '</div>'+
                         '<div style="clear:both"></div>'+
                     '</div>'+
    			'</li>');
		
			document.getElementById('chatItem'+messageNum+'').innerHTML = data.msg;
			document.getElementById('time'+messageNum+'').innerHTML = getTime();
			document.getElementById('user_name'+messageNum+'').innerHTML = data.user_name;
			if(data.picture != ""){
				document.getElementById('userlogo'+messageNum+'').src =  "../assets/usersImages/" + data.picture;
			}
			
			messageNum++;
			var e=document.getElementById("content");//使滚动条置底
			e.scrollTop=e.scrollHeight;
		}
	};
}

function setCookie(cName, cValue, cDays) {
	var date = new Date();
	var cookieStr = "";
	//如果保存天数未设置，则默认关闭浏览器或窗口即失效
	if (cDays==undefined||Number(cDays)!=cDays) {
		//cDays=1;
		cookieStr = escape(cName) + "=" + escape(cValue) + "; path=/;";
	}else{
		date.setTime(date.getTime() + cDays * 24 * 3600 * 1000);
		//var cookieStr = escape(cName) + "=" + escape(cValue) + "; expires=" + date.toGMTString() + ";";
		cookieStr = escape(cName) + "=" + escape(cValue) + "; expires=" + date.toGMTString() + "; path=/;";
	}
	document.cookie = cookieStr;
}
//根据cookie名取得其值
function getCookie(cName) {
	var cookieStr = document.cookie;
	var cookies = cookieStr.split("; ");
	for (var i = 0; i < cookies.length; i++) {
		var cookie = cookies[i];
		var theCookie = cookie.split("=");
		var theValue = unescape(theCookie["1"]);
		if (theValue == "undefined" || theValue == null) {
			theValue = "";
		}
		if (theCookie["0"] == escape(cName)) {
			return theValue;
			break;
		}
	}
	return "";
}
//根据cookie名删除
function delCookie(cName) {
	var date = new Date();
	date.setTime(date.getTime() - 1000);
	//var cookieStr = escape(cName) + "=; expires=" + date.toGMTString() + ";";
	var cookieStr = escape(cName) + "=; expires=" + date.toGMTString() +"; path=/;";
	document.cookie = cookieStr;
}

function GetRequest() {
	 var url = location.search; //获取url中"?"符后的字串
	 var theRequest = new Object();
	 if (url.indexOf("?") != -1) {
	    var str = url.substr(1);
	    strs = str.split("&");
	    for(var i = 0; i < strs.length; i ++) {
	    	//键值对的形式，使用 unescape() 来解码字符串
	       	theRequest[strs[i].split("=")[0]]=decodeURI(strs[i].split("=")[1]);
   	}
	}
	return theRequest;
}

function getUser_name(){
	var user_info = getCookie("user_info");
	if(user_info != "" && user_info.split(",").length > 0){
		return user_info.split(",")[0];
	}else{
		return "";
	}
		
}

function getUser_id(){
	var user_info = getCookie("user_info");
	if(user_info != "" && user_info.split(",").length > 1){
		return user_info.split(",")[1];
	}else{
		return "";
	}
}

function getUser_picture(){
	var user_info = getCookie("user_info");
	if(user_info != "" && user_info.split(",").length > 1){
		return user_info.split(",")[2];
	}else{
		return "";
	}
}

//获取当前日期 只包含年月日
function getNowFormat_Date() {
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

//获取当前时间 包括年月日时分秒
function getNowFormat_time() {
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

//获取当前时间 只包含时分秒
function getTime() {
    var date = new Date();
    var seperator = ":";
    var minutes = date.getMinutes();
    var seconds = date.getSeconds();
    if(minutes < 10){
    	minutes = "0" + minutes;
    }
    if(seconds < 10){
    	seconds = "0" + seconds;
    }
    var currentdate = date.getHours() + seperator + minutes
            + seperator + seconds;
    return currentdate;
}

function logout(){
	delCookie("user_info");
	window.location.href = "login.html";
}

function sendHeartBeat(){
	socket.send("HeartBeat");
	setTimeout(sendHeartBeat,3*1000);
}