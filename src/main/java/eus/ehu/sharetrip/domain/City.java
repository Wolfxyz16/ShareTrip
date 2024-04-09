package eus.ehu.sharetrip.domain;

import jakarta.persistence.*;
import jakarta.persistence.ManyToOne;

import java.util.Date;
import java.util.List;
import java.util.Vector;

@Entity
public class City {
    @Id
    private String name;

    //@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
    @OneToMany(mappedBy = "fromLocation")
    private List<Ride> ridesDeparture;

    //@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
    @OneToMany(mappedBy = "toLocation")
    private List<Ride> ridesArrival ;


    public City() {
    }

    public City(String name) {
        this.name = name;
        ridesDeparture = new Vector<Ride>();
        ridesArrival = new Vector<Ride>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Ride addRideDep(Ride rideToAdd) {
        this.ridesDeparture.add(rideToAdd);
        return rideToAdd;
    }
   /*
    public Ride addRideDep(City from, City to, Date date, int nPlaces, float price, Driver driver) {
        Ride ride=new Ride(from,to,date,nPlaces,price, driver);
        ridesDeparture.add(ride);
        return ride;
    } */
    public Ride addRideArr(Ride rideToAdd) {
        this.ridesArrival.add(rideToAdd);
        return rideToAdd;
    }

    @Override
    public String toString() {
        return "City name: " + name + "\n";
    }

}
