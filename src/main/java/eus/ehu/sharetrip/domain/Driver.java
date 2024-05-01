package eus.ehu.sharetrip.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Vector;

@Entity
@DiscriminatorValue("DRIVER")
public class Driver extends User implements Serializable {

	@OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true)
	//@OneToMany(mappedBy = "driver")
	private List<Ride> rides=new Vector<Ride>();


	public Driver() {
		super();
	}


	public List<Ride> getRides() {
		return rides;
	}

	public void setRides(List<Ride> rides) {
		this.rides = rides;
	}


	public static class Builder extends User.Builder {
		private List<Ride> rides = new Vector<Ride>();

		public Builder() {
			super();
		}

		@Override
		public Driver.Builder email(String email) {
			super.email(email);
			return this;
		}

		@Override
		public Driver.Builder username(String username) {
			super.username(username);
			return this;
		}

		@Override
		public Driver.Builder password(String password) {
			super.password(password);
			return this;
		}

		public Driver.Builder rides(List<Ride> rides) {
			this.rides = rides;
			return this;
		}

		@Override
		public Driver build() {
			User user = super.build();
			Driver driver = new Driver();
			driver.setEmail(user.getEmail());
			driver.setUsername(user.getUsername());
			driver.setPassword(user.getPassword());
			driver.setRides(this.rides);
			return driver;
		}
	}






	/**
	 * This method creates a new ride for the driver
	 * 
	 * @param
	 * @param
	 * @return
	 */
	public Ride addRide(Ride ride) {
        rides.add(ride);
        return ride;
	}

	/**
	 * This method checks if the ride already exists for that driver
	 * 
	 * @param from the origin location 
	 * @param to the destination location 
	 * @param date the date of the ride 
	 * @return true if the ride exists and false in other case
	 */
	public boolean doesRideExists(City from, City to, Date date)  {
		for (Ride r:rides)
			if ( (java.util.Objects.equals(r.getFromLocation(),from)) && (java.util.Objects.equals(r.getToLocation(),to)) && (java.util.Objects.equals(r.getDate(),date)) )
			 return true;
		
		return false;
	}
		
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Driver other = (Driver) obj;
		if (getEmail() != other.getEmail())
			return false;
		return true;
	}

	public Ride removeRide(String from, String to, Date date) {
		boolean found=false;
		int index=0;
		Ride r=null;
		while (!found && index<=rides.size()) {
			r=rides.get(++index);
			if ( (java.util.Objects.equals(r.getFromLocation(),from)) && (java.util.Objects.equals(r.getToLocation(),to)) && (java.util.Objects.equals(r.getDate(),date)) )
			found=true;
		}
			
		if (found) {
			rides.remove(index);
			return r;
		} else return null;
	}
	
}
