package ru.kata.spring.boot_security.demo.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;
    @NotEmpty(message = "Это поле не должно быть пустым")
    @Size(min = 2, max = 30, message = "Слишком длинное имя")
    @Column(name = "name")
    private String userName;
    @NotEmpty(message = "Это поле не должно быть пустым")
    @Size(min = 2, max = 30, message = "Слишком длинная фамилия")
    @Column(name = "lastname")
    private String lastname;
    @Min(value = 0, message = "Возвраст не может быть отрицательным")
    @Column(name = "age")
    private int age;
    @NotEmpty(message = "Это поле не должно быть пустым")
    @Email(message = "Адрес эл.почты некорректный")
    @Column(name = "email")
    private String email;
    @NotEmpty(message = "Password не должен быть пустым")
    @Size(min = 4, message = "Password должен быть не менее 4-х символов")
    @Column(name = "password")
    private String password;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
    public User() {
    }
    public User(String userName, String lastname) {
        this.userName = userName;
        this.lastname = lastname;
    }

    public User(int id, String userName, String lastname, int age, String email, String password, Set<Role> roles) {
        this.id = id;
        this.userName = userName;
        this.lastname = lastname;
        this.age = age;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + userName + '\'' +
                ", lastname='" + lastname + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", roles='" + roles + '\'' +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return  roles.stream().map(p->new SimpleGrantedAuthority(p.getName())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
