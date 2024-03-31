package eus.ehu.sharetrip.domain;

import jakarta.persistence.*;
import org.apache.logging.log4j.spi.MessageFactory2Adapter;

import java.util.ArrayList;
import java.util.Vector;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "USERS") // Renames the table to avoid using a reserved keyword
@DiscriminatorColumn(name = "USER_TYPE", discriminatorType = DiscriminatorType.STRING)
public class User {


    private String email;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    private String password;


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public User(String email, String name, String password) {
        this.userName = name;
        this.password = password;
        this.email = email;


    }


    public User() {

    }

    public String getName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }


}
