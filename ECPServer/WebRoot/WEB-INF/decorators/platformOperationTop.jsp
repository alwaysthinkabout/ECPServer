<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>

<div class="navbar navbar-default top-bar" role="navigation">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <!-- responsive menu bar icon -->
        <a href="javascript:void(0);" class="hidden-md hidden-lg main-nav-toggle">
            <i class="fa fa-bars"></i>
        </a>
        <!-- end responsive menu bar icon -->
        <a class="navbar-brand" href="#">
            <img src="../../assets/img/logo4.png" alt='金英台运营管理系统' class="hidden-xs-my">
            <span>金英台运营管理系统</span>
        </a>
        <!-- logged user and the menu -->
        <div class="logged-user navbar-brand-right">
              <!--  <div class="btn-group" style="margin-right:20px;">
					<a class="btn btn-link nav-link" title="消息管理" onclick="relocationMessagePage()">
						<i class="fa fa-envelope"></i>
						<div id="msgnum"></div>
					</a>
				</div> -->
            <div class="btn-group">
                <a href="#" class="btn btn-link dropdown-toggle" data-toggle="dropdown">
                    <i class="fa fa-user"></i>
                    <span class="name hidden-xs-my"><label id="user_id"></label></span>
                    &nbsp;&nbsp;&nbsp;&nbsp; <span class="caret"></span>
                </a>
                <ul class="dropdown-menu" role="menu">
                    <li>
                        <a onclick="userSetting()">
                            <i class="fa fa-user"></i>
                            <span class="text">个人中心</span>
                        </a>
                    </li>
                    <li>
                        <a onclick="logout()">
                            <i class="fa fa-power-off"></i>
                            <span class="text">退出</span>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
        <!-- end logged user and the menu -->
    </div>
</div><!-- /top -->

<script src="../../assets/js/ve1.js"></script>
<script src="../../assets/js/veHelper.js"></script>
<script src="../../assets/js/simpleUtil.js"></script>
<script>
var loginUserName=cVeUti.Cookie.getCookie("loginUserName");
var user_id=cVeUti.Cookie.getCookie("userId");
$("#user_id").text(user_id);

function userSetting(){
	window.location.href="../../content/common/userSettingPage.jsp";
}

function logout(){	
	cVeUti.Cookie.delCookie("nick_name");
	cVeUti.Cookie.delCookie("userId");
	cVeUti.Cookie.delCookie("lastOperTime");
	cVeUti.Cookie.delCookie("loginUserName");
	window.location.href="../../login.jsp";
}

</script>