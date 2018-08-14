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
public class obj_acciones_columnas_insertadas {
    private String NomColumna;
    private String TipoValor;
    private String Valor;
    private Boolean FK;
    private String IDColumn;     
    
    public obj_acciones_columnas_insertadas()
    {
        NomColumna="";
        TipoValor="";
        Valor="";
        FK=false;
        IDColumn="";
    }

    public String getNomColumna() {
        return NomColumna;
    }

    public void setNomColumna(String NomColumna) {
        this.NomColumna = NomColumna;
    }

    public String getTipoValor() {
        return TipoValor;
    }

    public void setTipoValor(String TipoValor) {
        this.TipoValor = TipoValor;
    }

    public String getValor() {
        return Valor;
    }

    public void setValor(String Valor) {
        this.Valor = Valor;
    }

    public Boolean getFK() {
        return FK;
    }

    public void setFK(Boolean FK) {
        this.FK = FK;
    }

    public String getIDColumn() {
        return IDColumn;
    }

    public void setIDColumn(String IDColumn) {
        this.IDColumn = IDColumn;
    }
   
}
