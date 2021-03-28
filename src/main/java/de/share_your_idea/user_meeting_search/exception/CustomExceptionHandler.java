package de.share_your_idea.user_meeting_search.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = CustomNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(CustomNotFoundException customNotFoundException) {
        CustomException customException =
                new CustomException(customNotFoundException.getMessage(), HttpStatus.NOT_FOUND, ZonedDateTime.now());
        return new ResponseEntity<>(customException, HttpStatus.BAD_REQUEST);
    }
}
