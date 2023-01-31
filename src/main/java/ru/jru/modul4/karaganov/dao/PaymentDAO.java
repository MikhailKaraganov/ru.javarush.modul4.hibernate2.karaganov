package ru.jru.modul4.karaganov.dao;

import org.hibernate.SessionFactory;
import ru.jru.modul4.karaganov.entity.Payment;

public class PaymentDAO extends ClazzDAO<Payment>{
    public PaymentDAO(SessionFactory sessionFactory) {
        super(Payment.class, sessionFactory);
    }
}
