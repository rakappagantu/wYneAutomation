package com.wYne.automation.dataTypes;

public enum SelectTypeOfWine {
    SPARKLING("sparkling"), WHITE("white"),ROSE("rose"),RED("red"),DESSERT("dessert");
    String name;

    /**
     * Constructor
     *
     * @param name value for the enum
     */
    SelectTypeOfWine(String name) {
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
