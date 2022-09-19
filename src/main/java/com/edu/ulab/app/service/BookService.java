package com.edu.ulab.app.service;


import com.edu.ulab.app.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto createBook(BookDto userDto);

    BookDto updateBook(BookDto userDto, Long userId);

    List<BookDto> updateAllBooksByUserId(List<BookDto> bookDtoList, Long userId);

    BookDto getBookById(Long id);

    List<BookDto> getAllBooksByUserId(Long userId);

    void deleteBookById(Long id);

    void deleteAllUserBooksByUserId(Long id);
}
