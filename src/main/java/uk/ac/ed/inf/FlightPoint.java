package uk.ac.ed.inf;

public class FlightPoint {
    private LngLat lngLatCoordinates;
    private FlightPoint nextPoint;
    private double angle;

    public FlightPoint() {
        lngLatCoordinates = new LngLat(0, 0);
        nextPoint = null;
        angle = -1;
    }

    public FlightPoint(LngLat lngLatCoordinates){
        this.lngLatCoordinates = lngLatCoordinates;
        nextPoint = null;
        angle = -1;
    }

    public void pointsTo(FlightPoint nextPoint, double angle){
        this.nextPoint = nextPoint;
        this.angle = angle;
    }

    public FlightPoint getNextPoint() {
        return nextPoint;
    }

    public double getAngle() {
        return angle;
    }

    public double getLng(){
        return lngLatCoordinates.lng();
    }

    public double getLat(){
        return lngLatCoordinates.lat();
    }
}
