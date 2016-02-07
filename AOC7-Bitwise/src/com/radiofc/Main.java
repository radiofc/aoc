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
        HashMap<String, String> signalMap = processInstructions(instructionList);
        ArrayList<Wire> baseSignals = getBaseSignals(signalMap);
        //  printMap(signalMap);
        ArrayList<Wire> allSignals = processMap(signalMap, baseSignals);

        System.out.println("The wires: ");
        for (Wire w : allSignals) {
            System.out.println(w.getIdentifier() + " - " + w.getSignal());
        }
     /*   processMap(signalMap, baseSignals);
        processMap(signalMap, baseSignals);
        processMap(signalMap, baseSignals);*/
        // signalMap.
        long a = 456;	/* 60 = 0011 1100 */
        long b = 456;	/* 13 = 0000 1101 */
        long c = 0;

        c = ~a;

        // c = bitwiseAnd(a, b);       /* 12 = 0000 1100 */
        System.out.println("~a: " + c);
    }

    private static HashMap<String, String> processInstructions(ArrayList<String> instructionList) {
        HashMap<String, String> signalMap = new HashMap<>();
        for (String instruction : instructionList) {
            System.out.println(instruction);
            String[] parts = instruction.split(" -> ");
            String signal = parts[0];
            String wire = parts[1];
            signalMap.put(wire, signal);
            //    System.out.println("Signal: "+signal+" Wire: "+wire);
        }
        return signalMap;
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


    public static ArrayList<Wire> getBaseSignals(Map mp) {
        System.out.println("**********");
        Iterator it = mp.entrySet().iterator();
        ArrayList<Wire> wireList = new ArrayList<>();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            String signal = pair.getValue().toString();
            if (!signal.contains("AND") && !signal.contains("OR") && !signal.contains("NOT") && !signal.contains("LSHIFT") && !signal.contains("RSHIFT")) {
                if (isInteger(signal)) {
                    System.out.println("Signal: " + signal + " wire: " + pair.getKey());
                    Wire wire = new Wire(pair.getKey().toString(), Integer.parseInt(signal));
                    wireList.add(wire);
                    it.remove(); // avoids a ConcurrentModificationException
                }
            }
        }

        System.out.println("The wires: ");
        for (Wire w : wireList) {
            System.out.println(w.getIdentifier() + " - " + w.getSignal());
        }
        return wireList;
    }

    public static ArrayList<Wire> processMap(Map mp, ArrayList<Wire> baseSignals) {
        System.out.println("**********");
        ArrayList<Wire> wireList = new ArrayList<>();
        wireList.addAll(baseSignals);

        Iterator it = mp.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            String signal = pair.getValue().toString();
            if (signal.contains("AND")) {
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
                    System.out.println(part1 + " - AND - " + part2 + " wire: " + pair.getKey() + " = " + res);
                    Wire wire = new Wire(pair.getKey().toString(), res);
                    wireList.add(wire);
                    it.remove();    // avoids a ConcurrentModificationException
                }
            } else if (signal.contains("OR")) {
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
            } else if (signal.contains("NOT")) {
                String varToNot = signal.split(" ")[1].toString();

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
            } else if (signal.contains("LSHIFT")) {
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

            } else if (signal.contains("RSHIFT")) {
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

            } else {
                System.out.println("Signal: " + signal + " wire: " + pair.getKey());
                if (isInteger(signal)) {
                    Wire wire = new Wire(pair.getKey().toString(), Integer.parseInt(signal));
                    wireList.add(wire);
                } else {
                    for (Wire wire : baseSignals) {
                        if (wire.getIdentifier().equals(signal)) {
                            Wire wire1 = new Wire(pair.getKey().toString(), wire.getSignal());
                            wireList.add(wire1);
                            it.remove();
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


