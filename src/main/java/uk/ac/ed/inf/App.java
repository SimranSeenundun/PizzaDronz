package uk.ac.ed.inf;
import org.json.JSONArray;

import java.util.Arrays;

public class App 
{
    public static void main( String[] args ) {
        LngLat lngLatTopLeft = new LngLat(-3.192473, 55.946233);
        System.out.println(lngLatTopLeft.inCentralArea());

        LngLat lngLatBottomRight = new LngLat(-3.184319,55.942617);
        System.out.println(lngLatBottomRight.inCentralArea());
    }
}
