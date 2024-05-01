package eus.ehu.sharetrip.domain;

import jakarta.persistence.*;
import javafx.util.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "USERS") // Renames the table to avoid using a reserved keyword
@DiscriminatorColumn(name = "USER_TYPE", discriminatorType = DiscriminatorType.STRING)
public class User {

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_favorites",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "ride_id")
    )
    private List<Ride> favRides = new ArrayList<>();

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

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    // Builder static inner class
    public static class Userbuilder {
        private String email;
        private String username;
        private String password;

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public User build() {
            return new User(this);
        }
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

    public List<Ride> getFavRides() {
        return favRides;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id.equals(user.id);
    }

    public void addFavRide(Ride ride) {
        favRides.add(ride);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }


}
