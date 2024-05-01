package eus.ehu.sharetrip.domain;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("TRAVELER")
public class Traveler extends User {

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
            super.build();
        }
    }
}
