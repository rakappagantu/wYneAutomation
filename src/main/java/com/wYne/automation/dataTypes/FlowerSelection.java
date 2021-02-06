package com.wYne.automation.dataTypes;

public enum FlowerSelection {
    JASMINE("jasmine, apple blossom, chamomile"), Honeysuckle("honeysuckle, orange blossom, elderflower");
    String name;

    /**
     * Constructor
     *
     * @param name value for the enum
     */
    FlowerSelection(String name) {
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
