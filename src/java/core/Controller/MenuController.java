/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.Controller;


import core.Config.Soporte;
import core.Objects.obj_tabla;
import core.Objects.obj_grupo_tabla;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 *
 * @author brian
 */
@Controller
public class MenuController {

    String ls_catalog = "Dannasoft";
    String ls_schema = "mod_administracion";
    String ls_nombre_software = "Dannasoft";  

    public MenuController(){       
        Soporte.borrarGarbage();
        System.out.println("borro garbagecolector()");
    }
    
    @RequestMapping("/sistema")
    public String sistema(Model model) {
        model.addAttribute("TitlePage", this.ls_nombre_software + ": Login");
        model.addAttribute("saludo", "Hola Brian Ñandú");
        return "sistema";
    }

    @RequestMapping("/Prueba")
    public String usuarios(Model model) {
        return "PruebaSelect";
    }

    @RequestMapping("/Usuarios")
    public String VerDatosTabla(Model model) {
       
        String ls_query = "";
        String ls_where = "";
        String ls_orden = "nombre_usu ASC";
        obj_tabla Tabla1 = new obj_tabla();//Se instancia 
        Tabla1.configTabla(ls_catalog, ls_schema, "s_usuario", ls_query, ls_where, ls_orden);//Se configura la tabla
        //Se configura la tabla indispensable seguir el orden
        Tabla1.setLb_paginar(true);
        Tabla1.setLi_num_reg_x_pagina(10);
        Tabla1.setLs_Id_Tabla("s_usuario");
        Tabla1.setLs_ordenTB("1");
        Tabla1.setTituloTabla("Usuarios"); 
        Tabla1.setLs_IdDivTabla("tabla1");
        //Se crea la tabla
        Tabla1.crearTabla(); 
        Tabla1.setObjsonTabla(Soporte.convertObjTablaJson(Tabla1));//si estructura JSON se almacena en una variable ObjsonTabla
        
        model.addAttribute("tabla1", Tabla1.getTablaHtml());
        return "sistema";        
    }

    @RequestMapping("/PaisCiudad")
    public String PaisCiudad(Model model) {
      
        //Tabla Pais
        String ls_query = "";
        String ls_where = "";
        String ls_orden = "nombre_pai ASC";
        obj_tabla Tabla1 = new obj_tabla();
        Tabla1.configTabla(ls_catalog, ls_schema, "v_pais", ls_query, ls_where, ls_orden);
        Tabla1.setLb_paginar(true);
        Tabla1.setLi_num_reg_x_pagina(5);
        Tabla1.setLb_cargaHija(true);        
        Tabla1.setLs_Id_Tabla("v_pais");
        Tabla1.setLs_ordenTB("1");
        Tabla1.setLs_AltoTabla("50%");
        Tabla1.setTituloTabla("PAÍS");
        Tabla1.setLs_IdDivTabla("tabla1");
        Tabla1.crearTabla();
        Tabla1.setObjsonTabla(Soporte.convertObjTablaJson(Tabla1));//estructura JSON se almacena en una variable ObjsonTabla
        
        //Tabla Ciudad
        String ls_query2 = "";
        obj_tabla Tabla2 = new obj_tabla();
        Tabla2.configTabla(ls_catalog, ls_schema, "v_ciudad", ls_query2, "codigo_pai=0", "nombre_ciu ASC");
        Tabla2.setLb_paginar(true);
        Tabla2.setLi_num_reg_x_pagina(2);
        Tabla2.setLs_nombre_campo_padre("codigo_pai");       
        Tabla2.setLs_Id_Tabla("v_ciudad");
        Tabla2.setLs_ordenTB("2");
        Tabla2.setLs_AltoTabla("50%");
        Tabla2.setTituloTabla("CIUDAD");
        Tabla2.setLs_IdDivTabla("tabla2");
        Tabla2.crearTabla();        
        
        //Se asocia la tabla hija a la tabla padre
        obj_grupo_tabla GrupTables = new obj_grupo_tabla(Tabla1, Tabla2);
        model.addAttribute("tabla1", GrupTables.getTablaHtml());
        //Se genera solo el esquema de la tabla hija
        model.addAttribute("tabla2", Tabla2.getTablaHtml());
        
        return "sistema";
    }

    @RequestMapping("/PaisCiudadEstado")
    public String PaisCiudadEstado(Model model) {
        model.addAttribute("ls_schema", ls_schema);
        //Tabla Pais
        String ls_query = "";
        String ls_where = "";
        String ls_orden = "nombre_pai ASC";
        obj_tabla Tabla1 = new obj_tabla();
        Tabla1.configTabla(ls_catalog, ls_schema, "v_pais", ls_query, ls_where, ls_orden);
        Tabla1.setLb_cargaHija(true);
        Tabla1.crearTabla();
        Tabla1.setLs_Id_Tabla("v_pais");
        Tabla1.setLs_ordenTB("1");
        Tabla1.setLs_AltoTabla("50%");
        Tabla1.setTituloTabla("PAÍS");

        //Tabla Ciudad
        String ls_query2 = "";
        obj_tabla Tabla2 = new obj_tabla();
        Tabla2.configTabla(ls_catalog, ls_schema, "v_ciudad", ls_query2, "codigo_pai=0", "nombre_ciu ASC");
        Tabla2.setLb_cargaHija(true);
        Tabla2.setLs_nombre_campo_padre("codigo_pai");
        Tabla2.crearTabla();
        Tabla2.setLs_Id_Tabla("v_ciudad");
        Tabla2.setLs_ordenTB("2");
        Tabla2.setLs_AltoTabla("50%");
        Tabla2.setTituloTabla("CIUDAD");
        Tabla2.setLs_IdDivTabla("tabla2");
        
        //Tabla Estado
        obj_tabla Tabla3 = new obj_tabla();
        Tabla3.configTabla(ls_catalog, ls_schema, "v_estado","", "codigo_ciu=0", "nombre_est ASC");
        Tabla3.setLs_nombre_campo_padre("codigo_ciu");
        Tabla3.crearTabla();
        Tabla3.setLs_Id_Tabla("v_estado");
        Tabla3.setLs_ordenTB("3");
        Tabla3.setLs_AltoTabla("50%");
        Tabla3.setTituloTabla("Estado");
        Tabla3.setLs_IdDivTabla("tabla3");
        
        //Se asocia la tabla hija a la tabla padre
        obj_grupo_tabla GrupTables = new obj_grupo_tabla(Tabla1, Tabla2);
        obj_grupo_tabla GrupTables2 = new obj_grupo_tabla(Tabla2, Tabla3);
        
        model.addAttribute("tabla1", GrupTables.getTablaHtml());        
        model.addAttribute("tabla2", GrupTables2.getTablaHtml());  
        model.addAttribute("tabla3", Tabla3.getTablaHtml());
        
        return "sistema";
    }
    
    @RequestMapping("/PerfilUsuario")
    public String tb_perfiles(Model model) {

        String ls_nombreTabla1 = "s_perfil_usuario";
        String ls_query = "";
        String ls_where = "";
        String ls_orden = "nombre_per ASC";
        obj_tabla Tabla1 = new obj_tabla();
        Tabla1.configTabla(ls_catalog, ls_schema, ls_nombreTabla1, ls_query, ls_where, ls_orden);
        Tabla1.crearTabla();
        Tabla1.setLs_Id_Tabla("s_perfil_usuario");
        Tabla1.setLs_ordenTB("1");
        Tabla1.setTituloTabla("Perfiles de Usuarios");
        model.addAttribute("tabla1", Tabla1.getTablaHtml());
        model.addAttribute("ls_schema", ls_schema);
        return "sistema";
    }

    @RequestMapping("/Opcion")
    public String tb_opcion(Model model) {

        String ls_nombreTabla1 = "s_opcion";
        String ls_query = "";
        String ls_where = "";
        String ls_orden = "nombre_opc ASC";
        obj_tabla Tabla1 = new obj_tabla();
        Tabla1.configTabla(ls_catalog, ls_schema, ls_nombreTabla1, ls_query, ls_where, ls_orden);
        Tabla1.crearTabla();
        Tabla1.setLs_Id_Tabla("s_opcion");
        Tabla1.setLs_ordenTB("1");
        Tabla1.setTituloTabla("Opciones del Sistema");
        model.addAttribute("tabla1", Tabla1.getTablaHtml());
        model.addAttribute("ls_schema", ls_schema);
        return "sistema";
    }
    @RequestMapping(value = "/GetObjTable", method = RequestMethod.GET)
    public @ResponseBody String getData(@RequestBody String jsonString) {

//            Gson gson = new Gson();           
//            obj_tabla obj2 = gson.fromJson(jsonString, obj_tabla.class); 
        return "hola " + jsonString;
    }

    @RequestMapping("/Delete")
    public @ResponseBody String Eliminar(@RequestParam("Data") String DATA[],@RequestParam("Tabla") String Tabla) {
//        soporte so = new soporte();
//        DATA=so.ArrayDeletRow(DATA,Tabla.trim());
//        CrudHimbernate Crud = new CrudHimbernate();
//        String mensaje= Crud.ejecutarSQL(DATA);
        String mensaje="";
        return mensaje;
    }
    
    @RequestMapping("/Update")
    public void Actualizar(@RequestParam("Data") String DATA[],@RequestParam("Tabla") String Tabla) {
//         soporte sop = new soporte();
//         DATA=sop.ArrayUpdatetRow(DATA,Tabla);
//       // DATA = sop.remplaceStringVector(DATA, "?", " , ");
//        CrudHimbernate Crud = new CrudHimbernate();
//        Crud.ejecutarSQL(DATA);
        System.out.println(Tabla);
        System.out.println(DATA.length);
        System.out.println(DATA[0]);
    }
}
