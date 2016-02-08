package com.radiofc;

import java.awt.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        ArrayList<String> instructionList = InputFileReader.readFile("C:\\Users\\eshamcc\\IdeaProjects\\AOC7-Bitwise\\resources\\input2.txt");


      //  HashMap<String, String> signalMap = processInstructions(instructionList);
        ArrayList<Signal> signalList = processInstructions(instructionList);
        ArrayList<Wire> baseSignals = getBaseSignals(signalList);
        //  printMap(signalMap);
     //   ArrayList<Wire> allSignals = processMap(signalList, baseSignals);
     //   allSignals = processMap(signalList, baseSignals);
     //   System.out.println("The wires: ");
     //   for (Wire w : allSignals) {
     //       System.out.println(w.getIdentifier() + " - " + w.getSignal());
     //   }
     /*   processMap(signalMap, baseSignals);
        processMap(signalMap, baseSignals);
        processMap(signalMap, baseSignals)
        // signalMap.
        long a = 456;	/* 60 = 0011 1100
        long b = 456;	/* 13 = 0000 1101 */
     /*   long c = 0;

        c = ~a;

        // c = bitwiseAnd(a, b);       /* 12 = 0000 1100 */
    //    System.out.println("~a: " + c);
    }

    private static ArrayList<Signal> processInstructions(ArrayList<String> instructionList) {
      //  HashMap<String, String> signalMap = new HashMap<>();
        ArrayList<Signal> signalList = new ArrayList<>();
        for (String instruction : instructionList) {
            System.out.println(instruction);
            String[] parts = instruction.split(" -> ");
           // String signal = parts[0];
           // String wire = parts[1];
            Signal signal = new Signal(parts[0], parts[1]);
           // signalMap.put(wire, signal);
            signalList.add(signal);
            //    System.out.println("Signal: "+signal+" Wire: "+wire);
        }
        return signalList;
    }

    private static int bitwiseAnd(int a, int b) {
        return a & b;
    }

    public static void printMap(Map mp) {
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
    }


    public static ArrayList<Wire> getBaseSignals(ArrayList<Signal> signalList) {
        System.out.println("**********");

        ArrayList<Wire> wireList = new ArrayList<>();

        for (Signal aSignal : signalList) {
            String signal = aSignal.getName();
            System.out.println("doing signal: "+signal);

            if (!signal.contains("AND") && !signal.contains("OR") && !signal.contains("NOT") && !signal.contains("LSHIFT") && !signal.contains("RSHIFT")) {
                if (isInteger(signal)) {
                    System.out.println("Signal: " + signal + " wire: " + aSignal.getWire());
                    Wire wire = new Wire(aSignal.getWire(), Integer.parseInt(signal));
                    wireList.add(wire);
                }
            }
        }

        System.out.println("The wires: ");
        for (Wire w : wireList) {
            System.out.println(w.getIdentifier() + " - " + w.getSignal());
        }
        return wireList;
    }

    public static ArrayList<Wire> processMap(ArrayList<Signal> signalList, ArrayList<Wire> baseSignals) {
        System.out.println("**********");
        ArrayList<Wire> wireList = new ArrayList<>();
        wireList.addAll(baseSignals);

       // Iterator it = mp.entrySet().iterator();

     //   while (it.hasNext()) {
        for (Signal aSignal : signalList) {
          //  Map.Entry pair = (Map.Entry) it.next();
         //   String signal = pair.getValue().toString();
            String signal = aSignal.getName();
            String wireName = aSignal.getWire();
            if (signal.contains("AND")) {
                performAndOperation(baseSignals, wireList, wireName, signal);
         /*   } else if (signal.contains("OR")) {
                performOrOperation(baseSignals, wireList, it, pair, signal);
            } else if (signal.contains("NOT")) {
                performNotOperation(baseSignals, wireList, it, pair, signal.split(" ")[1]);
            } else if (signal.contains("LSHIFT")) {
                performLShiftOperation(baseSignals, wireList, it, pair, signal);
            } else if (signal.contains("RSHIFT")) {
                performRShiftOperation(baseSignals, wireList, it, pair, signal);*/
            } else {
                System.out.println("Signal: " + signal + " wire: " + aSignal.getWire());
                if (isInteger(signal)) {
                    Wire wire = new Wire(aSignal.getWire(), Integer.parseInt(signal));
                    wireList.add(wire);
                } else {
                    for (Wire wire : baseSignals) {
                        if (wire.getIdentifier().equals(signal)) {
                            Wire wire1 = new Wire(aSignal.getWire(), wire.getSignal());
                            wireList.add(wire1);
                          //  it.remove();
                            break;
                        }
                    }

                }

            }
            //String[] parts = pair.getValue().toString().split(" ");

            //     System.out.println(pair.getKey() + " = " + pair.getValue());

        }
        //   }

        return wireList;
    }

    private static void performAndOperation(ArrayList<Wire> baseSignals, ArrayList<Wire> wireList, String wireName, String signal) {
        String part1 = signal.split(" ")[0].toString();
        String part2 = signal.split(" ")[2].toString();

        int val1 = 0;
        int val2 = 0;
        for (Wire wire : baseSignals) {
            if (wire.getIdentifier().equals(part1)) {
                val1 = wire.getSignal();
            } else if (wire.getIdentifier().equals(part2)) {
                val2 = wire.getSignal();
            }
        }
        if ((val1 > 0) && (val2 > 0)) {
            int res = val1 & val2;
            System.out.println(part1 + " - AND - " + part2 + " wire: " + wireName + " = " + res);
            Wire wire = new Wire(wireName, res);
            wireList.add(wire);
          //  it.remove();    // avoids a ConcurrentModificationException
        }
    }

    private static void performOrOperation(ArrayList<Wire> baseSignals, ArrayList<Wire> wireList, Iterator it, Map.Entry pair, String signal) {
        String part1 = signal.split(" ")[0].toString();
        String part2 = signal.split(" ")[2].toString();

        int[] theValues = findTwoSignals(baseSignals, part1, part2);
        int val1 = theValues[0];
        int val2 = theValues[1];

        if ((val1 > 0) && (val2 > 0)) {
            int res = val1 | val2;
            System.out.println(part1 + " - OR - " + part2 + " wire: " + pair.getKey() + " = " + res);
            Wire wire = new Wire(pair.getKey().toString(), res);
            wireList.add(wire);
            it.remove();    // avoids a ConcurrentModificationException
        }
    }

    private static void performRShiftOperation(ArrayList<Wire> baseSignals, ArrayList<Wire> wireList, Iterator it, Map.Entry pair, String signal) {
        String part1 = signal.split(" ")[0].toString();
        String part2 = signal.split(" ")[2].toString();
        int val1 = 0;
        int val2 = Integer.parseInt(part2);
        for (Wire wire : baseSignals) {
            if (wire.getIdentifier().equals(part1)) {
                val1 = wire.getSignal();
                int res = val1 >> val2;
                System.out.println(part1 + " - RSHIFT - " + part2 + " wire: " + pair.getKey() + " = " + res);
                Wire wire1 = new Wire(pair.getKey().toString(), res);
                wireList.add(wire1);
                it.remove();    // avoids a ConcurrentModificationException
            }
        }
    }

    private static void performLShiftOperation(ArrayList<Wire> baseSignals, ArrayList<Wire> wireList, Iterator it, Map.Entry pair, String signal) {
        String part1 = signal.split(" ")[0].toString();
        String part2 = signal.split(" ")[2].toString();
        int val1 = 0;
        int val2 = Integer.parseInt(part2);
        for (Wire wire : baseSignals) {
            if (wire.getIdentifier().equals(part1)) {
                val1 = wire.getSignal();
                int res = val1 << val2;
                System.out.println(part1 + " - LSHIFT - " + part2 + " wire: " + pair.getKey() + " = " + res);
                Wire wire1 = new Wire(pair.getKey().toString(), res);
                wireList.add(wire1);
                it.remove();    // avoids a ConcurrentModificationException
            }
        }
    }

    private static void performNotOperation(ArrayList<Wire> baseSignals, ArrayList<Wire> wireList, Iterator it, Map.Entry pair, String s) {
        String varToNot = s.toString();

        int val1 = 0;
        for (Wire wire : baseSignals) {
            if (wire.getIdentifier().equals(varToNot)) {
                val1 = wire.getSignal();
                int res = 65535 + ~val1 + 1;

                System.out.println("to be NOTed: " + varToNot + "(" + val1 + ") wire: " + pair.getKey() + " = " + res);

                Wire wire1 = new Wire(pair.getKey().toString(), res);
                wireList.add(wire1);
                it.remove();    // avoids a ConcurrentModificationException
            }
        }
    }

    private static boolean isInteger(String string) {
        try {
            Integer.valueOf(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static int[] findTwoSignals(ArrayList<Wire> baseSignals, String part1, String part2) {
        int val1 = 0;
        int val2 = 0;
        for (Wire wire : baseSignals) {
            if (wire.getIdentifier().equals(part1)) {
                val1 = wire.getSignal();
            } else if (wire.getIdentifier().equals(part2)) {
                val2 = wire.getSignal();
            }
        }
        int[] theSignals = {val1, val2};
        return theSignals;
    }
}


