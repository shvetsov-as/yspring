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
@Qualifier("bookStorageUtilsImpl")
public class BookStorageUtilsImpl implements StorageUtils<BookEntity, Long> {

    private final Storage storage;

    public BookStorageUtilsImpl(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void save(@NotNull BookEntity entity) {
        if (Objects.equals(entity.getUserId(), null)) {
            storage.getBookEntityList().add(entity);
        } else {
            UserEntity userEntity = storage.getUserEntityList()
                    .stream()
                    .filter(Objects::nonNull)
                    .filter(user -> user.getId().equals(entity.getUserId()))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundException("book not saved"));

            userEntity.getBookList().add(entity);
            storage.getBookEntityList().add(entity);
        }
    }

    @Override
    public BookEntity findById(@NotNull Long id) {
        BookEntity bookEntity;
        bookEntity = storage.getBookEntityList()
                .stream()
                .filter(Objects::nonNull)
                .filter(book -> id.equals(book.getId()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("book with id " + id + " not found"));

        return bookEntity;
    }

    @Override
    public List<BookEntity> findAll() {
        List<BookEntity> bookEntityList;
        bookEntityList = storage.getBookEntityList();

        return bookEntityList;
    }

    @Override
    public void delete(@NotNull Long id) {
        BookEntity bookEntity;
        bookEntity = findById(id);

        if (Objects.equals(bookEntity.getUserId(), null)) {
            storage.getBookEntityList().removeIf(book -> id.equals(book.getId()));
        } else {
            UserEntity userEntity = storage.getUserEntityList()
                    .stream()
                    .filter(Objects::nonNull)
                    .filter(user -> user.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundException("user with id " + id + " not found"));

            userEntity.getBookList().removeIf(book -> id.equals(book.getId()));
            storage.getBookEntityList().removeIf(book -> id.equals(book.getId()));
        }
    }
}
