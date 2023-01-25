package com.levelup.library.exceptions;

public class InvalidCpfException extends RuntimeException {
    public InvalidCpfException(String cpf){
        super("CPF " + cpf + " are not valid!");
    }
}
