package com.edu.ulab.app.storage;

import com.edu.ulab.app.entity.book.BookEntity;
import com.edu.ulab.app.entity.user.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class Storage {

    private Map<Long, UserEntity> userEntityMap = new HashMap<>();

    private Map<Long, BookEntity> bookEntityMap = new HashMap<>();

    public Map<Long, UserEntity> getUserEntityMap() {
        log.info("Get user data from storage");
        return userEntityMap;
    }

    public void setUserEntityMap(Map<Long, UserEntity> userEntityMap) {
        this.userEntityMap = userEntityMap;
    }

    public Map<Long, BookEntity> getBookEntityMap() {
        log.info("Get book data from storage");
        return bookEntityMap;
    }

    public void setBookEntityMap(Map<Long, BookEntity> bookEntityMap) {
        this.bookEntityMap = bookEntityMap;
    }
}


