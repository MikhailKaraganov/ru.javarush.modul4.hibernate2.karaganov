package ru.jru.modul4.karaganov.dao;

import org.hibernate.SessionFactory;
import ru.jru.modul4.karaganov.entity.Country;

public class CountryDAO extends ClazzDAO<Country> {
    public CountryDAO(SessionFactory sessionFactory) {
        super(Country.class, sessionFactory);
    }
}
