package ru.job4j.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CarHbmRun {
    private static final Logger LOG = LoggerFactory.getLogger(CarHbmRun.class.getName());

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            CarBrand bmw = CarBrand.of("BMW");
            session.save(bmw);

            CarModel x5 = CarModel.of("X5");
            CarModel x3 = CarModel.of("X3");
            CarModel x1 = CarModel.of("X1");
            CarModel m5 = CarModel.of("M5");
            CarModel m4 = CarModel.of("M4");
            bmw.addModel(session.load(CarModel.class, 1));
            bmw.addModel(session.load(CarModel.class, 2));
            bmw.addModel(session.load(CarModel.class, 3));
            bmw.addModel(session.load(CarModel.class, 4));
            bmw.addModel(session.load(CarModel.class, 5));

            session.save(x5);
            session.save(x3);
            session.save(x1);
            session.save(m5);
            session.save(m4);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            LOG.error(e.getMessage());
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
