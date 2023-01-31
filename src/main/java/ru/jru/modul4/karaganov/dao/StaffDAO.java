package ru.jru.modul4.karaganov.dao;

import org.hibernate.SessionFactory;
import ru.jru.modul4.karaganov.entity.Staff;

public class StaffDAO extends ClazzDAO<Staff>{
    public StaffDAO(SessionFactory sessionFactory) {
        super(Staff.class, sessionFactory);
    }
}
