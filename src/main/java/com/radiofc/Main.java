package com.radiofc;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class);

    private Main() {

    }

    public static void main(String[] args) {
        PropertyConfigurator.configure(LogMessage.LOG4JCONFPATH);
        LOGGER.info("AOC9-Distances");

        List<String> distancePlotList = InputFileReader.readFile("C:\\Users\\eshamcc\\Dropbox\\git\\aoc\\AOC9-Distances\\resources\\input2.txt");
        List<DistancePlot> distancePlots = new ArrayList<>();
        Set<String> placesSet = new HashSet<>();

        for (String distancePlotInfo : distancePlotList) {
            distancePlots.add(new DistancePlot(distancePlotInfo));
        }

        for (DistancePlot distancePlot : distancePlots) {
            placesSet.add(distancePlot.getStartPoint());
            placesSet.add(distancePlot.getEndPoint());
        }

        LOGGER.info(placesSet);

        for (String place : placesSet) {

        }

    }


}
