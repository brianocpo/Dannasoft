<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="es">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <%@ include file="/WEB-INF/jsp/complementosJS.jsp" %>
        <title>${TitlePage}</title>
    </head>

    <body>
        <div class="ui-layout-north">

        </div>
        <div class="ui-layout-west" >
            <ul>
                <li> <a href="Usuarios" onclick="onLoad();">Usuarios</a></li>
                <li> <a href="PerfilUsuario" onclick="onLoad();">Perfiles</a></li>
                <li> <a href="PaisCiudad" onclick="onLoad();">Paises y Ciudades</a></li> 
            </ul>
        </div>
        <div  class="ui-layout-center" >
            <div class="table-responsive" style="padding-bottom:20px">

                <button type="button"  class="btn btn-primary insert" onclick="insertRow()"  onmouseover="setToolTip(this, 'Nuevo')" >
                    <span  class = "glyphicon glyphicon-plus" > </span>
                </button>
                <button type="button" class="btn btn-primary" onclick="deletRow()" onmouseover="setToolTip(this, 'Eliminar')" >
                    <span  class = "glyphicon glyphicon-remove" > </span>
                </button>
                <button type="button" class="btn btn-primary" onclick="saveTable()" onmouseover="setToolTip(this, 'Guardar')">
                    <span  class = "glyphicon glyphicon-floppy-disk" > </span>
                </button>

                <!--Buscar:<input type="text" id="inp_busqueda" onkeyup="busquedaTabla()" onmouseover="setToolTip(this,'Seleccione la tabla y digite su búsqueda')"/> <span class="glyphicon glyphicon-search"></span>-->
            </div>
            <div id="contenedor_tablas">
               
            </div>
        </div> 
</body>
</html>
