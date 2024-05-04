package eus.ehu.sharetrip.businessLogic;

import eus.ehu.sharetrip.domain.*;
import eus.ehu.sharetrip.exceptions.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * Interface that specifies the business logic.
 */

public interface BlFacade {
    /**
     * This method retrieves the rides from two locations on a given date
     *
     * @param from the origin location of a ride
     * @param to   the destination location of a ride
     * @param date the date of the ride
     * @return collection of rides
     */
    List<Ride> getRides(City from, City to, Date date, int numSeats);

    /**
     * This method retrieves from the database the dates a month for which there are events
     *
     * @param from the origin location of a ride
     * @param to   the destination location of a ride
     * @param date of the month for which days with rides want to be retrieved
     * @return collection of rides
     */
    public List<Date> getThisMonthDatesWithRides(City from, City to, Date date);


    /**
     * This method retrieves from the database the dates in a month for which there are events
     *
     * @param date of the month for which days with events want to be retrieved
     * @return collection of dates
     */
    public Vector<Date> getEventsMonth(Date date);


    void setCurrentUser(User user);

    User getCurrentUser();

    Ride createRide(City dep, City arr, Date date, int inputSeats, float price, long driverID) throws RideMustBeLaterThanTodayException, RideAlreadyExistException;

    Alert createAlert(City from, City to, Date date, int nPlaces) throws AlertAlreadyExistException;

    City createCity(String name) throws CityAlreadyExistException;

    List<String> getCities();

    City getCity(City name) throws CityDoesNotExistException;

    /**
     * This method returns all the cities where rides depart
     *
     * @return collection of cities
     */

    public List<City> getDepartCities();

    /**
     * This method returns all the arrival destinations, from all rides that depart from a given city
     *
     * @param from the departure location of a ride
     * @return all the arrival destinations
     */

    public List<City> getDestinationCities(City from);

    /**
     * This method returns all cities, departure and arrival cities
     *
     * @return all the cities
     */

    public List<City> getAllCities();


    List<Date> getDatesWithRides(City value, City value1);

    public User getUser(String username);
    public User login(String username, String password) throws UnknownUser;
    public void signup(String email, String username, String password, String role) throws UnknownUser, UserAlreadyExistException;

    void saveMessage(Message message);

    public  List<Message> getSentMessages(User currentUser);

    public  List<Message> getReceivedMessages(User currentUser);


    String getCurrentUserType();

    List<Alert> getAlerts();


    public List<Ride> getFavoriteRides(User currentUser);

    public void addFavoriteRide(User currentUser, Ride ride);

    boolean alertAlreadyExist(City city, City city1, Date date, int i);

    boolean favoriteAlreadyExist(User user, Ride ride);

    void deleteFavoriteRide(User currentUser, Ride selectedRide);

    void deleteAlert(Alert alert);

    public String getHashedPassword(String username) throws UnknownUser;

    void bookRide(Traveler user, Ride selectedRide, Integer numSeats);

    ArrayList<Ride> getRidesByDriver(Driver currentUser);

    ArrayList<Reservation> getMyBookings(User currentUser);

}
