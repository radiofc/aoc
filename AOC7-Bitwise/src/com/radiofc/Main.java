package com.radiofc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        ArrayList<String> instructionList = InputFileReader.readFile("C:\\Users\\eshamcc\\Dropbox\\git\\aoc\\AOC7-Bitwise\\resources\\input3.txt");

        ArrayList<Signal> signalList = processInstructions(instructionList);
        ArrayList<Wire> baseSignals = getBaseSignals(signalList);
        ArrayList<Wire> allSignals = processMap(signalList, baseSignals);

        System.out.println("*****************\nThe wires: ");
        for (Wire w : allSignals) {
           System.out.println(w.getIdentifier() + " - " + w.getSignal());
        }
        int a = 123;
        int b = 127;
        int c = a & b;
        System.out.println("a & b: " + c);
    }

    private static ArrayList<Signal> processInstructions(ArrayList<String> instructionList) {
        ArrayList<Signal> signalList = new ArrayList<>();
        for (String instruction : instructionList) {
            System.out.println(instruction);
            String[] parts = instruction.split(" -> ");
            Signal signal = new Signal(parts[0], parts[1]);
            signalList.add(signal);
        }
        return signalList;
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
           // System.out.println("doing signal: "+signal);

            if (!signal.contains("AND") && !signal.contains("OR") && !signal.contains("NOT") && !signal.contains("LSHIFT") && !signal.contains("RSHIFT")) {
                if (isInteger(signal)) {
                    System.out.println("Signal: " + signal + " wire: " + aSignal.getWire());
                    addWireToList(wireList, aSignal.getWire(), Integer.parseInt(signal));
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

        System.out.println("The wires in processMap: ");
        for (Wire w : wireList) {
            System.out.println(w.getIdentifier() + " - " + w.getSignal());
        }
        System.out.println("+++++++++++++++++++++++++++++++++");

        for (Signal aSignal : signalList) {
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
                    addWireToList(wireList, aSignal.getWire(), Integer.parseInt(signal));
                } else {
                    for (Wire wire : baseSignals) {
                        if (wire.getIdentifier().equals(signal)) {
                            addWireToList(wireList, aSignal.getWire(), wire.getSignal());
                          //  it.remove();
                            break;
                        }
                    }

                }

            }
        }

        System.out.println("The wires in processMap 2: ");
        for (Wire w : wireList) {
            System.out.println(w.getIdentifier() + " - " + w.getSignal());
        }
        System.out.println("+++++++++++++++++++++++++++++++++");

        return wireList;
    }

    private static ArrayList<Wire> addWireToList(ArrayList<Wire> wireList, String identifier, int signal) {
        for (Wire wire : wireList) {
            if (wire.getIdentifier().equals(identifier)) {
                wire.setSignal(signal);
                return wireList;
            }
        }
        Wire wire = new Wire(identifier, signal);
        wireList.add(wire);
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
            wireList = addWireToList(wireList, wireName, res);
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
            addWireToList(wireList, pair.getKey().toString(), res);
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
                addWireToList(wireList, pair.getKey().toString(), res);
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
                addWireToList(wireList, pair.getKey().toString(), res);
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

                addWireToList(wireList, pair.getKey().toString(), res);
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


