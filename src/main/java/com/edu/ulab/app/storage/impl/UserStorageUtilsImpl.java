package com.edu.ulab.app.storage.impl;

import com.edu.ulab.app.entity.book.BookEntity;
import com.edu.ulab.app.entity.user.UserEntity;
import com.edu.ulab.app.exception.UserNotFoundException;
import com.edu.ulab.app.exception.UserNotSavedException;
import com.edu.ulab.app.storage.Storage;
import com.edu.ulab.app.storage.StorageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Slf4j
@Qualifier("userStorageUtilsImpl")
public class UserStorageUtilsImpl implements StorageUtils<UserEntity, Long> {

    private final Storage storage;

    public UserStorageUtilsImpl(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void save(UserEntity entity) {
        log.info("UserEntity to save {}", entity);
        storage.getUserEntityMap().put(entity.getId(), entity);

        if (!entity.getBookList().isEmpty()) {
            log.info("UserEntity book list not empty");
            storage.getBookEntityMap()
                    .putAll(
                            entity.getBookList()
                                    .stream()
                                    .collect(
                                            Collectors.toMap(
                                                    BookEntity::getId,
                                                    book -> book,
                                                    (o1, o2) -> o1)));

            UserEntity userEntity = storage.getUserEntityMap().entrySet()
                    .stream()
                    .filter(Objects::nonNull)
                    .filter(entry -> entry.getKey().equals(entity.getId()))
                    .map(Map.Entry::getValue)
                    .peek(user -> log.info("user data from storage: {}", user))
                    .findFirst()
                    .orElseThrow(UserNotSavedException::new);

            List<BookEntity> bookEntityList = storage.getBookEntityMap().entrySet()
                    .stream()
                    .filter(Objects::nonNull)
                    .filter(entry -> entry.getKey().equals(entity.getId()))
                    .map(Map.Entry::getValue)
                    .peek(book -> log.info("book data from storage: {}", book))
                    .toList();

            userEntity.getBookList().removeIf(book -> !Objects.equals(book.getUserId(), null));
            log.info("userEntity books after remove{}", userEntity.getBookList());
            userEntity.getBookList().addAll(bookEntityList);
            log.info("userEntity books after add {}", userEntity.getBookList());
        }
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        log.info("UserEntity findById - id {}", id);
        return storage.getUserEntityMap().entrySet()
                .stream()
                .filter(Objects::nonNull)
                .filter(entry -> id.equals(entry.getKey()))
                .map(Map.Entry::getValue)
                .peek(user -> log.info("user data found: {}", user))
                .findFirst();
    }

    @Override
    public List<UserEntity> findAll() {
        log.info("UserEntity findAll");
        return storage.getUserEntityMap()
                .entrySet()
                .stream()
                .filter(Objects::nonNull)
                .map(Map.Entry::getValue)
                .peek(user -> log.info("user data found: {}", user))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        log.info("UserEntity deleteById - id {}", id);
        UserEntity userEntity = findById(id).orElseThrow(() -> new UserNotFoundException(id));

        if (!userEntity.getBookList().isEmpty()) {
            log.info("remove books");
            storage.getBookEntityMap().entrySet().removeIf(entry -> id.equals(entry.getKey()));
        }
        log.info("remove user");
        storage.getUserEntityMap().entrySet().removeIf(entry -> id.equals(entry.getKey()));
    }
}


