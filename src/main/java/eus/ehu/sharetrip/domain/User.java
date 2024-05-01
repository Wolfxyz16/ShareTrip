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

    private String email;
    private String username;
    private String password;

    public User() {
    }

    // Builder static inner class
    public static class Builder {
        private String email;
        private String username;
        private String password;

        public Builder() {
        }

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
            if(email == null || username == null || password == null) {
                throw new IllegalStateException("Cannot create User without email, username and password");
            }
            User user = new User();
            user.email = this.email;
            user.username = this.username;
            user.password = this.password;
            return user;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFavRides(List<Ride> favRides) {
        this.favRides = favRides;
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
