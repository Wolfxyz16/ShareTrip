package eus.ehu.sharetrip.domain;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("TRAVELER")
public class Traveler extends User {
    public Traveler(String email, String userName, String password) {
        super(email, userName, password);
    }

    public Traveler() {
    }
}
