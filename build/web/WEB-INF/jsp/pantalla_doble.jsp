<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="es">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <%@ include file="/WEB-INF/jsp/complementosJS.jsp" %>
        <title>${TitlePage}</title>       
    </head>

    <body onload="loadTablas()">
        <div id="mensajeAlerta" class="mensajeFixed"></div>
        <!--Botones Principales-->
        <div class="table-responsive" style="padding-bottom:20px">

            <button type="button"  class="btn btn-primary insert" onclick="insertarFilaNueva()"  onmouseover="setToolTip(this, 'Nuevo')" >
                <span  class = "glyphicon glyphicon-plus" > </span>
            </button>
            <button type="button" class="btn btn-primary" onclick="deletRow()" onmouseover="setToolTip(this, 'Eliminar')" >
                <span  class = "glyphicon glyphicon-remove" > </span>
            </button>
            <button type="button" class="btn btn-primary" onclick="guardarTabla()" onmouseover="setToolTip(this, 'Guardar')">
                <span  class = "glyphicon glyphicon-floppy-disk" > </span>
            </button>

            Buscar:<input type="text" id="inp_busqueda" onkeyup="busquedaTabla()" onmouseover="setToolTip(this,'Seleccione la tabla y digite su búsqueda')"/> <span class="glyphicon glyphicon-search"></span>
        </div>

        
        <!--Sección de Tablas-->
        <div id="contenedor_tablas">
            <div class="table-responsive" id="tabla1">${tabla} </div> 
            <div class="table-responsive" id="tabla2">${tabla2}</div>
        </div>
        <!--Mensajes Flotantes-->
        <div id="dialog-message" class="hide">
            <p id="mensaje"> </p>
        </div>

    </body>
</html>
