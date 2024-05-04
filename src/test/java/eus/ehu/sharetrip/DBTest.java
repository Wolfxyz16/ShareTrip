package eus.ehu.sharetrip;

import eus.ehu.sharetrip.businessLogic.BlFacade;
import eus.ehu.sharetrip.businessLogic.BlFacadeImplementation;
import eus.ehu.sharetrip.configuration.UtilDate;
import eus.ehu.sharetrip.dataAccess.DataAccess;
import eus.ehu.sharetrip.domain.*;
import eus.ehu.sharetrip.exceptions.CityAlreadyExistException;
import eus.ehu.sharetrip.exceptions.CityDoesNotExistException;
import eus.ehu.sharetrip.exceptions.UnknownUser;
import eus.ehu.sharetrip.exceptions.UserAlreadyExistException;
import eus.ehu.sharetrip.utils.Dates;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class DBTest {

    private DataAccess db;

    @BeforeEach
    void setUp() {
        db = new DataAccess(true);

    }

    @AfterEach
    void tearDown() {
        db.close();
    }

    @Test
    public void testSignUp()  {
        // Ensure that the database is initialized correctly
        db.initializeDB();

        String email = "Jtest@gmail.com";
        String username = "JTest";
        String password = "1234#Test";
        String role = "Traveler";

        User user = null;
        try {
            user = db.signup(email, username, password, role);
        } catch (UserAlreadyExistException e) {
            fail("UserAlreadyExistException should not have been thrown");
        }

        assertNotNull(user, "User should not be null");
        assertEquals(username, user.getUsername(), "Username should match");

    }

    @Test
    public void testInitializeDB() {
        // Ensure that the database is initialized correctly
        db.initializeDB();

        List<String> cities = db.getCities();
        assertTrue(cities.size() > 0 , "Cities created properly");

        Calendar today = Calendar.getInstance();
        int month = today.get(Calendar.MONTH) + 2;
        int year = today.get(Calendar.YEAR);
        if (month == 12) {
            month = 1;
            year += 1;
        }
        Date date = Dates.toDate(year, month);

        List<Date> rides = db.getEventsMonth(date);
        assertTrue(rides.size() > 0, "Rides created properly");
    }

    @Test
    public void testGetCities() {
        // Ensure that the database is initialized correctly
        db.initializeDB();

        List<String> cities = db.getCities();

        assertNotNull(cities);
        assertFalse(cities.isEmpty());

        assertTrue(cities.contains("donostia"));
        assertTrue(cities.contains("bilbo"));
        assertTrue(cities.contains("gasteiz"));
        assertTrue(cities.contains("iruña"));
        assertTrue(cities.contains("eibar"));
    }

    @Test
    public void testGetEventsMonth() {
        // Ensure that the database is initialized correctly
        db.initializeDB();

        Calendar today = Calendar.getInstance();
        int month = today.get(Calendar.MONTH) + 2;
        int year = today.get(Calendar.YEAR);
        if (month == 12) {
            month = 1;
            year += 1;
        }
        Date date = Dates.toDate(year, month);

        Vector<Date> events = db.getEventsMonth(date);

        assertNotNull(events);
        assertTrue(!events.isEmpty());
    }


    @Test
    public void testCreateCity() throws CityAlreadyExistException {
        // Ensure that the database is initialized correctly
        db.initializeDB();

        String newCity = "NewCity";
        City city = db.createCity(newCity);

        assertNotNull(city);
        assertEquals(newCity.toLowerCase(), city.getName().toLowerCase());

        List<String> cities = db.getCities();
        assertTrue(cities.contains(newCity.toLowerCase()));

        try {
            db.createCity(newCity);
            fail("CityAlreadyExistException should have been thrown");
        } catch (CityAlreadyExistException e) {
            // Success
        }
    }

    @Test
    public void testGetCity() throws CityAlreadyExistException {
        // Ensure that the database is initialized correctly
        db.initializeDB();

        String newCity = "NewCity";
        City city = db.createCity(newCity);

        assertNotNull(city);
        assertEquals(newCity.toLowerCase(), city.getName().toLowerCase());

        List<String> cities = db.getCities();
        assertTrue(cities.contains(newCity.toLowerCase()));

        // Try getting the city
        City city2 = null;
        try {
            city2 = db.getCity(city);
        } catch (CityDoesNotExistException e) {
            fail("CityDoesNotExistException should not have been thrown");
        }

        assertNotNull(city2);
        assertEquals(newCity.toLowerCase(), city2.getName().toLowerCase());
    }



    @Test
    public void testLoginValidCredentials() throws UserAlreadyExistException, UnknownUser {
        // Ensure that the database is initialized correctly
        db.initializeDB();


        String username = "test";
        String password = "test";

        String hashed = db.getHashedPassword(username);

        User user = null;
        try {
            user = db.login(username, hashed);
        } catch (UnknownUser e) {
            fail("UnknownUser should not have been thrown");
        }

        assertNotNull(user);
        assertEquals(username, user.getUsername());
    }

    @Test
    public void testLoginInvalidCredentials()  {
        // Ensure that the database is initialized correctly
        db.initializeDB();

        String username = "invaliduser@gmail.com";
        String password = "wrongpassword";
        try {
            db.login(username, password);
            fail("UnknownUser should have been thrown");
        } catch (UnknownUser e) {
            // Success

        }
    }

    @Test
    public void testGetRides() {
        // Ensure that the database is initialized correctly
        db.initializeDB();

        City origin = new City("Donostia");
        City destination = new City("Bilbo");
        int year = 2024;
        int month = 6;  // month 6 equals May, month 0 equals January
        Date date = UtilDate.newDate(year, month,15); //Dates.toDate(year, month);
        int numSeats = 1;

        List<Ride> rides = db.getRides(origin, destination, date, numSeats);
        assertNotNull(rides);
        assertTrue(!rides.isEmpty());
    }

    @Test
    public void testCreateRides() {
        // Ensure that the database is initialized correctly
        db.initializeDB();

        City origin = new City("Donostia");
        City destination = new City("Bilbo");
        int year = 2024;
        int month = 6;  // month 6 equals May, month 0 equals January
        Date date = UtilDate.newDate(year, month,15); //Dates.toDate(year, month);
        int numSeats = 1;
        float price = 10;
        long driverID = 1;

        Ride ride = null;
        try {
            ride = db.createRide(origin, destination, date, numSeats, price, driverID);
        } catch (Exception e) {
            fail("Exception should not have been thrown");
        }

        assertNotNull(ride);
        List<Ride> rides = db.getRides(origin, destination, date, numSeats);
        assertNotNull(rides);
        assertTrue(rides.contains(ride));
    }

    @Test
    public void testSignupDriver()  {
        // Ensure that the database is initialized correctly
        db.initializeDB();

        String email = "testDriver@gmail.com";
        String userName = "testDriver";
        String password = "passworD#1234";
        String role = "Driver";

        User user = null;
        try {

            user = db.signup(email, userName, password, role);
        } catch (UserAlreadyExistException e) {
            fail("UserAlreadyExistException should not have been thrown");
        }
        assertNotNull(user);
        assertInstanceOf(Driver.class, user);


    }

    @Test
    public void testSignupTraveler() {
        // Ensure that the database is initialized correctly
        db.initializeDB();

        String email = "testTraveler@gmail.com";
        String userName = "testTraveler";
        String password = "passworD#1234";
        String role = "Traveler";
        User user = null;
        try {
            user = db.signup(email, userName, password, role);
        } catch (UserAlreadyExistException e) {
            fail("UserAlreadyExistException should not have been thrown");
        }
        assertNotNull(user);
        assertInstanceOf(Traveler.class, user);
    }

    @Test
    public void testSignupExistingUser()  {
        // Ensure that the database is initialized correctly
        db.initializeDB();

        String email = "driver1@gmail.com";
        String userName = "driver1";
        String password = "password";
        String role = "Driver";
        try {
            db.signup(email, userName, password, role);
            fail("UserAlreadyExistException should have been thrown");
        } catch (UserAlreadyExistException e) {
            // Success
        }
    }

    @Test
    public void testGetDepartCities() {
        // Ensure that the database is initialized correctly
        db.initializeDB();

        List<City> cities = db.getDepartCities();
        assertNotNull(cities);
        assertTrue(!cities.isEmpty());
    }

    @Test
    public void testGetSentMessages() {
        // Ensure that the database is initialized correctly
        db.initializeDB();

        String username = "User1";
        User user = db.getUser(username);
        assertNotNull(user);

        List<Message> messages = db.getSentMessages(user);
        assertNotNull(messages);
        assertTrue(!messages.isEmpty());

    }

    @Test
    public void testGetAlerts() {
        // Ensure that the database is initialized correctly
        db.initializeDB();

        //we can name directly the method getAlerts() because we know there are some alerts in db
        //else we should create an alert first but is not tested yet
        List<Alert> alerts = db.getUserAlerts(db.getUser("User1"));
        assertNotNull(alerts);
        assertTrue(!alerts.isEmpty());
    }

    @Test
    public void testCreateAlert() {
        // Ensure that the database is initialized correctly
        db.initializeDB();

        City from = new City("Donostia");
        City to = new City("Bilbo");
        int year = 2024;
        int month = 6;  // month 6 equals May, month 0 equals January
        Date date = UtilDate.newDate(year, month,15); //Dates.toDate(year, month);
        int numSeats = 1;

        Alert alert = null;
        try {
            alert = db.createAlert(from, to, date, numSeats, db.getUser("User1"));
        } catch (Exception e) {
            fail("Exception should not have been thrown");
        }

        assertNotNull(alert);
        List<Alert> alerts = db.getUserAlerts(db.getUser("User1"));
        assertNotNull(alerts);
        assertTrue(alerts.contains(alert));

    }

    @Test
    public void testGetHashePassword() {
        // Ensure that the database is initialized correctly
        db.initializeDB();

        String username = "User1";
        String hashed = null;
        try {
            hashed = db.getHashedPassword(username);
        } catch (UnknownUser e) {
            fail("UnknownUser should not have been thrown");
        }
        assertNotNull(hashed);
    }


}
