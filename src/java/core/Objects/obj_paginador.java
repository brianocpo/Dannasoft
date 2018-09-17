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
    private int li_pag_inicio;
    private int li_pag_fin;
    private int li_total_pag;
    private int li_pag_actual;
    private double ld_num_reg_x_pagina;
    private double ld_total_reg_tabla;
    private ArrayList<obj_pagina> lista_paginas;
    private String ls_ordenTB;
    //Para mensajes
    private int li_row_inicio;
    private int li_row_fin;
    public obj_paginador(){
        this.li_num_pag_visibles=5; 
        this.li_pag_inicio=1;
        this.li_row_inicio=0;
        this.li_row_fin=0;
        this.li_total_pag=0;
    }
    public void configurar(int ld_total_reg_tabla,int ld_num_reg_x_pagina,String ls_id_paginador, int li_pagina_actual,String ls_ordenTB, Integer li_num_pag_visibles) {        
        this.ld_total_reg_tabla=(double)ld_total_reg_tabla;
        this.ld_num_reg_x_pagina=(double)ld_num_reg_x_pagina;
        this.ls_id_paginador=ls_id_paginador;       
        this.li_pag_actual=li_pagina_actual;
        this.ls_htmlPaginador="";
        this.lista_paginas=new ArrayList<obj_pagina>();
        this.ls_ordenTB=ls_ordenTB; 
        this.li_num_pag_visibles=li_num_pag_visibles;
        this.armarPaginas();  
        this.paginaVisibles();
    }
    
    public final void armarPaginas(){
        double ld_total;
        String ls_numero; 
        int li_reg_inicio=0;
        int li_reg_fin=(int)this.ld_num_reg_x_pagina;
        boolean lb_pagina_activa;       
        //Se verifica si se debe generar mas de una página    
        if(this.ld_total_reg_tabla>0 && (this.ld_total_reg_tabla>this.ld_num_reg_x_pagina)){
            ld_total=this.ld_total_reg_tabla/this.ld_num_reg_x_pagina;
            ls_numero= String.valueOf(ld_total);            
            //Se separa la parte entera de la decimal
            int intNumber = Integer.parseInt(ls_numero.substring(0,ls_numero.indexOf('.')));
            int decNumberInt = Integer.parseInt(ls_numero.substring(ls_numero.indexOf('.') + 1));
            if(decNumberInt>0){//Si la parte decimal es mayor a cero se considera como un pagina más
                intNumber=intNumber+1;
            }
            //Se  crean las páginas
            for(int i=0;i<intNumber;i++){               
                int nombre_pagina=(i+1);
                if(nombre_pagina==li_pag_actual){
                    setLi_pag_actual(nombre_pagina);
                    lb_pagina_activa=true;
                    li_row_inicio=li_reg_inicio;
                    li_row_fin=li_reg_fin;
                }
                else{
                    lb_pagina_activa=false;
                }                
                obj_pagina pagina=new obj_pagina(String.valueOf(nombre_pagina), li_reg_inicio, li_reg_fin, lb_pagina_activa,this.ls_ordenTB);
                li_reg_inicio=li_reg_fin;
                li_reg_fin=li_reg_fin+ (int)this.ld_num_reg_x_pagina;
                //verifico si el calculo de reg_fin es mayor al total de registro                 
                if(li_reg_fin>this.ld_total_reg_tabla){
                    li_reg_fin=(int)this.ld_total_reg_tabla;
                }
                lista_paginas.add(pagina);               
            }
            //Se asigna la pos de la página final de la lista de paginas
            this.li_total_pag=intNumber;            
        }else{
            this.li_total_pag=1;
            obj_pagina pagina=new obj_pagina("1", 0, (int)ld_total_reg_tabla, true,this.ls_ordenTB);
            lista_paginas.add(pagina); 
            li_row_inicio=0;
            li_row_fin=(int)this.ld_total_reg_tabla;
        }        
    }
    public void paginaVisibles(){
        
        this.li_num_pag_visibles = (this.li_num_pag_visibles > this.li_total_pag) ? this.li_total_pag : this.li_num_pag_visibles;
        
        if(this.li_pag_actual==1){
            this.li_pag_inicio=1;
            this.li_pag_fin=this.li_num_pag_visibles;            
        }
        else if(this.li_pag_actual==this.li_total_pag){
            this.li_pag_fin=this.li_total_pag;
            this.li_pag_inicio=this.li_pag_fin - (this.li_num_pag_visibles - 1);
            if(this.li_pag_inicio<1){
                    this.li_pag_inicio=1;
            }
        }
        else{
            if(this.li_pag_actual==this.li_pag_fin && this.li_pag_fin< this.li_total_pag){
                this.li_pag_inicio=this.li_pag_fin;
                this.li_pag_fin=this.li_pag_inicio + (this.li_num_pag_visibles - 1);
                if(this.li_pag_fin>this.li_total_pag){
                    this.li_pag_fin=this.li_total_pag;
                }
            }else{
              if(this.li_pag_actual<=this.li_pag_inicio && this.li_pag_actual>1){
                this.li_pag_fin=this.li_pag_inicio;
                this.li_pag_inicio=this.li_pag_inicio - (this.li_num_pag_visibles - 1);
                if(this.li_pag_inicio<1){
                    this.li_pag_inicio=1;
                }
               }  
            }            
        }
        
        //Ajustes
        if(this.li_pag_inicio==1 && this.li_pag_fin<this.li_num_pag_visibles){
            this.li_pag_fin=this.li_num_pag_visibles;
        }
        
        if(this.li_pag_fin==li_total_pag){
            this.li_pag_inicio=this.li_pag_fin - (this.li_num_pag_visibles -1);
            if(this.li_pag_inicio<1){
                this.li_pag_inicio=1;
            }
        }
     
    }
    public String crearPaginador(){
        ls_htmlPaginador="<div class='row justify-content-center justify-content-md-start paginadorTB' >";
            ls_htmlPaginador+="<div class='col-md-4' style='padding-top:6px'>";
               ls_htmlPaginador+="Mostrando "+(li_row_inicio+1)+" al "+ (li_row_fin)+ " filas de "+(int)ld_total_reg_tabla;
            ls_htmlPaginador+="</div>";
            
            ls_htmlPaginador+="<div class='col-md-8'>";
                ls_htmlPaginador+="<ul class='pagination pull-right'>";
                ls_htmlPaginador+=htmlPagina("inicio");
                ls_htmlPaginador+=htmlPagina("anterior");
                for(int i=this.li_pag_inicio-1;i<this.li_pag_fin;i++){            
                    ls_htmlPaginador+=lista_paginas.get(i).getLs_htmlPagina();
                }
                ls_htmlPaginador+=htmlPagina("siguiente");
                ls_htmlPaginador+=htmlPagina("fin");
                ls_htmlPaginador+="</ul>";
            ls_htmlPaginador+="</div>";    
        ls_htmlPaginador+="</div>";
        return ls_htmlPaginador;
    }
    public String htmlPagina(String tipo){
        String ls_class="";
        String ls_class2="";
        String ls_texto="";
        String ls_style="";
        
        int li_rag_inicio=0;
        int li_nombre_pagina=0;
        int index=-1;
        
        switch(tipo){
        case "inicio":  ls_class="";        
                        ls_texto="Inicio";
                        li_nombre_pagina=1;
                        index=BuscarIndexPagina(String.valueOf(0));
                         break;
        case "fin":     ls_class="";
                        ls_texto="Fin";
                        li_nombre_pagina=li_total_pag;
                        index=BuscarIndexPagina(String.valueOf(li_total_pag));
                        break;
        case "siguiente":   ls_class="ace-icon fa fa-angle-double-right";
                        ls_style="padding-top:9px;";
                        li_nombre_pagina=paginaSiguiente();
                        if(li_nombre_pagina==0){  ls_class2 ="disabled"; }else{
                            index=BuscarIndexPagina(String.valueOf(li_nombre_pagina));
                        }
                         break;
        case "anterior":  ls_class="ace-icon fa fa-angle-double-left";
                        ls_style="padding-top:9px;";
                        li_nombre_pagina=paginaAnterior();
                        if(li_nombre_pagina==0){  ls_class2 ="disabled"; }else{
                            index=BuscarIndexPagina(String.valueOf(li_nombre_pagina));
                        }
                        break;                 
            
        }

        if(index!=-1){           
           li_rag_inicio= lista_paginas.get(index).getLi_rag_inicio();
        }else{
           li_rag_inicio=0;
           li_nombre_pagina=1;
        }
        
        String ls_htmlPagina=""; 
        ls_htmlPagina="<li class='"+ls_class2+"' style='cursor:pointer;' >";
        ls_htmlPagina+="<a style='"+ls_style+"' onclick='actualizarTablaPaginador("+li_rag_inicio+","+li_nombre_pagina+"," + ls_ordenTB + ")'> <i class='"+ls_class+"'>"+ls_texto+"</i> </a>";
        ls_htmlPagina+="</li>";
        return ls_htmlPagina;
    }
    public int BuscarIndexPagina(String nombrePagina){        
        for(int i=0;i<lista_paginas.size();i++){                        
            String nombrePaginaAct=String.valueOf(lista_paginas.get(i).getLs_nombre_pagina()).trim();            
            if(nombrePaginaAct.equals(nombrePagina)){
                return i;
            }
        }
        return -1;
    }
    public int paginaSiguiente(){
        if(this.li_pag_actual<this.li_total_pag){
            return this.li_pag_actual+1;
        }
        return 0;
    }
    public int  paginaAnterior(){
        if(this.li_pag_actual>1){
            return this.li_pag_actual-1;            
        }
        return 0;
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

    
    public String getLs_id_paginador() {
        return ls_id_paginador;
    }

    public void setLs_id_paginador(String ls_id_paginador) {
        this.ls_id_paginador = ls_id_paginador;
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
