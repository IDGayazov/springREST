package com.rest.project.dao;

import com.rest.project.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Generic interface for data manipulation
 *
 * @param <T> the data type
 * @param <K> the id type
 * */
public interface Dao<T, K> {

    /**
     * Retrieve data by name
     *
     * @param name the name of entity to retrieve
     * */
    public T getByName(String name);

    /**
     * Retrieve all entities
     * */
    public List<T> getAll();

    /**
     * Save data to repository
     *
     * @param value the entity saving to repository
     * */
    public void save(T value);

    /**
     * Update data with key
     *
     * @param value the new entity
     * @param key the ID of saving entity
     *
     * @throws ResourceNotFoundException if value with key is not exist
     * */
    public void update(T value, K key) throws ResourceNotFoundException;

    /**
     * Delete data by key
     *
     * @param key the ID of deleting entity
     *
     * @throws ResourceNotFoundException if value with key is not exist
     * */
    public void delete(K key) throws ResourceNotFoundException;

}
