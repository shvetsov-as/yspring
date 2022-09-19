package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.book.BookEntity;
import com.edu.ulab.app.mapper.BookEntityMapper;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.storage.StorageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    private final StorageUtils<BookEntity, Long> storageUtils;
    private final BookEntityMapper bookEntityMapper;

    public BookServiceImpl(
            @Qualifier("bookStorageUtilsImpl") StorageUtils<BookEntity, Long> storageUtils,
            BookEntityMapper bookEntityMapper) {

        this.storageUtils = storageUtils;
        this.bookEntityMapper = bookEntityMapper;
    }

    @Override
    public BookDto createBook(BookDto bookDto) {

        BookEntity bookEntity = bookEntityMapper.bookDtoToBookEntity(bookDto);
        bookEntity.setUserId(bookDto.getUserId());
        storageUtils.save(bookEntity);

        log.info("bookEntity saved: {}", bookEntity);

        bookDto = bookEntityMapper.bookEntityToBookDto(bookEntity);

        return bookDto;
    }

    @Override
    public BookDto updateBook(BookDto bookDto, Long userId) {
        BookEntity entity = storageUtils.findById(bookDto.getId());
        if (!bookDto.getId().equals(userId)) {
            entity.setUserId(userId);
        }
        if (null != bookDto.getTitle()) {
            entity.setTitle(bookDto.getTitle());
        }
        if (null != bookDto.getAuthor()) {
            entity.setAuthor(bookDto.getAuthor());
        }
        if (0L != bookDto.getPageCount()) {
            entity.setPageCount(bookDto.getPageCount());
        }
        return bookEntityMapper.bookEntityToBookDto(entity);
    }

    @Override
    public List<BookDto> updateAllBooksByUserId(List<BookDto> bookDtoList, Long userId) {
        List<BookEntity> entityList = storageUtils.findAll()
                .stream()
                .filter(Objects::nonNull)
                .filter(book -> userId.equals(book.getUserId()))
                .toList();

        return entityList.stream()
                .map(entity -> updateBook(bookEntityMapper.bookEntityToBookDto(entity), entity.getUserId()))
                .toList();
    }

    @Override
    public BookDto getBookById(Long id) {
        BookEntity entity = storageUtils.findById(id);
        return bookEntityMapper.bookEntityToBookDto(entity);
    }

    @Override
    public List<BookDto> getAllBooksByUserId(Long userId) {
        List<BookEntity> entityList = storageUtils.findAll();

        return entityList.stream()
                .filter(Objects::nonNull)
                .filter(book -> userId.equals(book.getUserId()))
                .map(bookEntityMapper::bookEntityToBookDto)
                .toList();
    }

    @Override
    public void deleteBookById(Long id) {
        BookEntity entity = storageUtils.findById(id);
        storageUtils.delete(entity.getUserId());
    }

    @Override
    public void deleteAllUserBooksByUserId(Long id) {
        List<BookEntity> entityList = storageUtils.findAll();
        entityList.removeIf(book -> id.equals(book.getUserId()));
    }
}
