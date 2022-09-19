package com.edu.ulab.app.storage;

import java.util.List;

public interface StorageUtils<T, ID> {

    void save(T entity);

    T findById(ID id);

    List <T> findAll();

    void delete(ID id);

}
