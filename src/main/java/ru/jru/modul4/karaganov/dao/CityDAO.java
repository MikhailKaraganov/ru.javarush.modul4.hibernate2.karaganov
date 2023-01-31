package ru.jru.modul4.karaganov.dao;

import org.hibernate.SessionFactory;
import ru.jru.modul4.karaganov.entity.City;

public class CityDAO extends ClazzDAO<City>{
    public CityDAO(SessionFactory sessionFactory) {
        super(City.class, sessionFactory);
    }

    public City getById(final short id) {
        return getCurrentSession().get(City.class, id);
    }

    public City getCityById(short id) {
        return getCurrentSession().get(City.class, id);
    }
}
