package Repository;

import Entity.Sale;
import Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.List;

public class SaleRepository {
    // Save
    public void save(Sale sale) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(sale);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    // Update
    public void update(Sale sale) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(sale);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    // Delete
    public void delete(Sale sale) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.remove(sale);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    // Find by ID
    public Sale findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT s FROM Sale s LEFT JOIN FETCH s.items WHERE s.id = :id", Sale.class)
                    .setParameter("id", id)
                    .uniqueResult();
        }
    }



    // Get all
    public List<Sale> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Sale", Sale.class).list();
        }
    }

    public List<Sale> findByCustomerId(int customerId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Sale s WHERE s.customer.id = :customerId";
            return session.createQuery(hql, Sale.class)
                    .setParameter("customerId", customerId)
                    .list();
        }
    }

    public List<Sale> findBySaleDateBetween(LocalDate start, LocalDate end) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Sale s WHERE s.saleDate BETWEEN :start AND :end";
            return session.createQuery(hql, Sale.class)
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .list();
        }
    }


}
