package ru.jru.modul4.karaganov.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.jru.modul4.karaganov.entity.Language;
import ru.jru.modul4.karaganov.entity.Store;

public class LanguageDAO extends ClazzDAO<Language>{
    public LanguageDAO(SessionFactory sessionFactory) {
        super(Language.class, sessionFactory);
    }

    public Language getById (Byte id){
        String hql ="from Language c where c.id =:id";
        Query<Language> query = getCurrentSession().createQuery(hql, Language.class);
        query.setParameter("id", id);
        return query.uniqueResult();
    }
}
