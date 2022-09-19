package com.edu.ulab.app.entity.user;

import com.edu.ulab.app.entity.book.BookEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

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

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<BookEntity> getBookList() {
        return bookList;
    }

    public void setBookList(List<BookEntity> bookList) {
        this.bookList = bookList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity)) return false;

        UserEntity that = (UserEntity) o;

        if (!id.equals(that.id)) return false;
        return getFullName().equals(that.getFullName());
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + getFullName().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", title='" + title + '\'' +
                ", age=" + age +
                ", bookList=" + bookList +
                '}';
    }
}

