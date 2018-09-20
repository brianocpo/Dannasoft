/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.Objects;

import com.google.gson.Gson;
import core.Config.CrudGenerico;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

/**
 *
 * @author brian
 */
public class obj_tabla implements Serializable {

    private List<obj_row> lista_gridRow;
    private List<obj_column> lista_gridColumn;
    private String ls_catalog;
    private String ls_schema;
    private String ls_name_tabla;
    private String ls_nombre_colPK;
    private String ls_query;
    private String ls_where;
    private String ls_orden;
    private String htmlTabla;
    private String ls_Id_Tabla;
    private String ls_ordenTB;
    private String ls_AltoTabla;
    private String TituloTabla = "";
    private Boolean lb_cargaHija;
    //En caso de que esta tabla llame a otra
    private String ls_nombre_campo_padre; //nombre del campo codigo de la tabla  : Ejem: codigo_pai
    private String ls_valor_codigo_padre;
    private String ls_valor_nombre_padre;
    private String ls_condicion;
    private String ls_IdDivTabla;
    private String ls_classTabla;
    private String ls_classTablaPadre;
    //Datos para la paguinacion
    private obj_paginador paginador;
    private Integer li_num_reg_x_pagina;
    private Boolean lb_paginar;
    private Integer offset;
    private Integer li_pagina_actual;
    private String  ls_paginacion;
    private Integer li_total_rows;
    private Integer li_num_pag_visibles;     
    //Estructura OBJETO JSON
    private String ObjsonTabla;
    //Fila
    private String ls_idFilaSeleccionada;
    //Solo lectura
    private boolean lb_readonly;
    
    public obj_tabla() {
        this.htmlTabla = "";
        this.ls_Id_Tabla = "";
        this.ls_nombre_colPK = "";
        this.ls_ordenTB = "";
        this.ls_AltoTabla = "";
        this.TituloTabla = "";
        this.lb_cargaHija = false;
        this.ls_nombre_campo_padre = "";
        this.ls_valor_codigo_padre = "";
        this.ls_condicion = "";
        this.ls_IdDivTabla = "";
        this.ls_valor_nombre_padre = "";
        this.ls_classTabla = "";
        this.ls_classTablaPadre = "";
        this.paginador=new obj_paginador();
        this.li_num_reg_x_pagina=10;
        this.lb_paginar=false;
        this.offset=0;
        this.ls_paginacion="";
        this.li_total_rows=0;
        this.ObjsonTabla="";
        this.li_pagina_actual=1;
        this.ls_idFilaSeleccionada="";
        this.li_num_pag_visibles=5; 
        this.lb_readonly=false;
    }

    public void configTabla(String ls_catalog, String ls_schema, String ls_name_tabla, String ls_query, String ls_where, String ls_orden) {
        this.lista_gridRow = new ArrayList<obj_row>();
        this.lista_gridColumn = new ArrayList<obj_column>();
        this.ls_catalog = ls_catalog;
        this.ls_schema = ls_schema;
        this.ls_name_tabla = ls_name_tabla;
        this.ls_query = ls_query;
        this.ls_where = ls_where;
        this.ls_orden = ls_orden;
    }

    public void crearTabla() {
       
        CrudGenerico EstructuraBDD = new CrudGenerico();
        if(lb_paginar==false){//Paginación 
           this.ls_paginacion="";
        }else{           
            this.li_total_rows=EstructuraBDD.getTotalRegistros(ls_schema, ls_name_tabla,this.ls_where);
            this.ls_paginacion="  limit "+ this.li_num_reg_x_pagina+" offset "+this.offset;
            this.paginador.configurar(this.li_total_rows, this.li_num_reg_x_pagina, this.ls_Id_Tabla+"_paginador",li_pagina_actual,ls_ordenTB,li_num_pag_visibles);
        }   
        EstructuraBDD.crearListArrayRowsTable(this.ls_catalog, this.ls_schema, this.ls_name_tabla, this.ls_query, this.ls_where, this.ls_orden, this.ls_paginacion);
        //Objetos para almacenar las Filas del Grid
        this.lista_gridRow = EstructuraBDD.getLista_gridRow();
        this.lista_gridColumn = EstructuraBDD.getLista_gridColumn();
    }

    public void ocultarColumna(String nombre_column) {
        String ls_columna = "";
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < this.lista_gridRow.get(i).getLista_gridColumn().size(); j++) {
                ls_columna = this.lista_gridRow.get(i).getLista_gridColumn().get(j).getLs_nombre_column();
                if (nombre_column.equals(ls_columna)) {
                    this.lista_gridRow.get(i).getLista_gridColumn().get(j).setLb_visible(false);
                }
            }
        }
        for (int k = 0; k < this.lista_gridColumn.size(); k++) {
            ls_columna = lista_gridColumn.get(k).getLs_nombre_column();
            if (nombre_column.equals(ls_columna)) {
                lista_gridColumn.get(k).setLb_visible(false);
            }
        }
    }

    public String getlista_gridColumn_nombre_colPK() {
        for (int j = 0; j < this.lista_gridColumn.size(); j++) {
            if (lista_gridColumn.get(j).getLb_PK()) {
                this.ls_nombre_colPK = lista_gridColumn.get(j).getLs_nombre_column();
                break;
            }
        }
        return this.ls_nombre_colPK;
    }

    public String getlista_gridRow_nombre_colPK() {
        if(lista_gridRow.size()>0){
            for (int j = 0; j < this.lista_gridRow.get(0).getLista_gridColumn().size(); j++) {
                if (lista_gridRow.get(0).getLista_gridColumn().get(j).getLb_PK()) {
                    this.ls_nombre_colPK = lista_gridRow.get(0).getLista_gridColumn().get(j).getLs_nombre_column();
                }
            } 
        }       
        return this.ls_nombre_colPK;
    }

    public String getTablaHtml() {
        this.htmlTabla="<div class='row titulotb'><div class='col-md-12'><h4 class='header smaller lighter blue'>"+getTituloTabla()+"</h4></div></div>";
        getlista_gridColumn_nombre_colPK();
        String ls_classRow = "";
        //Paginador
        if(getLb_paginar()){
            this.htmlTabla+=this.paginador.crearPaginador();
        }
        
        //TABLA
        String ls_width = "0";
        String htmlTablaHeadTH = "";
        String htmlTablaBodyTR = "";
        String htmlTablaBodyTD = "";
        String ls_IdRow = "";
        String ls_nombreColumn = "";
        String ls_valor_column = "";
        this.htmlTabla+= "<div class='table-responsive'>";
        this.htmlTabla+= "<table  style='height:" + ls_AltoTabla + "' class='table table-responsive table-bordered Grid-" + this.ls_ordenTB + "' id='" + this.ls_Id_Tabla + "'>";
        
        //CABECERA TABLA
        this.htmlTabla+= "<thead>";                
        //Se valida que lista_gridRow tenga filas en caso de no ser así solo se consulta las cabeceras de columnas
        if (0 == this.lista_gridRow.size()) {
            //setear this.ls_nombre_colPK           
            for (int k = 0; k < this.lista_gridColumn.size(); k++) {
                ls_width = getWidthColumn(lista_gridColumn.get(k));
                htmlTablaHeadTH = htmlTablaHeadTH + "<th scope='col'  width='" + ls_width + "' class='rowTablaThead" + ls_ordenTB + "' onclick=\"inicializarTabla('" + this.ls_Id_Tabla + "','" + this.ls_nombre_colPK + "',jsonRows" + ls_ordenTB + ",jsonColumns" + ls_ordenTB + ",'" + this.ls_name_tabla + "','" + ls_ordenTB + "')\">" + lista_gridColumn.get(k).getLs_nombre_column() + "</th>";
            }
        }
        else {
            for (int i = this.lista_gridRow.size() - 1; i < this.lista_gridRow.size(); i++) {   //setear this.ls_nombre_colPK               
                for (int k = 0; k < this.lista_gridRow.get(i).getLista_gridColumn().size(); k++) {
                    ls_width = getWidthColumn(lista_gridRow.get(i).getLista_gridColumn().get(k));
                    htmlTablaHeadTH = htmlTablaHeadTH + "<th scope='col'  width='" + ls_width + "' class='rowTablaThead" + ls_ordenTB + "' onclick=\"inicializarTabla('" + this.ls_Id_Tabla + "','" + this.ls_nombre_colPK + "',jsonRows" + ls_ordenTB + ",jsonColumns" + ls_ordenTB + ",'" + this.ls_name_tabla + "','" + ls_ordenTB + "')\">" + lista_gridRow.get(i).getLista_gridColumn().get(k).getLs_nombre_column() + "</th>";
                }
            }
        }
        this.htmlTabla+= htmlTablaHeadTH;
        this.htmlTabla+= "</thead>";
        
        //CUERPO DE LA TABLA
        this.htmlTabla+= "<tbody>";
        String cargar_TablaHija = "";
        String getColumn = "";
        String display="";
        for (int i = 0; i < this.lista_gridRow.size(); i++) {          
            ls_classRow = this.ls_ordenTB + "_row_"+ i;
            this.ls_classTabla = ls_classRow;
            htmlTablaBodyTD = "";
            ls_IdRow = "CodPK";
            cargar_TablaHija = "";
            getColumn="";
            for (int j = 0; j < this.lista_gridRow.get(i).getLista_gridColumn().size(); j++) {
                display="";
                ls_width = getWidthColumn(lista_gridRow.get(i).getLista_gridColumn().get(j));
                ls_nombreColumn = lista_gridRow.get(i).getLista_gridColumn().get(j).getLs_nombre_column();
                //Dropdowns
                String ls_codigo_fk_select = "";
                if (lista_gridRow.get(i).getLista_gridColumn().get(j).getLb_PK()) {
                    ls_IdRow = lista_gridRow.get(i).getLista_gridColumn().get(j).getLs_valor();
                    this.ls_nombre_colPK = lista_gridRow.get(i).getLista_gridColumn().get(j).getLs_nombre_column();
                    display="style='display:none'";
                }
                if (lista_gridRow.get(i).getLista_gridColumn().get(j).getLb_FK()) {
                    ls_codigo_fk_select = lista_gridRow.get(i).getLista_gridColumn().get(j).getData_dropdown().getCodigo_fk_select().trim();
                    ls_valor_column = lista_gridRow.get(i).getLista_gridColumn().get(j).getData_dropdown().getValor_fk_select().trim();
                    display="style='display:none'";
                } else {
                    ls_codigo_fk_select = "0";
                    ls_valor_column = lista_gridRow.get(i).getLista_gridColumn().get(j).getLs_valor().trim();
                }
                //Tabla en modo lectura
                if(this.lb_readonly==true){
                    getColumn="";
                }else{
                    getColumn="getColumn(" + i + "," + j + ",'" + ls_nombreColumn + "','" + ls_codigo_fk_select + "',false,'" + this.ls_nombre_colPK + "','" + this.ls_Id_Tabla + "',jsonRows" + ls_ordenTB + ",jsonColumns" + ls_ordenTB + ",'" + this.ls_name_tabla + "','" + this.ls_ordenTB + "','" + ls_IdRow + "','R"+this.ls_ordenTB+"_"+i+"')";
                }
                
                htmlTablaBodyTD += "<td scope='col' "+display+"   width='" + ls_width + "' id='col" + this.ls_ordenTB + "_" + i + "_" + j + "' onclick=\""+getColumn+"\">" + ls_valor_column + "</td>";
                //Carga tabla Hija
                if (this.lb_cargaHija == true) {
                    cargar_TablaHija = ";onLoad();cargar_TablaHija('" + ls_valor_column + "','" + ls_IdRow + "'," + ls_ordenTB + ",'" + this.ls_classTabla + "')";
                }else{cargar_TablaHija ="";}
            }
            
            htmlTablaBodyTD=htmlTablaBodyTD.replaceAll("CodPK",ls_IdRow );
            htmlTablaBodyTR += "<tr id='R" + this.ls_ordenTB + "_" + i + "' scope='row'    onclick=\"getRow(this,'" + ls_IdRow + "','" + this.ls_nombre_colPK + "','" + this.ls_Id_Tabla + "',jsonRows" + ls_ordenTB + ",jsonColumns" + ls_ordenTB + ",'" + this.ls_name_tabla + "','" + this.ls_ordenTB + "','" + ls_classRow + "','R"+this.ls_ordenTB+"_"+i+"')" + cargar_TablaHija + "\"  class=\"rowTabla" + ls_ordenTB + " " + this.ls_classTabla + " " + this.ls_classTablaPadre + "\">";
            htmlTablaBodyTR += htmlTablaBodyTD;
            htmlTablaBodyTR += "</tr>";
        }
       
        this.htmlTabla+= htmlTablaBodyTR;
        this.htmlTabla+= "</tbody>";
        this.htmlTabla +="</table>";  
        this.htmlTabla+= "</div>";
        
        
        //Data que será pasada para el CRUD en cada TABLA
        String jsonRows = new Gson().toJson(getLista_gridRow());
        String jsonColumns = new Gson().toJson(getLista_gridColumn());
        //Script para la configuracion de Datatable Jquery 
        String scriptTabla = " <script type='text/javascript' >"
                + "var jsonColumns" + ls_ordenTB + "='" + jsonColumns + "';  "
                + "var jsonRows" + ls_ordenTB + "='" + jsonRows + "';  "
                + "var ordenTabla" + ls_ordenTB + "='" + ls_ordenTB + "';  "
                + "TablasRelacionadas[" + ls_ordenTB + "]='" + this.ls_Id_Tabla + "';"
                + "ObjsonTabla[" + ls_ordenTB + "]='" + this.ObjsonTabla + "';  "
                + "nombre_campo_padre[" + ls_ordenTB + "]='" + this.ls_nombre_campo_padre + "';  "                 
                + " </script>";
        //Estilo Tabla
        String styleTabla = "<style type='text/css'>"
                + ".rowTabla" + ls_ordenTB + " {\n"
                + "            border: none;\n"
                + "            outline: none;\n"
                + "            padding: 10px 16px;\n"
                + "            background-color: #ffffff;\n"
                + "            cursor: pointer;\n"
                + "            font-size: 11px;\n"
                + "        }"
                + ".rowTabla" + ls_ordenTB + ":hover {background-color:  #286090 }"
                + ".rowActive" + ls_ordenTB + " {background-color:  #286090; color: #ffffff}"
                + ".rowTablaThead" + ls_ordenTB + " {cursor:  pointer }"
                + "</style>";

        htmlTabla += scriptTabla + styleTabla ;
        
        return htmlTabla;
    }


    public String getWidthColumn(obj_column Columna) {
        String ls_width_columm = "";
        obj_data dataColumn = new obj_data();
        dataColumn = Columna.getData_column();
        dataColumn.getData_type();
        try {
            if (dataColumn.getLongitud() == 0) {
                ls_width_columm = "20px";
            } else if (dataColumn.getLongitud() < 30) {
                ls_width_columm = "40px";
            } else if (dataColumn.getLongitud() >= 30 && dataColumn.getLongitud() < 50) {
                ls_width_columm = "150px";
            } else if (dataColumn.getLongitud() >= 50) {
                ls_width_columm = "200px";
            }

        } catch (Exception e) {
            ls_width_columm = "50px";
        }

        return ls_width_columm;
    }

    public List<obj_row> getLista_gridRow() {
        return lista_gridRow;
    }

    public void setLista_gridRow(List<obj_row> lista_gridRow) {
        this.lista_gridRow = lista_gridRow;
    }

    public String getLs_catalog() {
        return ls_catalog;
    }

    public void setLs_catalog(String ls_catalog) {
        this.ls_catalog = ls_catalog;
    }

    public String getLs_schema() {
        return ls_schema;
    }

    public void setLs_schema(String ls_schema) {
        this.ls_schema = ls_schema;
    }

    public String getLs_name_tabla() {
        return ls_name_tabla;
    }

    public void setLs_name_tabla(String ls_name_tabla) {
        this.ls_name_tabla = ls_name_tabla;
    }

    public String getLs_query() {
        return ls_query;
    }

    public void setLs_query(String ls_query) {
        this.ls_query = ls_query;
    }

    public String getLs_where() {
        return ls_where;
    }

    public void setLs_where(String ls_where) {
        this.ls_where = ls_where;
    }

    public String getLs_nombre_colPK() {
        return ls_nombre_colPK;
    }

    public void setLs_nombre_colPK(String ls_nombre_colPK) {
        this.ls_nombre_colPK = ls_nombre_colPK;
    }

    public String getLs_Id_Tabla() {
        return ls_Id_Tabla;
    }

    public void setLs_Id_Tabla(String ls_Id_Tabla) {
        this.ls_Id_Tabla = ls_Id_Tabla;
    }

    public List<obj_column> getLista_gridColumn() {
        return lista_gridColumn;
    }

    public void setLista_gridColumn(List<obj_column> lista_gridColumn) {
        this.lista_gridColumn = lista_gridColumn;
    }

    public String getLs_ordenTB() {
        return ls_ordenTB;
    }

    public void setLs_ordenTB(String ls_ordenTB) {
        this.ls_ordenTB = ls_ordenTB;
    }

    public String getLs_AltoTabla() {
        return ls_AltoTabla;
    }

    public void setLs_AltoTabla(String ls_AltoTabla) {
        this.ls_AltoTabla = ls_AltoTabla;
    }

    public String getTituloTabla() {
        return TituloTabla;
    }

    public void setTituloTabla(String TituloTabla) {
        this.TituloTabla = TituloTabla;
    }

    public Boolean getLb_cargaHija() {
        return lb_cargaHija;
    }

    public void setLb_cargaHija(Boolean lb_cargaHija) {
        this.lb_cargaHija = lb_cargaHija;
    }

    public String getLs_orden() {
        return ls_orden;
    }

    public void setLs_orden(String ls_orden) {
        this.ls_orden = ls_orden;
    }

    public String getHtmlTabla() {
        return htmlTabla;
    }

    public void setHtmlTabla(String htmlTabla) {
        this.htmlTabla = htmlTabla;
    }

    public String getLs_condicion() {
        return ls_condicion;
    }

    public void setLs_condicion(String ls_condicion) {
        this.ls_condicion = ls_condicion;
    }

    public String getLs_IdDivTabla() {
        return ls_IdDivTabla;
    }

    public void setLs_IdDivTabla(String ls_IdDivTabla) {
        this.ls_IdDivTabla = ls_IdDivTabla;
    }

    public String getLs_nombre_campo_padre() {
        return ls_nombre_campo_padre;
    }

    public void setLs_nombre_campo_padre(String ls_nombre_campo_padre) {
        this.ls_nombre_campo_padre = ls_nombre_campo_padre;
    }

    public String getLs_valor_codigo_padre() {
        return ls_valor_codigo_padre;
    }

    public void setLs_valor_codigo_padre(String ls_valor_codigo_padre) {
        this.ls_valor_codigo_padre = ls_valor_codigo_padre;
    }

    public String getLs_valor_nombre_padre() {
        return ls_valor_nombre_padre;
    }

    public void setLs_valor_nombre_padre(String ls_valor_nombre_padre) {
        this.ls_valor_nombre_padre = ls_valor_nombre_padre;
    }

    public String getLs_classTabla() {
        return ls_classTabla;
    }

    public void setLs_classTabla(String ls_classTabla) {
        this.ls_classTabla = ls_classTabla;
    }

    public String getLs_classTablaPadre() {
        return ls_classTablaPadre;
    }

    public void setLs_classTablaPadre(String ls_classTablaPadre) {
        this.ls_classTablaPadre = ls_classTablaPadre;
    }

    public obj_paginador getPaginador() {
        return paginador;
    }

    public void setPaginador(obj_paginador paginador) {
        this.paginador = paginador;
    }

    public Integer getLi_num_reg_x_pagina() {
        return li_num_reg_x_pagina;
    }

    public void setLi_num_reg_x_pagina(Integer li_num_reg_x_pagina) {
        this.li_num_reg_x_pagina = li_num_reg_x_pagina;
    }

    public Boolean getLb_paginar() {
        return lb_paginar;
    }

    public void setLb_paginar(Boolean lb_paginar) {
        this.lb_paginar = lb_paginar;
    }

    public String getObjsonTabla() {
        return ObjsonTabla;
    }

    public void setObjsonTabla(String ObjsonTabla) {
        this.ObjsonTabla = ObjsonTabla;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLi_pagina_actual() {
        return li_pagina_actual;
    }

    public void setLi_pagina_actual(Integer li_pagina_actual) {
        this.li_pagina_actual = li_pagina_actual;
    }

    public boolean isLb_readonly() {
        return lb_readonly;
    }

    public void setLb_readonly(boolean lb_readonly) {
        this.lb_readonly = lb_readonly;
    }

    public String getLs_idFilaSeleccionada() {
        return ls_idFilaSeleccionada;
    }

    public void setLs_idFilaSeleccionada(String ls_idFilaSeleccionada) {
        this.ls_idFilaSeleccionada = ls_idFilaSeleccionada;
    }
    

}
