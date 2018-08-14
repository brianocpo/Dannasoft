/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.Objects;

import java.util.ArrayList;

/**
 *
 * @author brian
 */
public class obj_column {
    private obj_data data_column;//Informacion sobre el tipo de Typo dato, longitud, nombre_columna
    private obj_dropdown data_dropdown;
    private Boolean   lb_visible;
    private String    ls_nombre_column;
    private String    ls_valor;
    private Boolean   lb_PK;
    private Boolean   lb_FK;
    private String    ls_label_column; 

    public obj_column() {
       data_column = new obj_data();
       data_dropdown= new obj_dropdown();
       lb_visible=true;
       ls_nombre_column="";
       ls_valor="";
       lb_PK=false;
       lb_FK=false;
       ls_label_column="";
    } 

    public obj_data getData_column() {
        return data_column;
    }

    public void setData_column(obj_data data_column) {
        this.data_column = data_column;
    }

    public Boolean getLb_visible() {
        return lb_visible;
    }

    public void setLb_visible(Boolean lb_visible) {
        this.lb_visible = lb_visible;
    }

    public String getLs_nombre_column() {
        return ls_nombre_column;
    }

    public void setLs_nombre_column(String ls_nombre_column) {
        this.ls_nombre_column = ls_nombre_column;
    }

    public String getLs_valor() {
        return ls_valor;
    }

    public void setLs_valor(String ls_valor) {
        this.ls_valor = ls_valor;
    }

    public Boolean getLb_PK() {
        return lb_PK;
    }

    public void setLb_PK(Boolean lb_PK) {
        this.lb_PK = lb_PK;
    }

    public String getLs_label_column() {
        return ls_label_column;
    }

    public void setLs_label_column(String ls_label_column) {
        this.ls_label_column = ls_label_column;
    }

    public Boolean getLb_FK() {
        return lb_FK;
    }

    public void setLb_FK(Boolean lb_FK) {
        this.lb_FK = lb_FK;
    }
    public obj_dropdown getData_dropdown() {
        return data_dropdown;
    }

    public void setData_dropdown(obj_dropdown data_dropdown) {
        this.data_dropdown = data_dropdown;
    }
    
    
}
