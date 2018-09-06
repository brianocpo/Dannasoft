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
public class obj_pagina {
    
    private String ls_nombre_pagina;
    private Integer li_rag_inicio;
    private Integer li_rag_fin;
    private Boolean lb_estado;

    public obj_pagina(String ls_nombre_pagina, Integer li_rag_inicio, Integer li_rag_fin, Boolean lb_estado) {
        this.ls_nombre_pagina = ls_nombre_pagina;
        this.li_rag_inicio = li_rag_inicio;
        this.li_rag_fin = li_rag_fin;
        this.lb_estado = lb_estado;
    }

    public Boolean getLb_estado() {
        return lb_estado;
    }

    public void setLb_estado(Boolean lb_estado) {
        this.lb_estado = lb_estado;
    }

    public String getLs_nombre_pagina() {
        return ls_nombre_pagina;
    }

    public void setLs_nombre_pagina(String ls_nombre_pagina) {
        this.ls_nombre_pagina = ls_nombre_pagina;
    }

    public Integer getLi_rag_inicio() {
        return li_rag_inicio;
    }

    public void setLi_rag_inicio(Integer li_rag_inicio) {
        this.li_rag_inicio = li_rag_inicio;
    }

    public Integer getLi_rag_fin() {
        return li_rag_fin;
    }

    public void setLi_rag_fin(Integer li_rag_fin) {
        this.li_rag_fin = li_rag_fin;
    }
    
    
    
}
