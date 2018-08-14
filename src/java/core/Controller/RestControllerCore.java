/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.Controller;

import com.google.gson.Gson;
import core.Config.CrudGenerico;
import core.Config.Soporte;
import core.Objects.obj_acciones_array_tablas;
import core.Objects.obj_tabla;
import java.util.Collections;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author brian
 */
@RestController
public class RestControllerCore {
    /*Procesa las tablas Hijas Pantallas Dobles*/
    @RequestMapping(value = "/post/json", method = RequestMethod.POST)
    public @ResponseBody
    obj_tabla JsonToObjectTable(@RequestBody String jsonString) {
        Gson gson = new Gson();
        obj_tabla obj2 = gson.fromJson(jsonString, obj_tabla.class);
        obj2.crearTabla();
        obj2.getHtmlTabla();
        return obj2;
    }
    /*Procesa Todos las acciones de (update,insert,delete) de las Tablas simples y dobles*/ 
    @RequestMapping(value = "/post/jsonTable", method = RequestMethod.POST)
    public @ResponseBody
    obj_acciones_array_tablas JsonAccionTable(@RequestBody String jsonTable) {
        Gson gson = new Gson();
        obj_acciones_array_tablas AccionesTabla = gson.fromJson(jsonTable, obj_acciones_array_tablas.class);
        Collections.sort(AccionesTabla.getTablaBDD());
         
        Soporte Soporte = new Soporte();
        String[] las_sqlAcciones = Soporte.ArraySQLAccionesTablas(AccionesTabla);
        CrudGenerico.ejecutarSQL(las_sqlAcciones);
       
        return AccionesTabla;
    }

}
