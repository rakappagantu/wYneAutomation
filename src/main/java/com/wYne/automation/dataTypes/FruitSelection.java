package com.wYne.automation.dataTypes;

public enum FruitSelection {

    LEMON("Lemon"), ORANGE("Oranage"), LIME("Lime"), GRAPEFRUIT("GrapeFruit"), APPLE("Apple"), PEAR("Pear"),PEACH("Peach"),NECTARINE("Nectarine"),APRICOT("Apricot"),CANTALOUPE("Cantaloupe"),HONEYDEW("Honeydew"),PINEAPPLE("Pineapple"),GUAVA("Guava"),LYCHEE("Lychee"),MANGO("Mango"),PASSIONFRUIT("Passionfruit"),WATERMELON("Watermelon"),STRAWBERRY("Strawberry"),CRANBERRY("Cranberry"),POMEGRANATE("Pomegranate"),CHERRY("Cherry"),RASPBERRY("Raspberry"),BLACKPLUM("Black Plum"),BLACKBERRY("Blackberry"),BLUEBERRY("Blueberry"),BLACKCHERRY("Black Cherry"),OLIVE("Olive"),PRUNE("Prune"),RAISIN("Raisin");
    String name;

    /**
     * Constructor
     *
     * @param name value for the enum
     */
    FruitSelection(String name) {
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
