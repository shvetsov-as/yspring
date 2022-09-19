package com.edu.ulab.app.entity.book;

import java.util.concurrent.atomic.AtomicLong;


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

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookEntity)) return false;

        BookEntity that = (BookEntity) o;

        if (getPageCount() != that.getPageCount()) return false;
        if (!getId().equals(that.getId())) return false;
        if (getUserId() != null ? !getUserId().equals(that.getUserId()) : that.getUserId() != null) return false;
        if (!getTitle().equals(that.getTitle())) return false;
        return getAuthor() != null ? getAuthor().equals(that.getAuthor()) : that.getAuthor() == null;
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + (getUserId() != null ? getUserId().hashCode() : 0);
        result = 31 * result + getTitle().hashCode();
        result = 31 * result + (getAuthor() != null ? getAuthor().hashCode() : 0);
        result = 31 * result + (int) (getPageCount() ^ (getPageCount() >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", pageCount=" + pageCount +
                '}';
    }
}
