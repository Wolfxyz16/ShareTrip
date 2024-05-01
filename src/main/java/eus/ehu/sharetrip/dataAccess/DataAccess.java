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

    this.reset();




    try {

      Calendar today = Calendar.getInstance();

      int month = today.get(Calendar.MONTH) + 2;
      int year = today.get(Calendar.YEAR);
      if (month == 11) {
        month = 1;
        year += 1;
      }

      //Create drivers
      Driver driver1 = new Driver("driver1@gmail.com", "Aitor Fernandez", "1234");
      Driver driver2 = new Driver()
                .email("driver2@gmail.com")
                .username("Ane Gaztañaga")
                .password("1234")
                .build();
      Driver driver3 = new Driver("driver3@gmail.com", "test", "test");

      db.getTransaction().begin();
      db.persist(driver1);
      db.persist(driver2);
      db.persist(driver3);
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
              .driver(driver1)
              .build();

        Ride ride12 = new Ride.Builder()
                .fromLocation(city14)
                .toLocation(city15)
                .date(UtilDate.newDate(year, month, 6))
                .numPlaces(4)
                .price(8)
                .driver(driver1)
                .build();

        Ride ride13 = new Ride.Builder()
                .fromLocation(city16)
                .toLocation(city1)
                .date(UtilDate.newDate(year, month, 25))
                .numPlaces(4)
                .price(4)
                .driver(driver1)
                .build();

        Ride ride14 = new Ride.Builder()
                .fromLocation(city1)
                .toLocation(city15)
                .date(UtilDate.newDate(year, month, 7))
                .numPlaces(4)
                .price(8)
                .driver(driver1)
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
        driver1.addRide(ride11);
        driver2.addRide(ride12);
        driver3.addRide(ride13);
        driver2.addRide(ride14);


      //Create travelers

      Traveler traveler1 = new Traveler("user1@gmail.com", "User1", "1234");
      Traveler traveler2 = new Traveler("user2@gmail.com", "User2", "1234");

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
              .build();

      Alert alert2 = new Alert.Builder()
              .fromLocation(city3)
              .toLocation(city4)
              .rideDate(UtilDate.newDate(year, month + 1, 15))
              .numSeats(4)
              .build();

      Alert alert3 = new Alert.Builder()
              .fromLocation(city5)
              .toLocation(city1)
              .rideDate(UtilDate.newDate(year, month, 6))
              .numSeats(4)
              .build();

      Alert alert4 = new Alert.Builder()
              .fromLocation(city3)
              .toLocation(city5)
              .rideDate(UtilDate.newDate(year, month, 25))
              .numSeats(4)
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

  public Alert createAlert(City from, City to, Date date, int nPlaces) throws AlertAlreadyExistException {
    try{
      if (!getAlerts(from, to, date, nPlaces).isEmpty()) {
        throw new AlertAlreadyExistException(ResourceBundle.getBundle("Etiquetas").getString("CreateAlertGUI.AlertAlreadyExist"));
      }
      db.getTransaction().begin();
      Alert alert = new Alert.Builder()
              .fromLocation(from)
              .toLocation(to)
              .rideDate(date)
              .numSeats(nPlaces)
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
  public User login(String username, String password) throws UnknownUser {
    User user;
    TypedQuery<User> query = db.createQuery("SELECT u FROM User u WHERE u.username =?1 AND u.password =?2", User.class);
    query.setParameter(1, username);
    query.setParameter(2, password);
    try {
      user = query.getSingleResult();
    } catch (Exception e) {
      throw new UnknownUser();
    }

    return user;
  }

  public User signup(String email, String userName, String password, String role) throws UserAlreadyExistException {
    TypedQuery<User> query = db.createQuery(
            "SELECT u FROM User u WHERE u.email = :email OR u.username = :userName", User.class);
    query.setParameter("email", email);
    query.setParameter("userName", userName);

    try {
      User existingUser = query.getSingleResult();
      throw new UserAlreadyExistException();
    } catch (NoResultException e) {
        if (role.equals(ResourceBundle.getBundle("Etiquetas", Locale.getDefault()).getString("FindRidesGUI.Driver"))) {
          Driver driver = new Driver()
                    .email(email)
                    .username(userName)
                    .password(password)
                    .build();
          db.getTransaction().begin();
          db.persist(driver);
          db.getTransaction().commit();
          return driver;
        } else if (role.equals(ResourceBundle.getBundle("Etiquetas", Locale.getDefault()).getString("Traveler"))) {
          Traveler traveler = new Traveler()
                    .email(email)
                    .username(userName)
                    .password(password)
                    .build();
          db.getTransaction().begin();
          db.persist(traveler);
          db.getTransaction().commit();
          return traveler;
        } else {
          return null;
        }
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

  public List<Alert> getAlerts(City from, City to, Date date, int nPlaces) {
    TypedQuery<Alert> query = db.createQuery("SELECT a FROM Alert a WHERE a.fromLocation = :from AND a.toLocation = :to AND a.rideDate = :date AND a.numSeats = :nPlaces", Alert.class);
    query.setParameter("from", from);
    query.setParameter("to", to);
    query.setParameter("date", date);
    query.setParameter("nPlaces", nPlaces);
    return query.getResultList();
  }

  public List<Alert> getAlerts() {
    TypedQuery<Alert> query = db.createQuery("SELECT a FROM Alert a", Alert.class);
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

  public boolean alertAlreadyExist(City city, City city1, Date date, int i) {
    TypedQuery<Alert> query = db.createQuery("SELECT a FROM Alert a WHERE a.fromLocation = :from AND a.toLocation = :to AND a.rideDate = :date AND a.numSeats = :nPlaces", Alert.class);
    query.setParameter("from", city);
    query.setParameter("to", city1);
    query.setParameter("date", date);
    query.setParameter("nPlaces", i);
    return !query.getResultList().isEmpty();
  }

  // Check whether a favorite already exists
  public boolean favAlreadyExist(User user, Ride ride) {
    List<Ride> favoriteRides = this.getFavoriteRides(user);
    return favoriteRides.contains(ride);
  }
}
