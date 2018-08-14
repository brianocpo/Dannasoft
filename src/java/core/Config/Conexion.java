/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.Config;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author brian
 */
public class Conexion {

    private static ServiceRegistry serviceRegistry;
    private static SessionFactory sessionFactory;
    public static Session sessionBDD=getSession();

    private static SessionFactory configureSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();
            serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            return sessionFactory;
        } catch (HibernateException e) {
            System.out.append("** Exception in SessionFactory **"+e);
            e.printStackTrace();
        }

        return sessionFactory;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void rebuildSessionFactory() {
        try {
            sessionFactory = configureSessionFactory();
            System.out.println("Nueva sesion creada");
        } catch (Exception e) {
            System.err.println("%%%% Error Creando SessionFactory %%%%");
            e.printStackTrace();
        }
    }

    public static void rebuildSession() {
        try {
            if (sessionBDD == null || !sessionBDD.isOpen()) {
                sessionBDD = (sessionFactory != null) ? sessionFactory.openSession() : null;
                System.out.println("Open sesion");
            }
        } catch (Exception e) {
            System.err.println("%%%% Error Creando Session %%%%");
            e.printStackTrace();
        }
    }

    public static Session getSession() throws HibernateException {
        if (sessionFactory == null) {
            rebuildSessionFactory();
            rebuildSession();            
        } else {
            rebuildSession();          
        }
        return sessionBDD;
    }

    public static void closeSesion() {
        if (sessionBDD.isOpen()) {
            sessionBDD.close();
            System.out.println("Sesion cerrada");
        }
    }

    public static void closeFactory() {
        if (sessionFactory != null) {
            try {
                sessionFactory.close();
                System.out.println("sessionFactory cerrada");
            } catch (HibernateException ignored) {
                System.err.println("Couldn't close SessionFactory" + ignored);
            }
        }
    }

}
