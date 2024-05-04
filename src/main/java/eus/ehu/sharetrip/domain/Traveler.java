package eus.ehu.sharetrip.domain;
import eus.ehu.sharetrip.configuration.UtilDate;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@DiscriminatorValue("TRAVELER")
public class Traveler extends User {

    @OneToMany(mappedBy = "madeBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reservation> reservations;

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public static class Builder extends User.Builder {
        public Builder() {
            super();
        }

        @Override
        public Traveler.Builder email(String email) {
            super.email(email);
            return this;
        }

        @Override
        public Traveler.Builder username(String username) {
            super.username(username);
            return this;
        }

        @Override
        public Traveler.Builder password(String password) {
            super.password(password);
            return this;
        }

        // Override build method to return Traveler instead of User
        @Override
        public Traveler build() {
            User user = super.build();
            Traveler traveler = new Traveler();
            traveler.setId(user.getId());
            traveler.setEmail(user.getEmail());
            traveler.setUsername(user.getUsername());
            traveler.setPassword(user.getPassword());
            traveler.reservations = new ArrayList<>();
            return traveler;
        }

    }
}
