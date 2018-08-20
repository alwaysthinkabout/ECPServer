var Request = new Object();
	Request = GetRequest();
var room_id =Request["room_id"];
$(function(){
	getRoomInfo();
});
function goBack(){
	window.location.href = "homePage.html";
}
//获取聊天室信息
function getRoomInfo(){
	var data = {
			handle:"wechat.handle.RoomHandle.handleMsg",
			reqData : {
				op : "getRoomList",
				room_id: room_id
			}
		};
	$.ajax({
		url:"/WeChat/servlet/WeChatRouter",
		type : 'post',
		data:JSON.stringify(data),  			  			
		success : function(result){
			var data = JSON.parse(result);
			if(data.result == "success"){
				var array = data.array;
				//alert(array[0].room_name);
				document.getElementById('room_name').innerHTML = array[0].room_name;
		    }
		}
	});
}
function sendMessage(){
	var msg = document.getElementById("msg").value;
	var user_id = getUser_id();
	var user_name = getUser_name();
	var room_id = document.getElementById("room_id").value;
	if(msg == ""){
		alert("发送内容不能为空！");
		return;
	}
	var data = {
			handle:"wechat.handle.MessageHandle.handleMsg",
			reqData : {
				user_id : user_id,
				user_name : user_name,
				room_id : room_id,
				msg : msg,
				op : "sendToRoom" 
			}
		};
	$.ajax({
		url:"/WeChat/servlet/WeChatRouter",
		type : 'post',
		data:JSON.stringify(data),  			
		//contentType: "application/json; charset=utf-8",
		//dataType : 'json',   			
		success : function(result){
			var data = JSON.parse(result);
			if(data.result == "success"){
				$("#messageList").append('<li  class="myMessageLi" id="myLi'+messageNum+'">'+
	    				'<p class="time"><span id="time'+messageNum+'"></span></p>'+
	                	'<div  style="width:100%;position:relative;padding-right:3rem;">'+
	                		'<div style="float:right;width:12%;">'+
			                    '<img  style="border-radius:11rem" id = "userlogo'+messageNum+'" width="40" height="40" src="../assets/imgs/loginLogo.jpg">'+
			                '</div>'+
	                		'<div style="float:right;padding-right:1rem;text-align:right;width:75%">'+
		               			'<div style="padding:0.2rem">'+
		                   	 		'<span style="color:#7B7B7B"id="user_name'+messageNum+'">lady</span>'+
		                   	 	'</div>'+
		                		'<div class="rightText">'+
		                			'<p style="text-align:left;display:inline-block;margin-bottom:0px;margin-top:5px;line-height:20px"id="chatItem'+messageNum+'"></p>'+
		                			'<div class="rightArrow"></div>'+
		                		'</div>'+
	                		'</div>'+
	                		'<div style="clear:both"></div>'+		                
		             	'</div>'+
	      			 '</li> ');
				document.getElementById('chatItem'+messageNum+'').innerHTML = document.getElementById("msg").value;
				document.getElementById('time'+messageNum+'').innerHTML = getTime();
				document.getElementById('user_name'+messageNum+'').innerHTML = getUser_name();
				var picture = getUser_picture();
				if(picture != ""){
					document.getElementById('userlogo'+messageNum+'').src =  "../assets/usersImages/" + picture;
				}
				
				messageNum++;
				var e=document.getElementById("content");//使滚动条置底
				e.scrollTop=e.scrollHeight;
				document.getElementById("msg").value = "";
			}else{
				alert("发送失败，请稍后再试！");
			}
		}
	});
	
}

function getRoom_Users(){
	var data = {
			handle:"wechat.handle.RoomHandle.handleMsg",
			reqData : {
				op : "getRoom_UsersInfo",
				room_id: room_id
			}
		};
	$.ajax({
		url:"/WeChat/servlet/WeChatRouter",
		type : 'post',
		data:JSON.stringify(data),  			  			
		success : function(result){
			var data = JSON.parse(result);
			if(data.result == "success"){
				var array = data.array;
				var len = array.length;
				if(len <= 1){
					alert("本聊天室内只有您一位用户哦");
				}else{
					$("#users").empty();
					var user_id = getUser_id();
					var userNums = 0;
					for(var i = 0; i < len; i++){
						if(array[i].user_id != user_id){
							$("#users").append('<div class = "room_peruser_div" onclick="goPersonChat(this.id)" id = '+array[i].user_id+","+array[i].user_name+","+array[i].picture+'>'+
				    			'<img id="userImage'+userNums+'"alt="头像" src="../assets/imgs/loginLogo.jpg" width="80rem" height="50rem" class = "room_peruser_img">'+
				    			'<span id="userName'+userNums+'" style="display:block"></span>'+
				    			//'<input id="userId'+userNums+'style="display:none" />'+
							'</div>');
							document.getElementById('userName'+userNums+'').innerHTML = array[i].user_name;
							if(array[i].picture != "")
								document.getElementById('userImage'+userNums+'').src = "../assets/usersImages/" + array[i].picture;
							userNums++;
						}
					}
				}
				//alert(array[0].room_name);
				//document.getElementById('room_name').innerHTML = array[0].room_name;
		    }else{
		    	alert("获取在线好友信息失败");
		    }
		}
	});
}

/*
 * 参数id是user_id和user_name的组合
 */
function goPersonChat(id){
	var array = id.split(",");
	if(array.length < 3){
		alert("无法和该用户聊天，请刷新页面。");
		return;
	}
	var user_id = array[0];
	var user_name = array[1];
	var picture = array[2];
	window.location.href = "chat.html?room_id=" + room_id + "&user_id=" + user_id + "&user_name=" + user_name + "&picture" + picture;
}