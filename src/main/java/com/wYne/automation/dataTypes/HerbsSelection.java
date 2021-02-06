package com.wYne.automation.dataTypes;

public enum HerbsSelection {

    DIL("dill, basil, cilantro, tarragon, mint, parsley"), ANISE("anise, cardamom, 5-spice, ginger, sichuan, saffron, cumin, turmeric"), HONEY("honey, honeycomb"), CLOVE("clove, allspice, cinnamon, nutmeg, vanilla"), ANCHO("ancho, aleppo, chipotle, chilli"), ROSEMARY("rosemary, thyme, oregano, sage, lavender, marjoram");

    String name;

    /**
     * Constructor
     *
     * @param name value for the enum
     */
    HerbsSelection(String name) {
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
