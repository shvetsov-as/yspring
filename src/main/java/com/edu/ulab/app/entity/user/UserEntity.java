package com.edu.ulab.app.entity.user;

import com.edu.ulab.app.entity.book.BookEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Data
public class UserEntity {

    private static final AtomicLong sequence = new AtomicLong();
    private final Long id;

    private String fullName;
    private String title;
    private int age;
    private List<BookEntity> bookList = new ArrayList<>();

    private static Long next() {
        return sequence.incrementAndGet();
    }

    public UserEntity() {
        id = next();
    }

    public UserEntity(String fullName, String title, int age) {
        this();
        this.fullName = fullName;
        this.title = title;
        this.age = age;
    }

    public UserEntity(String fullName, String title, int age, List<BookEntity> bookList) {
        this();
        this.fullName = fullName;
        this.title = title;
        this.age = age;
        this.bookList = bookList;
    }
}


