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
public class obj_dropdown {
    private String codigo_fk;
    private String [] codigo_dw;
    private String [] valor_dw;
    private String codigo_fk_select;
    private String valor_fk_select;
    public obj_dropdown()
    {
        
    }

    public String getCodigo_fk() {
        return codigo_fk;
    }

    public void setCodigo_fk(String codigo_fk) {
        this.codigo_fk = codigo_fk;
    }

    public String[] getCodigo_dw() {
        return codigo_dw;
    }

    public void setCodigo_dw(String[] codigo_dw) {
        this.codigo_dw = codigo_dw;
    }

    public String[] getValor_dw() {
        return valor_dw;
    }

    public void setValor_dw(String[] valor_dw) {
        this.valor_dw = valor_dw;
    }

    public String getCodigo_fk_select() {
        return codigo_fk_select;
    }

    public void setCodigo_fk_select(String codigo_fk_select) {
        this.codigo_fk_select = codigo_fk_select;
    }

    public String getValor_fk_select() {
        return valor_fk_select;
    }

    public void setValor_fk_select(String valor_fk_select) {
        this.valor_fk_select = valor_fk_select;
    }
    
    
}
