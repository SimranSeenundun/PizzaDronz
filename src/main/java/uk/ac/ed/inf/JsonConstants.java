package uk.ac.ed.inf;

public enum JsonConstants {
    NAME("name"),
    MENU("menu"),
    PRICE("priceInPence"),
    LONGITUDE("longitude"),
    LATITUDE("latitude");

    public final String label;

    JsonConstants(String label) {
        this.label = label;
    }
}
