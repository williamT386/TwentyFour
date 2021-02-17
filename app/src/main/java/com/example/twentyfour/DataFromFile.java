package com.example.twentyfour;

import android.app.Activity;
import android.util.Log;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DataFromFile {

    private static ArrayList<Problem> problems;
    private static final String fileName = "24 solutions file.txt";

    private DataFromFile() { }

    public static void readDataFromFile(Activity activity) {
        problems = new ArrayList<>();

        Scanner scanner = setScanner(activity);

        //each line will have all of the following information, so no need to
        // check if the scanner has more information during each line
        while(scanner.hasNext()) {
            int[] valuesIn = new int[4];
            for(int i = 0; i < valuesIn.length; i++)
                valuesIn[i] = scanner.nextInt();

            String successRateIn = scanner.nextLine().trim();
            problems.add(new Problem(valuesIn, successRateIn));
        }
    }

    /**
     * Sets the scanner. Takes an Activity to acts as the parameter of the
     * DataInputStream.
     * @param activity the input activity
     */
    private static Scanner setScanner(Activity activity) {
        Scanner scanner = null;
        try {
            DataInputStream textFileStream1 = new DataInputStream(activity.
                    getAssets().open(fileName));
            scanner = new Scanner(textFileStream1);
        } catch(IOException e) {
            Log.e("input file", "input file not found.");
            System.exit(1);
        }

        return scanner;
    }

    public static Problem getRandomProblem() {
        return problems.get((int)(Math.random() * problems.size()));
    }
}
