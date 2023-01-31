package ru.jru.modul4.karaganov.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.jru.modul4.karaganov.entity.Customer;
import ru.jru.modul4.karaganov.entity.Store;

public class StoreDAO extends ClazzDAO<Store>{
    public StoreDAO(SessionFactory sessionFactory) {
        super(Store.class, sessionFactory);
    }

    public Store getById(byte id){
        String hql ="from Store c where c.id =:id";
        Query<Store> query = getCurrentSession().createQuery(hql, Store.class);
        query.setParameter("id", id);
        return query.uniqueResult();
    }
}
