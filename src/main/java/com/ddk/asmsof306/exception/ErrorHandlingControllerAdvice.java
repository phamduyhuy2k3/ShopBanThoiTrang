package com.ddk.asmsof306.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
class ErrorHandlingControllerAdvice {

//    @ExceptionHandler(ConstraintViolationException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    ValidationErrorResponse onConstraintValidationException(
//            ConstraintViolationException e) {
//        ValidationErrorResponse error = new ValidationErrorResponse();
//        for (ConstraintViolation violation : e.getConstraintViolations()) {
//            error.getViolations().add(
//                    new Violation(violation.getPropertyPath().toString(), violation.getMessage()));
//        }
//        return error;
//    }
@ExceptionHandler({ConstraintViolationException.class,MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("timestamp", new Date());
        responseBody.put("status", status.value());
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();

            if (responseBody.containsKey(fieldName) && !String.valueOf(responseBody.get(fieldName)).contains(errorMessage)) {
                responseBody.put(fieldName, String.format("%s, %s", responseBody.get(fieldName), errorMessage));
            } else {
                responseBody.put(fieldName, errorMessage);
            }
        }
//        List<String> errors = ex.getBindingResult().getFieldErrors()
//                .stream()
//                .map(x -> x.getDefaultMessage())
//                .collect(Collectors.toList());
//
//        responseBody.put("errors", errors);

        return new ResponseEntity<>(responseBody, headers, status);
    }
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    Map<String, String> onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
//        Map<String, String> errors = new HashMap<>();
//
//        for (FieldError error : e.getBindingResult().getFieldErrors()) {
//            String fieldName = error.getField();
//            String errorMessage = error.getDefaultMessage();
//
//            if (errors.containsKey(fieldName) && !errors.get(fieldName).contains(errorMessage)) {
//                errors.put(fieldName, String.format("%s, %s", errors.get(fieldName), errorMessage));
//            } else {
//                errors.put(fieldName, errorMessage);
//            }
//        }
//
//        return errors;
//    }

}
