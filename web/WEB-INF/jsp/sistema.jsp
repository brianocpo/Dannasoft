<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Dannasoft - Login</title>

        <!-- bootstrap & fontawesome -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/bootstrap.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/font-awesome/4.5.0/css/font-awesome.min.css" />

        <!-- page specific plugin styles -->
        <!-- text fonts -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/fonts.googleapis.com.css" />

        <!-- ace styles -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style" />

        <!--[if lte IE 9]>
                <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/ace-part2.min.css" class="ace-main-stylesheet" />
        <![endif]-->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/ace-skins.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/ace-rtl.min.css" />

        <!--[if lte IE 9]>
          <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/ace-ie.min.css" />
        <![endif]-->

        <!-- inline styles related to this page -->

        <!-- ace settings handler -->
        <script src="${pageContext.request.contextPath}/resources/assets/js/ace-extra.min.js"></script>

        <!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->

        <!--[if lte IE 8]>
        <script src="${pageContext.request.contextPath}/resources/assets/js/html5shiv.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/assets/js/respond.min.js"></script>
        <![endif]-->
        <title>Dannasoft - Login</title>
    </head>

    <body class="no-skin" onload="loadTablas()">
        
        <%@ include file="navbar.jsp" %>

        <div class="main-container ace-save-state" id="main-container">
            <script type="text/javascript">
                try {
                    ace.settings.loadState('main-container')
                } catch (e) {
                }
            </script>

            <div id="sidebar" class="sidebar                  responsive                    ace-save-state">
                <script type="text/javascript">
                    try {
                        ace.settings.loadState('sidebar')} catch (e) {
                    }
                </script>
                
                <!--MENU DEL SISTEMA-->
                <%@ include file="navlist.jsp" %>

                <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
                    <i id="sidebar-toggle-icon" class="ace-icon fa fa-angle-double-left ace-save-state" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
                </div>
            </div>

            <div class="main-content">
                <div class="main-content-inner">
                    
                    <div class="breadcrumbs ace-save-state" id="breadcrumbs">
                        <ul class="breadcrumb">
                            <li>
                                <i class="ace-icon fa fa-home home-icon"></i>
                                <a href="#">Home</a>
                            </li>
                            <li class="active">Dashboard</li>
                        </ul><!-- /.breadcrumb -->

                        <div class="nav-search" id="nav-search">
                            <form class="form-search">
                                <span class="input-icon">
                                    <input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />
                                    <i class="ace-icon fa fa-search nav-search-icon"></i>
                                </span>
                            </form>
                        </div>
                    </div>

                    <div class="page-content">
                        <div class="row">
                            <div class="col-xs-12">
                               
                                 <%@ include file="pantalla_triple.jsp" %>
                                
                            </div><!-- /.col -->
                        </div><!-- /.row -->
                    </div><!-- /.page-content -->
                </div>
            </div><!-- /.main-content -->

            <div class="footer">
                <div class="footer-inner">
                    <div class="footer-content">
                        <span class="bigger-120">                            
                            Dannasoft &copy; 2018
                        </span>

                        &nbsp; &nbsp;

                    </div>
                </div>
            </div>

            <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
                <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
            </a>
        </div><!-- /.main-container -->

        <!-- basic scripts -->

        <!--[if !IE]> -->
        <script src="${pageContext.request.contextPath}/resources/assets/js/jquery-2.1.4.min.js"></script>

        <!-- <![endif]-->

        <!--[if IE]>
        <script src="${pageContext.request.contextPath}/resources/assets/js/jquery-1.11.3.min.js"></script>
        <![endif]-->
        <script type="text/javascript">
            if ('ontouchstart' in document.documentElement)
                document.write("<script src='${pageContext.request.contextPath}/resources/assets/js/jquery.mobile.custom.min.js'>" + "<" + "/script>");
        </script>
        <script src="${pageContext.request.contextPath}/resources/assets/js/bootstrap.min.js"></script>

        <!-- page specific plugin scripts -->

        <!--[if lte IE 8]>
          <script src="${pageContext.request.contextPath}/resources/assets/js/excanvas.min.js"></script>
        <![endif]-->
        <script src="${pageContext.request.contextPath}/resources/assets/js/jquery-ui.custom.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/assets/js/jquery.ui.touch-punch.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/assets/js/jquery.easypiechart.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/assets/js/jquery.sparkline.index.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/assets/js/jquery.flot.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/assets/js/jquery.flot.pie.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/assets/js/jquery.flot.resize.min.js"></script>

        <!-- ace scripts -->
        <script src="${pageContext.request.contextPath}/resources/assets/js/ace-elements.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/assets/js/ace.min.js"></script>

    </body>
</html>
