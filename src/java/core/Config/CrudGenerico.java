package core.Config;

import core.Objects.obj_data;
import core.Objects.obj_row;
import core.Objects.obj_dropdown;
import core.Objects.obj_column;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author brian
 */
public class CrudGenerico {
    
    public String ls_campos_tabla;
    private String ls_tablaOpcion;
    private String ls_schema;
    private String ls_catalog;
    List<obj_column> lista_gridColumn;
    List<obj_dropdown> lista_gridDropdown;
    List dataTablaRows;
    //Objetos para almacenar las Filas del Grid
    List<obj_row> lista_gridRow;

    public CrudGenerico() {
        lista_gridColumn = new ArrayList<obj_column>();
        lista_gridDropdown = new ArrayList<obj_dropdown>();
        dataTablaRows = null;
        lista_gridRow = new ArrayList<obj_row>();
        ls_tablaOpcion = "s_opcion";
        ls_schema = "";
        ls_catalog = "";       
    }
    public List getDataHSQL(String ls_hql) {
        List lst = null;
        try {
            Query query = Conexion.getSession().createQuery(ls_hql);
            lst = query.list();
        } catch (Exception e) {
            lst = null;
            System.out.println("Error al recuperar consulta gatDataHSQL() HQL= " + ls_hql + " - " + e);
        } finally {
           Conexion.closeSesion();
        }
        return lst;
    }

    public void saveData(Object objeto) {
        try {
            Conexion.getSession().beginTransaction();
            Conexion.getSession().save(objeto);
            Conexion.getSession().getTransaction().commit();
            System.out.println("Dato Guardado");
        } catch (Exception e) {
            System.out.println("Error al guardarData() " + e);
            Conexion.getSession().getTransaction().rollback();
        } finally {
            Conexion.closeSesion();
        }
    }

    public List getDataSQL(String table_catalog, String table_schema, String ls_tabla, String ls_query, String ls_where, String ls_orden ,String ls_paginacion) {
        List lst =null;
        String ls_campos_tabla = "";
        String[] la_campos_tabla = null;
        if (table_schema.length() > 0) {
            table_schema = table_schema + ".";
        }
        //Recupero el nombre de los campos de la tabla
        getNameColumnTable(ls_tabla);
        ls_campos_tabla = getLs_campos_tabla();
        la_campos_tabla = ls_campos_tabla.split(",");
        String ls_sql = "SELECT " + ls_campos_tabla + " FROM " + table_schema + ls_tabla;
        if (ls_query.length() > 0) {
            ls_sql = ls_query;
        }
        //Condicion
        if (ls_where.length() > 0) {
            ls_sql = ls_sql + " WHERE " + ls_where;
        }
        //Ordenamiento
        if (ls_orden.length() > 0) {
            ls_sql = ls_sql + " ORDER BY " + ls_orden;
        }
        //Paginacion
        if (ls_paginacion.length() > 0) {
            ls_sql = ls_sql + " " + ls_paginacion;
        }
        System.out.println(ls_sql);
        try {
            Query query = Conexion.getSession().createSQLQuery(ls_sql);
            lst=query.list();
            Object[] columna;
            for (int i = 0; i < lst.size(); i++) {
                columna = (Object[]) lst.get(i);
                String[] ls_columna = new String[columna.length];
                Integer li_pos = 0;
                for (Object column : columna) {
                    try {
                        ls_columna[li_pos] = la_campos_tabla[li_pos] + "=" + column.toString();

                    } catch (Exception e) {
                        ls_columna[li_pos] = la_campos_tabla[li_pos] + "=null";

                    }
                    li_pos++;
                }
                lst.set(i, ls_columna);
            }

        } catch (Exception e) {
            lst = null;
            System.out.println("Error al recuperar getDataSQL()  SQL= " + ls_sql + " - " + e);
        } finally {
            Conexion.closeSesion();
        }

        return lst;
    }

    public void getNameColumnTable(String ls_tabla) {
        List lst_estructura = null;
        String ls_columnas_tb = "";
        String ls_sqlEstructura = "SELECT \n"
                + "column_name\n"
                + "FROM \n"
                + "information_schema.columns t \n"
                + "WHERE\n"
                + "t.table_name = '" + ls_tabla + "';";
        try {   //Consulta Estructura
            Query query1 = Conexion.getSession().createSQLQuery(ls_sqlEstructura);
            lst_estructura = query1.list();
            for (int i = 0; i < lst_estructura.size() - 1; i++) {
                ls_columnas_tb = ls_columnas_tb + lst_estructura.get(i) + ",";
            }
            ls_columnas_tb = ls_columnas_tb + lst_estructura.get(lst_estructura.size() - 1);
        } catch (Exception e) {

            lst_estructura = null;
            System.out.println("Error al recuperar getNombreColumnTabla() " + ls_tabla + " - " + e);
        } finally {
            Conexion.closeSesion();
        }

        setLs_campos_tabla(ls_columnas_tb);
    }

    public List getMetadataBDD(String table_catalog, String table_schema, String table_name) {
        String ls_sql = "SELECT \n"
                + "table_name,\n"
                + "column_name,\n"
                + "is_nullable,data_type,\n"
                + "'' as  constraint_type,\n"
                + "character_maximum_length\n"
                + "FROM \n"
                + "information_schema.columns\n"
                + "WHERE table_name = '" + table_name + "' AND \n"
                + "table_catalog='" + table_catalog + "' AND \n"
                + "table_schema='" + table_schema + "'\n"
                + "UNION\n"
                + "SELECT 'PK' AS table_name,\n"
                + "c.column_name as column_name,\n"
                + "'' AS is_nullable,\n"
                + "'' AS data_type,\n"
                + "t.constraint_type,\n"
                + "0 AS character_maximum_length\n"
                + "FROM information_schema.key_column_usage AS c\n"
                + "LEFT JOIN information_schema.table_constraints AS t\n"
                + "ON t.constraint_name = c.constraint_name\n"
                + "WHERE t.table_name = '" + table_name + "' AND\n"
                + "t.table_catalog='" + table_catalog + "' AND\n"
                + "t.table_schema='" + table_schema + "' ";
        List lst = null;
        try {
            Query query = Conexion.getSession().createSQLQuery(ls_sql);
            lst = query.list();
        } catch (Exception e) {

            lst = null;
            System.out.println("Error al recuperar getMetadataBDD() " + ls_sql + " - " + e);
        } finally {
            Conexion.closeSesion();
        }
        return lst;
    }

    public List<obj_column> getColumEsquemaBDD(String table_catalog, String table_schema, String table_name) {

        String ls_column_name = "";
        String ls_column_name_pk = "";
        String la_column_name_fk = "";
        String ls_table_name = "";
        String ls_nombre_pk_fk = "";
        String ls_Data_type = "";
        String ls_Is_nullable = "";
        Integer li_longitud = 0;
        //Lista con todos los datos de las columnas de la tabla a nivel de BDD - incluye nombre de PK y FK
        List lista_data_bdd = getMetadataBDD(table_catalog, table_schema, table_name);
        Iterator it_data_bdd = lista_data_bdd.iterator();
        //Array que contiene todas las columnas de la tabla 
        List<obj_column> lista_gridColumn = new ArrayList<obj_column>();
        //Se rrecorre una lista que contienen toda la información del esquema de la tabla 
        while (it_data_bdd.hasNext()) {   //Asignar el Iterator a un Objeto para recuperar su información
            Object[] data_tb = (Object[]) it_data_bdd.next();
            //Recuperar informacion y asignacin a las variables
            ls_table_name = (String) data_tb[0];
            ls_column_name = (String) data_tb[1];
            ls_Data_type = (String) data_tb[2];
            ls_Is_nullable = (String) data_tb[3];
            ls_nombre_pk_fk = (String) data_tb[4];
            //Validacion valores null en Enteros
            try {
                li_longitud = (Integer) data_tb[5];
            } catch (Exception e) {
                li_longitud = 0;
            }

            //Verifica si el nombre pertebnece a un PK o FK y almacena todas en un String
            if ("FOREIGN KEY".equals(ls_nombre_pk_fk)) {
                la_column_name_fk = la_column_name_fk + ls_column_name + ",";

            } else if ("PRIMARY KEY".equals(ls_nombre_pk_fk)) {
                ls_column_name_pk = ls_column_name_pk + ls_column_name + ",";
            } else if (!"FOREIGN KEY".equals(ls_nombre_pk_fk) && !"PRIMARY KEY".equals(ls_nombre_pk_fk)) {   //se instancia la clase grid_column y se agregan todas las Caracteristicas DE LA COLUMNA
                obj_column gridColumn = new obj_column();
                //crear un objeto DATA dicho objeto contienen toda la información del esquema de la columna
                obj_data data = new obj_data();
                data.setTable_name(ls_table_name);
                data.setColumn_name(ls_column_name);
                data.setData_type(ls_Data_type);
                data.setIs_nullable(ls_Is_nullable);
                data.setLongitud(li_longitud);
                //Se realiza el SET de la columna
                gridColumn.setData_column(data);
                gridColumn.setLs_nombre_column(ls_column_name);
                //Array PK
                String[] las_PK = ls_column_name_pk.split(",");
                int li_PK = Arrays.asList(las_PK).indexOf(ls_column_name);
                if (li_PK >= 0) {
                    gridColumn.setLb_PK(Boolean.TRUE);
                }
                //Array FK
                String[] las_FK = la_column_name_fk.split(",");
                int li_FK = Arrays.asList(las_FK).indexOf(ls_column_name);
                if (li_FK >= 0) {
                    gridColumn.setLb_FK(Boolean.TRUE);
                }
                //Se agrega la columna a un Array de columnas
                lista_gridColumn.add(gridColumn);
            }
        }
        return lista_gridColumn;
    }

    public void crearListArrayRowsTable(String ls_catalog, String ls_schema, String ls_name_tabla, String ls_query, String ls_where, String ls_orden, String ls_paginacion) {
        setLs_schema(ls_schema);
        setLs_catalog(ls_catalog);
        //Obtiene las Columnas del Esquema de la BDD de la tabla
        this.lista_gridColumn = getColumEsquemaBDD(ls_catalog, ls_schema, ls_name_tabla);
        //Data de toda la Tabla
        this.dataTablaRows = getDataSQL(ls_catalog, ls_schema, ls_name_tabla, ls_query, ls_where, ls_orden, ls_paginacion);
        this.lista_gridRow.clear();

        //Arma las filas con sus respectivas columnas y su Información para cada Campo de la tabla
        Object[] DataRow;
        String ls_nombre_pk = "";
        String ls_codigo_pk = "";
        String ls_nombre_fk = "";
        Integer li_index;
        Boolean lb_dropdown_fk = false;
        //Recorre todas las Filas de la Data
        for (int k = 0; k < this.dataTablaRows.size(); k++) {   //Se crean las filas para agregar al ArrayList de grid_row
            obj_row GridRow = new obj_row();
            List<obj_column> lista_gridColumnTemp = new ArrayList<obj_column>();
            //Obtengo las columnas por cada ROW de la DATA
            DataRow = (Object[]) this.dataTablaRows.get(k);
            //Variables que contendran el nombre y el valor de cada columna de la DATA
            String[] ls_nombre_col = new String[DataRow.length];
            String[] ls_valor_col = new String[DataRow.length];
            Integer li_pos = 0;
            //Recorro cada Columna de la Fila de la DATA y almaceno el nombre y valor de cada columna en arreglos
            for (Object columnRowData : DataRow) {
                String[] Col = columnRowData.toString().split("=");
                ls_nombre_col[li_pos] = Col[0];
                ls_valor_col[li_pos] = Col[1];
                li_pos++;
            }
            //Objeto lista_gridColumn contienen la meta data de cada columna de la BDD
            for (int i = 0; i < this.lista_gridColumn.size(); i++) {   //New Object temporal grid_column 
                obj_column gridColumnTemp = new obj_column();
                String ls_nombre_colcompare = this.lista_gridColumn.get(i).getLs_nombre_column();
                gridColumnTemp.setLb_PK(this.lista_gridColumn.get(i).getLb_PK());
                gridColumnTemp.setData_column(this.lista_gridColumn.get(i).getData_column());
                gridColumnTemp.setLb_FK(this.lista_gridColumn.get(i).getLb_FK());
                gridColumnTemp.setLb_visible(this.lista_gridColumn.get(i).getLb_visible());
                gridColumnTemp.setLs_label_column(this.lista_gridColumn.get(i).getLs_label_column());
                gridColumnTemp.setLs_nombre_column(this.lista_gridColumn.get(i).getLs_nombre_column());
                li_index = Arrays.asList(ls_nombre_col).indexOf(ls_nombre_colcompare);
                if (li_index >= 0) {
                    gridColumnTemp.setLs_valor(ls_valor_col[li_index]);
                    if (gridColumnTemp.getLb_PK() == true) {
                        ls_codigo_pk = gridColumnTemp.getLs_valor();
                        ls_nombre_pk = gridColumnTemp.getLs_nombre_column();
                    }
                    //Se verifica si exiten columnas que pertenecen a claves foraneas en caso de ser asi se carga la data
                    //para crear las listas desplegables
                    if (gridColumnTemp.getLb_FK() == true) {
                        ls_nombre_fk = gridColumnTemp.getLs_nombre_column();
                        //solo se llena esta información en memoria una sola vez al recorrer todos los campos foraneos de cada columna
                        if (lb_dropdown_fk == false) {
                            obj_dropdown gridDropd = new obj_dropdown();
                            getDropdown(gridDropd, ls_nombre_fk);
                            gridDropd.setCodigo_fk_select(ls_valor_col[li_index]);
                            setValorGridDropdown(gridDropd, ls_valor_col[li_index]);
                            gridColumnTemp.setData_dropdown(gridDropd);
                            lista_gridDropdown.add(gridDropd);
                        } else if (lb_dropdown_fk == true)//busca en moria la información de las columnas y las asigana 
                        {
                            Integer index;
                            //con el nombre de la columna se busca en la lista_gridDropdown qeu contiene la data de los grid_dropdown 
                            index = buscarGridDropdown(ls_nombre_fk);
                            if (index >= 0) {
                                obj_dropdown gridDropd = new obj_dropdown();
                                gridDropd.setCodigo_dw(lista_gridDropdown.get(index).getCodigo_dw());
                                gridDropd.setValor_dw(lista_gridDropdown.get(index).getValor_dw());
                                gridDropd.setCodigo_fk_select(ls_valor_col[li_index]);
                                setValorGridDropdown(gridDropd, ls_valor_col[li_index]);
                                gridColumnTemp.setData_dropdown(gridDropd);
                            }
                        }
                    }
                }
                lista_gridColumnTemp.add(gridColumnTemp);
            }
            lb_dropdown_fk = true;
            //Se carga las columnas de la Fila y sus atributos
            GridRow.setLista_gridColumn((ArrayList<obj_column>) lista_gridColumnTemp);
            GridRow.setLs_nombre_PK(ls_nombre_pk);
            GridRow.setLs_codigo_PK(ls_codigo_pk);
            GridRow.setLi_numeroFila(k);
            //En el primer registro se coloca el foco la primera Vez
            if (k == 0) {
                GridRow.setLb_foco(Boolean.TRUE);
            }
            //Se añade la Fila al Listado De Filas del GRID
            this.lista_gridRow.add(k, GridRow);
            //Seteamos Lista_gridColumn con el temporal que ya contiene las columnas de los dropdowns
            if (k == this.dataTablaRows.size() - 1) {
                setLista_gridColumn(lista_gridColumnTemp);
            }
        }
        //Solo caso de que no existan datos en la tabla se debe llenar la configuración de las columnas por separado
        if (this.dataTablaRows.size() == 0) {
            List<obj_column> lista_gridColumnTemp = new ArrayList<obj_column>();
            for (int i = 0; i < this.lista_gridColumn.size(); i++) {   //New Object temporal grid_column 
                obj_column gridColumnTemp = new obj_column();
                String ls_nombre_colcompare = this.lista_gridColumn.get(i).getLs_nombre_column();
                gridColumnTemp.setLb_PK(this.lista_gridColumn.get(i).getLb_PK());
                gridColumnTemp.setData_column(this.lista_gridColumn.get(i).getData_column());
                gridColumnTemp.setLb_FK(this.lista_gridColumn.get(i).getLb_FK());
                gridColumnTemp.setLb_visible(this.lista_gridColumn.get(i).getLb_visible());
                gridColumnTemp.setLs_label_column(this.lista_gridColumn.get(i).getLs_label_column());
                gridColumnTemp.setLs_nombre_column(this.lista_gridColumn.get(i).getLs_nombre_column());

                if (gridColumnTemp.getLb_PK() == true) {
                    ls_codigo_pk = gridColumnTemp.getLs_valor();
                    ls_nombre_pk = gridColumnTemp.getLs_nombre_column();
                }
                //Se verifica si exiten columnas que pertenecen a claves foraneas en caso de ser asi se carga la data
                //para crear las listas desplegables
                if (gridColumnTemp.getLb_FK() == true) {
                    ls_nombre_fk = gridColumnTemp.getLs_nombre_column();
                    //solo se llena esta información en memoria una sola vez al recorrer todos los campos foraneos de cada columna
                    if (lb_dropdown_fk == false) {
                        obj_dropdown gridDropd = new obj_dropdown();
                        getDropdown(gridDropd, ls_nombre_fk);
                        gridColumnTemp.setData_dropdown(gridDropd);
                        lista_gridDropdown.add(gridDropd);
                    } else if (lb_dropdown_fk == true)//busca en moria la información de las columnas y las asigana 
                    {
                        Integer index;
                        //con el nombre de la columna se busca en la lista_gridDropdown qeu contiene la data de los grid_dropdown 
                        index = buscarGridDropdown(ls_nombre_fk);
                        if (index >= 0) {
                            obj_dropdown gridDropd = new obj_dropdown();
                            gridDropd.setCodigo_dw(lista_gridDropdown.get(index).getCodigo_dw());
                            gridDropd.setValor_dw(lista_gridDropdown.get(index).getValor_dw());
                            gridColumnTemp.setData_dropdown(gridDropd);
                        }
                    }
                }
                lista_gridColumnTemp.add(gridColumnTemp);
            }
            setLista_gridColumn(lista_gridColumnTemp);
        }

    }

    public static Integer ejecutarSQL(String ls_sql[]) {
        Integer li_estado = 0;
        try {
            Conexion.getSession().beginTransaction();
            for (int i = 0; i < ls_sql.length; i++) {
                SQLQuery query = Conexion.getSession().createSQLQuery(ls_sql[i]);
                query.executeUpdate();
            }
            Conexion.getSession().getTransaction().commit();
            li_estado = 1;
        } catch (Exception e) {
            Conexion.getSession().getTransaction().rollback();
            li_estado = 0;
        } finally {
           Conexion.closeSesion();
        }

        return li_estado;
    }
    public static void iniciarTransaction(){
        try {
            Conexion.getSession().beginTransaction();
        }catch (Exception e) {
            System.out.println("Error iniciarTransaction(): "+e);
        } 
    }
    public static void commitTransaction(){
        try {
           Conexion.getSession().getTransaction().commit();
        }catch (Exception e) {
            System.out.println("Error CommitTransaction(): "+e);
        }finally {
           Conexion.closeSesion();
        } 
    }
    public static void rollbackTransaction(){
        try {
           Conexion.getSession().getTransaction().rollback();
        }catch (Exception e) {
            System.out.println("Error CommitTransaction(): "+e);
        } finally {
           Conexion.closeSesion();
        }
    }
    public static Integer ejecutarSQLSinCommit(String ls_sql) {
        Integer li_estado = 1;
        try {           
            SQLQuery query = Conexion.getSession().createSQLQuery(ls_sql);
            query.executeUpdate();           
        } catch (Exception e) {           
            li_estado = 0;
        } 
        return li_estado;
    }
    
    public static Integer getMaxID(String ls_nombreCampoPK,String EsquemaBaseDatos,String ls_nombreTablaPK) {
        String ls_sql="SELECT MAX("+ls_nombreCampoPK+") FROM "+EsquemaBaseDatos+"."+ls_nombreTablaPK;
        Integer maxID=0;
        List lst = null;
        try {           
            Query query = Conexion.getSession().createSQLQuery(ls_sql);
            lst = query.list();
            Iterator it_tabla = lst.iterator();
            while (it_tabla.hasNext()) {
                maxID = (Integer) it_tabla.next();               
            }
           
        } catch (Exception e) {           
            maxID = 0;
        } 
        return maxID;
    }
    
    public static Integer getTotalRegistros(String EsquemaBaseDatos,String ls_nombreTablaPK,String ls_where) {
        
        if(ls_where.trim().equals("")){
            ls_where="";
        }else{
            ls_where=" WHERE "+ls_where;
        }
        String ls_sql="SELECT count(*) as total FROM "+EsquemaBaseDatos+"."+ls_nombreTablaPK+" "+ls_where;
        System.out.println(ls_sql);
        Integer li_total=0;  
        try{           
            Query query= Conexion.getSession().createSQLQuery(ls_sql);
            li_total=Integer.parseInt(String.valueOf(query.uniqueResult()));
           
        } catch (Exception e) {           
            li_total = 0;
        } 
        return li_total;
    }

    public Integer buscarGridDropdown(String codigo1_opc) {
        Integer index = -1;
        String ls_codigo1_opc = "";
        for (int j = 0; j < lista_gridDropdown.size(); j++) {
            ls_codigo1_opc = lista_gridDropdown.get(j).getCodigo_fk();
            if (codigo1_opc == ls_codigo1_opc) {
                index = j;
                break;
            }
        }
        return index;
    }

    public void setValorGridDropdown(obj_dropdown gridDropd, String ls_codigo_dw) {

        Integer index = -1;
        String ls_codigo_dw_temp = "";
        String[] las_codigos = gridDropd.getCodigo_dw();
        String[] las_valores = gridDropd.getValor_dw();
        for (int j = 0; j < las_codigos.length; j++) {

            if (ls_codigo_dw.equals(las_codigos[j])) {
                gridDropd.setValor_fk_select(las_valores[j]);
                break;
            }
        }
    }

    public void getDropdown(obj_dropdown gridDropd, String codigo1_opc) {

        String dropdown = "";
        String ls_sql_dropdown = "";
        String[] la_campos_tabla = null;
        String ls_sql = "SELECT tabla1_opc,codigo1_opc,campo1_ocp from " + getLs_schema() + "." + ls_tablaOpcion + " where codigo1_opc='" + codigo1_opc + "'";
        List lst = null;
        List lst_dropdown = null;

        try {
            gridDropd.setCodigo_fk(codigo1_opc);
            Query query = Conexion.getSession().createSQLQuery(ls_sql);
            lst = query.list();
            Object[] columna;
            Object[] columnaDropdown;
            if (lst.size() > 0) {
                if (lst.size() > 1) {
                    System.out.println("Existe mas de una opcion configurada con el mismo nombre codigo1_opc= " + codigo1_opc + " en la tabla de opciones");
                }
                //Recorre la información de la Opcion configurada
                for (int i = 0; i < 1; i++) {
                    columna = (Object[]) lst.get(i);
                    String[] las_columna = new String[columna.length];
                    Integer li_pos = 0;
                    for (Object column : columna) {
                        try {
                            las_columna[li_pos] = column.toString().trim();

                        } catch (Exception e) {
                            las_columna[li_pos] = "null";

                        }
                        li_pos++;
                    }
                    //se arma el selec para obtener la informacion del dropdown
                    ls_sql_dropdown = "SELECT " + las_columna[1] + "," + las_columna[2] + " FROM " + getLs_schema() + "." + las_columna[0] + " ORDER BY " + las_columna[2] + " ASC";
                    Query query2 = Conexion.getSession().createSQLQuery(ls_sql_dropdown);
                    lst_dropdown = query2.list();
                    String[] las_codigo_fk = new String[lst_dropdown.size()];
                    String[] las_valor_fk = new String[lst_dropdown.size()];
                    for (int j = 0; j < lst_dropdown.size(); j++) {
                        columnaDropdown = (Object[]) lst_dropdown.get(j);
                        String[] las_codigo_val_temp = new String[columnaDropdown.length];

                        Integer li_pos2 = 0;
                        for (Object columnDrop : columnaDropdown) {
                            try {
                                las_codigo_val_temp[li_pos2] = columnDrop.toString().trim();
                            } catch (Exception e) {
                                las_codigo_val_temp[li_pos2] = "null";
                            }
                            li_pos2++;
                        }
                        las_codigo_fk[j] = las_codigo_val_temp[0];
                        las_valor_fk[j] = las_codigo_val_temp[1];
                    }
                    gridDropd.setCodigo_dw(las_codigo_fk);
                    gridDropd.setValor_dw(las_valor_fk);
                }

            } else {
                System.out.println("No se puede configurar el Dropdown  no se ecuentra configurada la opcion con el codigo1_opc = " + codigo1_opc);
            }

        } catch (Exception e) {

            lst = null;
            System.out.println("Error getDropdown()  SQL= " + ls_sql + " - " + e);
        } finally {
            Conexion.closeSesion();
        }

    }

    public List<obj_column> getLista_gridColumn() {
        return lista_gridColumn;
    }

    public void setLista_gridColumn(List<obj_column> lista_gridColumn) {
        this.lista_gridColumn = lista_gridColumn;
    }

    public List getDataTablaRows() {
        return dataTablaRows;
    }

    public void setDataTablaRows(List dataTablaRows) {
        this.dataTablaRows = dataTablaRows;
    }

    public List<obj_row> getLista_gridRow() {
        return lista_gridRow;
    }

    public void setLista_gridRow(List<obj_row> lista_gridRow) {
        this.lista_gridRow = lista_gridRow;
    }

    public String getLs_campos_tabla() {
        return ls_campos_tabla;
    }

    public void setLs_campos_tabla(String ls_campos_tabla) {
        this.ls_campos_tabla = ls_campos_tabla;
    }

    public String getLs_schema() {
        return ls_schema;
    }

    public void setLs_schema(String ls_schema) {
        this.ls_schema = ls_schema;
    }

    public String getLs_catalog() {
        return ls_catalog;
    }

    public void setLs_catalog(String ls_catalog) {
        this.ls_catalog = ls_catalog;
    }

}
