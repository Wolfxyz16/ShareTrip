package eus.ehu.sharetrip.dataAccess;

import eus.ehu.sharetrip.configuration.Config;
import eus.ehu.sharetrip.configuration.UtilDate;
import eus.ehu.sharetrip.domain.*;
import eus.ehu.sharetrip.exceptions.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.mindrot.jbcrypt.BCrypt;

import java.util.*;


/**
 * Implements the Data Access utility to the objectDb database
 */
public class DataAccess {

  protected EntityManager db;
  protected EntityManagerFactory emf;

  public DataAccess() {

    this.open();

  }

  public DataAccess(boolean initializeMode) {

    this.open(initializeMode);

  }

  public void open() {
    open(false);
  }


  public void open(boolean initializeMode) {

    Config config = Config.getInstance();

    System.out.println("Opening DataAccess instance => isDatabaseLocal: " +
            config.isDataAccessLocal() + " getDatabBaseOpenMode: " + config.getDataBaseOpenMode());

    String fileName = config.getDatabaseName();
    if (initializeMode) {
      fileName = fileName + ";drop";
      System.out.println("Deleting the DataBase");
    }

    if (config.isDataAccessLocal()) {
      final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
              .configure() // configures settings from hibernate.cfg.xml
              .build();
      try {
        emf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
      } catch (Exception e) {
        // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
        // so destroy it manually.
        StandardServiceRegistryBuilder.destroy(registry);
      }

      db = emf.createEntityManager();
      System.out.println("DataBase opened");
    }
  }

  public void reset() {
    db.getTransaction().begin();
    db.createNativeQuery("DELETE FROM USERS_RIDE").executeUpdate();
    db.createQuery("DELETE FROM Ride ").executeUpdate();
    db.createQuery("DELETE FROM Driver ").executeUpdate();
    db.getTransaction().commit();
  }

  public void initializeDB() {

    // When we try to connect to the db using this method the connection is refused because it does not found the tables
    // this.reset();

    try {
      Calendar today = Calendar.getInstance();

      int month = today.get(Calendar.MONTH) + 2;
      int year = today.get(Calendar.YEAR);
      int day = today.get(Calendar.DAY_OF_MONTH);
      if (month == 11) {
        month = 1;
        year += 1;
      }

      //Create drivers
      Driver driver1 = new Driver.Builder()
                .email("driver1@gmail.com")
                .username("Aitor Fernandez")
                .password(BCrypt.hashpw("1234", BCrypt.gensalt()))
                .build();
      Driver driver2 = new Driver.Builder()
                .email("driver2@gmail.com")
                .username("Ane Gaztañaga")
                .password(BCrypt.hashpw("1234", BCrypt.gensalt()))
                .build();
      Driver driver3 = new Driver.Builder()
                  .email("driver3@gmail.com")
                  .username("test")
                  .password(BCrypt.hashpw("test", BCrypt.gensalt()))
                  .build();
      Driver driver4 = new Driver.Builder()
              .email("driver4@gmail.com")
              .username("María Rodriguez")
              .password(BCrypt.hashpw("1234", BCrypt.gensalt()))
              .build();

      Driver driver5 = new Driver.Builder()
              .email("driver5@gmail.com")
              .username("Carlos García")
              .password(BCrypt.hashpw("1234", BCrypt.gensalt()))
              .build();

      Driver driver6 = new Driver.Builder()
              .email("driver6@gmail.com")
              .username("Laura Martínez")
              .password(BCrypt.hashpw("1234", BCrypt.gensalt()))
              .build();

      Driver driver7 = new Driver.Builder()
              .email("driver7@gmail.com")
              .username("Pedro Sánchez")
              .password(BCrypt.hashpw("1234", BCrypt.gensalt()))
              .build();

      Driver driver8 = new Driver.Builder()
              .email("driver8@gmail.com")
              .username("Sofía López")
              .password(BCrypt.hashpw("1234", BCrypt.gensalt()))
              .build();

      Driver driver9 = new Driver.Builder()
              .email("driver9@gmail.com")
              .username("Ana Fernandez")
              .password(BCrypt.hashpw("1234", BCrypt.gensalt()))
              .build();

      Driver driver10 = new Driver.Builder()
              .email("driver10@gmail.com")
              .username("David Gutiérrez")
              .password(BCrypt.hashpw("1234", BCrypt.gensalt()))
              .build();

      // Persistir los drivers en la base de datos
      db.getTransaction().begin();
      db.persist(driver1);
      db.persist(driver2);
      db.persist(driver3);
      db.persist(driver4);
      db.persist(driver5);
      db.persist(driver6);
      db.persist(driver7);
      db.persist(driver8);
      db.persist(driver9);
      db.persist(driver10);
      db.getTransaction().commit();

      //Create Cities
      City city1 = new City("Donostia");
      City city2 = new City("Bilbo");
      City city3 = new City("Gasteiz");
      City city4 = new City("Iruña");
      City city5 = new City("Eibar");
      City city6 = new City("Zarautz");
      City city7 = new City("Hernani");
      City city8 = new City("Tolosa");
      City city9 = new City("Zumaia");
      City city10 = new City("Orio");
      City city11 = new City("Hondarribia");
      City city12 = new City("Irun");
      City city13 = new City("Hendaia");
      City city14 = new City("Getxo");
      City city15 = new City("Barakaldo");
      City city16 = new City("Santurtzi");

      Ride ride1 = new Ride.Builder()
              .fromLocation(city1)
              .toLocation(city2)
              .date(UtilDate.newDate(year, month, 15))
              .numPlaces(4)
              .price(7)
              .driver(driver1)
              .build();

      Ride ride2 = new Ride.Builder()
              .fromLocation(city1)
              .toLocation(city2)
              .date(UtilDate.newDate(year, month + 1, 15))
              .numPlaces(4)
              .price(7)
              .driver(driver1)
              .build();

      Ride ride3 = new Ride.Builder()
              .fromLocation(city1)
              .toLocation(city3)
              .date(UtilDate.newDate(year, month, 6))
              .numPlaces(4)
              .price(8)
              .driver(driver1)
              .build();

      Ride ride4 = new Ride.Builder()
              .fromLocation(city2)
              .toLocation(city1)
              .date(UtilDate.newDate(year, month, 25))
              .numPlaces(4)
              .price(4)
              .driver(driver1)
              .build();

      Ride ride5 = new Ride.Builder()
              .fromLocation(city1)
              .toLocation(city4)
              .date(UtilDate.newDate(year, month, 7))
              .numPlaces(4)
              .price(8)
              .driver(driver1)
              .build();

      Ride ride6 = new Ride.Builder()
              .fromLocation(city1)
              .toLocation(city2)
              .date(UtilDate.newDate(year, month, 15))
              .numPlaces(3)
              .price(3)
              .driver(driver2)
              .build();

      Ride ride7 = new Ride.Builder()
              .fromLocation(city2)
              .toLocation(city1)
              .date(UtilDate.newDate(year, month, 25))
              .numPlaces(2)
              .price(5)
              .driver(driver2)
              .build();

      Ride ride8 = new Ride.Builder()
              .fromLocation(city5)
              .toLocation(city3)
              .date(UtilDate.newDate(year, month, 6))
              .numPlaces(2)
              .price(5)
              .driver(driver2)
              .build();

      Ride ride9 = new Ride.Builder()
              .fromLocation(city2)
              .toLocation(city1)
              .date(UtilDate.newDate(year, month, 14))
              .numPlaces(1)
              .price(3)
              .driver(driver3)
              .build();

      Ride ride10 = new Ride.Builder()
              .fromLocation(city12)
              .toLocation(city2)
              .date(UtilDate.newDate(year, month, 15))
              .numPlaces(4)
              .price(7)
              .driver(driver1)
              .build();

        Ride ride11 = new Ride.Builder()
              .fromLocation(city13)
              .toLocation(city10)
              .date(UtilDate.newDate(year, month + 1, 15))
              .numPlaces(4)
              .price(7)
              .driver(driver2)
              .build();

        Ride ride12 = new Ride.Builder()
                .fromLocation(city14)
                .toLocation(city15)
                .date(UtilDate.newDate(year, month, 6))
                .numPlaces(4)
                .price(8)
                .driver(driver2)
                .build();

        Ride ride13 = new Ride.Builder()
                .fromLocation(city16)
                .toLocation(city1)
                .date(UtilDate.newDate(year, month, 25))
                .numPlaces(4)
                .price(4)
                .driver(driver3)
                .build();

        Ride ride14 = new Ride.Builder()
                .fromLocation(city1)
                .toLocation(city15)
                .date(UtilDate.newDate(year, month, 7))
                .numPlaces(4)
                .price(8)
                .driver(driver2)
                .build();
      Ride ride15 = new Ride.Builder()
              .fromLocation(city6)
              .toLocation(city2)
              .date(UtilDate.newDate(year, month, 10))
              .numPlaces(3)
              .price(6)
              .driver(driver10)
              .build();

      Ride ride16 = new Ride.Builder()
              .fromLocation(city7)
              .toLocation(city1)
              .date(UtilDate.newDate(year, month + 1, 5))
              .numPlaces(2)
              .price(5)
              .driver(driver9)
              .build();

      Ride ride17 = new Ride.Builder()
              .fromLocation(city8)
              .toLocation(city4)
              .date(UtilDate.newDate(year, month, 20))
              .numPlaces(3)
              .price(7)
              .driver(driver8)
              .build();

      Ride ride18 = new Ride.Builder()
              .fromLocation(city9)
              .toLocation(city3)
              .date(UtilDate.newDate(year, month, 12))
              .numPlaces(2)
              .price(6)
              .driver(driver7)
              .build();

      Ride ride19 = new Ride.Builder()
              .fromLocation(city10)
              .toLocation(city6)
              .date(UtilDate.newDate(year, month, 18))
              .numPlaces(4)
              .price(8)
              .driver(driver5)
              .build();

      Ride ride20 = new Ride.Builder()
              .fromLocation(city11)
              .toLocation(city5)
              .date(UtilDate.newDate(year, month + 1, 3))
              .numPlaces(3)
              .price(6)
              .driver(driver5)
              .build();
      Ride ride21 = new Ride.Builder()
              .fromLocation(city12)
              .toLocation(city8)
              .date(UtilDate.newDate(year, month, 8))
              .numPlaces(4)
              .price(9)
              .driver(driver6)
              .build();

      Ride ride22 = new Ride.Builder()
              .fromLocation(city13)
              .toLocation(city7)
              .date(UtilDate.newDate(year, month, 14))
              .numPlaces(3)
              .price(7)
              .driver(driver7)
              .build();

      Ride ride23 = new Ride.Builder()
              .fromLocation(city14)
              .toLocation(city2)
              .date(UtilDate.newDate(year, month, 16))
              .numPlaces(2)
              .price(5)
              .driver(driver8)
              .build();

      Ride ride24 = new Ride.Builder()
              .fromLocation(city15)
              .toLocation(city3)
              .date(UtilDate.newDate(year, month + 1, 12))
              .numPlaces(4)
              .price(8)
              .driver(driver9)
              .build();

      Ride ride25 = new Ride.Builder()
              .fromLocation(city16)
              .toLocation(city1)
              .date(UtilDate.newDate(year, month + 1, 20))
              .numPlaces(3)
              .price(7)
              .driver(driver10)
              .build();


        driver1.addRide(ride1);
        driver1.addRide(ride2);
        driver1.addRide(ride3);
        driver1.addRide(ride4);
        driver1.addRide(ride5);
        driver2.addRide(ride6);
        driver2.addRide(ride7);
        driver2.addRide(ride8);
        driver3.addRide(ride9);
        driver1.addRide(ride10);
        driver2.addRide(ride11);
        driver2.addRide(ride12);
        driver3.addRide(ride13);
        driver2.addRide(ride14);
        driver10.addRide(ride15);
        driver9.addRide(ride16);
        driver8.addRide(ride17);
        driver7.addRide(ride18);
        driver5.addRide(ride19);
        driver5.addRide(ride20);
        driver6.addRide(ride21);
        driver7.addRide(ride22);
        driver8.addRide(ride23);
        driver9.addRide(ride24);
        driver10.addRide(ride25);

      //Create travelers
      Traveler traveler1 = new Traveler.Builder()
                .email("user1@gmail.com")
                .username("User1")
                .password(BCrypt.hashpw("1234", BCrypt.gensalt()))
                .build();
      Traveler traveler2 = new Traveler.Builder()
                .email("user2@gmail.com")
                .username("User2")
                .password(BCrypt.hashpw("1234", BCrypt.gensalt()))
                .build();


      // CREATE MESSAGES
      Message message1 = new Message.Builder()
              .messageText("Hello")
              .sender(traveler1)
              .receiver(traveler2)
              .build();

      // CREATE ALERTS
      Alert alert1 = new Alert.Builder()
              .fromLocation(city1)
              .toLocation(city2)
              .rideDate(UtilDate.newDate(year, month, 15))
              .numSeats(4)
              .user(traveler1)
              .build();

      Alert alert2 = new Alert.Builder()
              .fromLocation(city3)
              .toLocation(city4)
              .rideDate(UtilDate.newDate(year, month + 1, 15))
              .numSeats(4)
              .user(traveler1)
              .build();

      Alert alert3 = new Alert.Builder()
              .fromLocation(city5)
              .toLocation(city1)
              .rideDate(UtilDate.newDate(year, month, 6))
              .numSeats(4)
              .user(traveler2)
              .build();

      Alert alert4 = new Alert.Builder()
              .fromLocation(city3)
              .toLocation(city5)
              .rideDate(UtilDate.newDate(year, month, 25))
              .numSeats(4)
              .user(traveler2)
              .build();

      db.getTransaction().begin();
      //Persist the objects
      db.persist(alert1);
      db.persist(alert2);
      db.persist(alert3);
      db.persist(alert4);

      db.persist(city1);
      db.persist(city2);
      db.persist(city3);
      db.persist(city4);
      db.persist(city5);
      db.persist(city6);
      db.persist(city7);
      db.persist(city8);
      db.persist(city9);
      db.persist(city10);
      db.persist(city11);
      db.persist(city12);
      db.persist(city13);
      db.persist(city14);
      db.persist(city15);
      db.persist(city16);


      db.persist(driver1);
      db.persist(driver2);
      db.persist(driver3);

      db.persist(traveler1);
      db.persist(traveler2);

      db.persist(message1);

      User systemUser  = new User.Builder()
              .email("sharetripSystem@gmail.com")
              .username("System Sharetrip")
              .password(BCrypt.hashpw("admin", BCrypt.gensalt()))
              .build();
      db.persist(systemUser);

      db.getTransaction().commit();
      System.out.println("Db initialized");
    } catch (Exception e) {
      e.printStackTrace();
    }

  }


  /**
   * This method retrieves from the database the dates in a month for which there are events
   *
   * @param date of the month for which days with events want to be retrieved
   * @return collection of dates
   */
  public Vector<Date> getEventsMonth(Date date) {
    System.out.println(">> DataAccess: getEventsMonth");
    Vector<Date> res = new Vector<Date>();

    Date firstDayMonthDate = UtilDate.firstDayMonth(date);
    Date lastDayMonthDate = UtilDate.lastDayMonth(date);


    TypedQuery<Date> query = db.createQuery("SELECT DISTINCT ride.date FROM Ride ride "
            + "WHERE ride.date BETWEEN ?1 and ?2", Date.class);
    query.setParameter(1, firstDayMonthDate);
    query.setParameter(2, lastDayMonthDate);
    List<Date> dates = query.getResultList();
    for (Date d : dates) {
      System.out.println(d.toString());
      res.add(d);
    }
    return res;
  }

  public List<String> getCities(){
    List<String> citiesNames = new ArrayList<>();
    TypedQuery<City> query = db.createQuery("SELECT c FROM City c", City.class);
    List<City> cities =query.getResultList();
    for (City c:cities){
      citiesNames.add(c.getName().toLowerCase());
    }
    return citiesNames;
  }

  public City getCity(City name) throws CityDoesNotExistException {

      TypedQuery<City> query = db.createQuery("SELECT c FROM City c WHERE c.name = :name", City.class);
      query.setParameter("name", name.getName());
      try {
        City res = query.getSingleResult();
        return res;
      } catch (NoResultException e) {
        throw new CityDoesNotExistException();
      }
  }

  public City createCity(String city) throws CityAlreadyExistException {
    try {
        if (getCities().contains(city.toLowerCase())) {
          throw new CityAlreadyExistException(ResourceBundle.getBundle("Etiquetas").getString("CreateCityGUI.CityAlreadyExist"));
        }
        City newCity = new City(city);
        db.getTransaction().begin();
        db.persist(newCity);
        db.getTransaction().commit();
        return newCity;
      } catch (NullPointerException e) {
        db.getTransaction().commit();
        return null;
      }
  }

  public Alert createAlert(City from, City to, Date date, int nPlaces, User user) throws AlertAlreadyExistException {
    try{
      if (!getAlerts(from, to, date, nPlaces, user).isEmpty()) {
        throw new AlertAlreadyExistException(ResourceBundle.getBundle("Etiquetas").getString("CreateAlertGUI.AlertAlreadyExist"));
      }
      db.getTransaction().begin();
      Alert alert = new Alert.Builder()
              .fromLocation(from)
              .toLocation(to)
              .rideDate(date)
              .numSeats(nPlaces)
              .user(user)
              .build();
      db.persist(alert);
      db.getTransaction().commit();
      return alert;
    } catch (NoResultException e) {
      db.getTransaction().commit();
      return null;
    }
  }


  public Ride createRide(City from, City to, Date date, int nPlaces, float price, long driverID) throws RideAlreadyExistException, RideMustBeLaterThanTodayException {
    System.out.println(">> DataAccess: createRide=> from= " + from + " to= " + to + " driver=" + driverID + " date " + date);
    try {
      if (new Date().compareTo(date) > 0) {
        throw new RideMustBeLaterThanTodayException();
      }
      db.getTransaction().begin();

      Driver driver = db.find(Driver.class, driverID);
      if (driver.doesRideExists(from, to, date)) {
        db.getTransaction().commit();
        throw new RideAlreadyExistException();
      }

      Ride ride = new Ride.Builder()
              .fromLocation(from)
              .toLocation(to)
              .numPlaces(nPlaces)
              .date(date)
              .price(price)
              .driver(driver)
              .build();

      String email = driver.getEmail();
      TypedQuery<Driver> driverByEmail = db.createQuery("SELECT d FROM Driver d WHERE d.email = :email", Driver.class);
        driverByEmail.setParameter("email", email);
      Driver driver1 = driverByEmail.getSingleResult();
      driver1.addRide(ride);

      //next instruction can be obviated
      db.persist(driver1);
      //db.persist(ride);
      db.getTransaction().commit();

      return ride;
    } catch (NullPointerException e) {
      // TODO Auto-generated catch block
      db.getTransaction().commit();
      return null;
    }
  }

  public List<Ride> getRides(City origin, City destination, Date date, int numSeats) {
    System.out.println(">> DataAccess: getRides origin/dest/date");

    TypedQuery<Ride> query = db.createQuery("SELECT ride FROM Ride ride "
            + "WHERE ride.fromLocation=?1 AND ride.toLocation=?2 AND ride.date=?3 AND ride.numPlaces>=?4", Ride.class);
    query.setParameter(1, origin);
    query.setParameter(2, destination);
    query.setParameter(3, date);
    query.setParameter(4, numSeats);

    List<Ride> rides = query.getResultList();
    return rides;
  }


  /**
   * This method returns all the cities where rides depart
   * @return collection of cities
   */
  public List<City> getDepartCities(){
    List<City> depCities = new ArrayList<>();
    TypedQuery<City> query = db.createQuery("SELECT DISTINCT r.fromLocation FROM Ride r", City.class);
    List<City> cities =query.getResultList();
    return cities;
  }
  /**
   * This method returns all the arrival destinations, from all rides that depart from a given city
   *
   * @param from the departure location of a ride
   * @return all the arrival destinations
   */
  public List<City> getArrivalCities(City from){
    TypedQuery<City> query = db.createQuery("SELECT DISTINCT r.toLocation FROM Ride r WHERE r.fromLocation=?1", City.class);
    query.setParameter(1, from);
    List<City> cities =query.getResultList();
    return cities;
  }

  /**
   * This method retrieves from the database the dates a month for which there are events
   * @param from the origin location of a ride
   * @param to the destination location of a ride
   * @param date of the month for which days with rides want to be retrieved
   * @return collection of rides
   */
  public List<Date> getThisMonthDatesWithRides(City from, City to, Date date) {
    System.out.println(">> DataAccess: getEventsMonth");
    List<Date> res = new ArrayList<>();

    Date firstDayMonthDate= UtilDate.firstDayMonth(date);
    Date lastDayMonthDate= UtilDate.lastDayMonth(date);


    TypedQuery<Date> query = db.createQuery("SELECT DISTINCT r.date FROM Ride r WHERE r.fromLocation=?1 AND r.toLocation=?2 AND r.date BETWEEN ?3 and ?4",Date.class);

    query.setParameter(1, from);
    query.setParameter(2, to);
    query.setParameter(3, firstDayMonthDate);
    query.setParameter(4, lastDayMonthDate);
    List<Date> dates = query.getResultList();
    for (Date d:dates){
      res.add(d);
    }
    return res;
  }

  public List<Date> getDatesWithRides(City from, City to) {
    System.out.println(">> DataAccess: getEventsFromTo");
    List<Date> res = new ArrayList<>();

    TypedQuery<Date> query = db.createQuery("SELECT DISTINCT r.date FROM Ride r WHERE r.fromLocation=?1 AND r.toLocation=?2",Date.class);

    query.setParameter(1, from);
    query.setParameter(2, to);
    List<Date> dates = query.getResultList();
    for (Date d:dates){
      res.add(d);
    }
    return res;
  }
  private void generateTestingData() {
    // create domain entities and persist them
  }

  public User getUser(String username) {
    try {
      TypedQuery<User> query = db.createQuery(
              "SELECT u FROM User u WHERE u.username = :username", User.class);
      query.setParameter("username", username);
      return query.getSingleResult();
    } catch (jakarta.persistence.NoResultException e) {
      return null; // Or handle it in another appropriate way
    }
  }
  public User login(String username, String hashedPassword) throws UnknownUser {
    User user;
    TypedQuery<User> query = db.createQuery("SELECT u FROM User u WHERE u.username =?1 AND u.password =?2", User.class);
    query.setParameter(1, username);
    query.setParameter(2, hashedPassword);
    try {
      user = query.getSingleResult();
    } catch (Exception e) {
      throw new UnknownUser();
    }

    return user;
  }

  public User signup(String email, String userName, String hashedPassword, String role) throws UserAlreadyExistException {
    TypedQuery<User> query = db.createQuery(
            "SELECT u FROM User u WHERE u.email = :email OR u.username = :userName", User.class);
    query.setParameter("email", email);
    query.setParameter("userName", userName);

    try {
      User existingUser = query.getSingleResult();
      throw new UserAlreadyExistException();
    } catch (NoResultException e) {
      User newUser = null;
      if (role.equals(ResourceBundle.getBundle("Etiquetas", Locale.getDefault()).getString("FindRidesGUI.Driver")) || role.equals("Driver")) {
        newUser = new Driver.Builder()
                .email(email)
                .username(userName)
                .password(hashedPassword)
                .build();
      } else if (role.equals(ResourceBundle.getBundle("Etiquetas", Locale.getDefault()).getString("Traveler")) || role.equals("Traveler")) {
        newUser = new Traveler.Builder()
                .email(email)
                .username(userName)
                .password(hashedPassword)
                .build();
      }

      if(newUser != null) {
        db.getTransaction().begin();
        db.persist(newUser);
        db.getTransaction().commit();
      }else {
        System.out.println("User is null");
      }

      return newUser;
    }
  }



  public void close() {
    db.close();
    System.out.println("DataBase is closed");
  }

  public List<Message> getSentMessages(User currentUser) {
    TypedQuery<Message> query = db.createQuery("SELECT m FROM Message m WHERE m.sender = :user", Message.class);
    query.setParameter("user", currentUser);
    System.out.println("Messages received: " + query.getResultList());
    return query.getResultList();
  }

    public void saveMessage(Message message) {
        db.getTransaction().begin();
        db.persist(message);
        System.out.println("Message saved: " + message.toString());
        db.getTransaction().commit();
    }

  public List<Message> getReceivedMessages(User user) {
    TypedQuery<Message> query = db.createQuery("SELECT m FROM Message m WHERE m.receiver = :user", Message.class);
    query.setParameter("user", user);
    System.out.println("Messages received: " + query.getResultList());
    return query.getResultList();
  }

  public String getUserType(String username) {
    TypedQuery<String> query = db.createQuery(
            "SELECT u.userType FROM User u WHERE u.username = :username", String.class);
    query.setParameter("username", username);
    return query.getSingleResult();
  }

  public List<Alert> getAlerts(City from, City to, Date date, int nPlaces, User user) {
    TypedQuery<Alert> query = db.createQuery("SELECT a FROM Alert a WHERE a.fromLocation = :from AND a.toLocation = :to AND a.rideDate = :date AND a.user = :user AND a.numSeats = :nPlaces", Alert.class);
    query.setParameter("from", from);
    query.setParameter("to", to);
    query.setParameter("date", date);
    query.setParameter("nPlaces", nPlaces);
    query.setParameter("user", user);
    return query.getResultList();
  }

  public List<Alert> getUserAlerts(User user) {
    TypedQuery<Alert> query = db.createQuery("SELECT a FROM Alert a WHERE a.user = :user", Alert.class);
    query.setParameter("user", user);
    return query.getResultList();
  }


  public List<Ride> getFavoriteRides(User user) {
    TypedQuery<Ride> query = db.createQuery("SELECT u.favRides FROM User u WHERE u.id = :userId", Ride.class);
    query.setParameter("userId", user.getId());
    return query.getResultList();
  }


  public void addFavoriteRide(User user, Ride ride) {
    List<Ride> favoriteRides = this.getFavoriteRides(user);
    //System.out.println(ride.toString());
    //System.out.println(ride.getDriver().getUsername());
    if (!favoriteRides.contains(ride)) {
      user.addFavRide(ride);
      favoriteRides.add(ride);
     // System.out.println(favoriteRides.toString());
      db.getTransaction().begin();
      db.merge(user);
      db.getTransaction().commit();
    }
  }

  public boolean alertAlreadyExist(City city, City city1, Date date, int i, User user) {
    TypedQuery<Alert> query = db.createQuery("SELECT a FROM Alert a WHERE a.fromLocation = :from AND a.toLocation = :to AND a.rideDate = :date AND a.user = :user AND a.numSeats = :nPlaces", Alert.class);
    query.setParameter("from", city);
    query.setParameter("to", city1);
    query.setParameter("date", date);
    query.setParameter("nPlaces", i);
    query.setParameter("user", user);
    return !query.getResultList().isEmpty();
  }

  // Check whether a favorite already exists
  public boolean favAlreadyExist(User user, Ride ride) {
    List<Ride> favoriteRides = this.getFavoriteRides(user);
    return favoriteRides.contains(ride);
  }

  public void deleteFavoriteRide(User currentUser, Ride selectedRide) {
    List<Ride> favoriteRides = this.getFavoriteRides(currentUser);
    if (favoriteRides.contains(selectedRide)) {
      currentUser.deleteFavRide(selectedRide);
      favoriteRides.remove(selectedRide);
      db.getTransaction().begin();
      db.merge(currentUser);
      db.getTransaction().commit();
    }
  }

  public void deleteAlert(Alert alert) {
    db.getTransaction().begin();
    db.remove(alert);
    db.getTransaction().commit();
  }


    public String getHashedPassword(String username) throws UnknownUser {
        String hashedPass;
        TypedQuery<String> query = db.createQuery("SELECT u.password FROM User u WHERE u.username = :username", String.class);
        query.setParameter("username", username);
          try {
            hashedPass = query.getSingleResult();
          } catch (Exception e) {
            throw new UnknownUser();
          }
        return hashedPass;
    }

  public List<City> getAllCities() {
    TypedQuery<City> query = db.createQuery("SELECT c FROM City c", City.class);
    return query.getResultList();
  }

  public void bookRide(Traveler currentUser, Ride ride, Integer numSeats) {
    db.getTransaction().begin();
    Reservation reservation = new Reservation.Builder()
            .numSeats(numSeats)
            .forRide(ride)
            .madeBy(currentUser)
            .build();
    ride.setNumPlaces(ride.getNumPlaces() - numSeats);
    ride.getReservations().add(reservation);
    db.merge(ride);
    db.getTransaction().commit();

  }

  public ArrayList<Ride> getRidesByDriver(Driver driver) {
    TypedQuery<Ride> query = db.createQuery("SELECT r FROM Ride r WHERE r.driver = :driver", Ride.class);
    query.setParameter("driver", driver);
    return new ArrayList<>(query.getResultList());
  }



  public ArrayList<Reservation> getMyBookings(User currentUser) {
    TypedQuery<Reservation> query = db.createQuery("SELECT r FROM Reservation r WHERE r.madeBy = :user", Reservation.class);
    query.setParameter("user", currentUser);
    return new ArrayList<>(query.getResultList());
  }

  public boolean checkAlertsNewRide(City departCity, City arrivalCity, Date date, int numSeats, User user) {
      TypedQuery<Alert> query = db.createQuery("SELECT a FROM Alert a WHERE a.fromLocation = :fromLocation " +
              "AND a.toLocation = :toLocation AND a.rideDate = :rideDate AND a.numSeats <= :numSeats " +
              "AND a.user = :user", Alert.class);
      query.setParameter("fromLocation", departCity);
      query.setParameter("toLocation", arrivalCity);
      query.setParameter("rideDate", date);
      query.setParameter("numSeats", numSeats);
      query.setParameter("user", user);
      //System.out.println("Alerts found: " + query.getResultList());
      return !query.getResultList().isEmpty();
    }

  public User getSystemUser() {
    TypedQuery<User> query = db.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
    query.setParameter("username", "System Sharetrip");
    User systemUser = query.getSingleResult();
      return systemUser;

  }
}
