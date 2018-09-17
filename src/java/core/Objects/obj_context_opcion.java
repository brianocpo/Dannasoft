/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.Objects;

/**
 *
 * @author brian
 */
public class obj_context_opcion {
    private String ls_nombre;
    private boolean lb_estado;
    private String ls_funcion_js;
    private String ls_ordenTB;
    private String ls_htmlOpcion;
    public obj_context_opcion(String ls_nombre, boolean lb_estado, String ls_funcion_js) {
        this.ls_nombre = ls_nombre;
        this.lb_estado = lb_estado;
        this.ls_funcion_js = ls_funcion_js;
        this.ls_htmlOpcion="";
        this.crearOpcion();
    }
    public String crearOpcion(){
        String ls_estado="";
        String ls_style="";
        if(this.lb_estado){
            ls_estado="active";
        }else{
            ls_style="cursor:pointer;";
        }
        ls_htmlOpcion="<li class='"+ls_estado+"' style='"+ls_style+"'>";
        ls_htmlOpcion+="<a tabindex='-1' href='"+ls_funcion_js+"'></a>";
        ls_htmlOpcion+="</li>";
        return this.ls_htmlOpcion;
    }

    public String getLs_funcion_js() {
        return ls_funcion_js;
    }

    public void setLs_funcion_js(String ls_funcion_js) {
        this.ls_funcion_js = ls_funcion_js;
    }

    public String getLs_nombre() {
        return ls_nombre;
    }

    public void setLs_nombre(String ls_nombre) {
        this.ls_nombre = ls_nombre;
    }

    public boolean isLb_estado() {
        return lb_estado;
    }

    public void setLb_estado(boolean lb_estado) {
        this.lb_estado = lb_estado;
    }

    public String getLs_ordenTB() {
        return ls_ordenTB;
    }

    public void setLs_ordenTB(String ls_ordenTB) {
        this.ls_ordenTB = ls_ordenTB;
    }
    
    
    
}
