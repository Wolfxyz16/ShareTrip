package eus.ehu.sharetrip.domain;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private City fromLocation;

    @ManyToOne
    private City toLocation;

    private Date rideDate;

    private int numSeats;


    public Alert() {
    }

    public Alert(Date rideDate, int numSeats) {
        this.rideDate = rideDate;
        this.numSeats = numSeats;

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


}
