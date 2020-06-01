package kea.schedule.scheduleservice.services;

import  kea.schedule.scheduleservice.models.ModelInterface;

import java.util.List;

/**
 * Default CRUD interface for services.
 *
 * create: Create
 * edit: Edit/Update
 * delete: Delete
 * findById: Get 1 item
 * findAll: Get all item(s)
 * */

public interface CRUDServiceInterface<E extends ModelInterface> {
    /**
     * Create an Object of type ModelInterface and returns the object
     * */
    E create(E e);
    /**
     * Edit an Object of type ModelInterface
     * */
    void edit(E e);
    /**
     * Deletes an Object, based on id
     * */
    void delete(int id);
    /**
     * Finds an Object based on id
     * */
    E findById(int id);
    /**
     * Returns all Objectse
     * */
    List<E> findAll();
}