package ru.job4j.many;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HbmRun {
    private static final Logger LOG = LoggerFactory.getLogger(HbmRun.class.getName());

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            User one = User.of("Petr");
            session.save(one);

            Role admin = Role.of("ADMIN");
            admin.addUser(session.load(User.class, 1));

            session.save(admin);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            LOG.error(e.getMessage());
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
