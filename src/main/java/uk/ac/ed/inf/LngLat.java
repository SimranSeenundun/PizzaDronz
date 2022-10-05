package uk.ac.ed.inf;

public record LngLat (double lng, double lat) {
    private static final double MOVE_LENGTH = 0.00015;



    public boolean inCentralArea() {
        return false;
    }

    public double distanceTo(LngLat lnglat) {
        double x1 = lng;
        double x2 = lnglat.lng;
        double y1 = lat;
        double y2 = lnglat.lat;

        return Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }

    public boolean closeTo(LngLat lnglat) {
        if (distanceTo(lnglat) <= 0.00015) {
            return true;
        }
        return false;
    }

    public LngLat nextPosition(double directionAngle) {
        double newLong = 0;
        double newLat = 0;
        if ((directionAngle >= 0 && directionAngle <= 90) || (directionAngle > 180 && directionAngle <= 270)) {
            newLong += Math.sin(directionAngle) * MOVE_LENGTH;
            newLat += Math.cos(directionAngle) * MOVE_LENGTH;
        } else {
            newLong += Math.cos(directionAngle) * MOVE_LENGTH;
            newLat += Math.sin(directionAngle) * MOVE_LENGTH;
        }
        return new LngLat(newLong, newLat);
    }
}

