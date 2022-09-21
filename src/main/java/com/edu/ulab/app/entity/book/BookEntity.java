package com.edu.ulab.app.entity.book;

import lombok.Data;

import java.util.concurrent.atomic.AtomicLong;

@Data
public class BookEntity {

    private static final AtomicLong sequence = new AtomicLong();
    private final Long id;

    private Long userId;
    private String title;
    private String author;
    private long pageCount;

    private static Long next() {
        return sequence.incrementAndGet();
    }

    public BookEntity() {
        id = next();
    }


    public BookEntity(Long userId, String title, String author, long pageCount) {
        this();
        this.userId = userId;
        this.title = title;
        this.author = author;
        this.pageCount = pageCount;
    }

    public BookEntity(String title, String author, long pageCount) {
        this();
        this.title = title;
        this.author = author;
        this.pageCount = pageCount;
    }
}

