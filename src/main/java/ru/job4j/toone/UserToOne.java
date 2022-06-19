package ru.job4j.toone;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "j_user")
public class UserToOne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleToOne role;

    public static UserToOne of(String name, RoleToOne role) {
        UserToOne user = new UserToOne();
        user.name = name;
        user.role = role;
        return user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoleToOne getRole() {
        return role;
    }

    public void setRole(RoleToOne role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserToOne user = (UserToOne) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}