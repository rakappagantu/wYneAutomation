package com.wYne.automation.dataTypes;

public enum NewFlowerSelection {

    ROSE("rose, violet, lavender, potpourri"), HIBISCUS("hibiscus, lilac, peony");
    String name;

    /**
     * Constructor
     *
     * @param name value for the enum
     */
    NewFlowerSelection(String name) {
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
