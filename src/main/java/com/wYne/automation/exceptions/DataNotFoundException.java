package com.wYne.automation.exceptions;

public class DataNotFoundException  extends WyneException{
    public DataNotFoundException(String message){
        super(message);
        printStackTrace();
    }
}
