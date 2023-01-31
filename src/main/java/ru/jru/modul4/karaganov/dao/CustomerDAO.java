package ru.jru.modul4.karaganov.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.jru.modul4.karaganov.entity.Customer;

public class CustomerDAO extends ClazzDAO<Customer> {
    public CustomerDAO(SessionFactory sessionFactory) {
        super(Customer.class, sessionFactory);
    }

    public Customer getById(short id){
        String hql ="from Customer c where c.id =:id";
        Query<Customer> query = getCurrentSession().createQuery(hql, Customer.class);
        query.setParameter("id", id);
        return query.uniqueResult();
    }
}
