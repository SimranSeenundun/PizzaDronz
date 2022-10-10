package uk.ac.ed.inf;

import java.util.ArrayList;

public class Order {
    final static int BASE_DELIVERY_COST = 100;
    static int getDeliveryCost(Restaurant[] restaurants, ArrayList<String> pizzasOrdered) throws InvalidPizzaCombinationException {
       int totalCost = 0;
       String previousRestaurant = "";

       for (String pizzaOrdered: pizzasOrdered){
           int i = 0;
           boolean flag = false;
           while (i < restaurants.length && !flag){
               Restaurant currentRestaurant = restaurants[i];
               Menu[] menuItems = currentRestaurant.getMenu();

               int j = 0;
               while (j < menuItems.length && !flag){
                   Menu currentItem = menuItems[j];

                   if (currentItem.getName().equals(pizzaOrdered)){
                       if (currentRestaurant.getName().equals(previousRestaurant) || previousRestaurant.equals("")){
                           totalCost += currentItem.getPrice();
                           previousRestaurant = currentRestaurant.getName();
                           flag = true;
                       }
                       else {
                           throw new InvalidPizzaCombinationException();
                       }
                   }

                   j++;
               }

               i++;
           }
           if (!flag){
               throw new InvalidPizzaCombinationException();
           }
       }

       return totalCost + BASE_DELIVERY_COST;
    }
}
