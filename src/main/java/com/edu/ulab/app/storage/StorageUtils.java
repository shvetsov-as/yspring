package com.edu.ulab.app.storage;

import java.util.List;
import java.util.Optional;

public interface StorageUtils<T, ID> {

    void save(T entity);

    Optional<T> findById(ID id);

    List <T> findAll();

    void delete(ID id);

}
