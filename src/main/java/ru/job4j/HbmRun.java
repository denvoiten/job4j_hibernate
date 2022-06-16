package ru.job4j;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.Query;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            session.save(Candidate.of("Max", 2, 1500));
            session.save(Candidate.of("Alex", 5, 4000));
            session.save(Candidate.of("Denis", 1, 1000));

            Query query = session.createQuery("from Candidate");
            for (Object candidate : query.getResultList()) {
                System.out.println(candidate);
            }

            System.out.println(session.createQuery("from Candidate s where s.id = 3").uniqueResult());
            System.out.println(session.createQuery("from Candidate s where s.name = 'Alex'").uniqueResult());

            session.createQuery("update Candidate  s set s.experience = :newExp, s.salary = :newSalary where s.id = :fId")
                    .setParameter("newExp", 2)
                    .setParameter("newSalary", 2000)
                    .setParameter("fId", 3)
                    .executeUpdate();

            session.createQuery("delete from Candidate where id = :fId")
                    .setParameter("fId", 1)
                    .executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}