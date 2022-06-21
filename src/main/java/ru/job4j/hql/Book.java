package ru.job4j.hql;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String publishingHouse;

    public static Book of(String name, String publishingHouse) {
        Book b = new Book();
        b.name = name;
        b.publishingHouse = publishingHouse;
        return b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Book book = (Book) o;
        return id == book.id && Objects.equals(name, book.name) && Objects.equals(publishingHouse, book.publishingHouse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, publishingHouse);
    }
}
