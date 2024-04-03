package eus.ehu.sharetrip.businessLogic;

import eus.ehu.sharetrip.configuration.Config;
import eus.ehu.sharetrip.dataAccess.DataAccess;
import eus.ehu.sharetrip.domain.Driver;
import eus.ehu.sharetrip.domain.Message;
import eus.ehu.sharetrip.domain.Ride;
import eus.ehu.sharetrip.domain.User;
import eus.ehu.sharetrip.exceptions.RideAlreadyExistException;
import eus.ehu.sharetrip.exceptions.RideMustBeLaterThanTodayException;
import eus.ehu.sharetrip.exceptions.UnknownUser;
import eus.ehu.sharetrip.exceptions.UserAlreadyExistException;

import java.util.Date;
import java.util.List;
import java.util.Vector;


/**
 * Implements the business logic as a web service.
 */
public class BlFacadeImplementation implements BlFacade {

    DataAccess dbManager;
    Config config = Config.getInstance();
    private User currentUser;

    public BlFacadeImplementation() {
        System.out.println("Creating BlFacadeImplementation instance");
        boolean initialize = config.getDataBaseOpenMode().equals("initialize");
        dbManager = new DataAccess(initialize);
        if (initialize)
            dbManager.initializeDB();
        //dbManager.close();
    }

    public BlFacadeImplementation(DataAccess dam) {
        System.out.println("Creating BlFacadeImplementation instance with DataAccess parameter");
        if (config.getDataBaseOpenMode().equals("initialize")) {
            //dam.open(true);
            dam.initializeDB();
            //dam.close();
        }
        dbManager = dam;
    }

    public Ride createRide(String from, String to, Date date, int nPlaces, float price, String driverEmail) throws RideMustBeLaterThanTodayException, RideAlreadyExistException {
        Ride ride = dbManager.createRide(from, to, date, nPlaces, price, driverEmail);
        return ride;
    }

    @Override
    public List<Ride> getRides(String origin, String destination, Date date) {
        List<Ride> events = dbManager.getRides(origin, destination, date);
        return events;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Date> getThisMonthDatesWithRides(String from, String to, Date date) {
        List<Date> dates = dbManager.getThisMonthDatesWithRides(from, to, date);
        return dates;
    }


    /**
     * This method invokes the data access to retrieve the dates a month for which there are events
     *
     * @param date of the month for which days with events want to be retrieved
     * @return collection of dates
     */

    public Vector<Date> getEventsMonth(Date date) {
        Vector<Date> dates = dbManager.getEventsMonth(date);
        return dates;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    @Override
    public User getCurrentUser() {
        return this.currentUser;
    }

    public void close() {
        dbManager.close();
    }

    /**
     * This method invokes the data access to initialize the database with some events and questions.
     * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
     */

    public List<String> getDepartCities() {
        List<String> departLocations = dbManager.getDepartCities();
        return departLocations;

    }

    /**
     * {@inheritDoc}
     */
    public List<String> getDestinationCities(String from) {
        List<String> targetCities = dbManager.getArrivalCities(from);
        return targetCities;
    }

    @Override
    public List<Date> getDatesWithRides(String value, String value1) {
        List<Date> dates = dbManager.getDatesWithRides(value, value1);
        return dates;
    }

    public User login(String username, String password) throws UnknownUser {
        return dbManager.login(username, password);
    }

    public User getUser(String username) {
        return dbManager.getUser(username);
    }

    public void signup(String email, String username, String password, String role) throws UserAlreadyExistException {
        dbManager.signup(email, username, password, role);
    }

    public List<Message> getMessages() {
        return dbManager.getMessages();
    }
}
