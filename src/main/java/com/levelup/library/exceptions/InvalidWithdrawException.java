package com.levelup.library.exceptions;

public class InvalidWithdrawException extends RuntimeException{
    public InvalidWithdrawException(String message){
        super(message);
    }
}
