/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.Objects;

import core.Objects.obj_pagina;
import java.util.ArrayList;

/**
 *
 * @author brian
 */
public class obj_paginador {
    private String ls_htmlPaginador;
    private Integer li_num_pag_visibles;
    private String ls_id_paginador;
    private Integer li_pag_inicio;
    private Integer li_pag_fin;
    private Integer li_pag_actual;
    private Integer li_num_reg_x_pagina;
    private Integer li_total_reg_tabla;
    private ArrayList<obj_pagina> lista_paginas;
    public obj_paginador(){
        this.li_num_pag_visibles=5; 
    }
    public void configurar(Integer li_total_reg_tabla,Integer li_num_reg_x_pagina,String ls_id_paginador) {        
        this.li_total_reg_tabla=li_total_reg_tabla;
        this.li_num_reg_x_pagina=li_num_reg_x_pagina;
        this.ls_id_paginador=ls_id_paginador;
        lista_paginas=new ArrayList<obj_pagina>();
        this.armarPaginas();
        this.ls_htmlPaginador="";
    }
    public final void armarPaginas(){
        double ld_total;
        String ls_numero; 
        int li_reg_inicio=1;
        int li_reg_fin=this.li_num_reg_x_pagina;
        boolean lb_pagina_activa;
        
        if(this.li_total_reg_tabla>0 && (this.li_total_reg_tabla>this.li_num_reg_x_pagina)){
            ld_total=this.li_total_reg_tabla/this.li_num_reg_x_pagina;
            ls_numero= String.valueOf(ld_total);
            //Se separa la parte entera de la decimal
            int intNumber = Integer.parseInt(ls_numero.substring(0,ls_numero.indexOf('.')));
            int decNumberInt = Integer.parseInt(ls_numero.substring(ls_numero.indexOf('.') + 1));
            if(decNumberInt>0){//Si la parte decimal es mayor a cero se considera como un pagina más
                intNumber=intNumber+1;
            }
            //Se  crean las páginas
            for(int i=0;i<intNumber;i++){
                if(i==0){lb_pagina_activa=true;}else{lb_pagina_activa=false;}                
                obj_pagina pagina=new obj_pagina(String.valueOf(i+1), li_reg_inicio, li_reg_fin, lb_pagina_activa);
                li_reg_inicio=li_reg_fin+1;
                li_reg_fin=li_reg_fin+this.li_num_reg_x_pagina;
                //verifico si el calculo de reg_fin es mayor al total de registro                 
                if(li_reg_fin>this.li_total_reg_tabla){
                    li_reg_fin=this.li_total_reg_tabla;
                }
                lista_paginas.add(pagina);               
            }
            //Se asigna la pos de la página final de la lista de paginas
            this.li_pag_fin=intNumber-1;            
        }else{
            this.li_pag_fin=0;
            obj_pagina pagina=new obj_pagina("1", 1, this.li_total_reg_tabla, true);
            lista_paginas.add(pagina);           
        }
        
        //Inicializamos las variables
        if(lista_paginas.size()>0){
             this.li_pag_inicio=0;
             this.li_pag_actual=0;        
        }
    }
    public String crearPaginador(){
        ls_htmlPaginador="<ul class=\"pagination\">";
        for(int i=0;i<lista_paginas.size();i++){            
            ls_htmlPaginador+=lista_paginas.get(i).getLs_htmlPagina();
        }
        ls_htmlPaginador+="</ul>";
        
        return ls_htmlPaginador;
    }
    public void paginaSiguiente(){
        if(this.li_pag_actual<this.li_pag_fin){
            this.li_pag_actual=this.li_pag_actual+1;
        }
    }
    public void  paginaAnterior(){
        if(this.li_pag_actual>this.li_pag_inicio){
            this.li_pag_actual=this.li_pag_actual-1;            
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

    
    public Integer getLi_total_reg_tabla() {
        return li_total_reg_tabla;
    }

    public void setLi_total_reg_tabla(Integer li_total_reg_tabla) {
        this.li_total_reg_tabla = li_total_reg_tabla;
    }

    public String getLs_id_paginador() {
        return ls_id_paginador;
    }

    public void setLs_id_paginador(String ls_id_paginador) {
        this.ls_id_paginador = ls_id_paginador;
    }

    public Integer getLi_num_reg_x_pagina() {
        return li_num_reg_x_pagina;
    }

    public void setLi_num_reg_x_pagina(Integer li_num_reg_x_pagina) {
        this.li_num_reg_x_pagina = li_num_reg_x_pagina;
    }

    public ArrayList<obj_pagina> getLista_paginas() {
        return lista_paginas;
    }

    public void setLista_paginas(ArrayList<obj_pagina> lista_paginas) {
        this.lista_paginas = lista_paginas;
    }

    public String getLs_htmlPaginador() {
        return ls_htmlPaginador;
    }

    public void setLs_htmlPaginador(String ls_htmlPaginador) {
        this.ls_htmlPaginador = ls_htmlPaginador;
    }
    
  
}
