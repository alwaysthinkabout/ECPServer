<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<ul class="breadcrumb" id="breadcrumb">
	<li id="pageLi1"><i class="fa fa-home"></i><a id="pageUrl1" onclick="backPage1()"></a></li>
	<%-- <s:iterator value="#request.breadCrumb" status="st">
		<s:if test="#st.last">
			<li class="active"><s:property value="[0].top[0]"/></li>
		</s:if><s:else>
			<li><a href='<s:property value="[0].top[1]"/>'><s:property value="[0].top[0]"/></a></li>
		</s:else>
	</s:iterator> --%>
</ul>
<script src="../../assets/js/ve1.js"></script>
<script src="../../assets/js/veHelper.js"></script>
<script src="../../assets/js/simpleUtil.js"></script>
<script>
var pageUrl1=cVeUti.Cookie.getCookie("pageUrl1");
var pageName1=cVeUti.Cookie.getCookie("pageName1");
var pageUrl2=cVeUti.Cookie.getCookie("pageUrl2");
var pageName2=cVeUti.Cookie.getCookie("pageName2");
var pageUrl3=cVeUti.Cookie.getCookie("pageUrl3");
var pageName3=cVeUti.Cookie.getCookie("pageName3");
var pageUrl4=cVeUti.Cookie.getCookie("pageUrl4");
var pageName4=cVeUti.Cookie.getCookie("pageName4");
function backPage1(){
	window.location.href=pageUrl1;
	cVeUti.Cookie.delCookie("pageUrl2");
	cVeUti.Cookie.delCookie("pageName2");
	cVeUti.Cookie.delCookie("pageUrl3");
	cVeUti.Cookie.delCookie("pageName3");
	cVeUti.Cookie.delCookie("pageUrl4");
	cVeUti.Cookie.delCookie("pageName4");
};
document.getElementById("pageUrl1").text=pageName1;
if(pageUrl2!=""){
	$("#breadcrumb").append('<li id="pageLi2"><a id="pageUrl2" onclick="backPage2()"></a></li>');
	document.getElementById("pageUrl2").text=pageName2;
}
if(pageUrl3!=""){
	$("#breadcrumb").append('<li id="pageLi3"><a id="pageUrl3" onclick="backPage3()"></a></li>');
	document.getElementById("pageUrl3").text=pageName3;
}
if(pageUrl4!=""){
	$("#breadcrumb").append('<li id="pageLi4"><a id="pageUrl4" onclick="backPage4()"></a></li>');
	document.getElementById("pageUrl4").text=pageName4;
}
function backPage2(){
	window.location.href=pageUrl2;
	cVeUti.Cookie.delCookie("pageUrl3");
	cVeUti.Cookie.delCookie("pageName3");
	cVeUti.Cookie.delCookie("pageUrl4");
	cVeUti.Cookie.delCookie("pageName4");
}
function backPage3(){
	window.location.href=pageUrl3;
	cVeUti.Cookie.delCookie("pageUrl4");
	cVeUti.Cookie.delCookie("pageName4");
}
</script>
