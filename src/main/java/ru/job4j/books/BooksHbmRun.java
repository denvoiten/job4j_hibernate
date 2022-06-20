package ru.job4j.books;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class BooksHbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Author first = Author.of("Gogol");
            Author second = Author.of("Dostoevsky");
            Author third = Author.of("Pushkin");

            Book one = Book.of("Dead Souls");
            Book two = Book.of("Crime and Punishment");
            Book three = Book.of("Captain's daughter");
            Book special = Book.of("Writers' Party");

            one.getAuthors().add(first);
            two.getAuthors().add(second);
            three.getAuthors().add(third);
            special.getAuthors().add(first);
            special.getAuthors().add(second);
            special.getAuthors().add(third);

            session.persist(one);
            session.persist(two);
            session.persist(three);
            session.persist(special);

            Book book = session.get(Book.class, 1);
            Book bookSecond = session.get(Book.class, 2);
            session.remove(book);
            session.remove(bookSecond);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
