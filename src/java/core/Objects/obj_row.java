/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.Objects;

import core.Objects.obj_column;
import java.util.ArrayList;

/**
 *
 * @author brian
 */
public class obj_row {
    
    private ArrayList<obj_column> lista_gridColumn;
    private Boolean lb_foco;
    private Integer li_numeroFila;
    private String ls_nombre_PK;
    private String ls_codigo_PK;

    public obj_row() {
    }
    public obj_row(ArrayList<obj_column> lista_gridColumn, Boolean lb_foco, Integer li_numeroFila, String ls_nombre_PK, String ls_codigo_PK) {
        this.lista_gridColumn = lista_gridColumn;
        this.lb_foco = lb_foco;
        this.li_numeroFila = li_numeroFila;
        this.ls_nombre_PK = ls_nombre_PK;
        this.ls_codigo_PK = ls_codigo_PK;
    }

    public String getLs_nombre_PK() {
        return ls_nombre_PK;
    }

    public void setLs_nombre_PK(String ls_nombre_PK) {
        this.ls_nombre_PK = ls_nombre_PK;
    }

    public String getLs_codigo_PK() {
        return ls_codigo_PK;
    }

    public void setLs_codigo_PK(String ls_codigo_PK) {
        this.ls_codigo_PK = ls_codigo_PK;
    }
    

    public ArrayList<obj_column> getLista_gridColumn() {
        return lista_gridColumn;
    }

    public void setLista_gridColumn(ArrayList<obj_column> lista_gridColumn) {
        this.lista_gridColumn = lista_gridColumn;
    }

    public Boolean getLb_foco() {
        return lb_foco;
    }

    public void setLb_foco(Boolean lb_foco) {
        this.lb_foco = lb_foco;
    }

    public Integer getLi_numeroFila() {
        return li_numeroFila;
    }

    public void setLi_numeroFila(Integer li_numeroFila) {
        this.li_numeroFila = li_numeroFila;
    }
    public int getNumeroColumn()
    {
        return lista_gridColumn.size();
    }
    
    
}
