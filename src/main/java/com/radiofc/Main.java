package com.radiofc;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.*;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class);

    private Main() {

    }

    public static void main(String[] args) {
        PropertyConfigurator.configure(LogMessage.LOG4JCONFPATH);
        LOGGER.info("AOC9-Distances");

        List<String> distancePlotList = InputFileReader.readFile("C:\\Users\\eshamcc\\Dropbox\\git\\aoc\\AOC9-Distances\\resources\\input.txt");
        List<DistancePlot> distancePlots = new ArrayList<>();
        Set<String> placesSet = new HashSet<>();

        for (String distancePlotInfo : distancePlotList) {
            distancePlots.add(new DistancePlot(distancePlotInfo));
        }

        setUpPlaces(distancePlots, placesSet);

        LOGGER.info(placesSet);

        BinaryTree theTree = new BinaryTree();

        int nodeNum = 0;

        for (String place : placesSet) {
            theTree.addNode(nodeNum++, place);
        }


        ArrayList<ArrayList<String>> routesList = new ArrayList<>();

        int N = placesSet.size();
        int[] sequence = new int[N];

        for (int i = 0; i < N; i++)
            sequence[i] = i;


        System.out.println("\nThe permuted sequences are: ");
        Permuter permuter = new Permuter(sequence, 0);

        List<List<Integer>> routesList2 = permuter.getPermutationsList();

        LOGGER.info(routesList2);
        List<Integer> distancesSorted = new ArrayList<>();
        for (List<Integer> destinations2 : routesList2) {
            int totalDistance = 0;
            boolean firstPass = true;
            StringBuilder lastPlace = new StringBuilder();
            for (int place : destinations2) {
                System.out.print(theTree.findNode(place));
                
                String currentPlace = theTree.findNode(place).toString();
                if (firstPass) {
                    firstPass = false;
                } else {
                    for (DistancePlot distancePlot : distancePlots) {
                        if ((distancePlot.getStartPoint().equalsIgnoreCase(currentPlace) && distancePlot.getEndPoint().equalsIgnoreCase(lastPlace.toString()))
                                || (distancePlot.getEndPoint().equalsIgnoreCase(currentPlace) && distancePlot.getStartPoint().equalsIgnoreCase(lastPlace.toString()))) {
                            totalDistance += distancePlot.getDistance();
                        }
                    }
                }
                lastPlace.setLength(0);
                lastPlace.append(currentPlace);
            }
            LOGGER.info("Total Distance: "+totalDistance);
            distancesSorted.add(totalDistance);
        }

        Collections.sort(distancesSorted);
        LOGGER.info(distancesSorted.get(0));
    }

    private static void setUpPlaces(List<DistancePlot> distancePlots, Set<String> placesSet) {
        for (DistancePlot distancePlot : distancePlots) {
            placesSet.add(distancePlot.getStartPoint());
            placesSet.add(distancePlot.getEndPoint());
        }
    }


}
