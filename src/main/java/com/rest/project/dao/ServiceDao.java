package com.rest.project.dao;

import com.rest.project.entity.Service;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DAO class for managing Service entities.
 */
@Repository
public class ServiceDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public ServiceDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Retrieves a service from the database by name.
     *
     * @param name the name of service to retrieve
     * @return a service, or null if service not found
     */
    public Service getByName(String name){
        String hql = "FROM Service as service WHERE service.name = :name";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql).setParameter("name", name);
        List<Service> services = query.getResultList();
        if(services.isEmpty()){
            return null;
        }
        return services.get(0);
    }

}
