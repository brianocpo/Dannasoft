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
public class obj_paginacion {
    private Integer li_pag_inicio;
    private Integer li_pag_fin;
    private Integer li_pag_actual;
    private Integer li_num_reg_pagina;
    private Integer li_total_reg_tabla;
    private Integer li_pag_reg_inicio;
    private Integer li_pag_reg_fin;
    public obj_paginacion(Integer li_total_reg_tabla,Integer li_num_reg_pagina) {
        this.li_total_reg_tabla=li_total_reg_tabla;
        this.li_num_reg_pagina=li_num_reg_pagina;
        this.TotalPagina();
        this.li_pag_inicio=1;
        this.li_pag_actual=1;
        this.li_pag_reg_inicio=1;
        this.li_pag_reg_fin=li_num_reg_pagina;
    }
    public final void TotalPagina(){
        double ld_total;
        String ls_numero; 

        if(this.li_total_reg_tabla>0 && (this.li_total_reg_tabla>this.li_num_reg_pagina)){
            ld_total=this.li_total_reg_tabla/this.li_num_reg_pagina;
            ls_numero= String.valueOf(ld_total);
            int intNumber = Integer.parseInt(ls_numero.substring(0, ls_numero.indexOf('.')));
            int decNumberInt = Integer.parseInt(ls_numero.substring(ls_numero.indexOf('.') + 1));
            if(decNumberInt>0){
                intNumber=intNumber+1;
            }
            this.li_pag_fin=intNumber;
        }else{
            this.li_pag_fin=1; 
        }        
    }
    public void paginaSiguiente(){
        if(this.li_pag_actual>this.li_pag_inicio){
            this.li_pag_actual=this.li_pag_actual-1;
            this.li_pag_reg_inicio=this.li_pag_reg_fin+1;
            this.li_pag_reg_fin=this.li_pag_reg_fin+this.li_num_reg_pagina;
        }
    }
    public void  paginaAnterior(){
        if(this.li_pag_fin>this.li_pag_actual){
            this.li_pag_actual=this.li_pag_actual+1;
            this.li_pag_reg_fin=this.li_pag_reg_fin-this.li_num_reg_pagina;
            this.li_pag_reg_inicio=this.li_pag_reg_fin-(this.li_pag_reg_fin-1);
        }
    }
    public Integer getLi_pag_inicio() {
        return li_pag_inicio;
    }

    public void setLi_pag_inicio(Integer li_pag_inicio) {
        this.li_pag_inicio = li_pag_inicio;
    }

    public Integer getLi_pag_fin() {
        return li_pag_fin;
    }

    public void setLi_pag_fin(Integer li_pag_fin) {
        this.li_pag_fin = li_pag_fin;
    }

    public Integer getLi_pag_actual() {
        return li_pag_actual;
    }

    public void setLi_pag_actual(Integer li_pag_actual) {
        this.li_pag_actual = li_pag_actual;
    }

    public Integer getLi_num_reg_pagina() {
        return li_num_reg_pagina;
    }

    public void setLi_num_reg_pagina(Integer li_num_reg_pagina) {
        this.li_num_reg_pagina = li_num_reg_pagina;
    }

    public Integer getLi_total_reg_tabla() {
        return li_total_reg_tabla;
    }

    public void setLi_total_reg_tabla(Integer li_total_reg_tabla) {
        this.li_total_reg_tabla = li_total_reg_tabla;
    }

    public Integer getLi_pag_reg_inicio() {
        return li_pag_reg_inicio;
    }

    public void setLi_pag_reg_inicio(Integer li_pag_reg_inicio) {
        this.li_pag_reg_inicio = li_pag_reg_inicio;
    }

    public Integer getLi_pag_reg_fin() {
        return li_pag_reg_fin;
    }

    public void setLi_pag_reg_fin(Integer li_pag_reg_fin) {
        this.li_pag_reg_fin = li_pag_reg_fin;
    }
    
    
}
