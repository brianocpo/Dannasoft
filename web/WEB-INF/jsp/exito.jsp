<%-- 
    Document   : exito
    Created on : 20-may-2018, 12:11:50
    Author     : Brian
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        Clave: ${loginclass.getLs_usuario()} <br>
        Usuario: ${loginclass.getLs_clave()}
    </body>
</html>
