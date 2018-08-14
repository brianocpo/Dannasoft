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
public class obj_acciones_filas_actualizadas {
    private String codigoPK;
    private ArrayList<obj_acciones_columnas_actualizadas> ColumnasActualizada;
    public obj_acciones_filas_actualizadas()
    {
        codigoPK="";
    }

    public String getCodigoPK() {
        return codigoPK;
    }

    public void setCodigoPK(String codigoPK) {
        this.codigoPK = codigoPK;
    }  

    public ArrayList<obj_acciones_columnas_actualizadas> getColumnasActualizada() {
        return ColumnasActualizada;
    }

    public void setColumnasActualizada(ArrayList<obj_acciones_columnas_actualizadas> ColumnasActualizada) {
        this.ColumnasActualizada = ColumnasActualizada;
    }
    
}
