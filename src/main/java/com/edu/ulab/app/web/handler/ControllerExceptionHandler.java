package com.edu.ulab.app.web.handler;

import com.edu.ulab.app.exception.BookNotFoundException;
import com.edu.ulab.app.exception.BookNotSavedException;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.exception.UserNotFoundException;
import com.edu.ulab.app.exception.UserNotSavedException;
import com.edu.ulab.app.web.response.BaseWebResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<BaseWebResponse> handleNotFoundExceptionException(@NonNull final NotFoundException exc) {
        log.error(exc.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BaseWebResponse(createErrorMessage(exc)));
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<BaseWebResponse> handleBookNotFoundException(@NonNull final BookNotFoundException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new BaseWebResponse(createErrorMessage(e)));
    }

    @ExceptionHandler(BookNotSavedException.class)
    public ResponseEntity<BaseWebResponse> handleBookNotSavedException(@NonNull final BookNotSavedException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new BaseWebResponse(createErrorMessage(e)));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<BaseWebResponse> handleUserNotFoundException(@NonNull final UserNotFoundException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new BaseWebResponse(createErrorMessage(e)));
    }

    @ExceptionHandler(UserNotSavedException.class)
    public ResponseEntity<BaseWebResponse> handleUserNotSavedException(@NonNull final UserNotSavedException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new BaseWebResponse(createErrorMessage(e)));
    }

    private String createErrorMessage(Exception exception) {
        final String message = exception.getMessage();
        log.error(ExceptionHandlerUtils.buildErrorMessage(exception));
        return message;
    }
}
