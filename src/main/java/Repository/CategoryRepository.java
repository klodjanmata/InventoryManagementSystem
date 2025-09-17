package Repository;

import Entity.Category;
import org.hibernate.Session;
import org.hibernate.Transaction;
import Util.HibernateUtil;

import java.util.List;

public class CategoryRepository {

    public Category findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Category.class, id);
        }
    }

    public List<Category> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Category", Category.class).list();
        }
    }

    public void save(Category category) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(category);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Category category) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(category);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Category category = session.get(Category.class, id);
            if (category != null) {
                session.remove(category);
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Category findByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Category WHERE name = :name", Category.class)
                    .setParameter("name", name)
                    .uniqueResult();
        }
    }
}
