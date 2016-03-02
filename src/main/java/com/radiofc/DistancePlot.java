package com.radiofc;

/**
 * Created by ESHAMCC on 02/03/2016.
 */
public class DistancePlot {
    private String startPoint;
    private String endPoint;
    private int distance;

    public DistancePlot(String distanceData) {
        String[] distanceDataParts = distanceData.split(" ");
        this.startPoint = distanceDataParts[0];
        this.endPoint = distanceDataParts[2];
        this.distance = Integer.parseInt(distanceDataParts[4]);
    }

    public String getStartPoint() {
        return startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public int getDistance() {
        return distance;
    }
}
