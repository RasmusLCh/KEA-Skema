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
    E create(E e);
    void edit(E e);
    void delete(int id);
    E findById(int id);
    List<E> findAll();
}
