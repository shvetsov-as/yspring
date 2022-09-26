package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.book.BookEntity;
import com.edu.ulab.app.exception.BookNotFoundException;
import com.edu.ulab.app.mapper.BookEntityMapper;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.storage.StorageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    private final StorageUtils<BookEntity, Long> storageUtils;
    private final BookEntityMapper bookEntityMapper;

    public BookServiceImpl(
            @Qualifier("bookStorageUtilsImpl") StorageUtils<BookEntity,
                    Long> storageUtils,
                     BookEntityMapper bookEntityMapper) {

        this.storageUtils = storageUtils;
        this.bookEntityMapper = bookEntityMapper;
    }

    @Override
    public BookDto createBook(@NotNull BookDto bookDto) {

        BookEntity bookEntity = bookEntityMapper.bookDtoToBookEntity(bookDto);
        bookEntity.setUserId(bookDto.getUserId());
        storageUtils.save(bookEntity);

        bookDto = bookEntityMapper.bookEntityToBookDto(bookEntity);
        log.info("BookService create bookDto {}", bookDto);
        return bookDto;
    }

    @Override
    public BookDto updateBook(@NotNull BookDto bookDto, @NotNull Long bookId) {
        BookEntity entity = storageUtils.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        ;
        if (null != bookDto.getUserId()) {
            entity.setUserId(bookDto.getUserId());
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
        log.info("Updated book: {}", entity);
        return bookEntityMapper.bookEntityToBookDto(entity);
    }

    @Override
    public List<BookDto> updateAllBooksByUserId(@NotNull List<BookDto> bookDtoList, @NotNull Long userId) {
        bookDtoList.forEach(book -> updateBook(book, book.getId()));
        log.info("BookService update All {}", bookDtoList);
        return bookDtoList;
    }

    @Override
    public BookDto getBookById(@NotNull Long bookId) {
        BookEntity entity = storageUtils.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        log.info("BookService get Book By Id {}", entity);
        return bookEntityMapper.bookEntityToBookDto(entity);
    }

    @Override
    public List<BookDto> getAllBooksByUserId(@NotNull Long userId) {
        List<BookEntity> entityList = storageUtils.findAll();
        log.info("BookService get All Books {}", entityList);
        return entityList.stream()
                .filter(Objects::nonNull)
                .filter(book -> userId.equals(book.getUserId()))
                .map(bookEntityMapper::bookEntityToBookDto)
                .toList();
    }

    @Override
    public void deleteBookById(@NotNull Long bookId) {
        BookEntity entity = storageUtils.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        log.info("BookService delete Book By Id {}", entity);
        storageUtils.delete(entity.getUserId());
    }

    @Override
    public void deleteAllUserBooksByUserId(@NotNull Long userId) {
        List<BookEntity> entityList = storageUtils.findAll();
        log.info("BookService delete All User Books By User Id {}", entityList);
        entityList.removeIf(book -> userId.equals(book.getUserId()));
    }
}
