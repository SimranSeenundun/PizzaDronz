package uk.ac.ed.inf;

import org.javatuples.Pair;
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
    final static int STANDARD_CARD_MAX_EXPIRY = 50;
    final static int STANDARD_CARD_CVV_LENGTH = 3;
    final static int MAX_NUM_PIZZAS = 4;
    final static int CURRENT_CENTURY = ((int) LocalDate.now().getYear() / 1000) * 1000;

    private String orderNum;
    private ArrayList<String> pizzasOrdered;
    private OrderOutcome orderOutcome;
    private int totalOrderPrice;

    public Order(JSONObject orderJson) {
        // Validates order
        orderNum = orderJson.getString(ORDER_NUMBER.label);
        JSONArray pizzasOrderedJson = orderJson.getJSONArray(ORDER_ITEMS.label);
        pizzasOrdered = new ArrayList<>();
        for (int i = 0; i < pizzasOrderedJson.length(); i++){
            pizzasOrdered.add(pizzasOrderedJson.getString(i));
        }
        orderOutcome = ValidButNotDelivered;
        totalOrderPrice = orderJson.getInt(PRICE_TOTAL.label);
        checkCardValidity(orderJson);
    }

    /**
     * Checks the validity of the card's information
     * @param orderJson the order JSON with the card information
     */
    private void checkCardValidity(JSONObject orderJson){
        String cardNumber = orderJson.getString(CARD_NUMBER.label);
        String cardExpiry = orderJson.getString(CARD_EXPIRY.label);
        String cardCVV = orderJson.getString(CARD_CVV.label);

        // Checks card number validity - length & is numeric
        try {
            Long.parseLong(cardNumber);
        } catch (NumberFormatException e){
            orderOutcome = InvalidCardNumber;
        }

        if (cardNumber.length() != STANDARD_CARD_NUMBER_LENGTH){
            orderOutcome = InvalidCardNumber;
        }

        // Checks expiry validity - is numeric, length & in date
        if (cardExpiry.length() != STANDARD_CARD_EXPIRY_LENGTH){
            orderOutcome = InvalidExpiryDate;
        }

        try{
            int year = Integer.parseInt(cardExpiry.substring(3,5));
            int month = Integer.parseInt(cardExpiry.substring(0,2));
            LocalDate currDate = LocalDate.now();

            // Checks if expiration is in date - century, year and month
             if (!((year + CURRENT_CENTURY < (currDate.getYear() + STANDARD_CARD_MAX_EXPIRY)) && (year < currDate.getYear()) && (month < currDate.getMonthValue()))){
                 orderOutcome = InvalidExpiryDate;
             }
        } catch (NumberFormatException e){
            orderOutcome = InvalidExpiryDate;
        }

        // Checks card's CVV validity - length and is numeric
        try {
            Integer.parseInt(cardCVV);
        } catch (NumberFormatException e){
            orderOutcome = InvalidCvv;
        }

        if (cardCVV.length() != STANDARD_CARD_CVV_LENGTH){
            orderOutcome = InvalidCvv;
        }
    }

    /**
     * Finds the delivery cost and restaurant of the provided order.
     * @param restaurants array of all participating restaurants
     * @param pizzasOrdered array of all pizzas ordered
     * @return integer final delivery cost
     * @throws InvalidPizzaCombinationException for invalid pizza combinations
     */
    static Pair<Restaurant, Integer> getRestaurantDeliveryCostPair(Restaurant[] restaurants, ArrayList<String> pizzasOrdered) throws InvalidOrderException {
        // Checks if order has pizzas then the drone can carry
        if (pizzasOrdered.size() > MAX_NUM_PIZZAS){
            throw new InvalidPizzaCombinationException(InvalidPizzaCount);
        }

       int totalCost = 0;
       String previousRestaurantName = "";
       Restaurant chosenRestaurant = null;

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
                   throw new InvalidOrderException(Invalid);
               }
               //Checks if the menu item has been found for this current restaurant
               else if (matchedMenu.size() == 1) {
                   //Checks if orders are from multiple restaurants
                   if ((currentRestaurant.getName().equals(previousRestaurantName) || previousRestaurantName.equals(""))) {
                       totalCost += matchedMenu.get(0).price();
                       previousRestaurantName = currentRestaurant.getName();
                       chosenRestaurant = currentRestaurant;
                       flag = true;
                   }
                   //Thrown if orders are from different restaurants
                   else {
                       throw new InvalidPizzaCombinationException(InvalidPizzaCombinationMultipleSuppliers);
                   }
               }
               i++;
           }
           //If no order items are found in any of the restaurants
           if (!flag) {
               throw new InvalidPizzaCombinationException(InvalidPizzaNotDefined);
           }
       }

       return new Pair<>(chosenRestaurant, totalCost + BASE_DELIVERY_COST);
    }

    /**
     * Gets just the delivery cost of the current order
     * @param restaurants all participating restaurants
     * @return the total cost of order
     */
    public int getDeliveryCost(Restaurant[] restaurants){
        int totalOrderPrice = this.totalOrderPrice;
        try {
            totalOrderPrice = getRestaurantDeliveryCostPair(restaurants, pizzasOrdered).getValue1();
        } catch (InvalidOrderException e){
            orderOutcome = e.getReason();
        }
        return totalOrderPrice;
    }

    public int getTotalOrderPrice() {
        return totalOrderPrice;
    }

    public OrderOutcome getOrderOutcome() {
        return orderOutcome;
    }

    public void setOrderOutcome(OrderOutcome orderOutcome) {
        this.orderOutcome = orderOutcome;
    }

    /**
     * Gets the restaurant the oder is from
     * @param restaurants all participating restaurants
     * @return the restaurant making the order
     */
    public Restaurant getOrderRestaurant(Restaurant[] restaurants){
        try {
            return getRestaurantDeliveryCostPair(restaurants, pizzasOrdered).getValue0();
        } catch (InvalidOrderException e){
            orderOutcome = e.getReason();
        }
        return null;
    }

    public String getOrderNum() {
        return orderNum;
    }
}