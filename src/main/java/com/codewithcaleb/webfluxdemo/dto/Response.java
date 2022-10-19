package com.codewithcaleb.webfluxdemo.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString //prints a human readable format of the class when its instantiated
public class Response {

    //instantiated the date giving it a Value
    private Date date = new Date();
    private int output;


    public Response(int output) {
        this.output = output;
    }
}
