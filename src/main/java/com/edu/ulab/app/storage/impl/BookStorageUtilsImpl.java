package com.edu.ulab.app.storage.impl;

import com.edu.ulab.app.entity.book.BookEntity;
import com.edu.ulab.app.entity.user.UserEntity;
import com.edu.ulab.app.exception.BookNotFoundException;
import com.edu.ulab.app.exception.BookNotSavedException;
import com.edu.ulab.app.exception.UserNotFoundException;
import com.edu.ulab.app.storage.Storage;
import com.edu.ulab.app.storage.StorageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
@Slf4j
@Qualifier("bookStorageUtilsImpl")
public class BookStorageUtilsImpl implements StorageUtils<BookEntity, Long> {

    private final Storage storage;

    public BookStorageUtilsImpl(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void save(BookEntity entity) {
        log.info("BookEntity to save {}", entity);
        if (Objects.equals(entity.getUserId(), null)) {
            storage.getBookEntityMap().put(entity.getId(), entity);
        } else {
            UserEntity userEntity = storage.getUserEntityMap().entrySet()
                    .stream()
                    .filter(Objects::nonNull)
                    .filter(entry -> entry.getKey().equals(entity.getUserId()))
                    .map(Map.Entry::getValue)
                    .peek(book -> log.info("book data from storage {}", book))
                    .findFirst()
                    .orElseThrow(BookNotSavedException::new);

            userEntity.getBookList().add(entity);
            log.info("userEntity books after add {}", userEntity.getBookList());
            storage.getBookEntityMap().put(entity.getId(), entity);
            log.info("book to add in storage id: {}, value: {}", entity.getId(), entity);
        }
    }

    @Override
    public Optional<BookEntity> findById(Long id) {
        log.info("BookEntity findById - id {}", id);
        return storage.getBookEntityMap()
                .entrySet()
                .stream()
                .filter(Objects::nonNull)
                .filter(entry -> id.equals(entry.getKey()))
                .map(Map.Entry::getValue)
                .peek(book -> log.info("book found by id {}", book))
                .findFirst();
    }

    @Override
    public List<BookEntity> findAll() {
        log.info("BookEntity findAll");
        return new ArrayList<>(storage.getBookEntityMap().values());
    }

    @Override
    public void delete(Long id) {
        log.info("BookEntity deleteById - id {}", id);
        BookEntity bookEntity = findById(id).orElseThrow(() -> new UserNotFoundException(id));
        log.info("BookEntity found {}", bookEntity);
        if (Objects.equals(bookEntity.getUserId(), null)) {
            storage.getBookEntityMap().entrySet().removeIf(entry -> id.equals(entry.getKey()));
        } else {
            UserEntity userEntity = storage.getUserEntityMap().entrySet()
                    .stream()
                    .filter(Objects::nonNull)
                    .filter(entry -> entry.getKey().equals(id))
                    .map(Map.Entry::getValue)
                    .peek(user -> log.info("user data found: {}", user))
                    .findFirst()
                    .orElseThrow(() -> new BookNotFoundException(id));

            userEntity.getBookList().removeIf(book -> id.equals(book.getId()));
            log.info("Books from user removed");
            storage.getBookEntityMap().entrySet().removeIf(entry -> id.equals(entry.getKey()));
            log.info("Books from storage removed");
        }
    }
}

