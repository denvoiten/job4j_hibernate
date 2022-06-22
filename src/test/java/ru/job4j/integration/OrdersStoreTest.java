package ru.job4j.integration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class OrdersStoreTest {
    private BasicDataSource pool = new BasicDataSource();

    @Before
    public void setUp() throws SQLException {
        pool.setDriverClassName("org.hsqldb.jdbcDriver");
        pool.setUrl("jdbc:hsqldb:mem:tests;sql.syntax_pgs=true");
        pool.setUsername("sa");
        pool.setPassword("");
        pool.setMaxTotal(2);
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream("./db/update_001.sql")))
        ) {
            br.lines().forEach(line -> builder.append(line).append(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pool.getConnection().prepareStatement(builder.toString()).executeUpdate();
    }

    @After
    public void dropTable() throws SQLException {
        pool.getConnection().prepareStatement("DROP TABLE orders").executeUpdate();
        pool.close();
    }

    @Test
    public void whenSaveOrderAndFindAllOneRowWithDescription() {
        OrdersStore store = new OrdersStore(pool);

        store.save(Order.of("name1", "description1"));

        List<Order> all = (List<Order>) store.findAll();

        assertThat(all.size(), is(1));
        assertThat(all.get(0).getDescription(), is("description1"));
        assertThat(all.get(0).getId(), is(1));
    }

    @Test
    public void whenSaveOrderAndUpdateName() {
        OrdersStore store = new OrdersStore(pool);
        Order sergey = Order.of("Sergey", "description");
        Order alex = Order.of("Alex", "bla bla bla");
        store.save(sergey);
        assertThat(store.findById(1).getName(), is("Sergey"));
        store.update(alex, 1);
        assertThat(store.findById(1).getName(), is("Alex"));
    }

    @Test
    public void whenSaveOrdersAndFindByName() {
        OrdersStore store = new OrdersStore(pool);
        store.save(Order.of("Sergey", "description"));
        store.save(Order.of("Alex", "description"));
        assertThat(store.findByName("Sergey").getId(), is(1));
        assertThat(store.findByName("Sergey").getName(), is("Sergey"));
    }

    @Test
    public void whenSaveOrdersAndFindById() {
        OrdersStore store = new OrdersStore(pool);
        store.save(Order.of("Sergey", "description"));
        store.save(Order.of("Alex", "description"));
        assertThat(store.findById(2).getName(), is("Alex"));
    }
}