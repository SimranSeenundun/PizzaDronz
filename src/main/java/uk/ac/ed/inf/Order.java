package uk.ac.ed.inf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

               List<Menu> matchedMenu = Arrays.stream(menuItems).toList().stream()
                       .filter(menu -> menu.getName().equals(pizzaOrdered))
                       .toList();

               if (matchedMenu.size() > 1){
                   throw new InvalidPizzaCombinationException();
               } else if (matchedMenu.size() == 1) {
                   if ((currentRestaurant.getName().equals(previousRestaurant) || previousRestaurant.equals(""))){
                       totalCost += matchedMenu.get(0).getPrice();
                       previousRestaurant = currentRestaurant.getName();
                       flag = true;
                   }
                   else {
                       throw new InvalidPizzaCombinationException();
                   }
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


//List<Restaurant> restaurantList = Arrays.stream(restaurants).toList();

           /*
           Map<Object, List<Restaurant>> pizzas = restaurantList.stream()
                   .collect(Collectors.groupingBy(restaurant -> Arrays.stream(restaurant.getMenu())
                           .filter(menu -> menu.getName().equals(pizzaOrdered))
                           .collect(Collectors.toList())));

         */