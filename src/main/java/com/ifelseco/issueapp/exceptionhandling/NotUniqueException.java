package com.ifelseco.issueapp.exceptionhandling;

public class NotUniqueException extends IllegalArgumentException {
    public static final String MESSAGE =" has already registered! \n";
    public NotUniqueException(String message){
        super(message + MESSAGE);
    }
}
