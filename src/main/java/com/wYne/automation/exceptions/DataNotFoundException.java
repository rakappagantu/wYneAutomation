package com.wYne.automation.exceptions;

public class DataNotFoundException  extends SLException{
    public DataNotFoundException(String message){
        super(message);
        printStackTrace();
    }
}
