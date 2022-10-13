package uk.ac.ed.inf;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Arrays;

public record LngLat (double lng, double lat) {
    private static final double MOVE_LENGTH = 0.00015;


    /**
     * Determines if the current object is in the central area.
     * @return true if object in centralArea
     */
    public boolean inCentralArea() {
        CentralArea centralArea = CentralArea.getInstance();
        LngLat[] centralAreaPoints = centralArea.getCentralLngLats();
        LngLat[] allPoints = Arrays.copyOf(centralAreaPoints, centralAreaPoints.length + 1);
        allPoints[allPoints.length - 1] = this;
        int[][] intsAllPoints = removeDecimalPoint(allPoints);
        Polygon centralAreaPoly = new Polygon();

        for(int i = 0; i < intsAllPoints.length - 1; i++){
            centralAreaPoly.addPoint(intsAllPoints[i][0], intsAllPoints[i][1]);
        }
        return centralAreaPoly.contains(intsAllPoints[intsAllPoints.length - 1][0], intsAllPoints[intsAllPoints.length - 1][1]);
    }

    /**
     * Converts all LngLats into 2D integer arrays by multiplying them up by a factor of 10.
     * @param lngLats contains the LngLat objects to convert
     * @return 2D array of converted LngLats
     */
    public int[][] removeDecimalPoint(LngLat[] lngLats){
        int mostDecimalPoints = 0;
        double[] nums = new double[lngLats.length * 2];
        for (int i = 0, j = 0; i < lngLats.length; i++ , j +=2){
            nums[j] = lngLats[i].lng;
            nums[j + 1] = lngLats[i].lat;
        }

        for (double num: nums){
            String sNum = Double.toString(Math.abs(num));
            int decimalAt = sNum.indexOf(".");
            int decimalPoints =  sNum.length() - (decimalAt + 1);
            if (decimalPoints > mostDecimalPoints) mostDecimalPoints = decimalPoints;
        }

        int[][] intNums = new int[lngLats.length][2];
        for (int i = 0, j = 0; i < intNums.length; i++, j += 2){
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
        double x1 = lng;
        double x2 = lnglat.lng;
        double y1 = lat;
        double y2 = lnglat.lat;

        return Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }

    /**
     * Determines if the current LngLat object is close to another LngLat.
     * @param lnglat provided LngLat object to find if it is close to current LngLat object.
     * @return true if provided LngLat is close.
     */
    public boolean closeTo(LngLat lnglat) {
        if (distanceTo(lnglat) <= 0.00015) {
            return true;
        }
        return false;
    }

    /**
     * Finds the next LngLat position from the provided angle.
     * @param directionAngle direction the next LngLat should be in.
     * @return LngLat with the next psotion.
     */
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

