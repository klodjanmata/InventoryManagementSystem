package Repository;

import Entity.Sales;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class SaleRepository {

    private final SessionFactory sessionFactory;

    public SaleRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // Save
    public void save(Sales sales) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.persist(sales);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    // Update
    public void update(Sales sales) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.merge(sales);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    // Delete
    public void delete(Sales sales) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.remove(sales);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    // Find by ID
    public Sales findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Sales.class, id);
        }
    }

    // Get all
    public List<Sales> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Sales", Sales.class).list();
        }
    }
}
