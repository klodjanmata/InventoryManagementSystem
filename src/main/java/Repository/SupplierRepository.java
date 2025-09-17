package Repository;

import Entity.Supplier;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class SupplierRepository {

    private final SessionFactory sessionFactory;

    public SupplierRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // Save
    public void save(Supplier supplier) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.persist(supplier);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    // Update
    public void update(Supplier supplier) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.merge(supplier);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    // Delete
    public void delete(Supplier supplier) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.remove(supplier);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    // Find by ID
    public Supplier findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Supplier.class, id);
        }
    }

    // Get all suppliers
    public List<Supplier> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Supplier", Supplier.class).list();
        }
    }
}
