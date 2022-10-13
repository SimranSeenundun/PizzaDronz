package uk.ac.ed.inf;
import org.json.JSONArray;

import java.awt.*;
import java.awt.geom.Point2D;
//ONLY ONE INSTANCE OF A CLASS

public class CentralArea {
    private final String CENTRAL_AREA_NAV = "centralarea";
    private final String ILP_URL = "https://ilp-rest.azurewebsites.net/";
    private final String LONGITUDE_KEY = "longitude";
    private final String LATITUDE_KEY = "latitude";

    private JSONArray centralArea;
    static CentralArea centralAreaObject = new CentralArea();
    private CentralArea() {
        centralArea = ResponseHandler.getJSonResponse(ILP_URL + CENTRAL_AREA_NAV);
    }

    public static CentralArea getInstance() {
        return centralAreaObject;
    }

    public LngLat[] getCentralLngLats() {
        LngLat[] centralLngLats = new LngLat[centralArea.length()];
        for (int i = 0; i < centralArea.length(); i++) {
            double lng = centralArea.getJSONObject(i).getDouble(LONGITUDE_KEY);
            double lat = centralArea.getJSONObject(i).getDouble(LATITUDE_KEY);
            LngLat lngLatPoint = new LngLat(lng , lat);
            centralLngLats[i] = lngLatPoint;
        }
        return centralLngLats;
    }

}
