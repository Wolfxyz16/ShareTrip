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


    public Alert() {
    }

    public Alert(City fromLocation, City toLocation, Date rideDate, int numSeats) {
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
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
}
