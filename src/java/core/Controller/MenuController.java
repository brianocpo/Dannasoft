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
        Soporte so= new Soporte();
        so.borrarGarbage();
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
        model.addAttribute("ls_schema", ls_schema);
        String ls_nombreTabla1 = "s_usuario";
        String ls_query = "";
        String ls_where = "";
        String ls_orden = "nombre_usu ASC";
        obj_tabla Tabla1 = new obj_tabla();
        Tabla1.configTabla(ls_catalog, ls_schema, ls_nombreTabla1, ls_query, ls_where, ls_orden);
        Tabla1.crearTabla();
        Tabla1.setLs_Id_Tabla("s_usuario");
        Tabla1.setLs_ordenTB("1");
        Tabla1.setTituloTabla("Usuarios");
        model.addAttribute("tabla", Tabla1.getTablaHtml());
        return "pantalla_simple";        
    }

    @RequestMapping("/PaisCiudad")
    public String PaisCiudad(Model model) {
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
        Tabla2.setLs_nombre_campo_padre("codigo_pai");
        Tabla2.crearTabla();
        Tabla2.setLs_Id_Tabla("v_ciudad");
        Tabla2.setLs_ordenTB("2");
        Tabla2.setLs_AltoTabla("50%");
        Tabla2.setTituloTabla("CIUDAD");
        Tabla2.setLs_IdDivTabla("tabla2");
        
        //Se asocia la tabla hija a la tabla padre
        obj_grupo_tabla GrupTables = new obj_grupo_tabla(Tabla1, Tabla2);
        model.addAttribute("tabla", GrupTables.getTablaHtml());
        //Se genera solo el esquema de la tabla hija
        model.addAttribute("tabla2", Tabla2.getTablaHtml());
        model.addAttribute("tabla2", Tabla2.getTablaHtml());
        
        return "pantalla_doble";
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
        model.addAttribute("tabla", Tabla1.getTablaHtml());
        model.addAttribute("ls_schema", ls_schema);
        return "pantalla_simple";
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
        model.addAttribute("tabla", Tabla1.getTablaHtml());
        model.addAttribute("ls_schema", ls_schema);
        return "pantalla_simple";
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
