package com.levelup.library.exceptions;

public class InvalidDateException extends RuntimeException{
    public InvalidDateException(String date){
        super("Date " + date + " is a invalid date.");
    }
}
