var Request = new Object();
	Request = GetRequest();
var room_id =Request["room_id"];
var toUser_id = Request["user_id"];
var toUser_name = Request["user_name"];
$(function(){
	document.getElementById("user_name").innerHTML = toUser_name;
});
function goBack(){
	window.location.href = "room.html?room_id=" + room_id;
}
function sendToPerson(){
	var msg = document.getElementById("msg").value;
	var user_id = getUser_id();
	var user_name = getUser_name();
	var picture = getUser_picture();
	if(msg == ""){
		alert("发送内容不能为空！");
		return;
	}
	var data = {
			handle:"wechat.handle.MessageHandle.handleMsg",
			reqData : {
				fromUser_id : user_id,
				toUser_id: toUser_id,
				user_name : user_name,
				picture: picture,
				room_id : "",
				msg : msg,
				op : "sendToPerson" 
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
				//var picture = getUser_picture();
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