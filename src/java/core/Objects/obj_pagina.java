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
    private String ls_htmlPagina;    
    public obj_pagina(String ls_nombre_pagina, Integer li_rag_inicio, Integer li_rag_fin, Boolean lb_estado) {
        this.ls_nombre_pagina = ls_nombre_pagina;
        this.li_rag_inicio = li_rag_inicio;
        this.li_rag_fin = li_rag_fin;
        this.lb_estado = lb_estado;
        this.ls_htmlPagina="";
        crearPagina();
    }
    public String crearPagina(){
        String ls_estado="";
        if(this.lb_estado){ls_estado="active";}
        ls_htmlPagina="<li class='"+ls_estado+"'>";
        ls_htmlPagina+="<a onclick='actualizarTabla("+li_rag_inicio+")'> "+ls_nombre_pagina+" </a>";
        ls_htmlPagina+="</li>";
        return this.ls_htmlPagina;
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

    public String getLs_htmlPagina() {
        return ls_htmlPagina;
    }

    public void setLs_htmlPagina(String ls_htmlPagina) {
        this.ls_htmlPagina = ls_htmlPagina;
    }
    
    
    
}
