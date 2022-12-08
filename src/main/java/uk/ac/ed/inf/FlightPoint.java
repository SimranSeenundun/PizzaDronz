package uk.ac.ed.inf;

import java.sql.Time;
import java.time.LocalDateTime;

public class FlightPoint {
    private LngLat lngLatCoordinates;
    private FlightPoint fromPoint;
    private FlightDirection fromAngle;
    private FlightPoint nextPoint;
    private Order order;
    private LocalDateTime timeExecuted;

    public FlightPoint() {
        lngLatCoordinates = new LngLat(0, 0);
        fromPoint = null;
        fromAngle = null;
        order = null;
    }

    public FlightPoint(LngLat lngLatCoordinates){
        this.lngLatCoordinates = lngLatCoordinates;
        fromPoint = null;
        fromAngle = null;
        order = null;
    }

    public FlightPoint(LngLat lngLatCoordinates, FlightPoint fromPoint, FlightDirection fromAngle){
        this.lngLatCoordinates = lngLatCoordinates;
        this.fromPoint = fromPoint;
        this.fromAngle = fromAngle;
        order = null;
    }


    public FlightPoint getFromPoint() {
        return fromPoint;
    }

    public FlightPoint getNextPoint() {
        return nextPoint;
    }

    public void setNextPoint(FlightPoint nextPoint) {
        this.nextPoint = nextPoint;
    }

    public void setOrderNumber(Order order) {
        this.order = order;
    }

    public void setTimeExecuted(LocalDateTime timeExecuted) {
        this.timeExecuted = timeExecuted;
    }

    public FlightDirection getFromAngle() {
        return fromAngle;
    }

    public double getLng(){
        return lngLatCoordinates.lng();
    }

    public double getLat(){
        return lngLatCoordinates.lat();
    }

    public LngLat getLngLat(){
        return lngLatCoordinates;
    }

    public boolean closeTo(FlightPoint flightPoint){
        return lngLatCoordinates.closeTo(flightPoint.getLngLat());
    }

    public boolean closeTo(LngLat lngLat){
        return lngLatCoordinates.closeTo(lngLat);
    }
    public boolean equals(FlightPoint flightPoint) {
        return flightPoint.getLngLat().equals(lngLatCoordinates);
    }
}
