package com.wYne.automation.exceptions;

@SuppressWarnings("serial")
public class WyneException extends RuntimeException{

    public WyneException(String message){
        super(message);
        printStackTrace();
    }
    public WyneException(Exception e){
        super(e);
        printStackTrace();
    }
}
