package com.radiofc;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        ArrayList<String> instructionList = InputFileReader.readFile("C:\\Users\\eshamcc\\IdeaProjects\\AOC7-Bitwise\\resources\\input.txt");

        int a = 123;	/* 60 = 0011 1100 */
        int b = 456;	/* 13 = 0000 1101 */
        int c = 0;

        c = bitwiseAnd(a, b);       /* 12 = 0000 1100 */
        System.out.println("a & b :" + c );
    }

    private static int bitwiseAnd(int a,int b) {
        return a & b;
    }
}


