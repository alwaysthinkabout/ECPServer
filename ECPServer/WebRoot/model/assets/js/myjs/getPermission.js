function getUserId(cName) 
{
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
var user_id = getUserId("user_id");
if(user_id=="")
{
	alert("没有访问权限，请先登录");
	window.location.href="login.jsp";
} 