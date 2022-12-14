package com.codewithcaleb.webfluxdemo.exceptions;

import lombok.Data;

@Data
public class InputValidationException extends RuntimeException{

    private static final String MSG="allowed range is 10-20";
    private  final int errorCode =100;
    private final int input;

    //It will override the constructor of the Parent Class with a message
    public InputValidationException(int input) {
        super(MSG);
        this.input = input;
    }
}
