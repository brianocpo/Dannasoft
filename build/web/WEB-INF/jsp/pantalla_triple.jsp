<%@ include file="/WEB-INF/jsp/complementosJS.jsp" %>
<title>${TitlePage}</title> 
<!--Eventos del Teclado-->
<script type="text/javascript">
            $(document).ready(function() {
                document.onkeydown = function (e) {
                    e = e || window.event;//Get event
                        if (e.ctrlKey) {
                            var c = e.which || e.keyCode;//Get key code
                            switch (c) {
                                case 83://Block Ctrl+S
                                    e.preventDefault();
                                case 107://Block Ctrl+ +
                                    e.preventDefault();    
                                break;
                            }
                        }
                };
                
                $("body").keydown(function(e){

                        if(e.ctrlKey){
                            var c = e.which || e.keyCode; 
                            switch (c) {
                                case 83:
                                    guardarTabla(1);
                                    break;
                                case 46:
                                    deletRow();
                                    break;
                                case 107:
                                    insertarFilaNueva();
                                    break;    
                            }
                        }
                });
            });                       
</script>
<div id="mensajeAlerta" class="mensajeFixed"></div>
<!--Botones Principales-->
<div class="table-responsive" style="padding-bottom:20px">
    <button type="button"  class="btn btn-primary insert" onclick="insertarFilaNueva()"  onmouseover="setToolTip(this, 'Nuevo')" id="btn_insert" >
        <span  class = "glyphicon glyphicon-plus" > </span>
    </button>
    <button type="button" class="btn btn-primary" onclick="deletRow()" onmouseover="setToolTip(this, 'Eliminar')" id="btn_delete"  >
        <span  class = "glyphicon glyphicon-remove" > </span>
    </button>
    <button type="button" class="btn btn-primary" onclick="guardarTabla(1)" onmouseover="setToolTip(this, 'Guardar')" id="btn_save">
        <span  class = "glyphicon glyphicon-floppy-disk" > </span>
    </button>
    
    <button type="button" class="btn btn-primary"  onclick="actualizarTabla()" onmouseover="setToolTip(this, 'Actualizar')" id="btn_update">
        <span class="glyphicon glyphicon-refresh "></span>
    </button>
    
    Buscar:<input type="text" id="inp_busqueda" onkeyup="busquedaTabla()" onmouseover="setToolTip(this,'Seleccione la tabla y digite su búsqueda')"/> <span class="glyphicon glyphicon-search"></span>
</div>

<!--Sección de Tablas-->
<div id="contenedor_tablas">
    
    <div  id="tabla1">${tabla1} </div> <br>    
    <div  id="tabla2">${tabla2}</div>  <br>
    <div  id="tabla3">${tabla3}</div>  <br>
</div>
<!--Mensajes Flotantes-->
<div id="dialog-message" class="hide">
    <p id="mensaje"> </p>
</div>

