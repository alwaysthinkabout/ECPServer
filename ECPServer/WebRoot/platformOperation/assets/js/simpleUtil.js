function GetRequest() {
	 var url = location.search; //获取url中"?"符后的字串
	 var theRequest = new Object();
	 if (url.indexOf("?") != -1) {
	    var str = url.substr(1);
	    strs = str.split("&");
	    for(var i = 0; i < strs.length; i ++) {
	    	//键值对的形式，使用 unescape() 来解码字符串
	       	theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
    	}
	}
	return theRequest;
}

function getMessages(){
	$.ajax({
		url : "countNotReadMessage.ajax?",
		type : 'post',
		dataType : 'json',
		success : function(data){
			if (data != 0) {
				$("#msgnum").html(
						"<span class='nav-counter'>" + data
								+ "</span>");
			}
		 }
	  }); 
//    setTimeout("getMessage()",1000);
}
/*
$(document).ready(function(){
      getMessages();
});*/

function relocationMessagePage(){
	window.location.href="../../content/common/messagePage.jsp";
}


function toPage(id){
	//alert(cVeUti.Cookie.getCookie("page_id"));
	window.location.href="unprocessed.jsp";
}

