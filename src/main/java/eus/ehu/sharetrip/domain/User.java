package eus.ehu.sharetrip.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "USERS") // Renames the table to avoid using a reserved keyword
@DiscriminatorColumn(name = "USER_TYPE", discriminatorType = DiscriminatorType.STRING)
public class User {

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ride> favRides = new ArrayList<Ride>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_TYPE", insertable = false, updatable = false)
    private String userType;

    protected String email;
    protected String username;
    protected String password;

    public User() {
    }

    public User(String email, String name, String password) {
        this.username = name;
        this.password = password;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User{" + "username='" + username + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }


}
