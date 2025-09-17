package Repository;

import Entity.SaleItem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class SaleItemRepository {

    private final SessionFactory sessionFactory;

    public SaleItemRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // Save
    public void save(SaleItem saleItem) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.persist(saleItem);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    // Update
    public void update(SaleItem saleItem) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.merge(saleItem);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    // Delete
    public void delete(SaleItem saleItem) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.remove(saleItem);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    // Find by ID
    public SaleItem findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(SaleItem.class, id);
        }
    }

    // Get all SaleItems
    public List<SaleItem> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from SaleItem", SaleItem.class).list();
        }
    }
}
