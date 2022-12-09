package uk.ac.ed.inf;

/**
 * 16 Possible flight directions and their values in degrees - starts from east as 0 and goes round anticlockwise
 */
public enum FlightDirection {
    North(90),
    NorthNorthEast(67.5),
    NorthEast(45),
    EastNorthEast(22.5),
    East(0),
    EastSouthEast(337.5),
    SouthEast(315),
    SouthSouthEast(292.5),
    South(270),
    SouthSouthWest(247.5),
    SouthWest(225),
    WestSouthWest(202.5),
    West(180),
    WestNorthWest(157.5),
    NorthWest(135),
    NorthNorthWest(112.5);

    public final double label;
    FlightDirection(double label) {
        this.label = label;
    }
}
