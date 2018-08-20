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

function logout(){
	cVeUti.Cookie.delCookie("job_apply_id");	
	cVeUti.Cookie.delCookie("page_id");
	cVeUti.Cookie.delCookie("org_id");	
	cVeUti.Cookie.delCookie("org_name");
	cVeUti.Cookie.delCookie("nick_name");
	cVeUti.Cookie.delCookie("msgCounters");
	 cVeUti.Cookie.delCookie("msgCounter");
	 cVeUti.Cookie.delCookie("applysCount");
	 cVeUti.Cookie.delCookie("user_id");
	 cVeUti.Cookie.delCookie("lastOperTime");
	window.location.href="login.jsp";
}

function toPage(id){
	var org_id = cVeUti.Cookie.getCookie("org_id");
	var integrity = cVeUti.Cookie.getCookie("integrity");
	if(org_id=="")
	{
        alert("请完善您的店站信息");
        window.location.href="myStoreListPage.html";
	}else if(integrity==""||integrity<=0){
    	alert("您的店站信息正在审核当中，请耐心等候");
    }else{
			cVeUti.Cookie.setCookie("page_id",id);
			//cVeUti.Cookie.setCookie("applysCount",0);
			window.location.href="unprocessed.html";			
		}	
}