package Util;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    @Getter
    private static final SessionFactory sessionFactory = buildSessionFactory();

    public static SessionFactory buildSessionFactory() {
        try{
            return new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Entity.Product.class)
                    .buildSessionFactory();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void shutdown() {
        sessionFactory.close();
    }

}

