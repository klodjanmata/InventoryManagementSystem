package Repository;

import Entity.Employees;
import org.hibernate.Session;
import org.hibernate.Transaction;
import Util.HibernateUtil;

import java.util.List;

public class EmployeeRepository {

    public Employees findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Employees.class, id);
        }
    }

    public List<Employees> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Employees", Employees.class).list();
        }
    }

    public void save(Employees employee) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(employee);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Employees employee) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(employee);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Employees employee = session.get(Employees.class, id);
            if (employee != null) {
                session.remove(employee);
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Employees> findByRole(String role) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Employees WHERE role = :role", Employees.class)
                    .setParameter("role", role)
                    .list();
        }
    }
}
