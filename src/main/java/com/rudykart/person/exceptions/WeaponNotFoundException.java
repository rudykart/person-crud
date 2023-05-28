package com.rudykart.person.exceptions;

public class WeaponNotFoundException extends RuntimeException{
    // private static final long serialVerisionUID = 2;
    public WeaponNotFoundException(String message) {
        super(message);
    }
}
