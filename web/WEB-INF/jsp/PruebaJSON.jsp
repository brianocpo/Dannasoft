<%-- 
    Document   : PruebaJSON
    Created on : 22/06/2018, 11:02:35
    Author     : brian
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <title>JSP Page</title>
    </head>
    <style type="text/css">
        tr{cursor: pointer;
        }
        .detalleTabla{
            display: none;
        }
    </style>
    <body>
        <h1>JSON</h1>


        <h1>Factura</h1>
        <table border="1" id="s_usuario" >
            <tbody>
                <tr onclick="addTabla('s_usuario', 'codigo_usu', 1);addDetalleTabla('s_opcion', '1_row_1')" id="row_1" class="1_row_1">
                    <td>Producto 1</td>
                    <td>1</td>
                    <td><input type="button" value="Eliminar" onclick="eliminarFiltaTabla('s_usuario', 'codigo_usu', 1, 'row_1', 1, 's_opcion', '1_row_1')"/></td>
                </tr>
                <tr onclick="addTabla('s_usuario', 'codigo_usu', 1);addDetalleTabla('s_opcion', '1_row_2')" id="row_2" class="1_row_2">
                    <td>Producto 2</td>
                    <td>2</td>
                    <td><input type="button" value="Eliminar" onclick="eliminarFiltaTabla('s_usuario', 'codigo_usu', 1, 'row_2', 2, 's_opcion', '1_row_2');"/></td>
                </tr>               
            </tbody>
        </table>
        <br>
        <h1>Detalle</h1>
        <table border="1"  id="s_opcion" >
            <tbody>
                <tr onclick="addTabla('s_opcion', 'codigo_opc', 2)" id="row_1" class="detalleTabla 1_row_1">
                    <td>detalle producto 1</td>
                    <td>1</td>
                    <td><input type="button" value="Eliminar" onclick="eliminarFiltaTabla('s_opcion', 'codigo_opc', 2, 'row_1', 3,'','')"/></td>
                </tr>
                <tr onclick="addTabla('s_opcion', 'codigo_opc', 2)" id="row_2" class="detalleTabla 1_row_1">
                    <td>detalle producto 2</td>
                    <td>1</td>
                    <td><input type="button" value="Eliminar" onclick="eliminarFiltaTabla('s_opcion', 'codigo_opc', 2, 'row_2', 4,'','')"/></td>
                </tr>   


                <tr onclick="addTabla('s_opcion', 'codigo_opc', 2)" id="row_3" class="detalleTabla 1_row_2">
                    <td>detalle producto 3</td>
                    <td>2</td>
                    <td><input type="button" value="Eliminar" onclick="eliminarFiltaTabla('s_opcion', 'codigo_opc', 2, 'row_3', 5,'','')"/></td>
                </tr>
                <tr onclick="addTabla('s_opcion', 'codigo_opc', 2)" id="row_4" class="detalleTabla 1_row_2">
                    <td>detalle producto 4</td>
                    <td>2</td>
                    <td><input type="button" value="Eliminar" onclick="eliminarFiltaTabla('s_opcion', 'codigo_opc', 2, 'row_4', 6,'','')"/></td>
                </tr>   
            </tbody>
        </table>


    </body>
    <script type="text/javascript">
        var Tabla = {"TablaBDD": []};
        var TablaSeleccionada = "";

        function addDetalleTabla(NomTabla1, ClassRow1)
        {
            console.log(ClassRow1);
            $(".detalleTabla").css("display", "none");
            $("#" + NomTabla1 + " ." + ClassRow1).css("display", "block");
        }

     /*   function armarTabla()
        {

            console.log("-------" + Tabla.TablaBDD.length + "------------");
            for (var i = 0; i < Tabla.TablaBDD.length; i++)
            {
                console.log("-------TABLA # " + i + "------------");
                console.log(Tabla.TablaBDD[i].NomTabla);
                console.log(Tabla.TablaBDD[i].NomCampoPK);
                if (Tabla.TablaBDD[i].FilasEliminadas.length > 0)
                {
                    for (var j = 0; j < Tabla.TablaBDD[i].FilasEliminadas.length; j++)
                    {
                        console.log("Filas ha eliminar:" + Tabla.TablaBDD[i].FilasEliminadas[j].codigoPK);
                    }
                }

                console.log("-------------------------");
            }

        }*/
        function addTabla(NomTabla1, NomCampoPK1, OredenTabla1)
        {
            var TablaBDD_ADD = {"NomTabla": NomTabla1,
                "NomCampoPK": NomCampoPK1,
                "OredenTabla": OredenTabla1,
                "FilasEliminadas": []
            };

            var BuscarTabla = verificarTabla(TablaBDD_ADD.NomTabla);

            if (BuscarTabla == 0)
            {
                Tabla.TablaBDD[Tabla.TablaBDD.length] = TablaBDD_ADD;
            } else {
                console.log("La tabla " + TablaBDD_ADD.NomTabla + " ya se encuentra ingresada")
            }
            setTablaSeleccionada(NomTabla1);
            console.log(Tabla);
            return 1;
        }
        function verificarTabla(NomTabla1)
        {
            for (var i = 0; i < Tabla.TablaBDD.length; i++)
            {
                if (Tabla.TablaBDD[i].NomTabla == NomTabla1)
                {
                    return 1;
                }
            }
            return 0;
        }

        function eliminarFiltaTabla(NomTabla1, NomCampoPK1, OredenTabla1, idFila1, codigoPK1, NomTabla2, ClassRow1)
        {   var estadoDetalle =false; 
            if(NomTabla2.length>0)//Solo se verifica en caso de tener una tabla detalle
            {
               estadoDetalle = verificarDetalleTabla(NomTabla2, ClassRow1);
            }else{ estadoDetalle=true;}
            
            if (estadoDetalle == true)
            {
                var BuscarTabla = verificarTabla(NomTabla1);
                if (BuscarTabla == 0)//Solo en caso de no estar agregada la Tabla
                {
                    var estado = addTabla(NomTabla1, NomCampoPK1, OredenTabla1); //Se agrega la Tabla 
                    if (estado === 1) {
                        eliminarFiltaTabla(NomTabla1, NomCampoPK1, OredenTabla1, idFila1, codigoPK1, NomTabla2, ClassRow1); //Se elimina la fila
                    }
                } else {

                    var FilaEliminada = {"codigoPK": codigoPK1};
                    var FilaEliminadaAnteriores = BusFilasElimTabla(NomTabla1);
                    if (FilaEliminadaAnteriores !== 0)
                    {
                        FilaEliminadaAnteriores[FilaEliminadaAnteriores.length] = FilaEliminada;
                        addFilasElimTabla(NomTabla1, FilaEliminadaAnteriores);
                        //$("#" + NomTabla1 + " #" + idFila1).css("display", "none");
                        $("#" + NomTabla1 + " #" + idFila1).remove();

                    } else {

                    }
                }
            }
        }
        function verificarDetalleTabla(NomTabla2, ClassRow1)
        {
            //En caso de que la fila tenga una tablaDetalle
            var bl_estado=false;
            if (NomTabla2.length > 0) {
                //Cargar la data del detalle - 
                addDetalleTabla(NomTabla2, ClassRow1);
                //Comprueba los detalles 
                $('.' + ClassRow1).each(function (rowTabla) {
                    if (rowTabla > 1) {
                        console.log("No se puede eliminar posee datos relacionados ");
                        bl_estado= false;
                    }else{bl_estado= true;}
                });                
            }
            return bl_estado;
        }

        function BusFilasElimTabla(NomTabla1)
        {
            for (var i = 0; i < Tabla.TablaBDD.length; i++)
            {
                if (Tabla.TablaBDD[i].NomTabla == NomTabla1)
                {
                    return Tabla.TablaBDD[i].FilasEliminadas;
                }
            }
            return 0;
        }
        function addFilasElimTabla(NomTabla1, FilasEliminadas1)
        {
            for (var i = 0; i < Tabla.TablaBDD.length; i++)
            {
                if (Tabla.TablaBDD[i].NomTabla == NomTabla1)
                {
                    Tabla.TablaBDD[i].FilasEliminadas = FilasEliminadas1;
                    return 1;
                }
            }
            return 0;
        }

        /*SETERS AND GETERS*/
        function setTablaSeleccionada(NomTabla1)
        {
            TablaSeleccionada = NomTabla1;
        }
    </script>
</html>
