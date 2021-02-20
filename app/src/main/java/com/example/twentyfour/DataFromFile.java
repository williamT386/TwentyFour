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
    private static final int[] level = {0, 681, 1058, 1262, 1363};

    private DataFromFile() { }

    /**
     * Reads the data from the file called fileName. For each line of data,
     * create a Problem instance, add the information from that line of data
     * to it, and then add the instance to problems.
     * @param activity the activity for setting the Scanner
     */
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

    /**
     * Gets a random problem given 1 difficulty value.
     * @param difficulty the difficulty for the random problem
     * @return the random problem
     */
    public static Problem getRandomProblem(int difficulty) {
        int initial = level[difficulty];
        //range is final minus initial plus 1 to include both ends
        int range = level[difficulty + 1] - initial + 1;

        return problems.get((int)(Math.random()*range+initial));
    }

    /**
     * Gets a random problem given multiple difficulty values. Assume
     * difficulties is always multiple levels mathematically next to each
     * other and the list is in ascending order.
     * @param difficulties the difficulties for the random problem
     * @return the random problem
     */
    public static Problem getRandomProblem(int[] difficulties) {
        int initial = level[difficulties[0]];
        int finalValue = level[difficulties[difficulties.length-1] + 1];
        //range is final minus initial plus 1 to include both ends
        int range = finalValue - initial + 1;

        return problems.get((int)(Math.random()*range+initial));
    }

}
