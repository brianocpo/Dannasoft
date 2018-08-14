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
public class obj_acciones_filas_insertadas {
    private String codigoPK;
    private String codigoFK;
    private String nombreCampoFK;
    private ArrayList<obj_acciones_columnas_insertadas> ColumnasInsertadas;
    public obj_acciones_filas_insertadas()
    {
        codigoPK=""; 
        codigoFK="";   
        nombreCampoFK="";
    }

    public String getCodigoPK() {
        return codigoPK;
    }

    public void setCodigoPK(String codigoPK) {
        this.codigoPK = codigoPK;
    }  

    public ArrayList<obj_acciones_columnas_insertadas> getColumnasInsertadas() {
        return ColumnasInsertadas;
    }

    public void setColumnasInsertadas(ArrayList<obj_acciones_columnas_insertadas> ColumnasInsertadas) {
        this.ColumnasInsertadas = ColumnasInsertadas;
    } 

    public String getCodigoFK() {
        return codigoFK;
    }

    public void setCodigoFK(String codigoFK) {
        this.codigoFK = codigoFK;
    }

    public String getNombreCampoFK() {
        return nombreCampoFK;
    }

    public void setNombreCampoFK(String nombreCampoFK) {
        this.nombreCampoFK = nombreCampoFK;
    }

}
