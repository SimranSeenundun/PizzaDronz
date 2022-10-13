package uk.ac.ed.inf;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;

public class App 
{
    public static void main( String[] args ) {
        Restaurant[] restaurants = Restaurant.getRestaurantsFromRestServer("https://ilp-rest.azurewebsites.net/restaurants");
        ArrayList<String> order = new ArrayList<>();
        order.add("Proper Pizza");
        order.add("Proper Pizza");
        try {
            System.out.println(Order.getDeliveryCost(restaurants, order));
        } catch (InvalidPizzaCombinationException e) {
            System.out.println(e.getMessage());
        }
    }
}
