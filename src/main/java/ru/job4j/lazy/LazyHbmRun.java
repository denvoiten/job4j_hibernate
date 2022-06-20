package ru.job4j.lazy;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.List;

public class LazyHbmRun {
    public static void main(String[] args) {
        List<CarBrand> list = new ArrayList<>();
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            CarBrand bmw = CarBrand.of("BMW");
            CarBrand audi = CarBrand.of("Audi");
            session.save(bmw);
            session.save(audi);

            CarModel x5 = CarModel.of("X5", bmw);
            CarModel x3 = CarModel.of("X3", bmw);

            CarModel q5 = CarModel.of("Q5", audi);
            CarModel q7 = CarModel.of("Q7", audi);

            session.save(x5);
            session.save(x3);
            session.save(q5);
            session.save(q7);

            list = session.createQuery(
                    "select distinct b from CarBrand b join fetch b.models"
            ).list();
            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        for (CarModel model : list.get(0).getModels()) {
            System.out.println(model);
        }
    }
}
