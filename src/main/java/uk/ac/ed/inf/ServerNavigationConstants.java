package uk.ac.ed.inf;

public enum ServerNavigationConstants {
    ILP_SERVER_URL("https://ilp-rest.azurewebsites.net/"),
    CENTRAL_AREA("centralarea"),
    NO_FLY_ZONES("noFlyZones"),
    ORDERS("orders"),
    RESTAURANTS("restaurants");

    public final String label;

    ServerNavigationConstants(String label) {
        this.label = label;
    }

}