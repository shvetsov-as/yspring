package com.edu.ulab.app.mapper;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.book.BookEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookEntityMapper {

    BookEntity bookDtoToBookEntity(BookDto bookDto);

    BookDto bookEntityToBookDto(BookEntity bookEntity);
}
