package eus.ehu.sharetrip.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;


@SuppressWarnings("serial")
@Entity
public class Ride implements Serializable {
	@Id 
	@GeneratedValue
	private Integer rideNumber;

	@ManyToOne
	private City fromLocation;
	@ManyToOne
	private City toLocation;
	private int numPlaces;
	private Date date;
	private float price;

	@ManyToOne
	@JoinColumn(name = "driver_id")
	private Driver driver;
	
	public Ride(){
		super();
	}
	
	public Ride(Integer rideNumber, City from, City to, Date date, int numPlaces, float price, Driver driver) {
		super();
		this.rideNumber = rideNumber;
		this.fromLocation = from;
		this.toLocation = to;
		this.numPlaces = numPlaces;
		this.date=date;
		this.price=price;
		this.driver = driver;
	}

	

	public Ride(City from, City to,  Date date, int numPlaces, float price, Driver driver) {
		super();
		this.fromLocation = from;
		this.toLocation = to;
		this.numPlaces = numPlaces;
		this.date=date;
		this.price=price;
		this.driver = driver;
	}
	
	/**
	 * Get the  number of the ride
	 * 
	 * @return the ride number
	 */
	public Integer getRideNumber() {
		return rideNumber;
	}

	
	/**
	 * Set the ride number to a ride
	 * 
	 * @param rideNumber Number to be set	 */
	
	public void setRideNumber(Integer rideNumber) {
		this.rideNumber = rideNumber;
	}


	/**
	 * Get the origin  of the ride
	 * 
	 * @return the origin location
	 */

	public City getFromLocation() {
		return fromLocation;
	}


	/**
	 * Set the origin of the ride
	 * 
	 * @param origin to be set
	 */	
	
	public void setFromLocation(City origin) {
		this.fromLocation = origin;
	}

	/**
	 * Get the destination  of the ride
	 * 
	 * @return the destination location
	 */

	public City getToLocation() {
		return toLocation;
	}


	/**
	 * Set the origin of the ride
	 * 
	 * @param destination to be set
	 */	
	public void setToLocation(City destination) {
		this.toLocation = destination;
	}

	/**
	 * Get the free places of the ride
	 * 
	 * @return the available places
	 */
	
	/**
	 * Get the date  of the ride
	 * 
	 * @return the ride date 
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * Set the date of the ride
	 * 
	 * @param date to be set
	 */	
	public void setDate(Date date) {
		this.date = date;
	}

	
	public float getNumPlaces() {
		return numPlaces;
	}

	public void setNumPlaces( int numPlaces) {
		 this.numPlaces = numPlaces;
	}

	/**
	 * Set the free places of the ride
	 * 
	 * @param  numPlaces places to be set
	 */

	public void setBetMinimum(int numPlaces) {
		this.numPlaces = numPlaces;
	}

	/**
	 * Get the driver associated to the ride
	 * 
	 * @return the associated driver
	 */
	public Driver getDriver() {
		return driver;
	}

	/**
	 * Set the driver associated to the ride
	 * 
	 * @param driver to associate to the ride
	 */
	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}



	public String toString(){
		return rideNumber+";"+";"+ fromLocation +";"+ toLocation +";"+date;
	}




	
}
