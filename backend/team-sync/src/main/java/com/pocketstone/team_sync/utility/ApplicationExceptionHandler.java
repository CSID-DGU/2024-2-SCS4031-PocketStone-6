package com.pocketstone.team_sync.utility;


import com.pocketstone.team_sync.exception.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({UnauthorizedAccessException.class, CredentialsInvalidException.class})
    public ResponseEntity<Object> handleUnauthorizedAccessException(RuntimeException ex) {
        ErrorResponse error = new ErrorResponse(Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler({CredentialsNotFoundException.class, ProjectNotFoundException.class, CharterNotFoundException.class, CharterPdfNotFoundException.class, UserNotFoundException.class, DataNotFoundException.class})
    public ResponseEntity<Object> NotFoundException(RuntimeException ex) {
        ErrorResponse error = new ErrorResponse(Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({CharterAlreadyExistsException.class, CharterPdfAlreadyExistsException.class, LoginIdAlreadyExistsException.class, EmailAlreadyExistsException.class, ExceededWorkloadException.class})
    public ResponseEntity<Object> AlreadyExistsException(RuntimeException ex) {
        ErrorResponse error = new ErrorResponse(Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CharterUploadException.class)
    public ResponseEntity<Object> handleCharterUploadException(CharterUploadException ex) {
        ErrorResponse error = new ErrorResponse(Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //RequestBody 에 대한 @Validation 예외 처리
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        BindingResult result = ex.getBindingResult();
        ErrorResponse error = new ErrorResponse(result.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    //Entity에 대한 @Validation 예외 처리
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        ErrorResponse error = new ErrorResponse(violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}



