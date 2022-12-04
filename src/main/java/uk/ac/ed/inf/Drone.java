package uk.ac.ed.inf;

import static uk.ac.ed.inf.State.*;

public class Drone {
    private final LngLat location;
    private final State state;
    private final Order currOrder;
    private final Battery battery;
    private final Boolean isHoldingOrder;

    public Drone() {
        location = null;
        state = HOVER;
        currOrder = null;
        battery = new Battery();
        isHoldingOrder = false;
    }
}
