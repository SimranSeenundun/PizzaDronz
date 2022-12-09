package uk.ac.ed.inf;

/**
 * Contains all the string constants for JSON keys from the REST-server.
 */
public enum JsonConstants {
    NAME("name"),
    MENU("menu"),
    PRICE("priceInPence"),
    PRICE_TOTAL("priceTotalInPence"),
    LONGITUDE("longitude"),
    LATITUDE("latitude"),
    COORDINATES("coordinates"),
    ORDER_NUMBER("orderNo"),
    ORDER_DATE("orderDate"),
    CUSTOMER_NAME("customer"),
    CARD_NUMBER("creditCardNumber"),
    CARD_EXPIRY("creditCardExpiry"),
    CARD_CVV("cvv"),
    ORDER_ITEMS("orderItems"),
    OUTCOME("outcome"),
    FROM_LONGITUDE("fromLongitude"),
    FROM_LATITUDE("fromLatitude"),
    TO_LONGITUDE("toLongitude"),
    TO_LATITUDE("toLatitude"),
    ANGLE("angle"),
    TICKS_SINCE_START("ticksSinceStartOfCalculation");




    public final String label;

    JsonConstants(String label) {
        this.label = label;
    }
}
