package com.wYne.automation.exceptions;

import static com.sun.corba.se.impl.util.Utility.printStackTrace;

/**
 * Created by gaian on 25/7/16.
 */
public class PageNotFoundException extends WyneException {
    public PageNotFoundException(String message){
        super(message);
        printStackTrace();
    }
}
