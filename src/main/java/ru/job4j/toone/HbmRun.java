package ru.job4j.toone;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.List;

public class HbmRun {
    private static final Logger LOG = LoggerFactory.getLogger(HbmRun.class.getName());

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            RoleToOne role = create(RoleToOne.of("ADMIN"), sf);
            create(UserToOne.of("Petr Arsentev", role), sf);
            for (UserToOne user : findAll(UserToOne.class, sf)) {
                LOG.info(String.format("%s %s", user.getName(), user.getRole().getName()));
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static <T> T create(T model, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(model);
        session.getTransaction().commit();
        session.close();
        return model;
    }

    public static <T> List<T> findAll(Class<T> cl, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        List<T> list = session.createQuery("from " + cl.getName(), cl).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }
}
