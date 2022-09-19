package com.edu.ulab.app.storage;

import com.edu.ulab.app.entity.book.BookEntity;
import com.edu.ulab.app.entity.user.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class Storage {

    private List<UserEntity> userEntityList = new ArrayList<>();
    private List<BookEntity> bookEntityList = new ArrayList<>();

    public List<UserEntity> getUserEntityList() {
        return userEntityList;
    }

    public void setUserEntityList(List<UserEntity> userEntityList) {
        this.userEntityList = userEntityList;
    }

    public List<BookEntity> getBookEntityList() {
        return bookEntityList;
    }

    public void setBookEntityList(List<BookEntity> bookEntityList) {
        this.bookEntityList = bookEntityList;
    }

    public void printData() { //TODO test usage only [remove]
        System.out.println();
        userEntityList.forEach(element -> System.out.println("REPO: USER " + element));
        System.out.println();
        bookEntityList.forEach(element -> System.out.println("REPO: BOOK " + element));
        System.out.println();
    }

}


