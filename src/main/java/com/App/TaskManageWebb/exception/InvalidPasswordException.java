package com.App.TaskManageWebb.exception;

public class InvalidPasswordException extends RuntimeException{
    public InvalidPasswordException(String message){
        super(message);// This calls RuntimeException's constructor
    }
}
