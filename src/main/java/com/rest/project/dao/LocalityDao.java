package com.rest.project.dao;

import com.rest.project.entity.Locality;
import com.rest.project.exception.ResourceNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DAO implementation for managing Locality entities.
 */
@Repository
public class LocalityDao implements Dao<Locality, Integer> {

    private final SessionFactory sessionFactory;

    @Autowired
    public LocalityDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Retrieves all localities from the database.
     *
     * @return a list of all localities
     */
    @Override
    public List<Locality> getAll() {
        String hql = "FROM Locality";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql);
        List<Locality> localities = query.getResultList();
        return localities;
    }

    /**
     * Retrieves a locality from the database by name.
     *
     * @param name the name of locality to retrieve
     * @return a locality, or null if locality not found
     */
    @Override
    public Locality getByName(String name){
        String hql = "FROM Locality as locality WHERE locality.name = :name";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql).setParameter("name", name);
        List<Locality> localities = query.getResultList();
        if(localities.isEmpty()){
            return null;
        }
        return localities.get(0);
    }

    /**
     * Saves a new locality to the database.
     *
     * @param value the locality to save
     */
    @Override
    public void save(Locality value) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(value);
    }

    /**
     * Updates an existing locality in the database.
     *
     * @param value the locality to update
     * @param key the ID of the locality to update
     */
    @Override
    public void update(Locality value, Integer key) throws ResourceNotFoundException {
        Session session = sessionFactory.getCurrentSession();

        Locality updatingLocality = session.get(Locality.class, key);

        if(updatingLocality == null){
            throw new ResourceNotFoundException();
        }

        updatingLocality.setPopulation(value.getPopulation());
        updatingLocality.setHasMetro(value.isHasMetro());

        session.merge(updatingLocality);
    }

    /**
     * Deletes a locality from the database by ID.
     *
     * @param id the ID of the locality to delete
     */
    @Override
    public void delete(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Locality locality = session.get(Locality.class, id);

        if(locality != null){
            session.remove(locality);
        }
    }
}
