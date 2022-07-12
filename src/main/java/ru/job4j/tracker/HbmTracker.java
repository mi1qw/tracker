package ru.job4j.tracker;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;


public class HbmTracker implements Store {
    private static SessionFactory sf = null;
    private static StandardServiceRegistry registry;

    static {
        try {
            registry = new StandardServiceRegistryBuilder().configure().build();
            sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    @Override
    public void init() {
    }

    @Override
    public Item add(final Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.persist(item);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    @Override
    public boolean replace(final Integer id, final Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        int res = session.createQuery("update Item i set i.name = :name, "
                                      + "i.description = :description,"
                                      + "i.created = :created"
                                      + " where i.id = :id")
                .setParameter("name", item.getName())
                .setParameter("description", item.getDescription())
                .setParameter("created", item.getCreated())
                .setParameter("id", id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
        return res != 0;
    }

    @Override
    public boolean delete(final Integer id) {
        Session session = sf.openSession();
        session.beginTransaction();
        int res = session.createQuery("delete from Item i where i.id = :id")
                .setParameter("id", id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
        return res != 0;
    }

    @Override
    public List<Item> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Item> result = session
                .createQuery("from Item ", Item.class)
                .list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public List<Item> findByName(final String name) {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Item> result = session
                .createQuery("from Item i where i.name=:name", Item.class)
                .setParameter("name", name)
                .list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public Item findById(final Integer id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item result = session
                .createQuery("from Item i where i.id = :id", Item.class)
                .setParameter("id", id)
                .uniqueResult();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public void close() {
        if (sf != null) {
            try {
                sf.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                StandardServiceRegistryBuilder.destroy(registry);
            }
        }
    }
}
