package com.levelup.library.exceptions;

public class EmailInUseException extends RuntimeException {
    public EmailInUseException(String email){
        super("Email " + email + " has been in use by another user, please change another.");
    }
}
