$(function(){
	getRoomList();
	document.getElementById("user_name").innerHTML = getUser_name();
	var picture = getUser_picture();
	if(picture != "") document.getElementById("userlogo").src = "../assets/usersImages/" + picture;
});
function goRoom(id){
	window.location.href = "room.html?room_id="+id;
}
function showRoomModal(){
	$('#roomName').modal('show');
}

function createRoom(){
	var room_name = document.getElementById("room_name").value;
	var user_id = getUser_id();
	if(room_name == ""){
		alert("请输入聊天室名称！");
		return;
	}
	var data = {
			handle:"wechat.handle.RoomHandle.handleMsg",
			reqData : {
				room_name : room_name,
				user_id : user_id,
				op : "createRoom" 
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
				alert(data.resultTip);
				document.getElementById("room_name").value = "";
				$('#roomName').modal('hide');
				getRoomList();
				//window.location.href = "homePage.html";
			}else{
				alert("创建失败，请稍后再试。");
				document.getElementById("room_name").value = "";
				$('#roomName').modal('hide');
			}
		}
	});
}

function getRoomList(){
	var data = {
			handle:"wechat.handle.RoomHandle.handleMsg",
			reqData : {
				op : "getRoomList" 
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
			var user_id = getUser_id();
			if(data.result == "success"){
				var array = data.array;
				var myRoomCounter = 0;//聊天室个数记录
				var otherRoomCounter = 0;
				$("#myRoomList").empty();
				$("#otherRoomList").empty();
				$("#myRoomList").append('<li class="list-group-item active">我的聊天室<span id="myRoomCounter"style="margin-left:3%"></span></li>');
				$("#otherRoomList").append('<li class="list-group-item active" style="background-color:#FF7575;border-color:#FF7575">其他聊天室<span id="otherRoomCounter"style="margin-left:3%"></span></li>');
				if(array.length > 0){
					for(var i = 0, len = array.length; i < len; i++){
						if(array[i].user_id == user_id){
							myRoomCounter++;
							$("#myRoomList").append('<li id='+array[i].room_id+' onclick="goRoom(this.id)" class="list-group-item">'+
													 '<span class="badge"></span>'+
													 ''+array[i].room_name+'</li>');
						}else{
							otherRoomCounter++;
							$("#otherRoomList").append('<li id='+array[i].room_id+' onclick="goRoom(this.id)" class="list-group-item">'+
													   '<span class="badge"></span>'+
													   ''+array[i].room_name+'</li>');
						}
						
					}
				}
				if(myRoomCounter <= 10){
					$("#myRoomList").append('<li onclick="showRoomModal()" class="list-group-item">新建聊天室</li>');
				}
				if(otherRoomCounter <= 0 ){
					$("#otherRoomList").append('<li  class="list-group-item">暂时没有发现其他聊天室</li>');
				}
				document.getElementById("myRoomCounter").innerHTML = "(共有"+myRoomCounter+"个聊天室)";
				document.getElementById("otherRoomCounter").innerHTML = "(共有"+otherRoomCounter+"个聊天室)";
			}
		}
	});
}