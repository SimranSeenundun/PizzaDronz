package uk.ac.ed.inf;
/**
 * URL and navigational constants for the REST-server.
 */

public enum ServerNavigationConstants {
    CENTRAL_AREA("centralarea/"),
    NO_FLY_ZONES("noFlyZones/"),
    ORDERS("orders/"),
    RESTAURANTS("restaurants/");

    public final String label;

    ServerNavigationConstants(String label) {
        this.label = label;
    }

}
