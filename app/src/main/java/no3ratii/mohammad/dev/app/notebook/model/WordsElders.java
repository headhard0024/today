package no3ratii.mohammad.dev.app.notebook.model;

import java.util.Random;

public class WordsElders {
    private static String[] textList = new String[] {
            "سخن بزرگان",
            "سخن",
            "بزرگان",
            "مکن"
    };
    public static String word = getText();

    private static String getText(){
        Random rand = new Random();
        int n = rand.nextInt(textList.length); // Gives n such that 0 <= n < 20
        return textList[n];
    }
}
