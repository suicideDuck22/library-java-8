package com.levelup.library.exceptions;

public class NoSuchIdException extends RuntimeException {
    public NoSuchIdException(){
        super("ID must be provided.");
    }
}
