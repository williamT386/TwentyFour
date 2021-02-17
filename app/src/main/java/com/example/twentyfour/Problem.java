package com.example.twentyfour;

public class Problem {

    private final String[] values;
    private final String successRate;

    public Problem(int[] valuesIn, String successRateIn) {
        values = new String[4];
        for(int i = 0; i < valuesIn.length; i++)
            values[i] = "" + valuesIn[i];
        successRate = successRateIn;
    }

    public String[] getValues() {
        return values;
    }

    public String getSuccessRate() {
        return successRate;
    }
}
