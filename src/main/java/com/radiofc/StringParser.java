package com.radiofc;

/**
 * Created by ESHAMCC on 27/02/2016.
 */
public class StringParser {
    private int initialLength;
    private int finalLength;
    private int encodedLength;
    private String parsingText;
    private String parsedText;
    private String encodedText;

    public int getInitialLength() {
        return initialLength;
    }

    public int getFinalLength() {
        return finalLength;
    }

    public int getEncodedLength() {
        return encodedLength;
    }

    public String getEncodedText() {
        return encodedText;
    }

    public String getParsedText() {
        return parsedText;
    }

    public StringParser(String inputText) {
        this.parsingText = inputText;
        this.initialLength = inputText.length();
        removeSpecialCharacters();
        this.finalLength = this.parsedText.length();
        this.encodedLength = this.encodedText.length();
    }

    private void removeSpecialCharacters(){
        char[] charArray = this.parsingText.toCharArray();
        StringBuilder parsedTextBuilder = new StringBuilder();
        StringBuilder encodedTextBuilder = new StringBuilder();
        encodedTextBuilder.append("\"");
        for (int pointer = 0; pointer < this.initialLength;) {
            char c = charArray[pointer];
            //

            if (c == '"') {
                encodedTextBuilder.append("\\\"");
                pointer++;
            } else if ((c == '\\') && (charArray[pointer+1] == '\\')) {
                encodedTextBuilder.append("\\\\\\\\");
                parsedTextBuilder.append("X");
                pointer+=2;
            } else if ((c == '\\') && (charArray[pointer+1] == '\"')) {
                encodedTextBuilder.append("\\\\\\\"");
                parsedTextBuilder.append("X");
                pointer+=2;
            } else if ((c == '\\') && (charArray[pointer+1] == 'x')) {
                encodedTextBuilder.append("\\\\xxx");
                parsedTextBuilder.append("X");
                pointer+=4;
            } else {  // normal character
                encodedTextBuilder.append(c);
                parsedTextBuilder.append(c);
                pointer++;
            }
        }
        encodedTextBuilder.append("\"");

        this.parsedText = parsedTextBuilder.toString();
        this.encodedText = encodedTextBuilder.toString();
    }
}
