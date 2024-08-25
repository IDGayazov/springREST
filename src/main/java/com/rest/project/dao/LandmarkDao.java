package com.rest.project.dao;

import com.rest.project.entity.Landmark;
import com.rest.project.exception.ResourceNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DAO implementation for managing Landmark entities.
 */
@Repository
public class LandmarkDao implements Dao<Landmark, Integer>{

    private final SessionFactory sessionFactory;

    @Autowired
    public LandmarkDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Retrieves all landmarks from the database.
     *
     * @return a list of all landmarks
     */
    @Override
    public List<Landmark> getAll() {
        String hql = "FROM Landmark";
        Session session = sessionFactory.getCurrentSession();
        Query<Landmark> query = session.createQuery(hql);
        List<Landmark> attractions = query.getResultList();
        return attractions;
    }

    /**
     * Saves a new landmark to the database.
     *
     * @param value the landmark to save
     */
    @Override
    public void save(Landmark value) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(value);
    }

    /**
     * Updates an existing landmark in the database.
     *
     * @param value the landmark to update
     * @param key the ID of the landmark to update
     */
    @Override
    public void update(Landmark value, Integer key) throws ResourceNotFoundException {
        Session session = sessionFactory.getCurrentSession();

        Landmark updatingAttraction = session.get(Landmark.class, key);

        if(updatingAttraction == null){
            throw new ResourceNotFoundException();
        }

        updatingAttraction.setDescription(value.getDescription());
        session.merge(updatingAttraction);

    }

    /**
     * Retrieves a landmark from the database by localityName.
     *
     * @param localityName the name of the locality of landmark to retrieve
     * @return a list containing the found landmarks
     */
    public List<Landmark> getAttractionByLocality(String localityName){
        String hql = "FROM Landmark as landmark WHERE landmark.locality.name = :localityName";

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql).setParameter("localityName", localityName);
        List<Landmark> attractions = query.getResultList();

        return attractions;
    }

    /**
     * Retrieves a landmark from the database by name.
     *
     * @param name the name of landmark to retrieve
     * @return a landmark, or null if landmark not found
     */
    @Override
    public Landmark getByName(String name){
        String hql = "FROM Landmark as landmark WHERE landmark.name = :name";

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql).setParameter("name", name);
        List<Landmark> landmarks = query.getResultList();

        if(landmarks.isEmpty()){
            return null;
        }

        return landmarks.get(0);
    }

    /**
     * Deletes a landmark from the database by ID.
     *
     * @param key the ID of the landmark to delete
     */
    @Override
    public void delete(Integer key) throws ResourceNotFoundException {
        Session session = sessionFactory.getCurrentSession();
        Landmark deletingAttraction = session.get(Landmark.class, key);

        if(deletingAttraction != null){
            session.remove(deletingAttraction);
        }else{
            throw new ResourceNotFoundException();
        }
    }
}
