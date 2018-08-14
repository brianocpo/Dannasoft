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
public  class obj_acciones_tabla implements Comparable <obj_acciones_tabla>{
    private String NomTabla;
    private String NomCampoPK;
    private String OredenTabla;    
    private ArrayList<obj_acciones_filas_eliminadas> FilasEliminadas;
    private ArrayList<obj_acciones_filas_actualizadas> FilasActualizadas;
    private ArrayList<obj_acciones_filas_insertadas> FilasInsertadas;
    public obj_acciones_tabla()
    {
        NomTabla="";
        NomCampoPK="";
        OredenTabla="";  
        FilasEliminadas=null;
        FilasActualizadas=null;
        FilasInsertadas=null;
    }
    public String getNomTabla() {
        return NomTabla;
    }

    public void setNomTabla(String NomTabla) {
        this.NomTabla = NomTabla;
    }

    public String getNomCampoPK() {
        return NomCampoPK;
    }

    public void setNomCampoPK(String NomCampoPK) {
        this.NomCampoPK = NomCampoPK;
    }

    public String getOredenTabla() {
        return OredenTabla;
    }

    public void setOredenTabla(String OredenTabla) {
        this.OredenTabla = OredenTabla;
    }

    public ArrayList<obj_acciones_filas_eliminadas> getFilasEliminadas() {
        return FilasEliminadas;
    }

    public void setFilasEliminadas(ArrayList<obj_acciones_filas_eliminadas> FilasEliminadas) {
        this.FilasEliminadas = FilasEliminadas;
    }

    public ArrayList<obj_acciones_filas_actualizadas> getFilasActualizadas() {
        return FilasActualizadas;
    }

    public void setFilasActualizadas(ArrayList<obj_acciones_filas_actualizadas> FilasActualizadas) {
        this.FilasActualizadas = FilasActualizadas;
    }

    public ArrayList<obj_acciones_filas_insertadas> getFilasInsertadas() {
        return FilasInsertadas;
    }

    public void setFilasInsertadas(ArrayList<obj_acciones_filas_insertadas> FilasInsertadas) {
        this.FilasInsertadas = FilasInsertadas;
    }

    @Override
    public int compareTo(obj_acciones_tabla o) {
       
            if (Integer.parseInt(OredenTabla) < Integer.parseInt(o.OredenTabla)) {
                return -1;
            }
            if (Integer.parseInt(OredenTabla) > Integer.parseInt(o.OredenTabla)) {
                return 1;
            }
            return 0;
    }
    
}
