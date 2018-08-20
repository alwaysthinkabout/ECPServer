function openForList(obj){
	if(obj=="menuForEnterprise"){
		if($("#subMenuForEnterprise").hasClass("open")){
			$("#subMenuForEnterprise").removeClass("open");
			$("#iconForEnterprise").removeClass("fa-angle-down");
			$("#iconForEnterprise").addClass("fa-angle-left");
		}
		else{
			$("#subMenuForEnterprise").addClass("open");
			$("#iconForEnterprise").removeClass("fa-angle-left");
			$("#iconForEnterprise").addClass("fa-angle-down");
		}
	}
	if(obj=="menuForJobHunter"){
		if($("#subMenuForJobHunter").hasClass("open")){
			$("#subMenuForJobHunter").removeClass("open");
			$("#iconForJobHunter").removeClass("fa-angle-down");
			$("#iconForJobHunter").addClass("fa-angle-left");
		}
		else{
			$("#subMenuForJobHunter").addClass("open");
			$("#iconForJobHunter").removeClass("fa-angle-left");
			$("#iconForJobHunter").addClass("fa-angle-down");
		}
	}
	if(obj=="menuForStore"){
		if($("#subMenuForStore").hasClass("open")){
			$("#subMenuForStore").removeClass("open");
			$("#iconForStore").removeClass("fa-angle-down");
			$("#iconForStore").addClass("fa-angle-left");
		}
		else{
			$("#subMenuForStore").addClass("open");
			$("#iconForStore").removeClass("fa-angle-left");
			$("#iconForStore").addClass("fa-angle-down");
		}
	}
	if(obj=="menuForRole"){
		if($("#subMenuForRole").hasClass("open")){
			$("#subMenuForRole").removeClass("open");
			$("#iconForRole").removeClass("fa-angle-down");
			$("#iconForRole").addClass("fa-angle-left");
		}
		else{
			$("#subMenuForRole").addClass("open");
			$("#iconForRole").removeClass("fa-angle-left");
			$("#iconForRole").addClass("fa-angle-down");
		}
	}
	if(obj=="menuForUser"){
		if($("#subMenuForUser").hasClass("open")){
			$("#subMenuForUser").removeClass("open");
			$("#iconForUser").removeClass("fa-angle-down");
			$("#iconForUser").addClass("fa-angle-left");
		}
		else{
			$("#subMenuForUser").addClass("open");
			$("#iconForUser").removeClass("fa-angle-left");
			$("#iconForUser").addClass("fa-angle-down");
		}
	}
}

function toggle() {
	if($("leftMenu").hasClass("minified")){
		$(".left-sidebar").removeClass("minified"), 
		$(".content-wrapper").removeClass("expanded"), 
		$(".sidebar-minified").find("i.fa-angle-left").toggleClass("fa-angle-right");
		$(window).resize();
	}else{
		$(".left-sidebar").addClass("minified"), 
		$(".content-wrapper").addClass("expanded"), 
//		$("#leftMenu").css("display", "none");
//		document.getElementById("leftMenu").style.visibility="hidden";//隐藏
		$(".left-sidebar .sub-menu").css("display", "none").css("overflow", "hidden"), 
		$(".sidebar-minified").find("i.fa-angle-left").toggleClass("fa-angle-right");
		$(window).resize();
	}
};

/*function toEnterpriseInfoAuditListPage(){
	window.location.href="../../content/enterpriseInfo/enterpriseInfoAuditList.jsp";
	cVeUti.Cookie.setCookie("pageUrl2","../../content/enterpriseInfo/enterpriseInfoAuditList.jsp");
	cVeUti.Cookie.setCookie("pageName2","企业信息审核列表");
}*/