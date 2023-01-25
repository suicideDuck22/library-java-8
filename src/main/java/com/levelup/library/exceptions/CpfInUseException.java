package com.levelup.library.exceptions;

public class CpfInUseException extends RuntimeException {
    public CpfInUseException(String cpf){
        super("CPF " + cpf + " has been in use by another person.");
    }
}
