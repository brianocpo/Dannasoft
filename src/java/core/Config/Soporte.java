/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.Config;


import core.Objects.obj_acciones_array_tablas;
import java.util.List;

/**
 *
 * @author brian
 */
public class Soporte {

    CrudGenerico conexion = new CrudGenerico();
    private String BDD;
    private String SHECMAN;

    public Soporte() {
        this.BDD = "Dannasoft";
        this.SHECMAN = "mod_administracion";
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
        lst = conexion.getColumEsquemaBDD(BDD, SHECMAN, nombre_tabla);
        return lst;
    }

    public String[] ArraySQLAccionesTablas(obj_acciones_array_tablas AccionesTabla) {
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
                ls_sql = "DELETE FROM " + SHECMAN + "." + NomTabla + " WHERE  " + NomCampoPK + "=" + codigoPK;
                resp[li_pos]=ls_sql;
                li_pos++;
            }
            /*ACCIONES DE ACTUALIZACIÓN*/
            for (int a = 0; a < AccionesTabla.getTablaBDD().get(i).getFilasActualizadas().size(); a++) {
                String codigoPK = "";
                String setColumns = "";
                codigoPK=AccionesTabla.getTablaBDD().get(i).getFilasActualizadas().get(a).getCodigoPK();
                ls_sql = "UPDATE " + SHECMAN + "." + NomTabla + " SET  ";
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
            
            /*ACCIONES INSERSIÓN*/
            for (int a = 0; a < AccionesTabla.getTablaBDD().get(i).getFilasEliminadas().size(); a++) {
                String codigoPK = "";
            }
        }
        borrarGarbage();
        return resp;
    }
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

    public String[] ArrayUpdatetRow(String[] data, String Tabla) {
//         List lst=null;
        String[] resp = new String[data.length];
//         String [] rowData;
//
//         String nombre_campoPK="";
//         String campo_update="";
//         String id="";
//         String id_anterior="";
//         String ls_sql="";
//         for(int i=0;i<data.length;i++)
//         {
//             rowData=data[i].split(":");
//             nombre_campoPK=rowData[0];
//             id=rowData[1];
//             campo_update=rowData[2];
//
//            
//                    ls_sql="UPDATE "+SHECMAN+"."+Tabla+" SET "+campo_update+"WHERE  "+nombre_campoPK+"="+id; 
//                    resp[i]=ls_sql; 
//                    campo_update="";   
//            
//           
//             id_anterior=id;    
//         }
        return resp;
    }
    
    public static void borrarGarbage() {
            Runtime garbage = Runtime.getRuntime();        
            garbage.gc();
    }

}
