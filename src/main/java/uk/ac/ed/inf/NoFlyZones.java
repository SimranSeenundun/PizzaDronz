package uk.ac.ed.inf;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;

import static uk.ac.ed.inf.JsonConstants.*;
import static uk.ac.ed.inf.ServerNavigationConstants.*;

/**
 * Singleton that defines the no-fly zones.
 */
public class NoFlyZones {
    private final JSONArray noFlyZonesJson;
    static NoFlyZones noFlyZonesObject = new NoFlyZones();

    /**
     * Constructor gets all no-fly zones from the REST-server.
     */
    private NoFlyZones() {
        noFlyZonesJson = ResponseHandler.getJSonResponse(App.SERVER_URL + NO_FLY_ZONES.label);
    }

    /**
     * Parses the reference to the singleton's only object instance.
     * @return instance of the NoFlyZones singleton
     */
    public static NoFlyZones getInstance() {
        return noFlyZonesObject;
    }

    /**
     * Gets the LngLat objects of all the points that define the no-fly zones.
     * @return hashmap of no-fly zones and their point's coordinates.
     */
    public HashMap<String, LngLat[]> getNoFlyZones() {
        HashMap<String, LngLat[]> noFlyZones = new HashMap<>();

        //Loops through each no-fly zone JSON
        for (int i = 0; i < noFlyZonesJson.length(); i++) {
            //Gets the name from the json object
            String zoneName = noFlyZonesJson.getJSONObject(i).getString(NAME.label);
            // Gets all the points and their LngLats
            JSONArray coordinates = noFlyZonesJson.getJSONObject(i).getJSONArray(COORDINATES.label);
            LngLat[] lngLatsOfZone = new LngLat[coordinates.length()];

            // Gets all points and converts them into LngLat objects
            for (int j = 0; j < coordinates.length(); j++){
                JSONArray lngLatJson = coordinates.getJSONArray(j);
                lngLatsOfZone[j] = new LngLat(((BigDecimal) lngLatJson.get(0)).doubleValue(), ((BigDecimal) lngLatJson.get(1)).doubleValue());
            }

            // Puts collected points into map under the name of zone
            noFlyZones.put(zoneName, lngLatsOfZone);
        }
        return noFlyZones;
    }

}
