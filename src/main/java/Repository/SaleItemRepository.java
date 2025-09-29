package Repository;

import Entity.SaleItem;
import Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.util.List;

public class SaleItemRepository {
    // Save
    public void save(SaleItem saleItem) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
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
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
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
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
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
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(SaleItem.class, id);
        }
    }

    // Get all SaleItems
    public List<SaleItem> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from SaleItem", SaleItem.class).list();
        }
    }

    public List<SaleItem> findBySaleId(Long saleId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM SaleItem si WHERE si.sale.id = :saleId", SaleItem.class)
                    .setParameter("saleId", saleId)
                    .list();
        }
    }

    public BigDecimal calculateSubtotal(SaleItem item) {
        return item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
    }

    public List<SaleItem> findByProductId(Long productId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM SaleItem si WHERE si.product.id = :productId", SaleItem.class)
                    .setParameter("productId", productId)
                    .list();
        }
    }

    private void executeTransaction(java.util.function.Consumer<Session> action) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            action.accept(session);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }
}
