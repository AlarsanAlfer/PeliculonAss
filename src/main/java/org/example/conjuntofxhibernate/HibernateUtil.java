package org.example.conjuntofxhibernate;

import lombok.Getter;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.InputStream;

public class HibernateUtil {
    @Getter
    private static final SessionFactory sessionFactory;


    static{
        sessionFactory = new Configuration()
                .configure()
                //.setProperty("hibernate.connection.password",System.getenv("MYSQL_ROOT_PASSWORD"))
                .buildSessionFactory();
    }


}
