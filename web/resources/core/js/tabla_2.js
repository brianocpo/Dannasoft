var JSONRowsTabla;
var JSONColumnsTabla;
var RowsTable;
var ColumnsTable;
var ls_nombreTabla;
var SchemaBDD = 'mod_administracion';
var IdTabla = "";
var IdTablaAnt = "";
var ls_ordenTB = "";

var FilasActualizadasTB = [];
var DataActualizadasTB = [];
var ColumnasNuevasTB = [];

var CodigoCampoPKTB = "";
var NombreCampoPKTB;

var IdColumnSelect = "";
var ElementFilaSeleccionada;
var ElementoSelectTemp;
var IndexSelect_row;
var JsonTablaH = "";
var FilaIDTemp = [];
/*------JSON------------*/
var Tabla = {"TablaBDD": []};
var JsonTablas = [];
var TablaSeleccionada = "";
var ClassRow = "";
var NomTabla_hija = "";
var EstadoCambioFila = 0;
var ValorTemporal = "";
var filaSelectID = [];
var RowsTableTEMP = [];
var ColumnsTableTEMP = [];
var classFilaSelectID = [];
var TablasRelacionadas = [];
var RowID = [];
function addTabla(NomTabla1, NomCampoPK1, OredenTabla1)
{
    var TablaBDD_ADD = {"NomTabla": NomTabla1,
        "NomCampoPK": NomCampoPK1,
        "OredenTabla": OredenTabla1,
        "FilasEliminadas": [],
        "FilasActualizadas": [],
        "FilasInsertadas": []
    };

    var BuscarTabla = verificarTabla(TablaBDD_ADD.NomTabla);

    if (BuscarTabla == 0)
    {
        Tabla.TablaBDD[Tabla.TablaBDD.length] = TablaBDD_ADD;
    } else {

    }
    setTablaSeleccionada(NomTabla1);

    return 1;
}
function addFilasInsertadas(NomTabla1, FilasInsertadas1)
{
    for (var i = 0; i < Tabla.TablaBDD.length; i++)
    {
        if (Tabla.TablaBDD[i].NomTabla == NomTabla1)
        {   
            var FilasInsertadasTemp=Tabla.TablaBDD[i].FilasInsertadas;
            FilasInsertadasTemp[FilasInsertadasTemp.length]=FilasInsertadas1;
            Tabla.TablaBDD[i].FilasInsertadas = FilasInsertadasTemp;
            return 1;
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
function deletRow() //Evento Ejecutado desde los Botones principales
{
    if (CodigoCampoPKTB.length > 0)//Verifica si exite una clave Primaria
    {
        var codigoCampoTempora = CodigoCampoPKTB.split("=");//en el caso de una fila nueva CodigoCampoPKTB se rescribe

        if (codigoCampoTempora[0] != "N")//N significa que si es una columna Nueva
        {
            if(eliminarFilaTabla(ls_nombreTabla, NombreCampoPKTB, ls_ordenTB, CodigoCampoPKTB)===1){
                selectUltimaFilaTabla();
            }
            
        } else
        {
//            mensajeAccion("Alerta", "Fila Eliminada", "");
            $("." + codigoCampoTempora[1]).remove();            
            deleteRowCadenaInsert(codigoCampoTempora[2]);
            //Si se elimina una fila se posiciona el cursor en la ultima fila de la tabla
            selectUltimaFilaTabla();
            console.log(Tabla);
        }
    }
}
function selectUltimaFilaTabla(){    
    var IdRowFinalTabla = "";
    IdRowFinalTabla = getUltimoIdRowTabla();
    if (IdRowFinalTabla.length > 0) {
        $("#" + IdRowFinalTabla).click();
    } 
}
function eliminarFilaTabla(NomTabla1, NomCampoPK1, OredenTabla1, codigoPK1)
{
    var estadoDetalle = false;
    if (NomTabla_hija.length > 0)//Solo se verifica en caso de tener una tabla detalle
    {
        estadoDetalle = verificarDetalleTabla(NomTabla_hija, ClassRow);
    } else {
        estadoDetalle = true;
    }

    if (estadoDetalle == true)
    {
        var BuscarTabla = verificarTabla(NomTabla1);
        if (BuscarTabla == 0)//Solo en caso de no estar agregada la Tabla
        {
            var estado = addTabla(NomTabla1, NomCampoPK1, OredenTabla1); //Se agrega la Tabla 
            if (estado === 1) {
                eliminarFilaTabla(NomTabla1, NomCampoPK1, OredenTabla1, codigoPK1); //Se elimina la fila
            }
        } else {

            var FilaEliminada = {"codigoPK": codigoPK1};
            var FilaEliminadaAnteriores = BusFilasElimTabla(NomTabla1);
            if (FilaEliminadaAnteriores !== 0)
            {
                FilaEliminadaAnteriores[FilaEliminadaAnteriores.length] = FilaEliminada;
                addFilasElimTabla(NomTabla1, FilaEliminadaAnteriores);               
                $(ElementFilaSeleccionada).remove();
            }
        }
    }else{ return 0;}
        mensajeAccion("Alerta", "Fila Eliminada", "");
    return 1;

}
function BusFilasActualizadas()
{
    for (var i = 0; i < Tabla.TablaBDD.length; i++)
    {
        if (Tabla.TablaBDD[i].NomTabla == ls_nombreTabla)
        {
            return Tabla.TablaBDD[i].FilasActualizadas;
        }
    }
    return 0;
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

function verificarCambiosTablas(ordenTB)
{
    for (var i = 0; i < Tabla.TablaBDD.length; i++)
    {
        if (parseInt(Tabla.TablaBDD[i].OredenTabla) < parseInt(ordenTB))
        {
            var NumFilasEliminadas = Tabla.TablaBDD[i].FilasEliminadas.length;
            if (NumFilasEliminadas > 0)
            {
                return 1;
            }

            var NumFilasActualizadas = Tabla.TablaBDD[i].FilasActualizadas.length;
            if (NumFilasActualizadas > 0)
            {
                return 1;
            }

            var NumFilasInsertadas = Tabla.TablaBDD[i].FilasInsertadas.length;
            if (NumFilasInsertadas > 0)
            {
                if(getValorFilasNuevas()==1){
                    return 1; 
                }else{
                     return 0;
                }

            }
        }
    }
    return 0;
}

function transformarDato(TypeData, Dato)
{
    TypeData = TypeData.toUpperCase();
    if (TypeData === "CHARACTER" || TypeData === "CHAR" || TypeData === "VARCHAR" || TypeData === "DATE" || TypeData === "DATETIME")
    {
        Dato = "\'" + Dato + "\'";
    }
    return Dato;
}
function buscarColumnasActualizada(codigoPK1)
{
    for (var i = 0; i < Tabla.TablaBDD.length; i++)
    {   //Busca la Tabla
        if (Tabla.TablaBDD[i].NomTabla == ls_nombreTabla)
        {   //Busca la Fila
            if (Tabla.TablaBDD[i].FilasActualizadas.length)
            {
                for (var f = 0; f < Tabla.TablaBDD[i].FilasActualizadas.length; f++)
                {
                    if (Tabla.TablaBDD[i].FilasActualizadas[f].codigoPK == codigoPK1)
                    {
                        return Tabla.TablaBDD[i].FilasActualizadas[f].ColumnasActualizada;
                    }
                }
            }
        }
    }
    return -1;
}
function getColumnasActualizadas(ColumnasActualizada, NomColumna1)
{
    for (var i = 0; i < ColumnasActualizada.length; i++)
    {
        if (ColumnasActualizada[i].NomColumna == NomColumna1)
        {
            return i;
        }
    }
    return -1;
}


function findTypeColumn(nombre_column,ordenTB)
{   
    var Rows=RowsTableTEMP[ordenTB];
    var Columns=ColumnsTableTEMP[ordenTB];
    var nombre_columnCompare = "";
    var TypeData = "";
    if (Rows.length > 0) {

        for (var i in Rows[0].lista_gridColumn)//Columnas del Objeto
        {
            if (Rows[0].lista_gridColumn.hasOwnProperty(i)) //Verificacion de que exista datos
            {
                var nombre_columnCompare = Rows[0].lista_gridColumn[i].ls_nombre_column;
                if (nombre_columnCompare === nombre_column)
                {
                    TypeData = Rows[0].lista_gridColumn[i].data_column.is_nullable;
                    break;
                }
            }
        }
    } else
    {
        for (var i in Columns)//Columnas del Objeto
        {
            if (Columns.hasOwnProperty(i)) //Verificacion de que exista datos
            {
                var nombre_columnCompare = Columns[i].ls_nombre_column;
                if (nombre_columnCompare === nombre_column)
                {
                    TypeData = Columns[i].data_column.is_nullable;
                    break;
                }
            }
        }
    }
    return TypeData;
}
function findIndexColumn(nombre_column)
{
    var index_col = -1;
    var nombre_column_temp = "";
    for (var i = 0; i < ColumnsTable.length; i++)
    {
        nombre_column_temp = $.trim(ColumnsTable[i].ls_nombre_column);
        if (nombre_column_temp === $.trim(nombre_column))
        {
            index_col = i;
            break;
        }
    }
    return index_col;
}
function findIndexColumnVariaTablas(nombre_column,ColumnsTB)
{
    var index_col = -1;
    var nombre_column_temp = "";
    for (var i = 0; i < ColumnsTB.length; i++)
    {
        nombre_column_temp = $.trim(ColumnsTB[i].ls_nombre_column);
        if (nombre_column_temp === $.trim(nombre_column))
        {
            index_col = i;
            break;
        }
    }
    return index_col;
}
function findCodigoSelect(index_column, valor)
{
    var codigo = "";
    var codigoSelect = "";
    var valorSelect = "";
    if (ColumnsTable[index_column].data_dropdown.codigo_dw.length)
    {
        codigoSelect = ColumnsTable[index_column].data_dropdown.codigo_dw;
    }
    if (ColumnsTable[index_column].data_dropdown.valor_dw.length)
    {
        valorSelect = ColumnsTable[index_column].data_dropdown.valor_dw;
    }
    for (var i = 0; i < valorSelect.length; i++)
    {
        if (valor === valorSelect[i])
        {
            codigo = codigoSelect[i];
            break;
        }
    }
    return codigo;
}
function BorrarEstiloFila(ordenRow)
{
    //Se busca todas las filas con con las clase rowActive y se remplasa por ""
    var trActivo = document.getElementsByClassName("rowActive" + ordenRow);
    for (var j = 0; j < trActivo.length; j++)
    {
        trActivo[j].className = trActivo[j].className.replace("rowActive" + ordenRow, "");
    }
}
function EstiloFilaNueva(element, ordenRow, trs)//Actualiza el Estado y rescribe CodigoCampoPKTB
{
    //CodigoCampoPKTB: se modifica esta variable para ser validad al momento de la edición de columnas
    CodigoCampoPKTB = "N=rowActive" + ordenRow + "=N"+ordenRow+""+trs;
    //Se busca todas las filas con con las clase rowActive y se remplasa por ""
    var trActivo = document.getElementsByClassName("rowActive" + ordenRow);
    for (var j = 0; j < trActivo.length; j++)
    {
        trActivo[j].className = trActivo[j].className.replace("rowActive" + ordenRow, "");
    }
    //se agrega al elemento la nueva clase
    $(element).addClass("rowTabla" + ordenRow + " rowActive" + ordenRow + " rowNew");
}
function EstiloFilaActiva()
{
    var tabla = document.getElementById(IdTabla);
    var tr = tabla.getElementsByClassName("rowTabla" + ls_ordenTB);
    var trActivo = document.getElementsByClassName("rowActive" + ls_ordenTB);
    for (var j = 0; j < trActivo.length; j++)
    {
        trActivo[j].className = trActivo[j].className.replace("rowActive" + ls_ordenTB, "");
    }
    $(ElementFilaSeleccionada).addClass("rowTabla" + ls_ordenTB + " rowActive" + ls_ordenTB + "");
}

function deleteRowCadenaInsert(Row)
{
    var codigoPKFNueva = $.trim(Row);
    for (var i = 0; i < Tabla.TablaBDD.length; i++)
    {  
        if (Tabla.TablaBDD[i].NomTabla == ls_nombreTabla)
        {   
            if (Tabla.TablaBDD[i].FilasInsertadas.length)
            {
                for (var f = 0; f < Tabla.TablaBDD[i].FilasInsertadas.length; f++)
                {
                    if (Tabla.TablaBDD[i].FilasInsertadas[f].codigoPK == codigoPKFNueva)
                    {   
                        Tabla.TablaBDD[i].FilasInsertadas.splice(f, 1);
                    }
                }
            }
        }
    }
}

function inicializarTabla(IdTabla1, NombreCampPK, jsonRows, jsonColumns, nombreTabla, ordenTB)
{
    IdTabla = IdTabla1;
    NombreCampoPKTB = NombreCampPK;
    JSONRowsTabla = jsonRows;
    JSONColumnsTabla = jsonColumns;
    RowsTable = jQuery.parseJSON(JSONRowsTabla);
    ColumnsTable = jQuery.parseJSON(JSONColumnsTabla);
    RowsTableTEMP[ordenTB] = RowsTable;
    ColumnsTableTEMP[ordenTB] = ColumnsTable;
    ls_nombreTabla = nombreTabla;
    ls_ordenTB = ordenTB;
    activarTabla();
    setJsonTablas(jsonRows, jsonColumns, nombreTabla);
    addTabla(nombreTabla, NombreCampPK, ordenTB);
    return 1;
}


function activarTabla()
{
    $("#" + IdTabla).removeClass("table-bordered");
    $("#" + IdTabla).addClass("table-borderedActived");

    if (IdTablaAnt.length > 0) {
        if (IdTabla != IdTablaAnt) {
            $("#" + IdTablaAnt).removeClass("table-borderedActived");
            $("#" + IdTablaAnt).addClass("table-bordered");
        }
    }
    if (IdTabla != IdTablaAnt) {
        IdTablaAnt = IdTabla;
    }
    
}
/*---------------------------------------------------------------------------*/


function buscarJsonTablas(nombreTabla)
{
    for (var i = 0; i < JsonTablas.length; i++)
    {
        if (JsonTablas[i].NomTabla == nombreTabla)
        {
            return i;
        }
    }
    return -1;
}

function marcarFilaNueva(idFilaNueva)
{
    if (idFilaNueva !== 0)
    {
        $("#" + idFilaNueva).addClass("rowTabla" + ls_ordenTB + " rowActive" + ls_ordenTB + " rowNew");
    }
}

function eliminarDetalleSelectN()
{
    
    var filasEliminadas = 0;
    try {
        var li_ordentabla = parseInt(ls_ordenTB) + 1;
        $(".Grid-" + li_ordentabla + " tbody tr").each(function () {

            $(this).remove();
            filasEliminadas++;
        });

    } catch (Error) {
        console.log(Error);
    }
}
function crearComboDefecto(nombre_column, codigoPadreFK1) {
    var comboBox = "";
    var codigoPadreFK = 0;
    try {
        if (JsonTablaH.ls_nombre_campo_padre == nombre_column) {
            var bloquearColumn = "disabled";
            codigoPadreFK = codigoPadreFK1;
            console.log("Codigo padre=" + codigoPadreFK);
            var index_col = findIndexColumn(nombre_column);
            //ColumnsTable contiene la estructura en General común de todo el GRID
            var codigoSelect = ColumnsTable[index_col].data_dropdown.codigo_dw;
            var valorSelect = ColumnsTable[index_col].data_dropdown.valor_dw;
            comboBox = "<select " + bloquearColumn + " onchange='updateColumn()' class='form-control'  id='" + nombre_column + "' name='" + nombre_column + "' >";
            for (var i = 0; i < valorSelect.length; i++)
            {
                if (codigoPadreFK > 0)
                {
                    if (codigoSelect[i] == codigoPadreFK)
                    {
                        comboBox += "<option value='" + codigoSelect[i] + "' selected>" + valorSelect[i] + "</option>";
                    }
                }
            }
            comboBox += "</select>";
        }

    } catch (err) {
        return comboBox;
    }
    return comboBox;
}
/*-----------CREAR OBJETO EDICION----------------------*/
function crearObjeto(nombre_column, elementColumn, index_row, ls_codigo_fk_select, boolNuevo)
{
    //validacion para bloquear FK en caso de ser una tabla HIJA
    var bloquearColumn = "";
    var valorDefctoColumn = "";
    try {
        if (JsonTablaH.ls_nombre_campo_padre == nombre_column) {
            bloquearColumn = "disabled";
            valorDefctoColumn = JsonTablaH.ls_valor_codigo_padre;
        }
    } catch (err) {
        document.getElementById("demo").innerHTML = err.message;
    }

    var TypeData = "";
    var campo = "";
    TypeData = findTypeColumn(nombre_column,ls_ordenTB);

    if (TypeData !== "")
    {
        TypeData = TypeData.toUpperCase();
        //Setea el objeto anterior modificado
        eliminarObjeto();
        var textoCampo = $(elementColumn).text();
        ValorTemporal = $.trim(textoCampo);
        if (NombreCampoPKTB !== nombre_column)
        {
            if (TypeData === "CHARACTER" || TypeData === "CHAR" || TypeData === "INTEGER")
            {
                if (ls_codigo_fk_select == "0")//Verifica si se un campo de (0=Input) (1=Select)
                {
                    campo = "<input onkeyup='updateColumn()' class='form-control' type='text' size='20' id='" + nombre_column + "' name='" + nombre_column + "' value='" + $.trim(textoCampo) + "' />";
                } else //CREA UN OBJETO SELECT
                {
                    var index_col = findIndexColumn(nombre_column);
                    //ColumnsTable contiene la estructura en General común de todo el GRID
                    var codigoSelect = ColumnsTable[index_col].data_dropdown.codigo_dw;
                    var valorSelect = ColumnsTable[index_col].data_dropdown.valor_dw;
                    //RowsTable Contiene la data específica de cada fila y columna
                    var codigoDefectoSelect = "";
                    if (boolNuevo === false)
                    {
                        codigoDefectoSelect = RowsTable[index_row].lista_gridColumn[index_col].data_dropdown.codigo_fk_select;
                    } else {
                        codigoDefectoSelect = valorDefctoColumn;
                    }

                    campo = "<select " + bloquearColumn + " onchange='updateColumn()' class='form-control'  id='" + nombre_column + "' name='" + nombre_column + "' >";
                    for (var i = 0; i < valorSelect.length; i++)
                    {
                        if (valorDefctoColumn.length > 0 && boolNuevo === true)
                        {
                            if (codigoSelect[i] == valorDefctoColumn)
                            {
                                campo += "<option value='" + codigoSelect[i] + "' selected>" + valorSelect[i] + "</option>";
                            }
                        } else
                        {
                            if (textoCampo === valorSelect[i])
                            {
                                campo += "<option value='" + codigoSelect[i] + "' selected>" + valorSelect[i] + "</option>";
                            } else
                            {
                                campo += "<option  value='" + codigoSelect[i] + "'>" + valorSelect[i] + "</option>";
                            }
                        }
                    }
                    campo += "</select>";
                }
            }
            //Limpiar el Elemento
            $(elementColumn).html("");
            //Cargar el Elemento
            elementColumn.append(campo);
            $("#" + nombre_column).focus();
            ElementoSelectTemp = elementColumn;
            IdColumnSelect = nombre_column;
            IndexSelect_row = index_row;
        } else {
            eliminarObjeto();
            IdColumnSelect = "";
        }
    }
}
function eliminarObjeto()
{
    //Setea el objeto anterior modificado
    if (IdColumnSelect.length > 0)
    {
        var valorElemento = "";
        var valorSelect = "";
        valorElemento = $("#" + IdColumnSelect).val();
        valorSelect = $("#" + IdColumnSelect + " option:selected").text();
        if (valorSelect.length > 0)
        {
            $(ElementoSelectTemp).html(valorSelect);
        } else if (valorElemento !== null)
        {
            $(ElementoSelectTemp).html(valorElemento);
        }
    }
}
/*-----------VERIFICA SI SE PUEDE O NO ELIMINAR UNA FILA----------------------*/
function verificarDetalleTabla(NomTabla2, ClassRow1)
{
    //En caso de que la fila tenga una tablaDetalle
    var bl_estado = false;
    if (NomTabla2.length > 0) {
        //Comprueba los detalles 
        $('.' + ClassRow1).each(function (rowTabla) {
            if (rowTabla > 0) {
                mensajeAccion("Alerta", "No se puede eliminar posee datos relacionados", "");
                bl_estado = false;
            } else {
                bl_estado = true;
            }
        });
    }
    return bl_estado;
}
/*---------------------BLOQUEAR PANTALLA DURANTE LA CARGA----------------------*/
function onLoad()
{
    $("body").prepend("<div class=\"overlay\"></div>");
    $(".overlay").css({
        "position": "absolute",
        "width": $(document).width(),
        "height": $(document).height(),
        "z-index": 99999,
        "background-color": "#dddddd",
        "cursor": "progress"
    }).fadeTo(0, 0.8);
    $(window).load(function () {
        removeLoad();
    });
}
function removeLoad()
{
    $(".overlay").remove();
}
/*---------------------------BUSQUEDA DENTRO DE UNA TABLA---------------------*/
$.extend($.expr[":"],
        {
            "contains-ci": function (elem, i, match, array)
            {
                return (elem.textContent || elem.innerText || $(elem).text() || "").toLowerCase().indexOf((match[3] || "").toLowerCase()) >= 0;
            }
        });

function busquedaTabla()
{   //Setea el objeto anterior modificado para edicion
    eliminarObjeto();
    //Busqueda
    var textoBusqueda = $("#inp_busqueda").val();
    if (IdTabla.length > 0)
    {
        if (textoBusqueda !== "")
        {
            $("#" + IdTabla + " tbody>tr").hide();
            $("#" + IdTabla + " td:contains-ci('" + textoBusqueda + "')").parent("tr").show();
        } else
        {
            $("#" + IdTabla + " tbody>tr").show();
        }
    } else {
        console.log("Seleccione la tabla");
    }
}
/*----------------------------CARGAR DETALLE TABLA----------------------------*/
function cargar_TablaHija(nombreFK, valorFK, JsonTablaHija, lsClassPadre)
{   
    JsonTablaH = jQuery.parseJSON(JsonTablaHija);
    JsonTablaH.ls_where = JsonTablaH.ls_where.toString().replace("0", valorFK);
    NomTabla_hija = JsonTablaH.ls_name_tabla.toString();
    //En caso de que sea una FILA nueva la que se seleciona no carga ningún detalle
    try {
        if (ls_ordenTB >= 0)
        {
            if (filaSelectID[ls_ordenTB].indexOf("N") >= 0) {
                removeLoad();
                return 0;
            }
        }
    } catch (Error) {
    }
   
    if (FilaIDTemp[ls_ordenTB] != valorFK)
    {   
        if (JsonTablaH.ls_IdDivTabla.length)
        {

            JsonTablaH.ls_valor_codigo_padre = valorFK;
            JsonTablaH.ls_valor_nombre_padre = nombreFK;
            JsonTablaH.ls_classTablaPadre = lsClassPadre;
           
            var jsonString = JSON.stringify(JsonTablaH);
            //Equivalente a lo anterior
            $.ajax({
                data: jsonString,
                type: "POST",
                dataType: "json",
                contentType: 'application/json',
                url: "post/json"
            })
                    .done(function (data, textStatus, jqXHR) {

                        if (console && console.log) {
                            $('#' + JsonTablaH.ls_IdDivTabla.toString().trim()).html(data.tablaHtml);
                            console.log("La solicitud se ha completado correctamente.");
                            //Coloca el Foco de la primera fila de la tabla Hija y retorna el Foco a la Primera tabla 
                            var ordenTemp=parseInt(ls_ordenTB) + 1;
                            $("#R"+ordenTemp+"_0").click(); 
                            
                            if(ordenTemp>1){
                                var filamarcada=RowID[ordenTemp-1];
                                $(filamarcada).click(); 
                            }
                            
                            removeLoad();
                            
                        }
                    })
                    .fail(function (jqXHR, textStatus, errorThrown) {
                        if (console && console.log) {
                            $('#' + JsonTablaH.ls_IdDivTabla.toString().trim() + " tbody").html("");
                            console.log("La solicitud a fallado: " + textStatus + errorThrown);
                            removeLoad();
                        }
                    });
        }
        console.log("entro cargar_TablaHija"); 
    } else {

        removeLoad();
    }    
}
/*---------------------------------CONFIRM GUARDADO DE DATOS-------------------*/
$.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
    _title: function (title) {
        var $title = this.options.title || '&nbsp;'
        if (("title_html" in this.options) && this.options.title_html == true)
            title.html($title);
        else
            title.text($title);
    }
}));

function GuardarDataConfirm(titulo, mensaje,ordenTB1) {
    $("#mensaje").html(mensaje);
    var dialog = $("#dialog-message").removeClass('hide').dialog({
        modal: true,
        title: "<div class='widget-header widget-header-small'><h4 class='smaller'><i class='ace-icon fa fa-check'></i> " + titulo + "</h4></div>",
        title_html: true,
        buttons: [
            {
                text: "No Guardar",
                "class": "btn btn-minier",
                click: function () {
                    $(this).dialog("close");
                    mensajeAccion("Alerta", "su información no fue procesada", "");
                    setTablaJson2(ordenTB1);              
                }
            },
            {
                text: "Guardar",
                "class": "btn btn-primary btn-minier",
                click: function () {
                    $(this).dialog("close");
                    guardarTabla();
                    IdColumnSelect="";
                    mensajeAccion("Exito", "información guardada", "");
                }
            }
        ]
    });
 
}
/*---------------------------------MENSAJES FLOTANTES-------------------------*/
function mensajeAccion(valor, Mensaje, IdElementoAnclaje)
{
    var iconoMensaje = '';
    var classMensaje = "";
    switch (valor) {
        case "Exito":
            var iconoMensaje = '<i class="ace-icon fa fa-check"></i>';
            Mensaje = '<strong>' + iconoMensaje + ' OK: </strong>' + Mensaje;
            var classMensaje = "alert alert-block alert-success";
            break;
        case "Informativo":
            Mensaje = '<strong> Informativo: </strong>' + Mensaje;
            var classMensaje = "alert alert-info";
            break;
        case "Alerta":
            Mensaje = '<strong> Alerta: </strong>' + Mensaje;
            var classMensaje = "alert alert-warning";
            break;
        case "Peligro":
            var iconoMensaje = '<i class="ace-icon fa fa-times"></i>';
            Mensaje = '<strong>' + iconoMensaje + ' Peligro: </strong>' + Mensaje;
            var classMensaje = "alert alert-danger";
            break;
    }

    var divMensaje = $("<div>", {
        'class': classMensaje,
        'html': Mensaje
    }).append(
            $("<button>", {
                'type': 'button',
                'class': 'close',
                'data-dismiss': 'alert'

            })).hide().appendTo('#mensajeAlerta').fadeIn('slow');

    setTimeout(function () {
        eliminarMensaje(divMensaje);
    }, 3000);
}
function eliminarMensaje(elemento)
{
    elemento.remove();
}
/*-----------------------------GUARDAR - INSERTAR -ACTUALIZAR - ELIMINAR TABLAS---------------------------------*/

function guardarTabla()
{   
   
        var jsonString = JSON.stringify(Tabla);

        $.ajax({
            data: jsonString,
            type: "POST",
            dataType: "json",
            contentType: 'application/json',
            url: "post/jsonTable"
        })
                .done(function (data, textStatus, jqXHR) {
                    eliminarObjeto();
                    mensajeAccion("Exito", "información guardada", "");
                    setTablaJson();
                })
                .fail(function (jqXHR, textStatus, errorThrown) {
                    
                    mensajeAccion("Alerta", "su información no fue procesada", "");
                    setTablaJson();
                });

}
function insertarFilaNueva()
{
    /*Antes de insertar verificar si la columna Padre es Nueva*/
    marcarFilaNueva(insertRow());
    eliminarDetalleSelectN();
    FilaIDTemp[ls_ordenTB]=filaSelectID[ls_ordenTB];

    /*Acciona el evento click de la nueva fila*/
    try {       
        $("#" + filaSelectID[ls_ordenTB]).click();
        IdColumnSelect="";
    } catch (Error) {
        console.log("Error Funcion insertarFilaNueva: "+Error);
    }
    console.log(Tabla);
}
function insertRow()
{   
//Guardar en un arreglo la estructura principal de la TABLA identificado por su ORDEN
//    RowsTableTEMP[ls_ordenTB] = RowsTable;
//    ColumnsTableTEMP[ls_ordenTB] = ColumnsTable;
    //Setear el objeto que este activo para edicion
    eliminarObjeto();
    //Setear el CodigoCampoPKTB carcado en el evento onclick de cada columna
    CodigoCampoPKTB = "";
    //Si es una tabla Hija se consulta el codigo FK y el nombre de columna para colocarlo por defecto
    var valorDefectoFK = "";
    var nombreCampoFK = "";
    var codigoPadreFk = "";
    try
    {
        if (JsonTablaH.length == 0)
        {
            nombreCampoFK = "";
            valorDefectoFK = "";
        } else
        {
            nombreCampoFK = JsonTablaH.ls_nombre_campo_padre;
            valorDefectoFK = JsonTablaH.ls_valor_nombre_padre;
            codigoPadreFk = JsonTablaH.ls_valor_codigo_padre;
        }

    } catch (err)
    {
        console.log(err.message);
    }
    var styleColumn = "";
    var numeracionFila = 0;
    if (IdTabla.length > 0)
    {   //Se quita el estilo de la fila anteriormente seleccionada de la tabla
        BorrarEstiloFila(ls_ordenTB);
        //Solo en caso de ser una tabla sin información se setea tbody para ingresar las nuevas columnas
        var tds = $("#" + IdTabla + " tbody tr td").length;
        if (tds === 1) {
            $("#" + IdTabla + " tbody").html("");
        }
        //SE REALIZA EL CALCULO DEL NÚMERO DE COLUMNAS QUE SE DEBE AGREGAR
        var nuevaFila = "";
        var Tcolumns = 0;
        //Total de filas de la tabla, se resta -2 filas representa los titulos de la tabla 
        var trs = $("#" + IdTabla + " tr").length - 2;
        //Total de columnas de toda la tabla x cada fila
        var tds = $("#" + IdTabla + " tr td").length;
        //Se divide para obtener el total de columnas de una sola fila
        Tcolumns = tds / trs;
        //se suma mas uno al total de filas, representa la nueva fila
        trs = trs + 1;
        numeracionFila = trs;
        var classNuevaFila = "N" + ls_ordenTB + trs;
        var idNuevaFila = "N" + ls_ordenTB + trs;

        //Verificar que se seleccione el TABLA PADRE
        //para insertar los detalles

        classFilaSelectID[ls_ordenTB] = classNuevaFila;
        var PKTablaPadre = "";
        if (ls_ordenTB > 1)
        {
            try {
                if (filaSelectID[ls_ordenTB - 1].indexOf("N") >= 0 || filaSelectID[ls_ordenTB - 1] >= 0) {
                    //Esta condicion indica que la cabecera seleccionada es una fila nueva y que toca eliminar los detalles 
                    //que no correspondan a filas nuevas                                        
                    if (filaSelectID[ls_ordenTB - 1].indexOf("N") >= 0) {
                        classNuevaFila = classFilaSelectID[ls_ordenTB - 1];
                        PKTablaPadre = filaSelectID[ls_ordenTB - 1];
                    } else {
                        PKTablaPadre = filaSelectID[ls_ordenTB - 1];
                    }

                } else {
                    mensajeAccion("Alerta", "No se puede insertar el detalle ya que no exite una cabecera seleccionada", "");
                    return 0;
                }
            } catch (error) {
                mensajeAccion("Alerta", "No se puede insertar el detalle ya que no exite una cabecera seleccionada", "");
                return 0;
            }
        }
        ////////////////////////////////////////////////////

        filaSelectID[ls_ordenTB] = idNuevaFila;
      
        /*INSERTAR LA NUEVA FILA*/
        var nuevaFila = "<tr class='rowTabla" + ls_ordenTB + " rowNew " + classNuevaFila + "' id='" + idNuevaFila + "' onclick=' EstiloFilaNueva(this," + ls_ordenTB + "," + trs + "); getRowNueva(\"" + idNuevaFila + "\"," + ls_ordenTB + ",\"" + classNuevaFila + "\",\"" + NombreCampoPKTB + "\",\"" + ls_nombreTabla + "\",\"" + IdTabla + "\")' >";
        var index_col;
        //Clave primaria temporal
        if (numeracionFila <= 0 || numeracionFila == "") {
            numeracionFila = 1;
        }
        var codigoPK = "N" + ls_ordenTB + numeracionFila;
        
        setearFilaInsertada(); 
        if(PKTablaPadre.length>0){InsertFila(codigoPK,PKTablaPadre,nombreCampoFK);}else{
            InsertFila(codigoPK,"","");
        }
        
        var FK=false;
        //SI se conoce el total de columnas - en caso de que ya existan registros anteriores
        if (Tcolumns > 0)
        {
            for (var j = 0; j < Tcolumns; j++)
            {
                // añadimos las columnas
                var valor = "";
                var nombreColumn = "'" + getNameColumn(j) + "'";

                if (NombreCampoPKTB === getNameColumn(j)) {
                      valor =codigoPK;
                } else if (nombreCampoFK === getNameColumn(j)) { //Valor por defecto de la tabla PADRE                        
                    //comprobar si la fila de la tabla padre  es una FILA NUEVA (Fila nueva)                    
                    if (PKTablaPadre.indexOf("N") >= 0) {
                        valor = PKTablaPadre;
                        
                    } else {
                        valor = crearComboDefecto(nombreCampoFK, PKTablaPadre);
                    }
                    FK=true;
                }else{
                    FK=false;
                }

                index_col = findIndexColumn(nombreColumn.replace(/'/g, "")); //Elimina espacios de la cadena

                if (ColumnsTable[index_col].lb_FK === true && PKTablaPadre.indexOf("N") < 0)
                {
                    nuevaFila += '<td ' + styleColumn + ' onclick="getColumnNueva(' + trs + ',' + j + ',' + nombreColumn + ',1,true,\'' + ls_ordenTB + '\',\'' + IdTabla + '\',\'' + idNuevaFila + '\')" id="col' + ls_ordenTB + '_' + trs + '_' + j + '">' + valor + '</td>';

                } else if (ColumnsTable[index_col].lb_FK === true && getNameColumn(j) == nombreCampoFK && PKTablaPadre.indexOf("N") >= 0)
                {
                    nuevaFila += '<td ' + styleColumn + ' class="bloquear"  id="col' + ls_ordenTB + '_' + trs + '_' + j + '">' + valor + '</td>';
                } else
                {
                    nuevaFila += '<td ' + styleColumn + ' onclick="getColumnNueva(' + trs + ',' + j + ',' + nombreColumn + ',0,true,\'' + ls_ordenTB + '\',\'' + IdTabla + '\',\'' + idNuevaFila + '\')" id="col' + ls_ordenTB + '_' + trs + '_' + j + '">' + valor + '</td>';
                }
                
                var IDColumn="col" + ls_ordenTB + "_" + trs + "_" + j;
                var NomColumn=nombreColumn.replace(/'/g, "");
                InsertColumn(NomColumn,"","",FK,IDColumn);                
            }
        } else //SI no se conoce el total de columnas se consulta al ColumnsTable -  solo en caso de ser una tabla sin registros
        {
            for (var j = 0; j < ColumnsTable.length; j++)
            {
                // añadimos las columnas
                var valor = "";
                var nombreColumn = "'" + getNameColumn(j) + "'";
                if (NombreCampoPKTB === getNameColumn(j)) {
                    valor = "N" + ls_ordenTB + numeracionFila;
                } else if (getNameColumn(j) == nombreCampoFK) {//Valor por defecto de la tabla PADRE
                    //comprobar si la fila de la tabla padre  es una FILA NUEVA (Fila nueva)                     
                    if (PKTablaPadre.indexOf("N") >= 0) {
                        valor = PKTablaPadre;
                    } else {
                        valor = crearComboDefecto(nombreCampoFK, codigoPadreFk);
                    }
                    FK=true;
                }else{
                     FK=false;
                }

                index_col = findIndexColumn(nombreColumn.replace(/'/g, ""));
                if (ColumnsTable[index_col].lb_FK === true && PKTablaPadre.indexOf("N") < 0)
                {
                    nuevaFila += '<td ' + styleColumn + ' onclick="getColumnNueva(' + trs + ',' + j + ',' + nombreColumn + ',1,true,\'' + ls_ordenTB + '\',\'' + IdTabla + '\',\'' + idNuevaFila + '\')" id="col' + ls_ordenTB + '_' + trs + '_' + j + '">' + valor + '</td>';
                } else if (ColumnsTable[index_col].lb_FK === true && getNameColumn(j) == nombreCampoFK && PKTablaPadre.indexOf("N") >= 0)
                {
                    nuevaFila += '<td ' + styleColumn + ' class="bloquear" id="col' + ls_ordenTB + '_' + trs + '_' + j + '">' + valor + '</td>';
                } else
                {
                    nuevaFila += '<td ' + styleColumn + ' onclick="getColumnNueva(' + trs + ',' + j + ',' + nombreColumn + ',0,true,\'' + ls_ordenTB + '\',\'' + IdTabla + '\',\'' + idNuevaFila + '\')" id="col' + ls_ordenTB + '_' + trs + '_' + j + '">' + valor + '</td>';
                }
                
                var IDColumn="col" + ls_ordenTB + "_" + trs + "_" + j;
                var NomColumn=nombreColumn.replace(/'/g, "");
                InsertColumn(NomColumn,"","",FK,IDColumn);    

            }
        }
        addFilasInsertadas(ls_nombreTabla, FilaInsertadas);                        
        nuevaFila += "</tr>";
        // Agreabamos una Fila nueva a la tabla.
        $("#" + IdTabla).append(nuevaFila);
        $(".bloquear").attr('disabled', 'disabled');
        nuevaFila = "";
        return idNuevaFila;
    } else {
        mensajeAccion("Informativo", "Seleccione la Tabla para poder insertar una nueva Fila", "");
        nuevaFila = "";
        return 0;
    }
  
    return 0;
}
function updateColumn()
{
    var FilaActualizadas = {
        "codigoPK": 0,
        "ColumnasActualizada": []
    }
    var ColumnaActualizada = {
        "NomColumna": "",
        "TipoValor": "",
        "Valor": "",
    }
    var TipoValor = "";
    var Valor = "";
    var FilasActualizadas = "";
    FilasActualizadas = BusFilasActualizadas();
    TipoValor = findTypeColumn(IdColumnSelect,ls_ordenTB);
    Valor = $("#" + IdColumnSelect).val();
    //Buscar si ya esta la fila ingresada mediante su codigo primario
    var ColumnasActualizada_temp = "";
    ColumnasActualizada_temp = buscarColumnasActualizada(CodigoCampoPKTB);

    //var indexFilasActualizadasTB;
    //Busca el ID dentro de FilasActualizadasTB para no guardar dos veces el mismo
    //indexFilasActualizadasTB = FilasActualizadasTB.indexOf($.trim(CodigoCampoPKTB + ":" + IdColumnSelect));
    //Valor = transformarDato(TipoValor, Valor);
    //validar clave prmaria en caso de ser registro nuevo
    var codigoPK = CodigoCampoPKTB.split("=");
    if (codigoPK[0] !== "N")
    {
        if (ColumnasActualizada_temp !== -1) {
            var columna = getColumnasActualizadas(ColumnasActualizada_temp, IdColumnSelect);
            if (columna !== -1)
            {
                ColumnasActualizada_temp[columna].Valor = Valor;
            } else {
                ColumnaActualizada.NomColumna = IdColumnSelect;
                ColumnaActualizada.TipoValor = TipoValor;
                ColumnaActualizada.Valor = Valor;
                ColumnasActualizada_temp[ColumnasActualizada_temp.length] = ColumnaActualizada;
            }
        } else
        {
            FilaActualizadas.codigoPK = CodigoCampoPKTB;
            FilasActualizadas[FilasActualizadas.length] = FilaActualizadas;
            updateColumn();
        }
    }
}
function EliminarColumnaInsertada(codigoPK1){
    for (var i = 0; i < Tabla.TablaBDD.length; i++)
    {    //Busca la Fila Insertada
            if (Tabla.TablaBDD[i].FilaInsertadas.length)
            {
                for (var f = 0; f < Tabla.TablaBDD[i].FilaInsertadas.length; f++)
                {
                    if (Tabla.TablaBDD[i].FilaInsertadas[f].codigoPK == codigoPK1)
                    {
                        Tabla.TablaBDD[i].FilaInsertadas.splice(f, 1);
                        break;
                    }
                }
            }       
    }   
}
var FilaInsertadas={};
function setearFilaInsertada(){
    FilaInsertadas = {
        "codigoPK": "",
        "codigoFK": "",
        "nombreCampoFK":"",
        "ColumnasInsertadas": []
    }   
}
function InsertFila(codigoPK,codigoFK,nombreCampoFK){
    setearFilaInsertada();
    FilaInsertadas.codigoPK=codigoPK;
    FilaInsertadas.codigoFK=codigoFK; 
    FilaInsertadas.nombreCampoFK=nombreCampoFK; 
}
function InsertColumn(NomColumna,TipoValor,Valor,FK,IDColumn)
{   var vecColumnaInsertada=[];
    var ColumnaInsertada = {
        "NomColumna": "",
        "TipoValor": "",
        "Valor": "",
        "FK": "",
        "IDColumn": "",
    }    
    ColumnaInsertada.NomColumna=NomColumna;
    ColumnaInsertada.TipoValor=TipoValor;
    ColumnaInsertada.Valor=Valor;     
    ColumnaInsertada.FK=FK; 
    ColumnaInsertada.IDColumn=IDColumn;
    vecColumnaInsertada=FilaInsertadas.ColumnasInsertadas;
    vecColumnaInsertada[vecColumnaInsertada.length]=ColumnaInsertada;    
    FilaInsertadas.ColumnasInsertadas=vecColumnaInsertada;
}

function getValorFilasNuevas(){
    eliminarObjeto();
    for (var i = 0; i < Tabla.TablaBDD.length; i++)
    {   var ordenTB=Tabla.TablaBDD[i].OredenTabla;
        if (Tabla.TablaBDD[i].FilasInsertadas.length>0)
        {
            for (var f = 0; f < Tabla.TablaBDD[i].FilasInsertadas.length; f++)
            {  
                for (var g = 0; g < Tabla.TablaBDD[i].FilasInsertadas[f].ColumnasInsertadas.length; g++)
                {
                        var NombreColumn=Tabla.TablaBDD[i].FilasInsertadas[f].ColumnasInsertadas[g].NomColumna;
                        var IDColumn=Tabla.TablaBDD[i].FilasInsertadas[f].ColumnasInsertadas[g].IDColumn;
                        
                        var Valor="";
                        var ColumnTEMP=ColumnsTableTEMP[ordenTB];
                        var index_col = findIndexColumnVariaTablas(NombreColumn,ColumnTEMP);
                        
                        
                        if(ColumnTEMP[index_col].lb_FK === true)
                        {
                            Valor = ColumnTEMP[index_col].data_dropdown.codigo_dw[0];
                            Tabla.TablaBDD[i].FilasInsertadas[f].ColumnasInsertadas[g].FK=true;
                        }else{
                            Valor=$("#"+IDColumn.trim()).html();
                            Tabla.TablaBDD[i].FilasInsertadas[f].ColumnasInsertadas[g].FK=false;
                        }                                               
                        var TipoValor = findTypeColumn(NombreColumn,ordenTB);
                        Tabla.TablaBDD[i].FilasInsertadas[f].ColumnasInsertadas[g].Valor=Valor;
                        Tabla.TablaBDD[i].FilasInsertadas[f].ColumnasInsertadas[g].TipoValor=TipoValor;
                }               
            }
        }       
    }
    
    return 1;
}
/*--------------------------SETERS AND GETERS---------------------------------*/
function getNameColumn(index_column)
{
    if (RowsTable.length > 0) //Verificacion de que exista datos Columna
    {
        var nombre_column = RowsTable[0].lista_gridColumn[index_column].ls_nombre_column;
    } else if (ColumnsTable.length > 0) {
        var nombre_column = ColumnsTable[index_column].ls_nombre_column;
    } else {
        console.log("Error getNameColumn()");
    }
    return nombre_column;
}
function getBoolPK(index_column)
{
    if (RowsTable.length > 0) //Verificacion de que exista datos Columna
    {
        var lb_PK = RowsTable[0].lista_gridColumn[index_column].lb_PK;
    } else if (ColumnsTable.length > 0)
    {
        var lb_PK = ColumnsTable[index_column].lb_PK;
    } else {
        console.log("Error getBoolPK()");
    }
    return lb_PK;
}
function getBoolFK(index_column)
{
    if (RowsTable.length > 0) //Verificacion de que exista datos Columna
    {
        var lb_FK = RowsTable[0].lista_gridColumn[index_column].lb_FK;
    } else if (ColumnsTable.length > 0)
    {
        var lb_FK = ColumnsTable[index_column].lb_FK;
    } else {
        console.log("Error getBoolPK()");
    }
    return lb_FK;
}
function setTablaJson()
{   
    for (var i = 0; i < Tabla.TablaBDD.length; i++)
    {
        Tabla.TablaBDD[i].FilasEliminadas = [];
        Tabla.TablaBDD[i].FilasActualizadas = [];
        Tabla.TablaBDD[i].FilasInsertadas = [];
    }
    
}
function setTablaJson2(ordenTB1)
{   ordenTB1=parseInt(ordenTB1); 
    
    for (var i = 0; i < Tabla.TablaBDD.length; i++)
    {   
        var ordenTabla=parseInt(Tabla.TablaBDD[i].OredenTabla);       
        if (ordenTabla>ordenTB1)
        {
            Tabla.TablaBDD[i].FilasEliminadas = [];
            Tabla.TablaBDD[i].FilasActualizadas = [];
            Tabla.TablaBDD[i].FilasInsertadas = [];
        }
    } 
   console.log(Tabla);  
}
function setTablaSeleccionada(NomTabla1)
{
    TablaSeleccionada = NomTabla1;
}
function setJsonTablas(jsonRows, jsonColumns, nombreTabla)
{
    var JsonTabla = {
        "NomTabla": "",
        "jsonRows": [],
        "jsonColumns": []
    }
    var pos_vec = 0;
    pos_vec = buscarJsonTablas(nombreTabla);
    if (pos_vec == -1)
    {
        JsonTabla.NomTabla = nombreTabla;
        JsonTabla.jsonRows = jsonRows;
        JsonTabla.jsonColumns = jsonColumns;
        JsonTablas[JsonTablas.length] = JsonTabla;
    }
}
function setToolTip(element, title_tool)
{
    $(element).tooltip({title: title_tool, placement: "bottom", animation: true});
}
function getRow(ElementoSeleccionad, IdRowFocu, NombreCampPK, IdTabla1, jsonRows, jsonColumns, nombreTabla, ordenTB, ClassRow1)
{   
    RowID[ordenTB]=ElementoSeleccionad;
    //Guarda el PK de la fila seleccionada para cada tabla
    FilaIDTemp[ordenTB]=filaSelectID[ordenTB];
    filaSelectID[ordenTB] = IdRowFocu;

    console.log("Fila actual seleccionada="+parseInt(filaSelectID[ordenTB]));
    console.log("Fila temp seleccionada="+parseInt(FilaIDTemp[ordenTB]));
    if (parseInt(FilaIDTemp[ordenTB]) != parseInt(filaSelectID[ordenTB])) {
        console.log("Filas Diferentes");
        if (verificarCambiosTablas(ordenTB) == 1)
        { 
            GuardarDataConfirm("Alerta Cambios", "Por favor, para no perder los cambios realizados es necesario guardar la información",ordenTB);
        } 
    }
    
    ClassRow = ClassRow1;
    ElementFilaSeleccionada = ElementoSeleccionad;
    CodigoCampoPKTB = IdRowFocu;
    if (inicializarTabla(IdTabla1, NombreCampPK, jsonRows, jsonColumns, nombreTabla, ordenTB) === 1)
    {
        EstiloFilaActiva();
    }
}
function getColumn(index_row, index_column, nombre_column, ls_codigo_fk_select, boolNuevo, NombreCampPK, IdTabla1, jsonRows, jsonColumns, nombreTabla, ordenTB,ls_IdRow)
{   
    NomelementColumnAnterior = "";

    /*Setear los valores iniciales al seleccionar la columna*/
    if (inicializarTabla(IdTabla1, NombreCampPK, jsonRows, jsonColumns, nombreTabla, ordenTB) === 1)
    {
        if (IdColumnSelect !== nombre_column || IndexSelect_row !== index_row)
        {
            var elementColumn = $("#col" + ordenTB + "_" + index_row + "_" + index_column);
            crearObjeto(nombre_column, elementColumn, index_row, ls_codigo_fk_select, boolNuevo);
        }
    }
}
function getRowNueva(idNuevaFila1, ordenTB, classNuevaFila1, NombreCampoPKTB1, ls_nombreTabla1, IdTabla1)
{
    ls_ordenTB = ordenTB;
    classFilaSelectID[ls_ordenTB] = classNuevaFila1;
    RowsTable = RowsTableTEMP[ordenTB];
    ColumnsTable = ColumnsTableTEMP[ordenTB];
    NombreCampoPKTB = NombreCampoPKTB1;
    ls_nombreTabla = ls_nombreTabla1;
    IdTabla = IdTabla1;

    //Se verifica si la fila selecionada es diferente borra el detalle de la tabla
    if (filaSelectID[ordenTB] != FilaIDTemp[ordenTB]) {
        eliminarDetalleSelectN();
        filaSelectID[ordenTB] = idNuevaFila1;        
    }
}

var NomelementColumnAnterior = "";
function getColumnNueva(index_row, index_column, nombre_column, ls_codigo_fk_select, boolNuevo, ordenTB, IdTabla1, idNuevaFila1) {
    
    if (filaSelectID[ordenTB].length = 0) {
        console.log("reasigno valor");
        filaSelectID[ordenTB] = idNuevaFila1;
    }

    IdTabla = IdTabla1;
    RowsTable = RowsTableTEMP[ordenTB];
    ColumnsTable = ColumnsTableTEMP[ordenTB];
    FilaIDTemp[ordenTB]=filaSelectID[ordenTB];
    filaSelectID[ordenTB] = 'N' + ordenTB + '' + index_row;
    NomelementColumn = "#col" + ordenTB + "_" + index_row + "_" + index_column;
    var elementColumn = $(NomelementColumn);
    if (NomelementColumnAnterior != NomelementColumn) {
        eliminarObjeto();
        crearObjeto(nombre_column, elementColumn, index_row, ls_codigo_fk_select, boolNuevo);
        NomelementColumnAnterior = NomelementColumn;
    }
    activarTabla();
    
}
function getUltimoIdRowTabla() {
    var ultimoIRRowTabla = "";
    $("#" + IdTabla + " tbody tr").each(function () {

        try
        {
            if ($(this).attr("id").length > 0) {
                ultimoIRRowTabla = $(this).attr("id");
            }
        } catch (Error) {
            ultimoIRRowTabla = "";
        }
    });
    return ultimoIRRowTabla;
}
function getFirstIdRowTabla(IdTabla1) {
    var firstIDRowTabla = "";
    var cont = 0;
    $("#" + IdTabla1 + " tbody tr").each(function () {

        try
        {
            if ($(this).attr("id").length > 0) {
                cont++;
                if (cont == 1) {
                    firstIDRowTabla = $(this).attr("id");
                }
            }
        } catch (Error) {
            firstIDRowTabla = "";
        }
    });
    return firstIDRowTabla;
}
function loadTablas() {
    for (var i = 1; i < TablasRelacionadas.length; i++)
    {
        if (TablasRelacionadas[i].length > 0) {
           loadTB(TablasRelacionadas[i]);          
        }
    }
}
function loadTB(NomTabla) {
    var FristIdRowTabla = "";
    FristIdRowTabla = getFirstIdRowTabla(NomTabla);
    if (FristIdRowTabla.length > 0) {
            $("#" + FristIdRowTabla).click();      
    }else{console.log(NomTabla+" no tiene filas");}
}