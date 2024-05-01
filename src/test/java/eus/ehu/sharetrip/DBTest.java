package eus.ehu.sharetrip;

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
        // Clean up resources
        db.close();
    }

    @Test
    public void testSignUp() throws UserAlreadyExistException {
        //create with email, username, password

            User user = db.signup("user1@gmail.com", "User1", "1234", "Traveler");
            assertTrue(user != null, "user correctly created");

    }

    @Test
    public void testInitializeDB() {
        // Ensure that the database is initialized correctly
        db.initializeDB();

        // Check if there are some cities
        List<String> cities = db.getCities();
        assertTrue(cities.size() > 0 , "Cities created properly");

        // Check if there are some rides
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

        // Test getting cities
        List<String> cities = db.getCities();

        // Check if the list is not null and not empty
        assertNotNull(cities);
        assertFalse(cities.isEmpty());

        // Check if the cities contain expected values
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

        // Test getting events
        Calendar today = Calendar.getInstance();
        int month = today.get(Calendar.MONTH) + 2;
        int year = today.get(Calendar.YEAR);
        if (month == 12) {
            month = 1;
            year += 1;
        }
        Date date = Dates.toDate(year, month);

        Vector<Date> events = db.getEventsMonth(date);

        // Check if the list is not null and not empty
        assertNotNull(events);
        assertTrue(!events.isEmpty());
    }


    @Test
    public void testCreateCity() throws CityAlreadyExistException {
        // Ensure that the database is initialized correctly
        db.initializeDB();

        // Test creating a new city
        String newCity = "NewCity";
        City city = db.createCity(newCity);

        // Check if the city is created and exists in the database
        assertNotNull(city);
        assertEquals(newCity.toLowerCase(), city.getName().toLowerCase());

        // Check if the created city exists in the list of cities
        List<String> cities = db.getCities();
        assertTrue(cities.contains(newCity.toLowerCase()));

        // Try creating the same city again
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

        // Test getting a city
        String newCity = "NewCity";
        City city = db.createCity(newCity);

        // Check if the city is created and exists in the database
        assertNotNull(city);
        assertEquals(newCity.toLowerCase(), city.getName().toLowerCase());

        // Check if the created city exists in the list of cities
        List<String> cities = db.getCities();
        assertTrue(cities.contains(newCity.toLowerCase()));

        // Try getting the city
        City city2 = null;
        try {
            city2 = db.getCity(city);
        } catch (CityDoesNotExistException e) {
            fail("CityDoesNotExistException should not have been thrown");
        }

        // Check if the city is created and exists in the database
        assertNotNull(city2);
        assertEquals(newCity.toLowerCase(), city2.getName().toLowerCase());
    }

    /**
    @Test
    public void testGetRides() {
        // Ensure that the database is initialized correctly
        db.initializeDB();

        // Test getting rides
        City origin = new City("donostia");
        City destination = new City("bilbo");
        int year = 2024;
        int month = 5;
        Date date = Dates.toDate(year, month);
        int numSeats = 1;

        List<Ride> rides = db.getRides(origin, destination, date, numSeats);

        // Check if the list is not null and not empty
        assertNotNull(rides);
        assertTrue(!rides.isEmpty());
    }
    */

    @Test
    public void testLoginValidCredentials()  {
        // Ensure that the database is initialized correctly
        db.initializeDB();

        // Test login with valid credentials
        String username = "test";
        String password = "test";
        User user = null;
        try {
            user = db.login(username, password);
        } catch (UnknownUser e) {
            fail("UnknownUser should not have been thrown");
        }

        // Check if the user is not null
        assertNotNull(user);
        assertEquals(username, user.getUsername());
    }

    @Test
    public void testLoginInvalidCredentials()  {
        // Ensure that the database is initialized correctly
        db.initializeDB();

        // Test login with invalid credentials
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

        // Test getting rides
        City origin = new City("Donostia");
        City destination = new City("Bilbo");
        int year = 2024;
        int month = 6;  // month 6 equals May, month 0 equals January
        Date date = UtilDate.newDate(year, month,15); //Dates.toDate(year, month);
        int numSeats = 1;

        List<Ride> rides = db.getRides(origin, destination, date, numSeats);

        // Check if the list is not null and not empty
        assertNotNull(rides);
        assertTrue(!rides.isEmpty());
    }

    @Test
    public void testSignupDriver() throws UserAlreadyExistException {
        // Ensure that the database is initialized correctly
        db.initializeDB();

        // Test signup for a driver
        String email = "testdriver@gmail.com";
        String userName = "testdriver";
        String password = "password";
        String role = "Driver";

        User user = db.signup(email, userName, password, role);

        // Check if the user is not null and is an instance of Driver
        assertNotNull(user);
        assertTrue(user instanceof Driver);


    }

    @Test
    public void testSignupTraveler() {
        // Ensure that the database is initialized correctly
        db.initializeDB();

        // Test signup for a traveler
        String email = "testtraveler@gmail.com";
        String userName = "testtraveler";
        String password = "password";
        String role = "Traveler";

        User user = null;
        try {
            user = db.signup(email, userName, password, role);
        } catch (UserAlreadyExistException e) {
            fail("UserAlreadyExistException should not have been thrown");
        }

        // Check if the user is not null and is an instance of Traveler
        assertNotNull(user);
        assertTrue(user instanceof Traveler);
    }

    @Test
    public void testSignupExistingUser()  {
        // Ensure that the database is initialized correctly
        db.initializeDB();

        // Test signup with an existing user
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

}
