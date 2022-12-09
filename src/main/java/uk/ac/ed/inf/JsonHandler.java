package uk.ac.ed.inf;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import static uk.ac.ed.inf.JsonConstants.*;

public class JsonHandler {
    public static String ROOT_OUTPUT_DIR = "output/";
    public static String DELIVERIES_FILE_NAME = "deliveries";
    public static String FLIGHTPATH_FILE_NAME = "flightpath";
    public static String DRONE_GEO_FILE_NAME = "drone";
    public static String DEFAULT_GEO_JSON_STRING_START = "{\"type\": \"FeatureCollection\",\"features\": [{\"type\": \"Feature\", \"geometry\": { \"type\": \"LineString\", \"coordinates\":";
    public static String DEFAULT_GEO_JSON_STRING_END = "},\"properties\": {\"name\": \"PizzaDronz\"}}]}";


    public static void createDeliveriesJson(String date, ArrayList<Order> orders){
        try{
            FileWriter file = new FileWriter(ROOT_OUTPUT_DIR + DELIVERIES_FILE_NAME + "-" + date + ".json");
            file.write("[");
            for (int i = 0; i < orders.size(); i++){
                JSONObject orderJson = new JSONObject();
                Order order = orders.get(i);
                orderJson.put(ORDER_NUMBER.label, order.getOrderNum());
                orderJson.put(OUTCOME.label, String.valueOf(order.getOrderOutcome()));
                orderJson.put(PRICE.label, order.getTotalOrderPrice());
                if (i + 1 < orders.size()){
                    file.write(orderJson + ",");
                } else {
                    file.write(orderJson.toString());
                }
            }
            file.write("]");
            file.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void createFlightPathJson(String date, FlightPoint startingPoint, LocalDateTime startTime){
        try{
            FileWriter file = new FileWriter(ROOT_OUTPUT_DIR + FLIGHTPATH_FILE_NAME + "-" + date + ".json");
            file.write("[");
            FlightPoint currPoint = startingPoint;
            while (currPoint != null){
                JSONObject flightPathJson = new JSONObject();
                Duration ticksSinceStartOfCalculation = Duration.between(startTime, currPoint.getTimeExecuted());
                Double angle = null;
                Double toLng = null;
                Double toLat = null;
                if (currPoint.getNextPoint() != null){
                    if (currPoint.getNextPoint().getFromAngle() != null){
                        angle = currPoint.getNextPoint().getFromAngle().label;
                    }
                    toLng = currPoint.getNextPoint().getLng();
                    toLat = currPoint.getNextPoint().getLat();
                }
                flightPathJson.put(FROM_LONGITUDE.label, currPoint.getLng());
                flightPathJson.put(FROM_LATITUDE.label, currPoint.getLat());
                flightPathJson.put(ANGLE.label, angle);
                flightPathJson.put(TO_LONGITUDE.label, toLng);
                flightPathJson.put(TO_LATITUDE.label, toLat);
                flightPathJson.put(TICKS_SINCE_START.label, ticksSinceStartOfCalculation.toNanos());
                currPoint = currPoint.getNextPoint();
                if (currPoint != null){
                    file.write(flightPathJson + ",");
                } else {
                    file.write(flightPathJson.toString());
                }
            }
            file.write("]");
            file.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void createDroneGeoJson(String date, FlightPoint startPoint){
        try{
            FileWriter file = new FileWriter(ROOT_OUTPUT_DIR + DRONE_GEO_FILE_NAME + "-" + date + ".geojson");
            file.write(DEFAULT_GEO_JSON_STRING_START);
            FlightPoint currPoint = startPoint;
            JSONArray coordinates = new JSONArray();
            while (currPoint != null){
                JSONArray flightPointLngLat = new JSONArray();
                flightPointLngLat.put(currPoint.getLng());
                flightPointLngLat.put(currPoint.getLat());
                coordinates.put(flightPointLngLat);
                currPoint = currPoint.getNextPoint();
            }
            file.write(coordinates.toString());
            file.write(DEFAULT_GEO_JSON_STRING_END);
            file.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
