package com.edu.ulab.app.storage.impl;

import com.edu.ulab.app.entity.book.BookEntity;
import com.edu.ulab.app.entity.user.UserEntity;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.storage.Storage;
import com.edu.ulab.app.storage.StorageUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Repository
@Qualifier("userStorageUtilsImpl")
public class UserStorageUtilsImpl implements StorageUtils<UserEntity, Long> {

    private final Storage storage;

    public UserStorageUtilsImpl(Storage storage, BookStorageUtilsImpl bookStorageUtils) {
        this.storage = storage;
    }

    @Override
    public void save(@NotNull UserEntity entity) {

        storage.getUserEntityList().add(entity);

        if (!entity.getBookList().isEmpty()) {
            storage.getBookEntityList().addAll(entity.getBookList());

            UserEntity userEntity = storage.getUserEntityList()
                    .stream()
                    .filter(Objects::nonNull)
                    .filter(user -> entity.getId().equals(user.getId()))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundException("user not saved"));

            List<BookEntity> bookEntityList = storage.getBookEntityList()
                    .stream()
                    .filter(Objects::nonNull)
                    .filter(book -> book.getId().equals(entity.getId()))
                    .toList();

            userEntity.getBookList().removeIf(book -> !Objects.equals(book.getUserId(), null));
            userEntity.getBookList().addAll(bookEntityList);
        }
    }

    @Override
    public UserEntity findById(@NotNull Long id) {
        //return new UserEntity = new storage object
        return storage.getUserEntityList()//TODO make thread-safe
                .stream()
                .filter(Objects::nonNull)
                .filter(user -> id.equals(user.getId()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("user with id " + id + " not found"));
    }

    @Override
    public List<UserEntity> findAll() {
        return storage.getUserEntityList(); //TODO make thread-safe
    }

    @Override
    public void delete(@NotNull Long id) {
        UserEntity userEntity = findById(id);

        if(!userEntity.getBookList().isEmpty()) {
            storage.getBookEntityList().removeIf(book -> id.equals(book.getUserId()));
        }

        storage.getUserEntityList().removeIf(user -> id.equals(user.getId()));
    }
}

