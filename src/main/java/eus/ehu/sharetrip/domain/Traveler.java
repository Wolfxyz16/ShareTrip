package eus.ehu.sharetrip.domain;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("TRAVELER")
public class Traveler extends User {
    public Traveler(String email, String userName, String password) {
        super(email, userName, password);
    }

    public Traveler() {
    }
}