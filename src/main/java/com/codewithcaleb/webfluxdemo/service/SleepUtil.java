package com.codewithcaleb.webfluxdemo.service;

public class SleepUtil {

    //It is important to remember that every request and response in Java Works in a thread
    //We can therefore slow the currently executing thread so that the request takes time
    public static void sleepSeconds(int seconds){
        try {
            Thread.sleep(seconds* 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
