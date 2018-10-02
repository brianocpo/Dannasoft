<%@ include file="/WEB-INF/jsp/complementosJS.jsp" %>
<title>${TitlePage}</title> 
<!--Eventos del Teclado-->
<script type="text/javascript">
            //Maneja el evento para validar el formulario
            $.validator.setDefaults( {
                submitHandler: function () {
                    
                }
                ,
                showErrors: function(errorMap, errorList) {
                    if(this.numberOfInvalids()>0){                       
                        campoValidado=false;                        
                        $("#"+IdColumnSelect).css("background","#F8E0E0");
                        $("table input:checkbox").prop('disabled',true);
                        $("#btn_insert").prop('disabled',true);
                        $("#btn_delete").prop('disabled',true);
                        $("#btn_save").prop('disabled',true);
                        $("#inp_busqueda").prop('disabled',true);
                        
                        this.defaultShowErrors();
                    }else{
                        campoValidado=true;
                        $("#"+IdColumnSelect).css("background","#ffffff");
                        $("table input:checkbox").prop('disabled',false);
                        $("#btn_insert").prop('disabled',false);
                        $("#btn_delete").prop('disabled',false);
                        $("#btn_save").prop('disabled',false);
                        $("#inp_busqueda").prop('disabled',false);
                    }                      
                }
	    });
            $(document).ready(function() {
                //Quita las combinaciones de teclas por defecto
                document.onkeydown = function (e) {
                    e = e || window.event;//Get event
                        if (e.ctrlKey) {
                            var c = e.which || e.keyCode;//Get key code
                            switch (c) {
                                case 83://Block Ctrl+S
                                    e.preventDefault();
                                case 107://Block Ctrl++
                                    e.preventDefault();    
                                break;
                            }
                        }
                };
                //Aplica las acciones correspondientes a las combinaciones de Teclas
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
                //Validacion del Tabla Formulario
                var formulario=$( "#TablaForm" ).validate({				
				errorElement: "em",
				errorPlacement: function ( error, element ) {
					// Add the `help-block` class to the error element
					error.addClass( "help-block" );

					if ( element.prop( "type" ) === "checkbox" ) {
						error.insertAfter( element.parent( "label" ) );
					} else {
						error.insertAfter( element );
					}
				},
				highlight: function ( element, errorClass, validClass ) {
					$( element ).parents( ".col-sm-5" ).addClass( "has-error" ).removeClass( "has-success" );
				},
				unhighlight: function (element, errorClass, validClass) {
					$( element ).parents( ".col-sm-5" ).addClass( "has-success" ).removeClass( "has-error" );
				}
		});
                
               
            }); 
            function validarObjeto(){             
                $("#btnValidacion").click();
            }
 
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
    <button type="button" class="btn btn-primary" onclick="guardarTabla(1);" onmouseover="setToolTip(this, 'Guardar')" id="btn_save">
        <span  class = "glyphicon glyphicon-floppy-disk" > </span>
    </button>
    
    <button type="button" class="btn btn-primary"  onclick="actualizarTabla()" onmouseover="setToolTip(this, 'Actualizar')" id="btn_update">
        <span class="glyphicon glyphicon-refresh "></span>
    </button>
    
    Buscar:<input type="text" id="inp_busqueda" onkeyup="busquedaTabla()" onmouseover="setToolTip(this,'Seleccione la tabla y digite su búsqueda')"/> <span class="glyphicon glyphicon-search"></span>
</div>

<!--Sección de Tablas-->
<div id="contenedor_tablas">
    <form id="TablaForm" method="post" class="form-horizontal" action="">
        <div  id="tabla1">${tabla1} </div> <br>    
        <div  id="tabla2">${tabla2}</div>  <br>
        <div  id="tabla3">${tabla3}</div>  <br>
        <!--Btn ejecuta la accion de validación-->
        <button type="submit"  name="signup" value="" id="btnValidacion" style="display: none">AplicarValidacion</button>  
      
    </form>    
</div>
<!--Mensajes Flotantes-->
<div id="dialog-message" class="hide">
    <p id="mensaje"> </p>
</div>

