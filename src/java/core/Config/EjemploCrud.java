/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.Config;


import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 *
 * @author brian
 */
public class EjemploCrud {
    
    public static void main(String[] args) {
        List lst = null;
        Query query=null;
        query = Conexion.getSession().createSQLQuery("select nombre_usu,clave_usu from mod_administracion.s_usuario");       
        lst = query.list();

        Iterator it_data_bdd = lst.iterator();
       
        while (it_data_bdd.hasNext()) { 
             Object[] tabla = (Object[]) it_data_bdd.next();
             System.out.println( tabla[1]);
        }
        Conexion.closeSesion();


        query = Conexion.getSession().createSQLQuery("select nombre_opc,tabla1_opc from mod_administracion.s_opcion"); 
        lst = query.list();
        it_data_bdd = lst.iterator();
        while (it_data_bdd.hasNext()) { 
             Object[] tabla = (Object[]) it_data_bdd.next();
             System.out.println( tabla[1]);
        }
        Conexion.closeSesion();
        
        //transaccion
        try{
            Conexion.getSession().beginTransaction();                      
            SQLQuery query2 = Conexion.getSession().createSQLQuery("INSERT INTO mod_administracion.s_usuario (nombre_usu,usuario_usu,clave_usu) values('p1','p1','123') "); 
            //SQLQuery query2 = conexionBDD.getSession().createSQLQuery("delete from mod_administracion.s_usuario where codigo_usu=2"); 
            query2.executeUpdate();
            Conexion.getSession().getTransaction().commit();
            Conexion.closeSesion();
            System.out.println("transacción OK ");
        }catch(Exception e){
            System.out.println("Error durante la transacción: "+e);
            Conexion.getSession().getTransaction().rollback();
        }
        
    }
    
}
