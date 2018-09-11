/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.Config;



import com.google.gson.Gson;
import com.google.gson.JsonObject;
import core.Objects.obj_acciones_array_tablas;
import core.Objects.obj_tabla;
import java.util.List;

/**
 *
 * @author brian
 */
public class Soporte {

    CrudGenerico conexion = new CrudGenerico();
    private static String BaseDatos;
    private static String EsquemaBaseDatos;

    public Soporte() {
        this.BaseDatos = "Dannasoft";
        this.EsquemaBaseDatos = "mod_administracion";
    }

    // Funcion para remplazar un caracter dentro de un Vector String
    public String[] remplaceStringVector(String vector[], String oldChar, String newChar) {
        for (Integer i = 0; i < vector.length; i++) {
            vector[i] = vector[i].replace("?", " , ");
        }
        return vector;
    }

    //Funcion que Llena la tabla de s_opcion_campos con todos los campos de la tabla configurada
    public List cargarOpcionCampos() { //Consultar las opciones configuradas
        List lst = conexion.getDataHSQL("FROM SOpcion");
        return lst;
    }

    public List esquemaBDD(String nombre_tabla) {
        List lst = null;
        lst = conexion.getColumEsquemaBDD(BaseDatos, EsquemaBaseDatos, nombre_tabla);
        return lst;
    }
    public void ejecutarAcciones(obj_acciones_array_tablas AccionesTabla){        
       
        //Se ejecutan instrucciones de Actualización, Eliminación de las tablas genéricas
        String[] las_SqlAccionesDeleteUpdate= getSQLAccionesUpdateDelete(AccionesTabla);
        CrudGenerico.ejecutarSQL(las_SqlAccionesDeleteUpdate);
        
        //Se procede con la insersión de las nuevas Filas dentro de las tablas
        insertarNuevosDatos(AccionesTabla);
    }
    public void insertarNuevosDatos(obj_acciones_array_tablas AccionesTabla){
        String ls_nombreCampoPK="";
        String ls_nombreTablaPK="";
        Integer li_codigoFK=0;
        String ls_codigoFK="";
        String ls_sqlInsert="";
        String ls_camposInsert="";
        String ls_valuesInsert="";
        String ls_nombreColumna="";
        String ls_valor="";
        String ls_tipoValor="";
        Boolean lb_exiten_filas=false;
        String ls_nombreCampoFK="";
        String ls_codigoPKTEMP="";
        String ls_codigoFKTEMP="";
        JsonObject codigosTB = new JsonObject();

        //Comprueba si exiten nuevas filas en las tablas
        for (int i = AccionesTabla.getTablaBDD().size()-1; i >=0; i--) {
            if(AccionesTabla.getTablaBDD().get(i).getFilasInsertadas().size()>0){
               lb_exiten_filas=true;
               break;
            }
        }
        //Si existen nuevas filas se procede a ejecutar
        if(lb_exiten_filas==true){
            CrudGenerico.iniciarTransaction();        
            for (int i = AccionesTabla.getTablaBDD().size()-1; i >=0; i--) {
                ls_nombreTablaPK=AccionesTabla.getTablaBDD().get(i).getNomTabla();
                ls_nombreCampoPK=AccionesTabla.getTablaBDD().get(i).getNomCampoPK();            
                //RECORRO LAS FILAS INSERTADAS
                for (int a = 0; a < AccionesTabla.getTablaBDD().get(i).getFilasInsertadas().size(); a++) {
                    ls_sqlInsert="INSERT INTO "+this.EsquemaBaseDatos+"."+ls_nombreTablaPK+" ";
                    ls_camposInsert="";
                    ls_valuesInsert="";
                    ls_codigoPKTEMP=AccionesTabla.getTablaBDD().get(i).getFilasInsertadas().get(a).getCodigoPK();
                    ls_codigoFKTEMP=AccionesTabla.getTablaBDD().get(i).getFilasInsertadas().get(a).getCodigoFK();
                    if(AccionesTabla.getTablaBDD().get(i).getFilasInsertadas().get(a).getNombreCampoFK().length()>0){
                        ls_nombreCampoFK=AccionesTabla.getTablaBDD().get(i).getFilasInsertadas().get(a).getNombreCampoFK();
                    }
                    //EN CASO DE QUE TENGA CODIGO FORANEO SE RECUPERA Y SE AGREGA AL INSERT                    
                    if(AccionesTabla.getTablaBDD().get(i).getFilasInsertadas().get(a).getCodigoFK().length()>0){
                        boolean resultado = AccionesTabla.getTablaBDD().get(i).getFilasInsertadas().get(a).getCodigoFK().contains("N");
                        if(resultado==false){
                           li_codigoFK=Integer.parseInt(AccionesTabla.getTablaBDD().get(i).getFilasInsertadas().get(a).getCodigoFK()); 
                        }else{
                            try {
                                ls_codigoFK=codigosTB.get(ls_codigoFKTEMP).toString();
                                if(ls_codigoFK.length()>0){
                                    li_codigoFK=Integer.parseInt(ls_codigoFK);
                                }
                            } catch (Exception e) {
                                System.out.println("No se encontro la clave FORANEA a la que está enlazada la tabla "+e);
                            }                           
                        }                        
                    }
                    //RECORRO LAS COLUMNAS PARA ARMAR EL INSERT CON LA DATA POR CADA FILA
                    for (int b = 0; b < AccionesTabla.getTablaBDD().get(i).getFilasInsertadas().get(a).getColumnasInsertadas().size(); b++){
                        ls_nombreColumna=AccionesTabla.getTablaBDD().get(i).getFilasInsertadas().get(a).getColumnasInsertadas().get(b).getNomColumna();
                        if(ls_nombreCampoPK!=ls_nombreColumna){
                            ls_camposInsert=ls_camposInsert + ls_nombreColumna +",";
                            if(ls_nombreCampoFK==ls_nombreColumna){
                                ls_valor=String.valueOf(li_codigoFK);
                            }else{
                                ls_valor=AccionesTabla.getTablaBDD().get(i).getFilasInsertadas().get(a).getColumnasInsertadas().get(b).getValor();
                            }

                            ls_tipoValor=AccionesTabla.getTablaBDD().get(i).getFilasInsertadas().get(a).getColumnasInsertadas().get(b).getTipoValor();                    
                            ls_valor=transformarTipoValor(ls_tipoValor,ls_valor);
                            ls_valuesInsert=ls_valuesInsert + ls_valor +",";   
                        }                    
                    }
                    ls_camposInsert=acortarString(ls_camposInsert, 1);
                    ls_valuesInsert=acortarString(ls_valuesInsert, 1); 
                    //Se arma la consulta
                    ls_sqlInsert=ls_sqlInsert+" ("+ls_camposInsert+") VALUES ("+ls_valuesInsert+")";
                    System.out.println(ls_sqlInsert);
                    //Se ejecuta si es satisfactoria se consulta su ID
                    if(CrudGenerico.ejecutarSQLSinCommit(ls_sqlInsert)==1){
                        li_codigoFK=CrudGenerico.getMaxID(ls_nombreCampoPK,this.EsquemaBaseDatos,ls_nombreTablaPK);
                        codigosTB.addProperty(ls_codigoPKTEMP, li_codigoFK); 
                    }else{
                        CrudGenerico.rollbackTransaction();
                    }
                }

            }
            CrudGenerico.commitTransaction();
        }
        
    }
    public String[] getSQLAccionesUpdateDelete(obj_acciones_array_tablas AccionesTabla) {
        List lst = null;
        String[] resp = new String[numeroCambiosTabla(AccionesTabla)];
        Integer li_pos=0;
        for (int i = 0; i < AccionesTabla.getTablaBDD().size(); i++) {
            String NomTabla = "";
            String NomCampoPK = "";
            String ls_sql = "";
            String ls_sql_where="";
            NomTabla = AccionesTabla.getTablaBDD().get(i).getNomTabla();
            NomCampoPK = AccionesTabla.getTablaBDD().get(i).getNomCampoPK();
            /*ACCIONES DE ELIMINACION*/
            for (int j = 0; j < AccionesTabla.getTablaBDD().get(i).getFilasEliminadas().size(); j++) {
                String codigoPK = "";
                codigoPK=AccionesTabla.getTablaBDD().get(i).getFilasEliminadas().get(j).getCodigoPK();
                ls_sql = "DELETE FROM " + EsquemaBaseDatos + "." + NomTabla + " WHERE  " + NomCampoPK + "=" + codigoPK;
                resp[li_pos]=ls_sql;
                li_pos++;
            }
            /*ACCIONES DE ACTUALIZACIÓN*/
            for (int a = 0; a < AccionesTabla.getTablaBDD().get(i).getFilasActualizadas().size(); a++) {
                String codigoPK = "";
                String setColumns = "";
                codigoPK=AccionesTabla.getTablaBDD().get(i).getFilasActualizadas().get(a).getCodigoPK();
                ls_sql = "UPDATE " + EsquemaBaseDatos + "." + NomTabla + " SET  ";
                for (int c = 0; c < AccionesTabla.getTablaBDD().get(i).getFilasActualizadas().get(a).getColumnasActualizada().size(); c++) {
                     String NomColumna="";
                     String Valor="";
                     String TipoValor="";

                     NomColumna=AccionesTabla.getTablaBDD().get(i).getFilasActualizadas().get(a).getColumnasActualizada().get(c).getNomColumna();
                     Valor=AccionesTabla.getTablaBDD().get(i).getFilasActualizadas().get(a).getColumnasActualizada().get(c).getValor();
                     TipoValor=AccionesTabla.getTablaBDD().get(i).getFilasActualizadas().get(a).getColumnasActualizada().get(c).getTipoValor();
                     
                     Valor = transformarTipoValor(TipoValor,Valor);
                     setColumns+=NomColumna+"="+Valor+",";
                }
                setColumns=acortarString(setColumns, 1);//Elimino elimino el ultimo caracter del string
                ls_sql_where=" WHERE "+NomCampoPK + "=" + codigoPK;
                
                ls_sql=ls_sql+setColumns+ls_sql_where;
                resp[li_pos]=ls_sql;
                li_pos++;
            }
        }
        borrarGarbage();
        return resp;
    }
    //Reduce la cadena desde el final 
    public static String acortarString(String cadena, int n) {
         int indice = 0;
        if(cadena.length()>0){
           indice = n > cadena.length() ? 0 : cadena.length() - n;  
           cadena=cadena.substring(0, indice);
        }
       
        return cadena;
    }
    public String transformarTipoValor(String TipoValor,String Valor)
    {   
        TipoValor=TipoValor.toUpperCase();
        if (TipoValor.equals("CHARACTER")  || TipoValor.equals("CHAR")  || TipoValor.equals("VARCHAR")|| TipoValor.equals("DATE")|| TipoValor.equals("DATETIME"))
        {
            Valor = "\'" + Valor + "\'";
        }
        return Valor;
        
    }
    public Integer numeroCambiosTabla(obj_acciones_array_tablas AccionesTabla)
    {
        Integer li_numeroCambios=0;
        for (int i = 0; i < AccionesTabla.getTablaBDD().size(); i++) {
            /*Filas Eliminadas*/
            li_numeroCambios+=AccionesTabla.getTablaBDD().get(i).getFilasEliminadas().size();  
            li_numeroCambios+=AccionesTabla.getTablaBDD().get(i).getFilasActualizadas().size();
        }
        return li_numeroCambios;        
    }
    
    public static void borrarGarbage() {
            Runtime garbage = Runtime.getRuntime();        
            garbage.gc();
    }
    
    public static String convertObjTablaJson(obj_tabla tabla)
    {   
        String Jsontabla=new Gson().toJson(tabla);
        return Jsontabla;
    }

}
