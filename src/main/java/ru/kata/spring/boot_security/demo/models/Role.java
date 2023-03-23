package ru.kata.spring.boot_security.demo.models;

import org.springframework.security.core.GrantedAuthority;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name_role;

//    @ManyToMany(mappedBy = "roles")
//    private List<User> users;
    @Override
    public String getAuthority() {
        return getName();
    }

    public Role() {
    }

    public Role(Long id, String name, List<User> users) {
        this.id = id;
        this.name_role = name;
//        this.users = users;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name_role;
    }

    public void setName(String name) {
        this.name_role = name;
    }


}
