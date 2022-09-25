package uk.ac.ed.inf;

public class LngLat {
    public double lng, lat;
    public LngLat(double longitude, double latitude)    {
        lng = longitude;
        lat = latitude;
    }

    public boolean inCentralArea()  {
        return false;
    }

    public double distanceTo(LngLat lnglat) {
        double x1 = lng;
        double x2 = lnglat.lng;
        double y1 = lat;
        double y2 = lnglat.lat;

        return Math.sqrt(Math.pow((x1 - x2),2) + Math.pow((y1 - y2),2));
    }
}

