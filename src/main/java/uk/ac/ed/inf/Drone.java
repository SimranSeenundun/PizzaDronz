package uk.ac.ed.inf;

import org.javatuples.Pair;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static uk.ac.ed.inf.State.*;
import static uk.ac.ed.inf.OrderOutcome.*;

public class Drone {
    private LngLat location;
    private FlightPoint currFlightPoint;
    private State state;
    private Order currOrder;
    private Battery battery;
    private Boolean isHoldingOrder;

    public Drone() {
        location = null;
        state = HOVER;
        currOrder = null;
        battery = new Battery();
        isHoldingOrder = false;
        currFlightPoint = null;
    }

    public Drone(LngLat location) {
        this.location = location;
        state = HOVER;
        currOrder = null;
        battery = new Battery();
        isHoldingOrder = false;
        currFlightPoint = new FlightPoint(location);
    }

    public void flyTo(LngLat destination) throws BatteryOutOfChargeException{
        state = FLYING;
        LinkedList<FlightPoint> flightPath = generateFlightPath(location, destination);
        currFlightPoint.setNextPoint(flightPath.getFirst());

        for(FlightPoint flightPoint: flightPath){
            location = flightPoint.getLngLat();
            flightPoint.setOrderNumber(currOrder);
            flightPoint.setTimeExecuted(LocalDateTime.now());
            battery.decrementCharge();
        }
    }

    public void hover() throws BatteryOutOfChargeException{
        state = HOVER;
        FlightPoint hoverPoint = new FlightPoint(currFlightPoint.getLngLat(), currFlightPoint, null);
        currFlightPoint.setNextPoint(hoverPoint);
        currFlightPoint = hoverPoint;
        battery.decrementCharge();
    }

    public void pickupOrder(){
        if (currOrder.getOrderOutcome() == null && currOrder != null && state == HOVER){
            isHoldingOrder = true;
        }
    }

    public void dropOffOrder(){
        if (!isHoldingOrder){
            currOrder.setOrderOutcome(ValidButNotDelivered);
        } else if (state == HOVER){
            isHoldingOrder = false;
            currOrder = null;
        }
    }

    /**
     * Generates a flight path from one LngLat point to another using greedy search algorithm
     * @param from the initial location
     * @param to the goal location
     */
    public static LinkedList<FlightPoint> generateFlightPath(LngLat from, LngLat to){
        LinkedList<Pair<FlightPoint, Double>> fringe = new LinkedList<>();
        LinkedList<FlightPoint> explored = new LinkedList<>();
        FlightPoint currentPoint = new FlightPoint(from);
        explored.add(currentPoint);

        while (!currentPoint.closeTo(to)){
            LngLat currLngLat = currentPoint.getLngLat();

            // Explores current state's reachable points and adds them to the fringe with their f values
            for (FlightDirection flightDirection : FlightDirection.values()){
                FlightPoint newFlightPoint = new FlightPoint(currLngLat.nextPosition(flightDirection.label), currentPoint, flightDirection);
                // Skips any points that have been already explored or are in the no-fly zone
                List<FlightPoint> matchedPoints = explored.stream()
                        .filter(point -> point.equals(newFlightPoint))
                        .toList();
                if (newFlightPoint.getLngLat().inNoFlyZone() || matchedPoints.size() > 0){
                    continue;
                }
                // Using the straight line heuristic
                double heuristic = newFlightPoint.getLngLat().distanceTo(to);
                int i = 0;
                while (fringe.size() > i && heuristic > fringe.get(i).getValue1()){
                    i++;
                }
                fringe.add(i, new Pair<>(newFlightPoint, heuristic));
            }

            explored.add(fringe.get(0).getValue0());
            currentPoint = fringe.pop().getValue0();
        }

        currentPoint = explored.getLast();
        LinkedList<FlightPoint> goalPath = new LinkedList<>();

        while (currentPoint.getFromPoint() != null){
            goalPath.addFirst(currentPoint);
            currentPoint = currentPoint.getFromPoint();
            currentPoint.setNextPoint(goalPath.get(0));
        }
        return goalPath;
    }
}
