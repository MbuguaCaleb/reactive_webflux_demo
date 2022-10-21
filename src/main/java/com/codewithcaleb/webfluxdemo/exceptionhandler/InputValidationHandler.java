package com.codewithcaleb.webfluxdemo.exceptionhandler;

import com.codewithcaleb.webfluxdemo.dto.InputFailedValidationResponse;
import com.codewithcaleb.webfluxdemo.exceptions.InputValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


//It's a way of Globally Handling my Exceptions
//Makes My Code cleaner
//What do i do when i have an exception handler...
//I expect to Log My Exceptions

//Rest controller advice can be able to listen to Exceptions thrown inside my Controller
@RestControllerAdvice
public class InputValidationHandler {

    //The controller can throw different kinds of exceptions and therefore i should have a way
    //of handling each
    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<InputFailedValidationResponse> handleException(InputValidationException ex){
        InputFailedValidationResponse response = new InputFailedValidationResponse();
        response.setErrorCode(ex.getErrorCode());
        response.setInput(ex.getInput());
        response.setMessage(ex.getMessage());
        return ResponseEntity.badRequest().body(response);

    }
}
