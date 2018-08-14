var JSONRowsTabla;
var JSONColumnsTabla;
var RowsTable;
var ColumnsTable;
var ls_nombreTabla;
var SchemaBDD = 'mos_administracion';
var IdTabla = "";
var IdTablaAnt = "";
var ls_ordenTB = "";

var FilasEliminadasTB = [];
var FilasActualizadasTB = [];
var DataActualizadasTB = [];
var ColumnasNuevasTB = [];

var CodigoCampoPKTB = "";
var NombreCampoPKTB;

var IdColumnSelect = "";
var ElementoSeleccionado;
var ElementoSelectTemp;
var IndexSelect_row;
var JsonTablaH = "";
/*Bloquea la pantalla hasta que termine de gargar todos los elementos*/

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

$.extend($.expr[":"],
        {
            "contains-ci": function (elem, i, match, array)
            {
                return (elem.textContent || elem.innerText || $(elem).text() || "").toLowerCase().indexOf((match[3] || "").toLowerCase()) >= 0;
            }
        });
function cargar_TablaHija(nombreFK, valorFK, JsonTablaHija)
{
    JsonTablaH = jQuery.parseJSON(JsonTablaHija);
    JsonTablaH.ls_where = JsonTablaH.ls_where.toString().replace("0", valorFK);

    if (JsonTablaH.ls_IdDivTabla.length)
    {
        JsonTablaH.ls_valor_codigo_padre = valorFK;
        JsonTablaH.ls_valor_nombre_padre = nombreFK;
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
                        removeLoad();
                    }
                })
                .fail(function (jqXHR, textStatus, errorThrown) {
                    if (console && console.log) {
                        $('#' + JsonTablaH.ls_IdDivTabla.toString().trim() + " tbody").html("");
                        console.log("La solicitud a fallado: " + textStatus);
                        removeLoad();
                    }
                });
    }
}
function busquedaTabla()
{   //Setea el objeto anterior modificado para edicion
    clearObject();
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
function transformarDato(TypeData, Dato)
{
    TypeData = TypeData.toUpperCase();
    if (TypeData === "CHARACTER" || TypeData === "CHAR" || TypeData === "VARCHAR")
    {
        Dato = "\'" + Dato + "\'";
    }
    return Dato;
}
function getInsert()
{
    //Si es una tabla Hija se consulta el codigo FK y el nombre de columna para colocarlo por defecto
    var valorDefectoFK = "";
    var nombreCampoFK = "";
    try {
        nombreCampoFK = JsonTablaH.ls_nombre_campo_padre;
        valorDefectoFK = JsonTablaH.ls_valor_codigo_padre;
    } catch (err) {
        console.log(err.message);
    }
    //Setea el objeto anterior modificado para edicion
    clearObject();
    var index_data = "";
    var index_temp = "";
    var pos = 0;
    var nombreColumn = "";
    var elementoColumn = "";
    var valorColumn = "";
    var insertRowTable = "INSERT INTO " + SchemaBDD + "." + ls_nombreTabla + " (";
    var insertColumnsRow = "";
    var insertValorRow = "";
    var vectorInserts = [];
    //console.log(ColumnasNuevasTB);
    for (var i = 0; i < ColumnasNuevasTB.length; i++)
    {  //Ordena por el Ordende cada una de las tablas para crear
        var OrdeTabla = ColumnasNuevasTB[i].split("¡");
        OrdeTabla.sort();
        //console.log(OrdeTabla);
        console.log("Data-------------------");
        var Data = OrdeTabla[0].split(":");
        if (Data.length == 1) {
            Data = OrdeTabla[1].split(":");
        }


        console.log(Data);
        var TypeData = "";
        index_data = Data[0];

        var DataColumn = Data[1].split("=");


        nombreColumn = DataColumn[0];
        elementoColumn = DataColumn[1];
        var nombreColumnTemp = nombreColumn.replace(/'/g, "");
        valorColumn = $("#" + elementoColumn).text().trim();
        //Verificar si la columna es una FK en caso de ser se busca con el Valor el código FK Correspondiente 
        //Y se actualiza el valor
        var index_column = findIndexColumn(nombreColumnTemp);
        var boolFK_column = getBoolFK(index_column);
        if (boolFK_column === true)
        {
            valorColumn = findCodigoSelect(index_column, valorColumn);
        }
        if (i === 0)
        {
            if (NombreCampoPKTB !== nombreColumnTemp)
            {
                TypeData = findTypeColumn(nombreColumnTemp);
                valorColumn = transformarDato(TypeData, valorColumn);
                //condicion que aplica para tablas hijas
                if (nombreCampoFK == nombreColumnTemp) {
                    valorColumn = valorDefectoFK;
                }

                insertColumnsRow += nombreColumnTemp + "?";
                insertValorRow += valorColumn + "?";
            }
        } else
        {
            if (index_data === index_temp)
            {
                if (NombreCampoPKTB !== nombreColumnTemp)
                {
                    TypeData = findTypeColumn(nombreColumnTemp);
                    valorColumn = transformarDato(TypeData, valorColumn);
                    //condicion que aplica para tablas hijas
                    if (nombreCampoFK == nombreColumnTemp) {
                        valorColumn = valorDefectoFK;
                    }

                    insertColumnsRow += nombreColumnTemp + "?";
                    insertValorRow += valorColumn + "?";
                }
            } else
            {
                insertColumnsRow = insertColumnsRow.slice(0, -1);
                insertValorRow = insertValorRow.slice(0, -1);

                insertRowTable += insertColumnsRow + ") VALUES (" + insertValorRow + ");";
                vectorInserts[pos] = insertRowTable;
                pos++;
                insertRowTable = "INSERT INTO " + SchemaBDD + "." + ls_nombreTabla + " (";
                insertColumnsRow = "";
                insertValorRow = "";
                if (NombreCampoPKTB !== nombreColumnTemp)
                {
                    TypeData = findTypeColumn(nombreColumnTemp);
                    valorColumn = transformarDato(TypeData, valorColumn);
                    //condicion que aplica para tablas hijas
                    if (nombreCampoFK == nombreColumnTemp) {
                        valorColumn = valorDefectoFK;
                    }

                    insertColumnsRow += nombreColumnTemp + "?";
                    insertValorRow += valorColumn + "?";
                }

            }
        }
        index_temp = index_data;
    }
    insertColumnsRow = insertColumnsRow.slice(0, -1);
    insertValorRow = insertValorRow.slice(0, -1);

    insertRowTable += insertColumnsRow + ") VALUES (" + insertValorRow + ");";
    vectorInserts[pos] = insertRowTable;
    console.log(vectorInserts);
    return vectorInserts;

}

function insertRow()
{
    //Si es una tabla Hija se consulta el codigo FK y el nombre de columna para colocarlo por defecto
    var valorDefectoFK = "";
    var nombreCampoFK = "";
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
        }

    } catch (err)
    {
        console.log(err.message);
    }
    //Validacion en caso de que sea una pantalla hija debe contener el valor por defecto FK
    if (parseInt(ls_ordenTB) > 1 && nombreCampoFK == "" && valorDefectoFK == "")
    {
        alert("Seleccione una fila de la cabecera");
        console.log("Seleccione la tabla padre");
        return 0;
    }
    //Setear el CodigoCampoPKTB carcado en el evento onclick de cada columna
    CodigoCampoPKTB = "";
    //Setea el objeto anterior modificado para edicion
    clearObject();
    //Valida que se haya seleccionado la tabla para saber sobre cual agregar la nueva fila
    var styleColumn = "";
    if (IdTabla.length > 0)
    {
        //en caso de ser una tabla sin información se setea tbody para ingresar las nuevas columnas
        var tds = $("#" + IdTabla + " tbody tr td").length;
        if (tds === 1) {
            $("#" + IdTabla + " tbody").html("");
        }

        var nuevaFila = "";
        var Tcolumns = 0;
        var trs = $("#" + IdTabla + " tr").length - 2; //el -2 representa el # de headers de la tabla 
        var tds = $("#" + IdTabla + " tr td").length;
        Tcolumns = tds / trs;
        trs = trs + 1; //se suma mas uno al total de filas ya agregadas


        var nuevaFila = "<tr class='rowTabla" + ls_ordenTB + " rowNew' id='N" + trs + "' onclick='f_EstiloNewRow(this," + ls_ordenTB + "," + trs + ")'>";
        var index_col;
        if (Tcolumns > 0)
        {
            for (var j = 0; j < Tcolumns; j++)
            {
                // añadimos las columnas
                var valor = "";
                var nombreColumn = "'" + getNameColumn(j) + "'";
                //console.log("NombreCampoPKTB="+NombreCampoPKTB+" nombreColumn="+getNameColumn(j) );
                if (NombreCampoPKTB === getNameColumn(j)) {
                    valor = "N" + trs;
                } else if (getNameColumn(j) == nombreCampoFK) {
                    valor = valorDefectoFK;
                }

                index_col = findIndexColumn(nombreColumn.replace(/'/g, ""));
                if (ColumnsTable[index_col].lb_FK === true)
                {
                    nuevaFila += '<td ' + styleColumn + ' onclick="getColumn(' + trs + ',' + j + ',' + nombreColumn + ',1,true,NombreCampoPKTB,IdTabla,JSONRowsTabla,JSONColumnsTabla,ls_nombreTabla,ls_ordenTB)" id="col' + ls_ordenTB + '_' + trs + '_' + j + '">' + valor + '</td>';
                } else
                {
                    nuevaFila += '<td ' + styleColumn + ' onclick="getColumn(' + trs + ',' + j + ',' + nombreColumn + ',0,true,NombreCampoPKTB,IdTabla,JSONRowsTabla,JSONColumnsTabla,ls_nombreTabla,ls_ordenTB)" id="col' + ls_ordenTB + '_' + trs + '_' + j + '">' + valor + '</td>';
                }

                ColumnasNuevasTB[ColumnasNuevasTB.length] = ls_ordenTB + "¡" + trs + ":" + nombreColumn + "=" + "col" + ls_ordenTB + "_" + trs + "_" + j;
            }
        } else
        {
            for (var j = 0; j < ColumnsTable.length; j++)
            {
                // añadimos las columnas
                var valor = "";
                var nombreColumn = "'" + getNameColumn(j) + "'";
                if (NombreCampoPKTB === getNameColumn(j)) {
                    valor = "N";
                } else if (getNameColumn(j) == nombreCampoFK) {
                    valor = valorDefectoFK;
                }

                index_col = findIndexColumn(nombreColumn.replace(/'/g, ""));

                if (ColumnsTable[index_col].lb_FK === true)
                {
                    nuevaFila += '<td ' + styleColumn + ' onclick="getColumn(' + trs + ',' + j + ',' + nombreColumn + ',1,true,NombreCampoPKTB,IdTabla,JSONRowsTabla,JSONColumnsTabla,ls_nombreTabla,ls_ordenTB)" id="col' + ls_ordenTB + '_' + trs + '_' + j + '">' + valor + '</td>';
                } else
                {
                    nuevaFila += '<td ' + styleColumn + ' onclick="getColumn(' + trs + ',' + j + ',' + nombreColumn + ',0,true,NombreCampoPKTB,IdTabla,JSONRowsTabla,JSONColumnsTabla,ls_nombreTabla,ls_ordenTB)" id="col' + ls_ordenTB + '_' + trs + '_' + j + '">' + valor + '</td>';
                }

                ColumnasNuevasTB[ColumnasNuevasTB.length] = ls_ordenTB + "¡" + trs + ":" + nombreColumn + "=" + "col" + ls_ordenTB + "_" + trs + "_" + j;
            }
        }
        console.log(ColumnasNuevasTB);
        // Añadimos una columna con el numero total de columnas.
        nuevaFila += "</tr>";
        $("#" + IdTabla).append(nuevaFila);
        nuevaFila = "";
    } else {
        console.log("Seleccione su Tabla");
        nuevaFila = "";
    }
}
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
function getUpdate()
{
    var UpdateTable = "";
    var IDTabla = "";
    var CamposSet = "";
    var IDTablaTemp = "";
    var VectorUpdate = [];
    var pos = 0;
    DataActualizadasTB.sort();

    for (var i = 0; i < DataActualizadasTB.length; i++)
    {
        var VectorData = DataActualizadasTB[i].split(":");
        IDTabla = VectorData[0];
        CamposSet = VectorData[1];
        if (i === 0)
        {
            UpdateTable += CamposSet + "?";
        } else
        {
            if (IDTabla === IDTablaTemp)
            {
                UpdateTable += CamposSet + "?";
            } else
            {
                UpdateTable = UpdateTable.slice(0, -1);
                VectorUpdate[pos] = "UPDATE " + SchemaBDD + "." + ls_nombreTabla + " SET " + UpdateTable + " WHERE " + NombreCampoPKTB + "=" + IDTablaTemp;
                pos++;
                UpdateTable = "";
                UpdateTable += CamposSet + "?";
            }
        }
        IDTablaTemp = IDTabla;
    }
    if (DataActualizadasTB.length > 0)
    {
        UpdateTable = UpdateTable.slice(0, -1);//elimino la ultima posicion de la cadena
        VectorUpdate[pos] = "UPDATE " + SchemaBDD + "." + ls_nombreTabla + " SET " + UpdateTable + " WHERE " + NombreCampoPKTB + "=" + IDTabla;
    }
    return VectorUpdate;
}
function updateColumn()
{

    var TypeData = "";
    var valorColumn = "";
    TypeData = findTypeColumn(IdColumnSelect);
    valorColumn = $("#" + IdColumnSelect).val();
    // console.log(TypeData);
    var indexFilasActualizadasTB;
    //Busca el ID dentro de FilasActualizadasTB para no guardar dos veces el mismo
    indexFilasActualizadasTB = FilasActualizadasTB.indexOf($.trim(CodigoCampoPKTB + ":" + IdColumnSelect));
    valorColumn = transformarDato(TypeData, valorColumn);
    //validar clave prmaria en caso de ser registro nuevo
    var codigoPK = CodigoCampoPKTB.split("=");
    if (codigoPK[0] !== "N")
    {
        if (indexFilasActualizadasTB >= 0)
        {
            DataActualizadasTB[indexFilasActualizadasTB] = $.trim(CodigoCampoPKTB + ":" + IdColumnSelect + "=" + valorColumn);
        } else
        {
            FilasActualizadasTB[FilasActualizadasTB.length] = $.trim(CodigoCampoPKTB + ":" + IdColumnSelect);
            DataActualizadasTB[DataActualizadasTB.length] = $.trim(CodigoCampoPKTB + ":" + IdColumnSelect + "=" + valorColumn);
        }
    }
    //console.log(DataActualizadasTB);

}

function findTypeColumn(nombre_column)
{
    var nombre_columnCompare = "";
    var TypeData = "";
    if (RowsTable.length > 0) {

        for (var i in RowsTable[0].lista_gridColumn)//Columnas del Objeto
        {
            if (RowsTable[0].lista_gridColumn.hasOwnProperty(i)) //Verificacion de que exista datos
            {
                var nombre_columnCompare = RowsTable[0].lista_gridColumn[i].ls_nombre_column;
                if (nombre_columnCompare === nombre_column)
                {
                    TypeData = RowsTable[0].lista_gridColumn[i].data_column.is_nullable;
                    break;
                }
            }
        }
    } else
    {
        for (var i in ColumnsTable)//Columnas del Objeto
        {
            if (ColumnsTable.hasOwnProperty(i)) //Verificacion de que exista datos
            {
                var nombre_columnCompare = ColumnsTable[i].ls_nombre_column;
                if (nombre_columnCompare === nombre_column)
                {
                    TypeData = ColumnsTable[i].data_column.is_nullable;
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
function findCodigoSelect(index_column, valor)
{
    var codigo = "";
    var codigoSelect = ColumnsTable[index_column].data_dropdown.codigo_dw;
    var valorSelect = ColumnsTable[index_column].data_dropdown.valor_dw;
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
    TypeData = findTypeColumn(nombre_column);

    if (TypeData !== "")
    {
        TypeData = TypeData.toUpperCase();
        //Setea el objeto anterior modificado
        clearObject();
        var textoCampo = $(elementColumn).text();
        if (NombreCampoPKTB !== nombre_column)
        {
            if (TypeData === "CHARACTER" || TypeData === "CHAR" || TypeData === "INTEGER")
            {
                if (ls_codigo_fk_select == "0")//Verifica si se un campo de (0=Input) (1=Select)
                {
                    campo = "<input onkeyup='updateColumn()' class='form-control' type='text' size='20' id='" + nombre_column + "' name='" + nombre_column + "' value='" + $.trim(textoCampo) + "' />";
                } else
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
                            console.log("Columna nueva=" + valorDefctoColumn);
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
            clearObject();
            IdColumnSelect = "";
        }
    }
}
function clearObject()
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
function getColumn(index_row, index_column, nombre_column, ls_codigo_fk_select, boolNuevo, NombreCampPK, IdTabla1, jsonRows, jsonColumns, nombreTabla, ordenTB)
{
    if (setTabla(IdTabla1, NombreCampPK, jsonRows, jsonColumns, nombreTabla, ordenTB) === 1)
    {
        if (IdColumnSelect !== nombre_column || IndexSelect_row !== index_row)
        {
            console.log("Fila #:" + index_row + " Columna #:" + index_column + " Nombre Columna: " + nombre_column);
            var elementColumn = $("#col" + ordenTB + "_" + index_row + "_" + index_column);
            crearObjeto(nombre_column, elementColumn, index_row, ls_codigo_fk_select, boolNuevo);
        }
    }
}
function getRow(ElementoSeleccionad, IdRowFocu, NombreCampPK, IdTabla1, jsonRows, jsonColumns, nombreTabla, ordenTB)
{
    ElementoSeleccionado = ElementoSeleccionad;
    CodigoCampoPKTB = IdRowFocu;
    if (setTabla(IdTabla1, NombreCampPK, jsonRows, jsonColumns, nombreTabla, ordenTB) === 1)
    {
        f_EstiloRow();
    }
}
function f_EstiloNewRow(element, ordenRow, trs)//Actualiza el Estado y rescribe CodigoCampoPKTB
{
    CodigoCampoPKTB = "N=rowActive" + ordenRow + "=" + ordenRow + "¡" + trs;
    var trActivo = document.getElementsByClassName("rowActive" + ordenRow);
    for (var j = 0; j < trActivo.length; j++)
    {
        trActivo[j].className = trActivo[j].className.replace("rowActive" + ordenRow, "");
    }
    $(element).addClass("rowTabla" + ordenRow + " rowActive" + ordenRow + " rowNew");
}
function f_EstiloRow()
{
    var tabla = document.getElementById(IdTabla);
    var tr = tabla.getElementsByClassName("rowTabla" + ls_ordenTB);
    var trActivo = document.getElementsByClassName("rowActive" + ls_ordenTB);
    for (var j = 0; j < trActivo.length; j++)
    {
        trActivo[j].className = trActivo[j].className.replace("rowActive" + ls_ordenTB, "");
    }
    $(ElementoSeleccionado).addClass("rowTabla" + ls_ordenTB + " rowActive" + ls_ordenTB + "");
}
function deletRow()
{

    if (CodigoCampoPKTB.length > 0)//Verifica si exite una clave Primaria
    {
        var codigoCampoTempora = CodigoCampoPKTB.split("=");//en el caso de una fila nueva CodigoCampoPKTB se rescribe

        if (codigoCampoTempora[0] != "N")//N significa que si es una columna Nueva
        {
            $(ElementoSeleccionado).remove();
            var pos = FilasEliminadasTB.length;
            FilasEliminadasTB[pos] = "DELETE FROM " + SchemaBDD + "." + ls_nombreTabla + " WHERE " + NombreCampoPKTB + "=" + CodigoCampoPKTB;
            console.log(FilasEliminadasTB);
        } else
        {
            console.log("Fila Nueva Eliminada");
            $("." + codigoCampoTempora[1]).remove();
            deleteRowCadenaInsert(codigoCampoTempora[2]);
        }
    }
}
function deleteRowCadenaInsert(Row)
{
    Row = $.trim(Row);
    var stringCompare = "";
    var longVector = ColumnasNuevasTB.length;
    for (var j = 0; j < longVector; j++)
    {
        for (var i = 0; i < ColumnasNuevasTB.length; i++)
        {
            stringCompare = $.trim(ColumnasNuevasTB[i]);
            stringCompare = stringCompare.split(":");
            if (stringCompare[0] == Row)
            {
                ColumnasNuevasTB.splice(i, 1);
            }
        }
    }

    console.log(ColumnasNuevasTB);
}
function saveTable()
{
    if (FilasEliminadasTB.length > 0)
    {
        console.log("Save FilasEliminadasTB " + FilasEliminadasTB.length);
        $('#contenedor_tablas').load('EnviarData', "Data=" + FilasEliminadasTB);
        FilasEliminadasTB = [];
    }

    if (DataActualizadasTB.length > 0)
    {
        var data = getUpdate();
        console.log("Save DataActualizadasTB: " + data.length);
        if (data.length > 0)
        {
            $('#contenedor_tablas').load('EnviarData', "Data=" + data);
        }
        console.log(FilasActualizadasTB);
        console.log(data);
        DataActualizadasTB = [];
        FilasActualizadasTB = [];

    }
    if (ColumnasNuevasTB.length > 0)
    {
        var data = getInsert();
        console.log("Save ColumnasNuevasTB: " + data);
        $('#contenedor_tablas').load('EnviarData', "Data=" + data);
        ColumnasNuevasTB = [];
    }
    CodigoCampoPKTB = "";

    //Setea el objeto anterior modificado
    clearObject();
    /*  $.ajax({
     
     url : "SaveData",
     success : function(data) {
     console.log("SUCCESS: ", data);
     
     // $("#tabla").html(data);
     },
     error : function(e) {
     console.log("ERROR: ", e);
     
     },
     done : function(e) {
     console.log("DONE");
     }
     }); */
}

function setTabla(IdTabla1, NombreCampPK, jsonRows, jsonColumns, nombreTabla, ordenTB)
{
    IdTabla = IdTabla1;
    NombreCampoPKTB = NombreCampPK;
    JSONRowsTabla = jsonRows;
    JSONColumnsTabla = jsonColumns;
    RowsTable = jQuery.parseJSON(JSONRowsTabla);
    ColumnsTable = jQuery.parseJSON(JSONColumnsTabla);
    ls_nombreTabla = nombreTabla;
    console.log("nombre tb=" + ls_nombreTabla);
    ls_ordenTB = ordenTB;
    activarTabla();
    return 1;
}
function setToolTip(element, title_tool)
{
    $(element).tooltip({title: title_tool, placement: "bottom", animation: true});
}
function cargarTabla()
{
    $('#s_usuario').DataTable({retrieve: true});
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