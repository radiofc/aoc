package com.radiofc;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        ArrayList<String> instructionList = InputFileReader.readFile("C:\\Users\\eshamcc\\IdeaProjects\\AOC7-Bitwise\\resources\\input2.txt");
        HashMap<String,String> signalMap = processInstructions(instructionList);
      //  printMap(signalMap);
        processMap(signalMap);

       // signalMap.
        int a = 123;	/* 60 = 0011 1100 */
        int b = 456;	/* 13 = 0000 1101 */
        int c = 0;

        c = bitwiseAnd(a, b);       /* 12 = 0000 1100 */
      //  System.out.println("a & b :" + c );
    }

    private static HashMap<String,String> processInstructions(ArrayList<String> instructionList) {
        HashMap<String,String> signalMap = new HashMap<>();
        for (String instruction : instructionList) {
            System.out.println(instruction);
            String[] parts = instruction.split(" -> ");
            String signal = parts[0];
            String wire = parts[1];
            signalMap.put(wire,signal);
        //    System.out.println("Signal: "+signal+" Wire: "+wire);
        }
        return signalMap;
    }

    private static int bitwiseAnd(int a,int b) {
        return a & b;
    }

    public static void printMap(Map mp) {
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
    }

    public static void processMap(Map mp) {
        System.out.println("**********");
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            String signal = pair.getValue().toString();
            if (signal.contains("AND")) {
                String part1 = signal.split(" ")[0].toString();
                String part2 = signal.split(" ")[2].toString();
                System.out.println(part1 + " - AND - "+ part2);
            } else if (signal.contains("OR")) {
                String part1 = signal.split(" ")[0].toString();
                String part2 = signal.split(" ")[2].toString();
                System.out.println(part1 + " - OR - "+ part2);
            } else if (signal.contains("NOT")) {
                String varToNot = signal.split(" ")[1].toString();
                System.out.println("to be NOTed: " + varToNot);
            } else if (signal.contains("LSHIFT")) {


            } else if (signal.contains("RSHIFT")) {

            } else {
                //String part1 = signal.split(" ")[0].toString();
                System.out.println("Signal: "+signal + " "+pair.getKey());
            }
            String[] parts = pair.getValue().toString().split(" ");

       //     System.out.println(pair.getKey() + " = " + pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
    }
}


