package uk.ac.ed.inf;
import java.awt.geom.Path2D;
import java.util.HashMap;

public record LngLat (double lng, double lat) {
    public static final double MOVE_LENGTH = 0.00015;


    /**
     * Determines if the current object is in the central area.
     * @return true if object in centralArea
     */
    public boolean inCentralArea() {
        //Defines the central area vertices
        CentralArea centralArea = CentralArea.getInstance();
        LngLat[] centralAreaPoints = centralArea.getCentralLngLats();
        return isInsideZone(centralAreaPoints);
    }

    public boolean inNoFlyZone(){
        //Defines the no-fly zones and their areas
        NoFlyZones noFlyZonesObject = NoFlyZones.getInstance();
        HashMap<String, LngLat[]> noFlyZones = noFlyZonesObject.getNoFlyZones();

        for (String zoneName : noFlyZones.keySet()){
            LngLat[] currZoneLngLat = noFlyZones.get(zoneName);
            if (isInsideZone(currZoneLngLat)){
                return true;
            }
        }
        return false;
    }

    public boolean isInsideZone(LngLat[] lngLatZonePoints) {
        Path2D zoneArea = new Path2D.Double();

        // Adds initial point
        zoneArea.moveTo(lngLatZonePoints[0].lng, lngLatZonePoints[0].lat);

        // Adds all the other points
        for(int i = 1; i < lngLatZonePoints.length; i++) {
            zoneArea.lineTo(lngLatZonePoints[i].lng, lngLatZonePoints[i].lat);
        }
        // Connects last point to first
        zoneArea.closePath();

        return zoneArea.contains(this.lng, this.lat);
    }

    /**
     * Finds the distance between two LngLat objects.
     * @param lnglat provided LngLat object to find the distance between.
     * @return distance as double.
     */
    public double distanceTo(LngLat lnglat) {
        //Uses pythagoras theorem to find the distance between the two points
        return Math.sqrt(Math.pow((lng - lnglat.lng), 2) + Math.pow((lat - lnglat.lat), 2));
    }

    /**
     * Determines if the current LngLat object is close to another LngLat.
     * @param lnglat provided LngLat object to find if it is close to current LngLat object.
     * @return true if provided LngLat is close.
     */
    public boolean closeTo(LngLat lnglat) {
        return distanceTo(lnglat) <= 0.00015;
    }

    /**
     * Finds the next LngLat position from the provided angle using trigonometry.
     * @param directionAngle direction the next LngLat should be in
     * @return LngLat with the next position
     */
    public LngLat nextPosition(double directionAngle) {
        double addLong = 0;
        double addLat = 0;
        //Converts from degrees to radians
        double angleInRadian = Math.toRadians(directionAngle);

        addLong += Math.cos(angleInRadian) * MOVE_LENGTH;
        addLat += Math.sin(angleInRadian) * MOVE_LENGTH;
        return new LngLat(lng + addLong, lat + addLat);
    }

    /**
     * Determines if a provided LngLat object has the same lng and lat as the current object
     * @param lngLat the object to compare
     * @return true if the object has the same lng and lats
     */
    public boolean equals(LngLat lngLat){
        return lngLat.lng == lng && lngLat.lat == lat;
    }

    @Override
    public String toString() {
        return lng + " " + lat;
    }
}

