package com.rudykart.person.exceptions;

public class PersonNotFoundException extends RuntimeException{
    // private static final long serialVerisionUID = 1;

    public PersonNotFoundException(String message) {
        super(message);
    }
}
