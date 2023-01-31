package ru.jru.modul4.karaganov.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.jru.modul4.karaganov.entity.Inventory;
import ru.jru.modul4.karaganov.entity.Rental;

import java.util.List;
import java.util.Set;

public class InventoryDAO extends ClazzDAO<Inventory>{
    public InventoryDAO(SessionFactory sessionFactory) {
        super(Inventory.class, sessionFactory);
    }

    public List<Inventory> getAvailableInventoriesByStoreId(Byte storeId){
        String hql ="from Inventory i where i.store.id = :storeId";
        Query<Inventory> query = getCurrentSession().createQuery(hql, Inventory.class);
        query.setParameter("storeId", storeId);
        List<Inventory> inventoryList = query.list();
        return inventoryList;

    }

}
