package eus.ehu.sharetrip.domain;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "ALERTS")
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private City fromLocation;

    @ManyToOne
    private City toLocation;

    @Column(name = "rideDate")
    private Date rideDate;

    @Column(name = "numSeats")
    private int numSeats;

    @ManyToOne
    private User user;

    public Alert() {
    }

    public static class Builder {
        private City fromLocation;
        private City toLocation;
        private Date rideDate;
        private int numSeats;

        private User user;

        public Builder() {
        }

        public Builder fromLocation(City fromLocation) {
            this.fromLocation = fromLocation;
            return this;
        }

        public Builder toLocation(City toLocation) {
            this.toLocation = toLocation;
            return this;
        }

        public Builder rideDate(Date rideDate) {
            this.rideDate = rideDate;
            return this;
        }

        public Builder numSeats(int numSeats) {
            this.numSeats = numSeats;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Alert build() {
            if(fromLocation == null || toLocation == null || rideDate == null || numSeats == 0 || user == null) {
                throw new IllegalStateException("Cannot create Alert without fromLocation, toLocation, rideDate and numSeats");
            }
            Alert alert = new Alert();
            alert.fromLocation = this.fromLocation;
            alert.toLocation = this.toLocation;
            alert.rideDate = this.rideDate;
            alert.numSeats = this.numSeats;
            alert.user = this.user;
            return alert;
        }
    }
    public Date getRideDate() {
        return rideDate;
    }

    public void setRideDate(Date rideDate) {
        this.rideDate = rideDate;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public void setNumSeats(int numSeats) {
        this.numSeats = numSeats;
    }

    public City getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(City fromLocation) {
        this.fromLocation = fromLocation;
    }

    public City getToLocation() {
        return toLocation;
    }

    public void setToLocation(City toLocation) {
        this.toLocation = toLocation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

}
