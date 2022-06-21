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
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int age;

    private String city;

    @OneToOne
    private Account account;

    public static Student of(String name, int age, String city) {
        Student student = new Student();
        student.name = name;
        student.age = age;
        student.city = city;
        return student;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Student student = (Student) o;
        return id == student.id && age == student.age && Objects.equals(name, student.name) && Objects.equals(city, student.city) && Objects.equals(account, student.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, city, account);
    }
}