package ru.job4j.vacancy;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "vacancy_store")
public class VacancyStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vacancy> vacancies = new ArrayList<>();

    public static VacancyStore of(String name) {
        VacancyStore vacancyStore = new VacancyStore();
        vacancyStore.name = name;
        return vacancyStore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VacancyStore that = (VacancyStore) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "VacancyStore{" + "id=" + id
                + ", name='" + name + '\''
                + ", vacancies=" + vacancies + '}';
    }
}
