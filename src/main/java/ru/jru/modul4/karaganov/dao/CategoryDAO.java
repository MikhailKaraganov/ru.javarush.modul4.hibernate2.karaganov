package ru.jru.modul4.karaganov.dao;

import org.hibernate.SessionFactory;
import ru.jru.modul4.karaganov.entity.Category;

public class CategoryDAO extends ClazzDAO<Category> {
    public CategoryDAO( SessionFactory sessionFactory) {
        super(Category.class, sessionFactory);
    }
}
