package com.radiofc;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileReader;

public class InputFileReader {

    public static ArrayList<String> readFile(String filePath)
    {
        ArrayList<String> linesOfText = new ArrayList<>();

        try {

            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String sCurrentLine = null;

                while ((sCurrentLine = br.readLine()) != null) {
                    linesOfText.add(sCurrentLine);
                    System.out.println(sCurrentLine);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return linesOfText;
    }
}
