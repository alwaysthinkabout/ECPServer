<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- main-nav -->
<head>
<meta charset="utf-8" />
</head>
<body>
 	<nav class="navbar-default navbar-side" role="navigation">
         <div class="sidebar-collapse">
             <ul class="nav" id="main-menu">
                 <li>
                     <div class="user-img-div">
                         <img src="assets/img/user.png" class="img-thumbnail" />

                         <div class="inner-text">
                             Jhon Deo Alex
                         <br/>
                             <small>Last Login : 2 Weeks Ago </small>
                         </div>
                     </div>

                 </li>
                 <li>
                     <a href="myStoreListPage.jsp" id="myStoreListPage" onclick="clickAddClass(this.id)"><i class="fa fa-desktop "></i>招聘站设置</a>
                     <!--  <ul class="nav nav-second-level">
                         <li>
                             <a href="myStoreList.jsp"><i class="fa fa-toggle-on"></i>我的店站</a>
                         </li>
                         <li>
                             <a href="notification.jsp"><i class="fa fa-bell "></i>岗位设置</a>
                         </li>
                          <li>
                             <a href="progress.jsp"><i class="fa fa-circle-o "></i>Progressbars</a>
                         </li>
                          <li>
                             <a href="buttons.jsp"><i class="fa fa-code "></i>Buttons</a>
                         </li>
                          <li>
                             <a href="icons.jsp"><i class="fa fa-bug "></i>Icons</a>
                         </li>
                          <li>
                             <a href="wizard.jsp"><i class="fa fa-bug "></i>Wizard</a>
                         </li>
                          <li>
                             <a href="typography.jsp"><i class="fa fa-edit "></i>Typography</a>
                         </li>
                          <li>
                             <a href="grid.jsp"><i class="fa fa-eyedropper "></i>信息设置</a>
                         </li>
                     </ul> -->
                 </li>
                 
                  <li>
                     <a href="#"><i class="fa fa-yelp "></i>订单管理 <span class="fa arrow"></span></a>
                      <ul class="nav nav-second-level">
                        <!--  <li>
                             <a href="unprocessed.jsp" id="unprocessed" onclick="clickAddClass(this.id)"><i class="fa fa-coffee"></i>未受理订单</a>
                         </li>
                         <li>
                             <a href="processing.jsp"><i class="fa fa-flash "></i>已受理订单</a>                         </li>
                          <li>
                             <a href="done.jsp"><i class="fa fa-key "></i>已批准订单</a>                         </li>
                          <li>
                             <a href="historyApply.jsp"><i class="fa fa-send "></i>历史订单</a>                         </li> -->
                         <li>
                             <a onclick="toPage(0)"><i class="fa fa-coffee"></i>未受理订单</a>
                         </li>
                         <li>
                             <a onclick="toPage(1)"><i class="fa fa-flash "></i>已受理订单</a>                         
                         </li>
                          <li>
                             <a onclick="toPage(2)"><i class="fa fa-key "></i>已批准订单</a>                         
                          </li>
                          <li>
                             <a onclick="toPage(3)"><i class="fa fa-send "></i>历史订单</a>                         
                          </li>
                     </ul>
                 </li>
       
                 
                  <li>
                     <a href="#"><i class="fa fa-square-o "></i>合同管理 <span class="fa arrow"></span></a>
                      <ul class="nav nav-second-level">
                        
                          <li>
                             <a href="contractList.jsp"><i class="fa fa-desktop "></i>签订中 </a>
                         </li>
                          <li>
                             <a href="form-advance.jsp"><i class="fa fa-code "></i>历史</a>
                         </li>
                          <li>
                           <a href="createContractModel.jsp"><i class="fa fa-flash "></i>合同模板 </a>
                     
                           </li>
                        
                     </ul>
                 </li>
                 
                 <!-- <li>
                     <a href="gallery.jsp"><i class="fa fa-anchor "></i>Gallery</a>
                 </li>
                  <li>
                     <a href="error.jsp"><i class="fa fa-bug "></i>Error Page</a>
                 </li>
                 <li>
                     <a href="login.jsp"><i class="fa fa-sign-in "></i>Login Page</a>
                 </li>
                 <li>
                     <a href="#"><i class="fa fa-sitemap "></i>Multilevel Link <span class="fa arrow"></span></a>
                      <ul class="nav nav-second-level">
                         <li>
                             <a href="#"><i class="fa fa-bicycle "></i>Second Level Link</a>
                         </li>
                          <li>
                             <a href="#"><i class="fa fa-flask "></i>Second Level Link</a>
                         </li>
                         <li>
                             <a href="#">Second Level Link<span class="fa arrow"></span></a>
                             <ul class="nav nav-third-level">
                                 <li>
                                     <a href="#"><i class="fa fa-plus "></i>Third Level Link</a>
                                 </li>
                                 <li>
                                     <a href="#"><i class="fa fa-comments-o "></i>Third Level Link</a>
                                 </li>

                             </ul>

                         </li>
                     </ul>
                 </li>
                 <li>
                     <a href="blank.jsp"><i class="fa fa-square-o "></i>Blank Page</a>
                 </li> -->
             </ul>
         </div>
     </nav>
</body>

<!-- <content tag="scripts">
	<script>
		var before=null;
		var now=null;
		function clickAddClass(target){
			before=now;
			now=target;
			alert("before:"+before+"  now:"+now);
			alert("#"+now+"");
			$("'#"+now+"'").addClass("bule");
			
		}
	</script>
</content> -->