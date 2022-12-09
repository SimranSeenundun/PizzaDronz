package uk.ac.ed.inf;

import org.javatuples.Pair;
import org.json.JSONArray;
import org.json.JSONObject;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static uk.ac.ed.inf.ServerNavigationConstants.*;
import static uk.ac.ed.inf.App.*;

public class App
{
    public static String SERVER_URL = "https://ilp-rest.azurewebsites.net/";

    public static void main(String[] args) {
        SERVER_URL = args[1];
        String date = args[0];

        LocalDateTime startTime = LocalDateTime.now();
        JSONArray ordersJson = ResponseHandler.getJSonResponse(SERVER_URL + ORDERS.label + date);
        ArrayList<Order> orders = new ArrayList<>();

        // Gets all the orders from the server
        for (int i = 0; i < ordersJson.length(); i++){
            JSONObject orderJson = ordersJson.getJSONObject(i);
            Order currOrder = new Order(orderJson);
            // Checks if total price matches
            if (!(currOrder.getDeliveryCost(Restaurant.getRestaurantsFromRestServer(SERVER_URL + RESTAURANTS.label)) == currOrder.getTotalOrderPrice())){
                currOrder.setOrderOutcome(OrderOutcome.InvalidTotal);
            }
            orders.add(new Order(orderJson));
        }

        Restaurant[] restaurants = Restaurant.getRestaurantsFromRestServer(SERVER_URL + RESTAURANTS.label);

        // Drone movement
        Drone drone = new Drone();

        for(Order order: orders){
            Restaurant restaurantOrder = order.getOrderRestaurant(restaurants);
            if (order.getOrderOutcome() != OrderOutcome.ValidButNotDelivered){
                continue;
            }
            try {
                drone.executeOrder(order, restaurantOrder);
            } catch (BatteryOutOfChargeException e){
                break;
            }
        }

        FlightPoint point = drone.getStartingPoint();

        JsonHandler.createDeliveriesJson(date, orders);
        JsonHandler.createFlightPathJson(date, point, startTime);
        JsonHandler.createDroneGeoJson(date, point);
    }
}
