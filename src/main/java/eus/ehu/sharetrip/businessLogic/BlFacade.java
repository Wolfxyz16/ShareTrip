package eus.ehu.sharetrip.businessLogic;

import eus.ehu.sharetrip.domain.City;
import eus.ehu.sharetrip.domain.Message;
import eus.ehu.sharetrip.domain.Ride;
import eus.ehu.sharetrip.domain.User;
import eus.ehu.sharetrip.exceptions.*;

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
    List<Ride> getRides(String from, String to, Date date);

    /**
     * This method retrieves from the database the dates a month for which there are events
     *
     * @param from the origin location of a ride
     * @param to   the destination location of a ride
     * @param date of the month for which days with rides want to be retrieved
     * @return collection of rides
     */
    public List<Date> getThisMonthDatesWithRides(String from, String to, Date date);


    /**
     * This method retrieves from the database the dates in a month for which there are events
     *
     * @param date of the month for which days with events want to be retrieved
     * @return collection of dates
     */
    public Vector<Date> getEventsMonth(Date date);


    void setCurrentUser(User user);

    User getCurrentUser();

    Ride createRide(String text, String text1, Date date, int inputSeats, float price, String email) throws RideMustBeLaterThanTodayException, RideAlreadyExistException;

    City createCity(String name) throws CityAlreadyExistException;

    /**
     * This method returns all the cities where rides depart
     *
     * @return collection of cities
     */

    public List<String> getDepartCities();

    /**
     * This method returns all the arrival destinations, from all rides that depart from a given city
     *
     * @param from the departure location of a ride
     * @return all the arrival destinations
     */

    public List<String> getDestinationCities(String from);


    List<Date> getDatesWithRides(String value, String value1);

    public User getUser(String username);
    public User login(String username, String password) throws UnknownUser;
    public void signup(String email, String username, String password, String role) throws UnknownUser, UserAlreadyExistException;

    void saveMessage(Message message);

    public  List<Message> getSentMessages(User currentUser);

    public  List<Message> getReceivedMessages(User currentUser);

}
