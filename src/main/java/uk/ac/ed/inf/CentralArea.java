package uk.ac.ed.inf;
import org.json.JSONArray;

import static uk.ac.ed.inf.JsonConstants.LATITUDE;
import static uk.ac.ed.inf.JsonConstants.LONGITUDE;
import static uk.ac.ed.inf.ServerNavigationConstants.CENTRAL_AREA;

/**
 * Singleton that defines the central area.
 */
public class CentralArea {
    private final JSONArray centralArea;
    static CentralArea centralAreaObject = new CentralArea();

    /**
     * Constructor gets all central area points from the REST-server.
     */
    private CentralArea() {
        centralArea = ResponseHandler.getJSonResponse(App.SERVER_URL+ CENTRAL_AREA.label);
    }

    /**
     * Parses the reference to the singleton's only object instance.
     * @return instance of the CentralArea singleton
     */
    public static CentralArea getInstance() {
        return centralAreaObject;
    }

    /**
     * Gets the LngLat objects of all the points that define the central area.
     * @return array of LngLat[] object points.
     */
    public LngLat[] getCentralLngLats() {
        LngLat[] centralLngLats = new LngLat[centralArea.length()];
        //Loops through central area points
        for (int i = 0; i < centralArea.length(); i++) {
            //Gets the lng and lat from json
            double lng = centralArea.getJSONObject(i).getDouble(LONGITUDE.label);
            double lat = centralArea.getJSONObject(i).getDouble(LATITUDE.label);
            //Turns the lng and lat int a LngLat object and adds it to the array
            LngLat lngLatPoint = new LngLat(lng , lat);
            centralLngLats[i] = lngLatPoint;
        }
        return centralLngLats;
    }

}
