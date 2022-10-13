package uk.ac.ed.inf;

import org.json.JSONArray;

import static uk.ac.ed.inf.JsonConstants.*;

public class Restaurant {
    private final String name;
    private final LngLat location;
    private final Menu[] menuItems;

    public Restaurant(String name, LngLat location, Menu[] menuItems) {
        this.name = name;
        this.location = location;
        this.menuItems = menuItems;
    }

    /**
     * Gets all restaurants from the REST-server.
     * @param url string that points to the rest server to collect the JSONs from.
     * @return array of restaurants.
     */
    public static Restaurant[] getRestaurantsFromRestServer(String url) {
        //Gets the restaurants JSON from REST-server
        JSONArray restaurantsJson = ResponseHandler.getJSonResponse(url);
        Restaurant[] restaurants = new Restaurant[restaurantsJson.length()];

        //Loops through the json
        for (int i = 0; i < restaurantsJson.length(); i++) {
            //Gets all the details for the restaurant from the json array using the json's keys
            String resName = restaurantsJson.getJSONObject(i).getString(NAME.label);
            double lng = restaurantsJson.getJSONObject(i).getDouble(LONGITUDE.label);
            double lat = restaurantsJson.getJSONObject(i).getDouble(LATITUDE.label);
            JSONArray menu = restaurantsJson.getJSONObject(i).getJSONArray(MENU.label);

            //Gets all the menu items for the restaurant from the json array and converts into Menu object
            Menu[] menuItems = new Menu[menu.length()];
            for (int j = 0; j < menu.length(); j++){
                String itemName = menu.getJSONObject(j).getString(NAME.label);
                int itemPrice = menu.getJSONObject(j).getInt(PRICE.label);
                menuItems[j] = new Menu(itemName, itemPrice);
            }
            restaurants[i] = new Restaurant(resName, new LngLat(lng, lat), menuItems);
        }
        return restaurants;
    }

    /**
     * Gets all Menu items from the restaurant
     * @return Array of Menu items.
     */
    public Menu[] getMenu() {
        return menuItems;
    }

    /**
     * Gets the name of the restaurant.
     * @return name of restaurant.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the location of the restaurant.
     * @return LngLat location of the restaurant.
     */
    public LngLat getLocation() {
        return location;
    }
}
