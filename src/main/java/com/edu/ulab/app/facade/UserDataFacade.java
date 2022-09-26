package com.edu.ulab.app.facade;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.web.request.UserBookRequest;
import com.edu.ulab.app.web.response.UserBookResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class UserDataFacade {

    private final UserService userService;
    private final BookService bookService;
    private final UserMapper userMapper;
    private final BookMapper bookMapper;

    public UserDataFacade(UserService userService,
                          BookService bookService,
                          UserMapper userMapper,
                          BookMapper bookMapper) {
        this.userService = userService;
        this.bookService = bookService;
        this.userMapper = userMapper;
        this.bookMapper = bookMapper;
    }

    public UserBookResponse createUserWithBooks(UserBookRequest userBookRequest) {

        log.info("Got user book create request: {}", userBookRequest);
        UserDto userDto = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
        log.info("Mapped user request: {}", userDto);
        UserDto createdUser = userService.createUser(userDto);
        log.info("Created user: {}", createdUser);
        userBookRequest.getBookRequests().forEach(book -> book.setUserId(createdUser.getId()));

        List<Long> bookIdList = userBookRequest.getBookRequests()
                .stream()
                .filter(Objects::nonNull)
                .map(bookMapper::bookRequestToBookDto)
                .peek(bookDto -> bookDto.setUserId(createdUser.getId()))
                .peek(mappedBookDto -> log.info("mapped book: {}", mappedBookDto))
                .map(bookService::createBook)
                .peek(createdBook -> log.info("Created book: {}", createdBook))
                .map(BookDto::getId)
                .toList();
        log.info("Collected book ids: {}", bookIdList);

        return UserBookResponse.builder()
                .userId(createdUser.getId())
                .booksIdList(bookIdList)
                .build();
    }

    public UserBookResponse updateUserWithBooks(UserBookRequest userBookRequest, Long userId) {

        log.info("Update user with books request: {}. User Id: {}", userBookRequest, userId);
        UserDto userDto = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
        log.info("Mapped user request: {}", userDto);
        userBookRequest.getBookRequests().forEach(book -> book.setUserId(userId));

        List<BookDto> bookDtoList = userBookRequest.getBookRequests()
                .stream()
                .filter(Objects::nonNull)
                .map(bookMapper::bookRequestToBookDto)
                .peek(createdBookDto -> log.info("Created book: {}", createdBookDto))
                .toList();

        userDto.setId(userId);
        log.info("userDto set user id: {}", userId);
        userService.updateUser(userDto);
        log.info("userService.updateUser. userDto: {}", userDto);
        bookService.updateAllBooksByUserId(bookDtoList, userId);
        log.info("bookService.updateAllBooks. userDto {}, user Id {}", userDto, userId);

        List<Long> bookIdList = bookService.getAllBooksByUserId(userDto.getId())
                .stream()
                .filter(Objects::nonNull)
                .map(BookDto::getId)
                .toList();
        log.info("Updated book ids: {}", bookIdList);

        return UserBookResponse.builder()
                .userId(userDto.getId())
                .booksIdList(bookIdList)
                .build();
    }

    public UserBookResponse getUserWithBooks(Long userId) {
        log.info("get User with books request. User id: {}", userId);
        UserDto createdUser = userService.getUserById(userId);
        log.info("get User by Id from storage: {}", createdUser);
        List<Long> bookIdList = bookService.getAllBooksByUserId(userId)
                .stream()
                .filter(Objects::nonNull)
                .map(BookDto::getId)
                .toList();
        log.info("Found book ids: {}", bookIdList);

        return UserBookResponse.builder()
                .userId(createdUser.getId())
                .booksIdList(bookIdList)
                .build();
    }

    public void deleteUserWithBooks(Long userId) {
        log.info("delete User with books. User id: {}", userId);
        userService.deleteUserById(userId);
        log.info("userService user-delete-request successful");
        bookService.deleteAllUserBooksByUserId(userId);
        log.info("bookService book-delete-request successful");
    }
}
