/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.Objects;

import com.google.gson.Gson;

/**
 *
 * @author brian
 */
public class obj_grupo_tabla {
    obj_tabla tablaPadre;
    obj_tabla tablaHija;
    String ls_ordenTB;
    public obj_grupo_tabla(obj_tabla objTablaPadre, obj_tabla objTablaHija) {
        tablaPadre=objTablaPadre;
        tablaHija=objTablaHija;
        ls_ordenTB="";
    }
    public String getJsonObjTabla(obj_tabla tabla)
    {   
        String Jsontabla=new Gson().toJson(tabla);
        return Jsontabla;
    }
    public String getTablaHtml()
    {
        String HTMLTabla=tablaPadre.getTablaHtml();
        String jsonTabla = getJsonObjTabla(tablaHija);
         //Script para la configuracion de Datatable Jquery 
        String scriptTabla=" <script type='text/javascript' >"
                + " ObjsonTablaHija["+tablaPadre.getLs_ordenTB()+"]='"+jsonTabla+"';  "
                + " </script>";
        HTMLTabla+=scriptTabla;
        return HTMLTabla;        
    }

  
    
}
