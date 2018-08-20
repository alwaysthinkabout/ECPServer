function is360Bro(){
	var is360 = false;
	var nav = window.navigator;
	alert(nav.userAgent);
    if(nav.userAgent.indexOf("WOW") > 0){  
        is360 = true;  
    } 
    return is360
}
/*if(is360Bro()){
	alert("检测到您可能使用的是360浏览器或IE，为了保证能顺利访问本网站，请您务必将360浏览器升级到最新版本，推荐使用使用谷歌、火狐浏览器访问。");
}*/
