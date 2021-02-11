package com.wYne.automation.dataTypes;

public enum SelectWynePartner {
    ITALICAPALOALTO("italico-palo-alto"), PIZZAPALOALTO("terun-pizza-palo-alto"),ROOHPALOALTO("rooh-palo-alto"),TASTINGEFFICACY("tasting-efficacy");
    String name;

    /**
     * Constructor
     *
     * @param name value for the enum
     */
    SelectWynePartner(String name) {
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
