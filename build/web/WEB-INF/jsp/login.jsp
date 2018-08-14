<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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

        <!-- text fonts -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/fonts.googleapis.com.css" />

        <!-- ace styles -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/ace.min.css" />

        <!--[if lte IE 9]>
                <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/ace-part2.min.css" />
        <![endif]-->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/ace-rtl.min.css" />

        <!--[if lte IE 9]>
          <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/ace-ie.min.css" />
        <![endif]-->

        <!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->

        <!--[if lte IE 8]>
        <script src="${pageContext.request.contextPath}/resources/assets/js/html5shiv.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/assets/js/respond.min.js"></script>
        <![endif]--> 

        <title>Dannasoft - Login</title>
    </head>

    <body class="login-layout light-login">
        <div class="main-container">
            <div class="main-content">
                <div class="row">
                    <div class="col-sm-10 col-sm-offset-1">
                        <div class="login-container">

                            <div class="position-relative">
                                <div id="login-box" class="login-box visible widget-box no-border">
                                    <div class="widget-body">
                                        <div class="widget-main" style="text-align: center">

                                            <img style="height: 70px" src="${pageContext.request.contextPath}/resources/images/logo.png"  class="img-circle" />   								
                                            <h4 class="blue" id="id-company-text">&copy; Dannasoft</h4>
                                            <div class="space-6"></div>

                                            <form:form method="POST" modelAttribute="loginclass" action="login">
                                                <form:errors path="*" element="div" cssClass="alert alert-danger"/>
                                                <fieldset>
                                                    <label class="block clearfix">
                                                        <span class="block input-icon input-icon-right">
                                                            <form:input  path="ls_usuario" id="ls_usuario" cssClass="form-control" placeholder="Usuario" />                                                            
                                                            <i class="ace-icon fa fa-user"></i>
                                                        </span>
                                                    </label>
                                                    

                                                    <label class="block clearfix">
                                                        <span class="block input-icon input-icon-right">                                                           
                                                            <form:password  path="ls_clave" id="ls_clave" cssClass="form-control" placeholder="Clave"/>                                                            
                                                            <i class="ace-icon fa fa-lock"></i>
                                                        </span>
                                                    </label>
                                                    
                                                    <div class="space"></div>

                                                    <div class="clearfix">

                                                        <input type="submit" value="Submit" class="width-35 pull-right btn btn-sm btn-primary" />

                                                    </div>

                                                    <div class="space-4"></div>
                                                </fieldset>
                                            </form:form>

                                        </div><!-- /.widget-main -->

                                        <div class="toolbar clearfix">
                                            <div>
                                                <a href="#" data-target="#forgot-box" class="forgot-password-link">
                                                    <i class="ace-icon fa fa-arrow-left"></i>
                                                    Olvidé mi contraseña
                                                </a>
                                            </div>
                                        </div>
                                    </div><!-- /.widget-body -->
                                </div><!-- /.login-box -->

                                <div id="forgot-box" class="forgot-box widget-box no-border">
                                    <div class="widget-body">
                                        <div class="widget-main">
                                            <h4 class="header red lighter bigger">
                                                <i class="ace-icon fa fa-key"></i>
                                                Recuperar Contraseña
                                            </h4>

                                            <div class="space-6"></div>
                                            <p>
                                                Ingrese su correo electrónico.
                                            </p>

                                            <form>
                                                <fieldset>
                                                    <label class="block clearfix">
                                                        <span class="block input-icon input-icon-right">
                                                            <input type="email" class="form-control" placeholder="Email" />
                                                            <i class="ace-icon fa fa-envelope"></i>
                                                        </span>
                                                    </label>

                                                    <div class="clearfix">
                                                        <button type="button" class="width-35 pull-right btn btn-sm btn-danger">
                                                            <i class="ace-icon fa fa-lightbulb-o"></i>
                                                            <span class="bigger-110">Enviar</span>
                                                        </button>
                                                    </div>
                                                </fieldset>
                                            </form>
                                        </div><!-- /.widget-main -->

                                        <div class="toolbar center">
                                            <a href="#" data-target="#login-box" class="back-to-login-link">
                                                Atrás para iniciar sesión
                                                <i class="ace-icon fa fa-arrow-right"></i>
                                            </a>
                                        </div>
                                    </div><!-- /.widget-body -->
                                </div><!-- /.forgot-box -->


                            </div><!-- /.position-relative -->


                        </div>
                    </div><!-- /.col -->
                </div><!-- /.row -->
            </div><!-- /.main-content -->
        </div><!-- /.main-container -->

        <!-- basic scripts -->

        <!--[if !IE]> -->
        <script src="${pageContext.request.contextPath}/resources/assets/js/jquery-2.1.4.min.js"></script>

        <!-- <![endif]-->

        <!--[if IE]>
            <script src="${pageContext.request.contextPath}/resources/assets/js/jquery-1.11.3.min.js"></script>
        <![endif]-->
        <!-- inline scripts related to this page -->
        <script type="text/javascript">
            jQuery(function ($) {
                $(document).on('click', '.toolbar a[data-target]', function (e) {
                    e.preventDefault();
                    var target = $(this).data('target');
                    $('.widget-box.visible').removeClass('visible');//hide others
                    $(target).addClass('visible');//show target
                });
            });
        </script>
    </body>
   
</html>
