package ru.job4j.vacancy;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
@Entity
@Table(name = "vacancies")
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    public static Vacancy of(String name) {
        Vacancy vacancy = new Vacancy();
        vacancy.name = name;
        return vacancy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vacancy vacancy = (Vacancy) o;
        return id == vacancy.id && Objects.equals(name, vacancy.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
