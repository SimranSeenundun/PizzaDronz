package uk.ac.ed.inf;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import static uk.ac.ed.inf.ServerNavigationConstants.*;

public class App
{
    public static void main( String[] args ) {
        String date = "2023-01-01";
        JSONArray ordersJson = ResponseHandler.getJSonResponse(ILP_SERVER_URL.label + ORDERS.label + date);
        ArrayList<Order> orders = new ArrayList<>();

        // Gets all the orders from the server
        for (int i = 0; i < ordersJson.length(); i++){
            JSONObject orderJson = ordersJson.getJSONObject(i);
            Order currOrder = new Order(orderJson);
            // Checks if total price matches
            if (!(currOrder.getDeliveryCost(Restaurant.getRestaurantsFromRestServer(ILP_SERVER_URL.label + RESTAURANTS.label)) == currOrder.getTotalOrderPrice())){
                currOrder.setOrderOutcome(OrderOutcome.InvalidTotal);
            }
            orders.add(new Order(orderJson));
        }


        // Drone movement
        Drone drone = new Drone();
    }
}
