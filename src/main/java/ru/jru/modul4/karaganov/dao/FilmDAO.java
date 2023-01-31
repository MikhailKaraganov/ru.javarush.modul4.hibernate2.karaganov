package ru.jru.modul4.karaganov.dao;

import org.hibernate.SessionFactory;
import ru.jru.modul4.karaganov.entity.Film;

public class FilmDAO extends ClazzDAO<Film>{
    public FilmDAO(SessionFactory sessionFactory) {
        super(Film.class, sessionFactory);
    }
}
