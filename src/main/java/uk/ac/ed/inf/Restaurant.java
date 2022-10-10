package uk.ac.ed.inf;

import org.json.JSONArray;

public class Restaurant {
    private static final String NAME_KEY = "name";
    private static final String MENU_KEY = "menu";
    private static final String PRICE_KEY = "priceInPence";
    private static final String LONGITUDE_KEY = "longitude";
    private static final String LATITUDE_KEY = "latitude";
    private String name;
    private LngLat location;
    private Menu[] menuItems;

    public Restaurant(String name, LngLat location, Menu[] menuItems) {
        this.name = name;
        this.location = location;
        this.menuItems = menuItems;
    }

    public static Restaurant[] getRestaurantsFromRestServer(String url) {
        JSONArray restaurantsJson = ResponseHandler.getJSonResponse(url);
        Restaurant[] restaurants = new Restaurant[restaurantsJson.length()];

        for (int i = 0; i < restaurantsJson.length(); i++) {
            String resName = restaurantsJson.getJSONObject(i).getString(NAME_KEY);
            double lng = restaurantsJson.getJSONObject(i).getDouble(LONGITUDE_KEY);
            double lat = restaurantsJson.getJSONObject(i).getDouble(LATITUDE_KEY);
            JSONArray menu = restaurantsJson.getJSONObject(i).getJSONArray(MENU_KEY);

            Menu[] menuItems = new Menu[menu.length()];
            for (int j = 0; j < menu.length(); j++){
                String itemName = menu.getJSONObject(j).getString(NAME_KEY);
                int itemPrice = menu.getJSONObject(j).getInt(PRICE_KEY);
                menuItems[j] = new Menu(itemName, itemPrice);
            }
            restaurants[i] = new Restaurant(resName, new LngLat(lng, lat), menuItems);
        }
        return restaurants;
    }

    public Menu[] getMenu() {
        return menuItems;
    }

    public String getName() {
        return name;
    }

    public LngLat getLocation() {
        return location;
    }
}
