<%@ include file="/WEB-INF/jsp/complementosJS.jsp" %>
<title>${TitlePage}</title>       

<div id="mensajeAlerta" class="mensajeFixed"></div>
<!--Botones Principales-->
<div class="table-responsive" style="padding-bottom:20px">
    <button type="button"  class="btn btn-primary insert" onclick="insertarFilaNueva()"  onmouseover="setToolTip(this, 'Nuevo')" >
        <span  class = "glyphicon glyphicon-plus" > </span>
    </button>
    <button type="button" class="btn btn-primary" onclick="deletRow()" onmouseover="setToolTip(this, 'Eliminar')" >
        <span  class = "glyphicon glyphicon-remove" > </span>
    </button>
    <button type="button" class="btn btn-primary" onclick="guardarTabla(1)" onmouseover="setToolTip(this, 'Guardar')">
        <span  class = "glyphicon glyphicon-floppy-disk" > </span>
    </button>
    
    <button type="button" class="btn btn-primary"  onclick="actualizarTabla()" onmouseover="setToolTip(this, 'Actualizar')">
        <span class="glyphicon glyphicon-refresh "></span>
    </button>
    
    Buscar:<input type="text" id="inp_busqueda" onkeyup="busquedaTabla()" onmouseover="setToolTip(this,'Seleccione la tabla y digite su búsqueda')"/> <span class="glyphicon glyphicon-search"></span>
</div>

<!--Sección de Tablas-->
<div id="contenedor_tablas">
    <div class="table-responsive" id="tabla1" style="border-top:medium groove #438eb9;">${tabla1} </div> <br>    
    <div class="table-responsive" id="tabla2" style="border-top:medium groove #438eb9;">${tabla2}</div>  <br>
    <div class="table-responsive" id="tabla3" style="border-top:medium groove #438eb9;">${tabla3}</div>  <br>
</div>
<!--Mensajes Flotantes-->
<div id="dialog-message" class="hide">
    <p id="mensaje"> </p>
</div>

