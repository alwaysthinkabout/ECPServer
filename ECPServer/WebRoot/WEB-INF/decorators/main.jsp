<!DOCTYPE html>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- <s:i18n name="decoratorsMessage.main"> -->
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
    <head>
	    <meta charset="utf-8" />
	    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
	    <title>金英台招聘系统</title>
	
	    <!-- BOOTSTRAP STYLES-->
	    <link href="assets/css/bootstrap.css" rel="stylesheet" />
	    <!-- FONTAWESOME STYLES-->
	    <link href="assets/css/font-awesome.css" rel="stylesheet" />
	       <!--CUSTOM BASIC STYLES-->
	    <link href="assets/css/basic.css" rel="stylesheet" />
	    <!--CUSTOM MAIN STYLES-->
	    <link href="assets/css/custom.css" rel="stylesheet" />

	    <link href="assets/css/menu.css" rel="stylesheet" type="text/css" />
	    <link href="assets/css/bootstrap.min.css" rel="stylesheet" />
	    <link href="assets/css/jquery-ui.min.css" rel="stylesheet" />
	    <link href="assets/css/ui.jqgrid.css" rel="stylesheet" />
	    <link href="assets/css/mycss.css" rel="stylesheet" />
	    <!-- GOOGLE FONTS-->
	    <!-- <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' /> -->
	    <style>
	    .modal-backdrop {
		    position: absolute;
		    top: 0;
		    right: 0;
		    left: 0;
		    background-color: #000;
       }
       /* .nav-link {
		  position: relative;
		  padding: 0 14px;
		  line-height: 34px;
		  font-size: 10px;
		  font-weight: bold;
		  color: #555;
		  text-decoration: none;
		}
		.nav-link:hover {
		  color: #333;
		  text-decoration: underline;
		} */
		
		.nav-counter {
		  font-size:11px;
		  background:red;
		  position: absolute;
		  top: 6px;
		  /* right: 1px; */
		  min-width: 8px;
		  height: 20px;
		  line-height: 20px;
		  margin-top: -11px;
		  padding: 0 5px;
		  font-weight: normal;
		  color: white;
		  text-align: center;
		  text-shadow: 0 1px rgba(0, 0, 0, 0.2);
		  /* background: #e23442; */
		  border: 1px solid #911f28;
		  border-radius: 11px;
		 /*  background-image: -webkit-linear-gradient(top, #e8616c, #dd202f);
		  background-image: -moz-linear-gradient(top, #e8616c, #dd202f);
		  background-image: -o-linear-gradient(top, #e8616c, #dd202f);
		  background-image: linear-gradient(to bottom, #e8616c, #dd202f); */
		  -webkit-box-shadow: inset 0 0 1px 1px rgba(255, 255, 255, 0.1), 0 1px rgba(0, 0, 0, 0.12);
		  box-shadow: inset 0 0 1px 1px rgba(255, 255, 255, 0.1), 0 1px rgba(0, 0, 0, 0.12);
		}
		
		/* .nav-counter-green {
		  background: #75a940;
		  border: 1px solid #42582b;
		  background-image: -webkit-linear-gradient(top, #8ec15b, #689739);
		  background-image: -moz-linear-gradient(top, #8ec15b, #689739);
		  background-image: -o-linear-gradient(top, #8ec15b, #689739);
		  background-image: linear-gradient(to bottom, #8ec15b, #689739);
		}
		 */
		.nav-counter-blue {
		  background: #3b8de2;
		  border: 1px solid #215a96;
		  background-image: -webkit-linear-gradient(top, #67a7e9, #2580df);
		  background-image: -moz-linear-gradient(top, #67a7e9, #2580df);
		  background-image: -o-linear-gradient(top, #67a7e9, #2580df);
		  background-image: linear-gradient(to bottom, #67a7e9, #2580df);
		}
       </style>
		<decorator:head/>
    </head>
    <body >
		<decorator:usePage id="thePage"/>
			<!-- WRAPPER -->
	        <div class="wrapper">
	            <jsp:include page="top.jsp" flush="true"></jsp:include>
                    <div class="row">
                        <!-- content-wrapper -->
                        <div class="col-md-12 ">
							<div class="main-header">
								
							</div>
							<div class="main-content">
								<decorator:body/>
							</div>
                        </div><!-- /content-wrapper -->
                    </div><!-- /row -->
	            
	        </div><!-- WRAPPER -->

         
         <script src="assets/js/jquery-ui.min.js"></script>
         <!-- <script src="assets/js/jedate/jedate.js"></script> -->
         <decorator:getProperty property="page.scripts"/>
                    
		
    </body>
</html>
<!-- </s:i18n> -->

