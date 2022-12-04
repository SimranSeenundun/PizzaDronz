package uk.ac.ed.inf;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static uk.ac.ed.inf.JsonConstants.*;
import static uk.ac.ed.inf.OrderOutcome.*;

public class Order {
    final static int BASE_DELIVERY_COST = 100;
    final static int STANDARD_CARD_NUMBER_LENGTH = 16;
    final static int STANDARD_CARD_EXPIRY_LENGTH = 5;
    private String orderNum;
    private ArrayList<String> pizzasOrdered;
    private OrderOutcome orderOutcome;
    private int totalOrderPrice;

    public Order(JSONObject orderJson){
        orderNum = orderJson.getString(ORDER_NUMBER.label);
        JSONArray pizzasOrderedJson = orderJson.getJSONArray(ORDER_ITEMS.label);
        for (int i = 0; i < pizzasOrderedJson.length(); i++){
            pizzasOrdered.add(pizzasOrderedJson.getString(i));
        }
        orderOutcome = null;
        totalOrderPrice = 0;
    }

    private void checkCardValidity(JSONObject orderJson){
        String cardNumber = orderJson.getString(CARD_NUMBER.label);
        String cardExpiry = orderJson.getString(CARD_EXPIRY.label);
        String cardCVV = orderJson.getString(CARD_CVV.label);

        try {
            Integer.getInteger(cardNumber);
        } catch (NumberFormatException e){
            orderOutcome = InvalidCardNumber;
        }

        if (cardNumber.length() != STANDARD_CARD_NUMBER_LENGTH){
            orderOutcome = InvalidCardNumber;
        }

        if (cardExpiry.length() != STANDARD_CARD_EXPIRY_LENGTH){
            orderOutcome = InvalidExpiryDate;
        }

        //LocalDate expiryDate =

    }

    /**
     * Finds the delivery cost of the provided order.
     * @param restaurants array of all participating restaurants
     * @param pizzasOrdered array of all pizzas ordered
     * @return integer final delivery cost
     * @throws InvalidPizzaCombinationException for invalid pizza combinations
     */
    static int getDeliveryCost(Restaurant[] restaurants, ArrayList<String> pizzasOrdered) throws InvalidPizzaCombinationException {
       int totalCost = 0;
       String previousRestaurant = "";

       //Loops through each of the pizzas ordered
       for (String pizzaOrdered: pizzasOrdered) {
           int i = 0;
           boolean flag = false;
           //Loops through each restaurant, checking if current order iteration is a part of their menu
           while (i < restaurants.length && !flag) {
               Restaurant currentRestaurant = restaurants[i];
               Menu[] menuItems = currentRestaurant.getMenu();

               //Stream that gets a list of matched menu items to the current pizza order
               List<Menu> matchedMenu = Arrays.stream(menuItems).toList().stream()
                       .filter(menu -> menu.name().equals(pizzaOrdered))
                       .toList();

               //Checks if multiple pizzas have the same name
               if (matchedMenu.size() > 1) {
                   throw new InvalidPizzaCombinationException();
               }
               //Checks if the menu item has been found for this current restaurant
               else if (matchedMenu.size() == 1) {
                   //Checks if orders are from multiple restaurants
                   if ((currentRestaurant.getName().equals(previousRestaurant) || previousRestaurant.equals(""))) {
                       totalCost += matchedMenu.get(0).price();
                       previousRestaurant = currentRestaurant.getName();
                       flag = true;
                   }
                   //Thrown if orders are from different restaurants
                   else {
                       throw new InvalidPizzaCombinationException();
                   }
               }
               i++;
           }
           //If no order items are found in any of the restaurants
           if (!flag) {
               throw new InvalidPizzaCombinationException();
           }
       }

       return totalCost + BASE_DELIVERY_COST;
    }

}