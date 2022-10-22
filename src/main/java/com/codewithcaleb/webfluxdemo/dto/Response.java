package com.codewithcaleb.webfluxdemo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@NoArgsConstructor
public class Response {

    //instantiated the date giving it a Value
    private Date date = new Date();
    private int output;


    public Response(int output) {
        this.output = output;
    }
}
