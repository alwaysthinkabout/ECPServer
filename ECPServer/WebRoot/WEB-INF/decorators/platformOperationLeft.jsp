<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- main-nav -->
 	<nav class="main-nav" role="navigation" >
         <div class="sidebar-collapse">
             <ul class="main-menu" id="main-menu">
             	  <li><a href="../../content/common/homePage.jsp" id="menuForHomePage">&nbsp;<i class="fa fa-bullseye fa-fw"></i><b>首页</b>
                  	  </a>
                  </li>
                  <li><a onclick="openForList(this.id)" id="menuForEnterprise">&nbsp;<i class="fa fa-bullseye fa-fw"></i><b>企业信息审核</b>
                  		<i class="toggle-icon fa fa-angle-left" id="iconForEnterprise"></i>
                  	  </a>
                   	 <ul class="sub-menu" id="subMenuForEnterprise">
                   	 	<li><a href="../../content/enterpriseInfo/enterpriseInfoAuditList.jsp" id="auditForEnterprise">&nbsp;待审核列表</a></li>
                   	 	<li><a href="../../content/enterpriseInfo/enterpriseInfoAuditedList.jsp" id="auditedForEnterprise">&nbsp;已审核列表</a></li>
                   	 </ul>
                  </li>
                  <li><a onclick="openForList(this.id)" id="menuForJobHunter">&nbsp;<i class="fa fa-bullseye fa-fw"></i><b>求职者信息审核</b>
                  	  	<i class="toggle-icon fa fa-angle-left" id="iconForJobHunter"></i>
                  	  </a>
                   	 <ul class="sub-menu" id="subMenuForJobHunter">
                   	 	<li><a href="../../content/jobHunterInfo/jobHunterInfoAuditList.jsp" id="auditForJobHunter">&nbsp;待审核列表</a></li>
                   	 	<li><a href="../../content/jobHunterInfo/jobHunterInfoAuditedList.jsp" id="auditedForJobHunter">&nbsp;已审核列表</a></li>
                   	 </ul>
                  </li>
                  <li><a onclick="openForList(this.id)" id="menuForStore">&nbsp;<i class="fa fa-bullseye fa-fw"></i><b>店面信息审核</b>
                  	  	<i class="toggle-icon fa fa-angle-left" id="iconForStore"></i>
                  	  </a>
                   	 <ul class="sub-menu" id="subMenuForStore">
                   	 	<li><a href="../../content/storeInfo/storeInfoAuditList.jsp" id="auditForStore">&nbsp;待审核列表</a></li>
                   	 	<li><a href="../../content/storeInfo/storeInfoAuditedList.jsp" id="auditedForStore">&nbsp;已审核列表</a></li>
                   	 </ul>
                  </li>
                  <li><a onclick="openForList(this.id)" id="menuForRole">&nbsp;<i class="fa fa-bullseye fa-fw"></i><b>角色管理</b>
                  	  	<i class="toggle-icon fa fa-angle-left" id="iconForRole"></i>
                  	  </a>
                   	 <ul class="sub-menu" id="subMenuForRole">
                   	 	<li><a href="../../content/roleInfo/roleInfoAuditList.jsp" id="auditForRole">&nbsp;待审核列表</a></li>
                   	 	<li><a href="../../content/roleInfo/roleInfoAuditedList.jsp" id="auditedForRole">&nbsp;已审核列表</a></li>
                   	 </ul>
                  </li>
                  <li><a onclick="openForList(this.id)" id="menuForUser">&nbsp;<i class="fa fa-bullseye fa-fw"></i><b>用户管理</b>
                  	  	<i class="toggle-icon fa fa-angle-left" id="iconForUser"></i>
                  	  </a>
                   	 <ul class="sub-menu" id="subMenuForUser">
                   	 	<li><a href="../../content/userInfo/userInfoList.jsp" id="auditForUser">&nbsp;用户列表</a></li>
                   	 	<!-- <li><a href="../../content/roleInfo/roleInfoAuditedList.jsp" id="auditedForRole">&nbsp;已审核列表</a></li> -->
                   	 </ul>
                  </li>
             </ul>
         </div>
     </nav>

<div class="sidebar-minified js-toggle-minified" >
    <i class="fa fa-angle-left" ></i>
</div>

<script src="../../assets/js/decoratorsJS/platformOperationLeft.js"></script>
<script src="../../assets/js/king-common.js"></script>