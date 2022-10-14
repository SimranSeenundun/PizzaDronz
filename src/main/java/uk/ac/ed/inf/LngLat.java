package uk.ac.ed.inf;

import java.awt.*;
import java.util.Arrays;

public record LngLat (double lng, double lat) {
    private static final double MOVE_LENGTH = 0.00015;


    /**
     * Determines if the current object is in the central area.
     * @return true if object in centralArea
     */
    public boolean inCentralArea() {
        //Defines the central area vertices
        CentralArea centralArea = CentralArea.getInstance();
        LngLat[] centralAreaPoints = centralArea.getCentralLngLats();

        //Adds all the LngLat points into one array for decimal removal
        LngLat[] allPoints = Arrays.copyOf(centralAreaPoints, centralAreaPoints.length + 1);
        allPoints[allPoints.length - 1] = this;
        //Turns current object's and central area points lng lats into integers
        int[][] intAllPoints = removeDecimalPoint(allPoints);

        //Central area represented as a polygon
        Polygon centralAreaPoly = new Polygon();

        //Loops through the central area's int lng and lats and adds it to the polygon
        for(int i = 0; i < intAllPoints.length - 1; i++){
            centralAreaPoly.addPoint(intAllPoints[i][0], intAllPoints[i][1]);
        }
        //Checks if the current object's int lng lat are inside the polygon and returns corresponding boolean
        return centralAreaPoly.contains(intAllPoints[intAllPoints.length - 1][0], intAllPoints[intAllPoints.length - 1][1]);
    }

    /**
     * Converts all LngLats into 2D integer arrays by multiplying them up by a factor of 10.
     * @param lngLats contains the LngLat objects to convert
     * @return 2D array of converted LngLats
     */
    public int[][] removeDecimalPoint(LngLat[] lngLats){
        int mostDecimalPoints = 0;

        //Converts the lngs and lats into a double array
        double[] nums = new double[lngLats.length * 2];
        for (int i = 0, j = 0; i < lngLats.length; i++ , j +=2){
            //adds lngs before lats
            nums[j] = lngLats[i].lng;
            nums[j + 1] = lngLats[i].lat;
        }

        //Loops through the lngs and lats and finds the one with the most decimal points
        for (double num: nums){
            String sNum = Double.toString(Math.abs(num));
            int decimalAt = sNum.indexOf(".");
            int decimalPoints =  sNum.length() - (decimalAt + 1);
            if (decimalPoints > mostDecimalPoints) mostDecimalPoints = decimalPoints;
        }

        //Creates new 2D int array for lng lats and converts each lng and lat pair
        int[][] intNums = new int[lngLats.length][2];
        for (int i = 0, j = 0; i < intNums.length; i++, j += 2){
            //Multiplies each lng and lat by a factor of 10, depending on the pair that had the highest amount of decimal points
            intNums[i][0] = (int) (nums[j] * Math.pow(10, mostDecimalPoints));
            intNums[i][1] = (int) (nums[j + 1] * Math.pow(10, mostDecimalPoints));
        }
        return intNums;
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
     * @param directionAngle direction the next LngLat should be in.
     * @return LngLat with the next psotion.
     */
    public LngLat nextPosition(double directionAngle) {
        double addLong = 0;
        double addLat = 0;
        //Converts from degrees to radians
        double angleInRadian = Math.toRadians(directionAngle);

        //Checks which way to use sin and cos depending on the angle and uses trig to solve new lng and lat
        if ((directionAngle >= 0 && directionAngle <= 90) || (directionAngle > 180 && directionAngle <= 270)) {
            addLong += Math.cos(angleInRadian) * MOVE_LENGTH;
            addLat += Math.sin(angleInRadian) * MOVE_LENGTH;
        }
        else {
            addLong += Math.sin(angleInRadian) * MOVE_LENGTH;
            addLat += Math.cos(angleInRadian) * MOVE_LENGTH;
        }
        return new LngLat(lng + addLong, lat + addLat);
    }
}

