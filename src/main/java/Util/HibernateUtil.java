package Util;

import Entity.Customer;
import Entity.Employees;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@Slf4j
public class HibernateUtil {
    @Getter
    private static final SessionFactory sessionFactory = buildSessionFactory();

    public static SessionFactory buildSessionFactory() {
        try{
            return new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Entity.Product.class)
                    .addAnnotatedClass(Customer.class)
                    .addAnnotatedClass(Entity.Supplier.class)
                    .addAnnotatedClass(Entity.Category.class)
                    .addAnnotatedClass(Employees.class)
                    .addAnnotatedClass(Entity.Sales.class)
                    .addAnnotatedClass(Entity.SaleItem.class)
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

