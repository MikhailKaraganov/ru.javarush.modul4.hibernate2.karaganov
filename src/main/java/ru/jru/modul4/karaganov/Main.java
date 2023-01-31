package ru.jru.modul4.karaganov;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import ru.jru.modul4.karaganov.dao.*;
import ru.jru.modul4.karaganov.entity.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.*;

public class Main {

    private final SessionFactory sessionFactory;

    private final ActorDAO actorDAO;
    private final AddressDAO addressDAO;
    private final CategoryDAO categoryDAO;
    private final CityDAO cityDAO;
    private final CountryDAO countryDAO;
    private final CustomerDAO customerDAO;
    private final FilmDAO filmDAO;
    private final FilmTextDAO filmTextDAO;
    private final InventoryDAO inventoryDAO;
    private final LanguageDAO languageDAO;
    private final PaymentDAO paymentDAO;
    private final RentalDAO rentalDAO;
    private final StaffDAO staffDAO;
    private final StoreDAO storeDAO;

    public Main() {
        Properties properties = new Properties();
        properties.put(Environment.DRIVER, "com.p6spy.engine.spy.P6SpyDriver");
        properties.put(Environment.URL, "jdbc:p6spy:mysql://localhost:3306/movie");
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        properties.put(Environment.USER, "root");
        properties.put(Environment.PASS, "root");
        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        properties.put(Environment.HBM2DDL_AUTO, "validate");

        sessionFactory = new Configuration()
                .addAnnotatedClass(Actor.class)
                .addAnnotatedClass(Address.class)
                .addAnnotatedClass(Category.class)
                .addAnnotatedClass(City.class)
                .addAnnotatedClass(Film.class)
                .addAnnotatedClass(Payment.class)
                .addAnnotatedClass(Inventory.class)
                .addAnnotatedClass(FilmText.class)
                .addAnnotatedClass(Rental.class)
                .addAnnotatedClass(Language.class)
                .addAnnotatedClass(Staff.class)
                .addAnnotatedClass(Store.class)
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Country.class)
                .setProperties(properties)
                .buildSessionFactory();

                actorDAO = new ActorDAO(sessionFactory);
                addressDAO = new AddressDAO(sessionFactory);
                categoryDAO = new CategoryDAO(sessionFactory);
                cityDAO = new CityDAO(sessionFactory);
                countryDAO = new CountryDAO(sessionFactory);
                customerDAO = new CustomerDAO(sessionFactory);
                filmDAO = new FilmDAO(sessionFactory);
                filmTextDAO = new FilmTextDAO(sessionFactory);
                inventoryDAO = new InventoryDAO(sessionFactory);
                languageDAO = new LanguageDAO(sessionFactory);
                paymentDAO = new PaymentDAO(sessionFactory);
                rentalDAO = new RentalDAO(sessionFactory);
                staffDAO = new StaffDAO(sessionFactory);
                storeDAO = new StoreDAO(sessionFactory);
    }

    public static void main(String[] args) {
        Main main = new Main();
        Customer customer = main.createCustomer();
        main.customerReturnFilm(7);
        main.customerRentInventory(Short.valueOf("4"), (byte) 1);
        main.addNewFilmForRent(Byte.valueOf("1"));
    }

    private void addNewFilmForRent(Byte storeId) {
        try (Session currentSession = sessionFactory.getCurrentSession()){
            Transaction transaction = currentSession.beginTransaction();
            Set<Actor> actorSet = getActorsSet(150, 3);
            Film film = new Film();
            film.setActors(actorSet);
            film.setCategories(formCategoryList(1,3,5,8,9));
            film.setDescription("Some description of new film");
            film.setTitle("2020 new film. For real.");
            film.setLanguage(languageDAO.getById(Byte.valueOf("3")));
            film.setRating(Rating.PG);
            film.setRentDuration(Byte.valueOf("31"));
            film.setRentalRate(BigDecimal.valueOf(9L));
            film.setReplacementCost(BigDecimal.valueOf(15L));
            film.setLength(Short.valueOf("70"));
            film.setReleaseYear(Year.of(2020));
            filmDAO.save(film);
            Inventory inventory = new Inventory();
            inventory.setFilm(film);
            inventory.setStore(storeDAO.getById(storeId));
            inventoryDAO.save(inventory);
            transaction.commit();
        }
    }

    private Set<Actor> getActorsSet(int count, int each) {
        Set<Actor> actorSet = new HashSet<>();
        List<Actor> items = actorDAO.getItems(0, count);
        for (int i=0; i<items.size(); i++) {
            if(i % each == 0){
                actorSet.add(items.get(i));
            }
        }
        return actorSet;
    }

    private Set<Category> formCategoryList(int ... whatCategoriesYouNeed){
        Set<Category> resultSet = new HashSet<>();
        List<Category> items = categoryDAO.getItems(0,16);
        for (int categoryIndex : whatCategoriesYouNeed) {
            resultSet.add(items.get(categoryIndex));
        }
        return resultSet;
    }

    private void customerRentInventory(Short customerId, Byte storeId){



        try (Session session = sessionFactory.getCurrentSession()){
            Transaction transaction = session.beginTransaction();
            Customer customer = customerDAO.getById((short)customerId);
            Rental rental = rentalDAO.getFirstAvaliableRental();
            rental.setRentalDate(LocalDateTime.now());
            rental.setCustomer(customer);
            Inventory inventory = rental.getInventory();
            Store store = storeDAO.getById(storeId);
            customer.setStore(store);
            Staff staff = store.getStaff();
            Payment payment = createPayment(customer, staff, rental);
            paymentDAO.save(payment);
            customerDAO.update(customer);
            rentalDAO.update(rental);
            storeDAO.update(store);
            transaction.commit();
            }
        }

    private Payment createPayment(Customer customer, Staff staff, Rental rental) {
        Payment payment = new Payment();
        payment.setCustomer(customer);
        payment.setAmount(BigDecimal.valueOf(30L));
        payment.setPaymentDate(LocalDateTime.now());
        payment.setStaff(staff);
        payment.setRental(rental);
        return payment;
    }

    private void customerReturnFilm(Integer rentalId) {
        try(Session session = sessionFactory.getCurrentSession()){
            Transaction transaction = session.beginTransaction();
            Rental rental = rentalDAO.getById(rentalId);
            Customer customer = rental.getCustomer();
            rental.setReturnDate(LocalDateTime.now());
            customer.setActive(false);
            customerDAO.update(customer);
            rentalDAO.update(rental);
            transaction.commit();
        }
    }

    private Customer createCustomer() {
        try (Session session = sessionFactory.getCurrentSession()){
            Transaction transaction = session.beginTransaction();
            Customer customer = new Customer();
            customer.setStore(storeDAO.getItems(0,1).get(0));
            City city = cityDAO.getCityById((short) 8);
            Address address = new Address();
            address.setAddress("Some address");
            address.setCity(city);
            address.setPhone("243213412");
            address.setDistrict("SomeDistrict");
            addressDAO.save(address);

            customer.setAddress(address);
            customer.setEmail("some@mail.com");
            customer.setActive(true);
            customer.setFirstName("Ivan");
            customer.setLastName("Petrov");
            customerDAO.save(customer);
            transaction.commit();
            return customer;
        }
    }
}