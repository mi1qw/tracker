package ru.job4j.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class HbnTracker implements RepoInterface {
    private static final RepoInterface INSTANCE = new HbnTracker();
    private static SessionFactory sf;

    public HbnTracker() {
        if (sf == null) {
            StandardServiceRegistry registry =
                    new StandardServiceRegistryBuilder().configure().build();
            sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        }
    }

    public static RepoInterface getINSTANCE() {
        return INSTANCE;
    }

    /**
     * короткий вариант.
     * <pre> {@code session.persist(candidate);
     *  }</pre>
     *
     * @param candidate Candidate
     * @return Candidate
     */
    @Override
    public Candidate add(final Candidate candidate) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.persist(candidate);
        session.getTransaction().commit();
        session.close();
        return candidate;
    }

    /**
     * короткий вариант.
     *
     * <pre> {@code candidate.setId(id);
     * session.update(candidate);
     *  }</pre>
     *
     * @param id        id
     * @param candidate candidate
     * @return boolean
     */
    @Override
    public boolean replace(final int id, final Candidate candidate) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("update Candidate c set c.name = :name, "
                            + "c.experience = :experience,"
                            + "c.salary = :salary"
                            + " where c.id = :id")
                .setParameter("name", candidate.getName())
                .setParameter("experience", candidate.getExperience())
                .setParameter("salary", candidate.getSalary())
                .setParameter("id", id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
        return true;
    }

    /**
     * вариант.
     * <pre> {@code Candidate candidate = Candidate.of("", 0, BigDecimal.ZERO);
     * candidate.setId(id);
     * session.delete(candidate); }</pre>
     *
     * @param id id
     * @return boolean
     */
    @Override
    public boolean delete(final int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("delete from Candidate c where c.id = :id")
                .setParameter("id", id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
        return true;
    }

    /**
     * или напрямую указываем класс.
     * <pre>{@code createQuery("from ru.job4j.hql.Candidate").list() }</pre>
     *
     * @return List Candidate
     */
    @Override
    public List<Candidate> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Candidate> result = session.createQuery("from Candidate").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public List<Candidate> findByName(final String name) {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Candidate> result = session
                .createQuery("from ru.job4j.hql.Candidate where name=:name")
                .setParameter("name", name)
                .list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    /**
     * вариант.
     * <pre> {@code session.get(Candidate.class, id) }</pre>
     *
     * @param id id
     * @return Candidate
     */
    @Override
    public Candidate findById(final int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Candidate result = (Candidate) session
                .createQuery("from Candidate s where s.id = :fId")
                .setParameter("fId", id)
                .uniqueResult();
        session.getTransaction().commit();
        session.close();
        return result;
    }
}
