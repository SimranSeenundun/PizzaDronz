package uk.ac.ed.inf;

public class LngLat {
    public double lng, lat;
    public int north = 90;
    public int south = 270;
    public int east = 0;
    public int west = 180;

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

    public boolean closeTo(LngLat lnglat)  {
       if ((distanceTo(lnglat) <= 0.00015) && (distanceTo(lnglat) != 0)) {
           return true;
       }
       return false;
    }

    //public double nextPosition(int compDirection) {
        //compDirection =
    //}
}

