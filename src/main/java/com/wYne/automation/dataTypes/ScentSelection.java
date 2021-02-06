package com.wYne.automation.dataTypes;

public enum ScentSelection {
    LEATHER("leather and tobacco, cigar box, smoke, tea"), FRESHLYBAKED("freshly baked bread with butter, toasted almonds"),PETRICHOR("petrichor: earthy scent when rain falls on dry ground");
    String name;

    /**
     * Constructor
     *
     * @param name value for the enum
     */
    ScentSelection(String name) {
        this.name = name;
    }


    /**
     * Return int for the enum
     *
     * @return int value
     */
    public String getValue() {
        return this.name;
    }
}
