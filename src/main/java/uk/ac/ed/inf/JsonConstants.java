package uk.ac.ed.inf;

/**
 * Contains all the string constants for JSON keys from the REST-server.
 */
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
