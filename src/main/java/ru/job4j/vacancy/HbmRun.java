package ru.job4j.vacancy;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

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

            Candidate alex = Candidate.of("Alex", 2, 150_000);
            Candidate den = Candidate.of("Den", 1, 115_000);
            Candidate anna = Candidate.of("Anna", 7, 250_000);

            VacancyStore store = VacancyStore.of("HH.ru");

            Vacancy junior = Vacancy.of("Junior Java Developer");
            Vacancy middle = Vacancy.of("Middle Java Developer");
            Vacancy senior = Vacancy.of("Senior Java Developer");
            session.save(junior);
            session.save(middle);
            session.save(senior);

            store.setVacancies(List.of(junior, middle, senior));

            session.save(store);

            session.save(alex);
            session.save(den);
            session.save(anna);

            alex.setStore(store);
            den.setStore(store);
            anna.setStore(store);

            session.createQuery("select vs from VacancyStore vs "
                            + "join fetch vs.vacancies v "
                            + "where vs.id = :vsId", VacancyStore.class)
                    .setParameter("vsId", 1).uniqueResult();

            session.createQuery("select c from Candidate c "
                            + "join fetch c.store s "
                            + "join fetch s.vacancies v "
                            + "where c.id = :cId", Candidate.class)
                    .setParameter("cId", 1).uniqueResult();

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}