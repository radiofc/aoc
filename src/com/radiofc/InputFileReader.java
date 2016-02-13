package com.radiofc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class InputFileReader {

    public static ArrayList<String> readFile(String filePath)
    {
        ArrayList<String> linesOfText = new ArrayList<>();

        try {
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String sCurrentLine;
                while ((sCurrentLine = br.readLine()) != null) {
                    System.out.println("Line: "+ sCurrentLine);
                    linesOfText.add(sCurrentLine);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return linesOfText;
    }
}
