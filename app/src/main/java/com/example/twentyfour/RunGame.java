package com.example.twentyfour;

import android.util.Log;

/**
 * TimedTrialsActivity.java
 * Operates the game simulation.
 *
 * @author William Tang
 * @since 2/16/2021
 */

public class RunGame {
    private String[] cardValues; //store as String to handle fractions

    public RunGame() {
        resetCardValues();
    }

    /**
     * Sets cardValues to the input.
     * @param cardValuesIn the value of the cards to set to
     */
    public void setCardValues(String[] cardValuesIn) {
        for(int i = 0; i < cardValuesIn.length; i++)
            cardValues[i] = cardValuesIn[i];
    }

    /**
     * Called when the user makes a successful move. Calculates the 
     * resulting answer of the two input values and save it in answer. 
     * Precondition: 2 cards and 1 operation are clicked
     * @param index1 the index for the first card clicked
     * @param operationIndex the index for the the operation clicked
     * @param index2 the index for the second card clicked
     */
    public String[] makeMove(int index1, int operationIndex, int index2) {
        //figure out which operation was called
        char operation;
        if(operationIndex == 0)
            operation = '+';
        else if(operationIndex == 1)
            operation = '-';
        else if(operationIndex == 2)
            operation = '*';
            //operationIndex must be 3 at this point
        else
            operation = '/';

        String answer = doCalculation(index1, operation, index2);

        //after each move, make a new array that is 1 smaller, since each
        // operation should remove exactly 1 card value
        String[] newCardValues = new String[cardValues.length-1];

        //want to change the min index to the answer and remove the max index
        int indexToChange = Math.min(index1, index2);
        int indexToRemove = Math.max(index1, index2);

        //initialize newCardValues
        int index = 0;
        for(int i = 0; i < cardValues.length; i++) {
            //if this is not the index that needs change or needs to be
            // removed, then set newCardValues to the value in cardValues
            if(i != indexToChange && i != indexToRemove) {
                newCardValues[index] = cardValues[i];
                index++;
            }
            //if this is the index that needs change, then set newCardValues
            // to answer
            else if(i == indexToChange) {
                newCardValues[index] = answer;
                index++;
            }
        }

        cardValues = newCardValues;
        return newCardValues;
    }

    /**
     * This returns the int format of a String number.
     * @param value the String value to convert
     * @return the int value
     */
    private int findIntFromString(String value) {
        int intValue = 0;
        int placeHolderCount = 1;
        int start = 0;

        //if the String value is negative, start checking from index 1
        if(value.charAt(0) == '-')
            start = 1;

        value = value.substring(start);

        //converts each digit in the String into an int and adds it in
        // the correct digit into intValue 
        for(int i = value.length() - 1; i >= 0; i--) {
            int temp = (int)(value.charAt(i)) - 48;
            intValue += temp*placeHolderCount;
            placeHolderCount *= 10;
        }
        
        //if the String was negative, then make the int negative
        if(start == 1)
            intValue = -intValue;

        return intValue;
    }
    
    /**
     * This returns the numerator in int format from a String fraction.
     * @param value the String value to convert
     * @return the int value
     */
    private int findIntFromStringNumerator(String value) {
        int numeratorInt = 0;
        int placeHolderCount = 1;
        int start = 0;
        
        //if the String value is negative, start checking from index 1
        if(value.charAt(0) == '-')
            start = 1;
        
        String numeratorString = value.substring(start, value.indexOf('/'));
        
        //converts each digit in the String into an int and adds it in
        // the correct digit into intValue 
        for(int i = numeratorString.length() - 1; i >= 0; i--) {
            int characterInIntFormat = numeratorString.charAt(i) - 48;
            numeratorInt += characterInIntFormat*placeHolderCount;
            placeHolderCount *= 10;
        }

        //if the String value is negative, make the int value negative
        if(start == 1)
            numeratorInt = -numeratorInt;

        return numeratorInt;
    }
    
    /**
     * This returns the denominator in int format from a String fraction.
     * @param value the String value to convert
     * @return the int value
     */
    private int findIntFromStringDenominator(String value) {
        int denominatorInt = 0;
        int placeHolderCount = 1;
        
        String denominatorString = value.substring(value.indexOf('/') + 1);

        //converts each digit in the String into an int and adds it in
        // the correct digit into intValue 
        for(int i = denominatorString.length() - 1; i >= 0; i--) {
            int characterInIntFormat = denominatorString.charAt(i) - 48;
            denominatorInt += characterInIntFormat*placeHolderCount;
            placeHolderCount *= 10;
        }
        
        return denominatorInt;
    }

    /**
     * Gets the firstValue, numeratorIntFirst, denominatorIntFirst,
     * secondValue, numeratorIntSecond, and denominatorIntSecond, where
     * applicable.
     * @param index1 the index for the first card clicked
     * @param index2 the index for the second card clicked
     * @return an int array of all the values to get
     */
    private int[] getValues(int index1, int index2) {
        int firstValue, numeratorIntFirst, denominatorIntFirst, secondValue,
                numeratorIntSecond, denominatorIntSecond;
        firstValue = numeratorIntFirst = denominatorIntFirst = secondValue =
                numeratorIntSecond = denominatorIntSecond = 0;

        //if it is not a fraction, the int is just the entire string
        if(cardValues[index1].indexOf('/') == -1)
            firstValue = findIntFromString(cardValues[index1]);
        //if it is a fraction, the the numerators and denominators are added
        else {
            numeratorIntFirst = findIntFromStringNumerator(cardValues[index1]);
            denominatorIntFirst =
                    findIntFromStringDenominator(cardValues[index1]);
        }

        //if it is not a fraction, the int is just the entire string
        if(cardValues[index2].indexOf('/') == -1)
            secondValue = findIntFromString(cardValues[index2]);
        //if it is a fraction, the the numerators and denominators are added
        else {
            numeratorIntSecond = findIntFromStringNumerator(cardValues[index2]);
            denominatorIntSecond =
                    findIntFromStringDenominator(cardValues[index2]);
        }

        return new int[]{firstValue, numeratorIntFirst, denominatorIntFirst,
                secondValue, numeratorIntSecond, denominatorIntSecond};
    }

    /**
     * Calls the corresponding method for arithmetic depending on what the 
     * operation chosen was.
     * @param index1 the index for the first card clicked
     * @param operation the operation clicked
     * @param index2 the index for the second card clicked
     * @return the answer of the calculation
     */
    private String doCalculation(int index1, char operation, int index2) {
        int[] values = getValues(index1, index2);

        String answer = null;
        switch (operation) {
            case '+':
                answer = doAddition(values[0], values[1], values[2],
                        values[3], values[4], values[5]);
                break;
            case '-':
                answer = doSubtraction(values[0], values[1], values[2],
                        values[3], values[4], values[5]);
                break;
            case '*':
                answer = doMultiplication(values[0], values[1], values[2],
                        values[3], values[4], values[5]);
                break;
            case '/':
                answer = doDivision(values[0], values[1], values[2],
                        values[3], values[4], values[5]);
                break;
        }

        //if the number is a fraction with a negative sign in the denominator
        if(answer.contains("/-"))
            answer = negativeSignMover(answer);
        //if the number is a fraction
        if(answer.indexOf('/') != -1)
            answer = fractionSimplifier(answer);

        return answer;
    }

    /**
     * Called when the user wants to add 2 values.
     * @param firstValue            first value, if applicable
     * @param numeratorIntFirst     numerator of first value, if applicable
     * @param denominatorIntFirst   denominator of first value, if applicable
     * @param secondValue           second value, if applicable
     * @param numeratorIntSecond    numerator of second value, if applicable
     * @param denominatorIntSecond  denominator of second value, if applicable
     * @return the answer
     */
    private String doAddition(int firstValue, int numeratorIntFirst,
                            int denominatorIntFirst, int secondValue,
                            int numeratorIntSecond, int denominatorIntSecond) {
        String answer;
        int answerNumerator;
        int answerDenominator;

        if(numeratorIntFirst == 0 && numeratorIntSecond == 0)
            answer = firstValue + secondValue + "";
        else if (numeratorIntFirst != 0 && numeratorIntSecond == 0) {
            answerNumerator = numeratorIntFirst + secondValue * denominatorIntFirst;
            answerDenominator = denominatorIntFirst;
            answer = "" + answerNumerator + "/" + answerDenominator;
        }
        //assume numeratorIntSecond is not 0, since already checked
        else if (numeratorIntFirst == 0) {
            answerNumerator = firstValue * denominatorIntSecond + numeratorIntSecond;
            answerDenominator = denominatorIntSecond;
            answer = "" + answerNumerator + "/" + answerDenominator;
        }
        else {
            numeratorIntFirst *= denominatorIntSecond;
            numeratorIntSecond *= denominatorIntFirst;
            denominatorIntFirst *= denominatorIntSecond;
            answerNumerator = numeratorIntFirst * denominatorIntSecond + numeratorIntSecond;
            answerDenominator = denominatorIntFirst;
            answer = "" + answerNumerator + "/" + answerDenominator;
        }

        return answer;
    }

    /**
     * Called when the user wants to subtract 2 values.
     * @param firstValue            first value, if applicable
     * @param numeratorIntFirst     numerator of first value, if applicable
     * @param denominatorIntFirst   denominator of first value, if applicable
     * @param secondValue           second value, if applicable
     * @param numeratorIntSecond    numerator of second value, if applicable
     * @param denominatorIntSecond  denominator of second value, if applicable
     * @return the answer
     */
    private String doSubtraction(int firstValue, int numeratorIntFirst,
                                 int denominatorIntFirst, int secondValue,
                                 int numeratorIntSecond,
                                 int denominatorIntSecond) {
        String answer;
        int answerNumerator;
        int answerDenominator;

        if(numeratorIntFirst == 0 && numeratorIntSecond == 0)
            answer = firstValue - secondValue + "";
        else if (numeratorIntFirst != 0 && numeratorIntSecond == 0) {
            answerNumerator = numeratorIntFirst - secondValue * denominatorIntFirst;
            answerDenominator = denominatorIntFirst;
            answer = "" + answerNumerator + "/" + answerDenominator;
        }
        //assume numeratorIntSecond is not 0, since already checked
        else if (numeratorIntFirst == 0) {
            answerNumerator = firstValue * denominatorIntSecond - numeratorIntSecond;
            answerDenominator = denominatorIntSecond;
            answer = "" + answerNumerator + "/" + answerDenominator;
        }
        else {
            numeratorIntFirst *= denominatorIntSecond;
            numeratorIntSecond *= denominatorIntFirst;
            denominatorIntFirst *= denominatorIntSecond;
            answerNumerator = numeratorIntFirst - numeratorIntSecond;
            answerDenominator = denominatorIntFirst;
            answer = "" + answerNumerator + "/" + answerDenominator;
        }

        return answer;
    }

    /**
     * Called when the user wants to multiply 2 values.
     * @param firstValue            first value, if applicable
     * @param numeratorIntFirst     numerator of first value, if applicable
     * @param denominatorIntFirst   denominator of first value, if applicable
     * @param secondValue           second value, if applicable
     * @param numeratorIntSecond    numerator of second value, if applicable
     * @param denominatorIntSecond  denominator of second value, if applicable
     * @return the answer
     */
    private String doMultiplication(int firstValue, int numeratorIntFirst,
                                 int denominatorIntFirst, int secondValue,
                                 int numeratorIntSecond,
                                 int denominatorIntSecond) {
        String answer;
        int answerNumerator;
        int answerDenominator;
        if(numeratorIntFirst == 0 && numeratorIntSecond == 0)
            answer = firstValue * secondValue + "";
        else if (numeratorIntFirst != 0 && numeratorIntSecond == 0) {
            answerNumerator = numeratorIntFirst * secondValue;
            answerDenominator = denominatorIntFirst;
            answer = "" + answerNumerator + "/" + answerDenominator;
        }
        //assume numeratorIntSecond is not 0, since already checked
        else if (numeratorIntFirst == 0) {
            answerNumerator = firstValue * numeratorIntSecond;
            answerDenominator = denominatorIntSecond;
            answer = "" + answerNumerator + "/" + answerDenominator;
        }
        else {
            answerNumerator = numeratorIntFirst * numeratorIntSecond;
            answerDenominator = denominatorIntFirst * denominatorIntSecond;
            answer = "" + answerNumerator + "/" + answerDenominator;
        }

        return answer;
    }

    /**
     * Called when the user wants to divide 2 values.
     * @param firstValue            first value, if applicable
     * @param numeratorIntFirst     numerator of first value, if applicable
     * @param denominatorIntFirst   denominator of first value, if applicable
     * @param secondValue           second value, if applicable
     * @param numeratorIntSecond    numerator of second value, if applicable
     * @param denominatorIntSecond  denominator of second value, if applicable
     * @return the answer
     */
    private String doDivision(int firstValue, int numeratorIntFirst,
                                    int denominatorIntFirst, int secondValue,
                                    int numeratorIntSecond,
                                    int denominatorIntSecond) {
        String answer;
        int answerNumerator;
        int answerDenominator;

        if(numeratorIntFirst == 0 && numeratorIntSecond == 0)
            answer = "" + firstValue + "/" + secondValue;
            //assume numeratorIntSecond is not 0, since already checked
        else if(numeratorIntFirst == 0 && denominatorIntSecond == 0) {
            //value is impossible, so set answer to a nonsense value
            answer = "1/0";
        }
        else if (numeratorIntFirst != 0 && numeratorIntSecond == 0) {
            answerNumerator = numeratorIntFirst;
            answerDenominator = denominatorIntFirst * secondValue;
            answer = "" + answerNumerator + "/" + answerDenominator;
        }
        //assume numeratorIntSecond is not 0, since already checked
        else if (numeratorIntFirst == 0) {
            answerNumerator = firstValue * denominatorIntSecond;
            answerDenominator = numeratorIntSecond;
            answer = "" + answerNumerator + "/" + answerDenominator;
        }
        else {
            answerNumerator = numeratorIntFirst * denominatorIntSecond;
            answerDenominator = denominatorIntFirst * numeratorIntSecond;
            answer = "" + answerNumerator + "/" + answerDenominator;
        }

        return answer;
    }

    /**
     * This moves the negative sign to the front of a fraction if the
     * negative sign is in the denominator.
     * Precondition: this is a fraction with a negative sign in the denominator
     * @param toChange the String to change
     * @return the String after being changed
     */
    private String negativeSignMover(String toChange) {
        return ("-" + toChange.substring(0,toChange.indexOf('/')) +
                "/" + toChange.substring(toChange.indexOf('/') + 2));
    }

    /**
     * This simplifies a fraction, if possible.
     * Precondition: this is a fraction
     * @param toChange the String to change
     * @return the String after being changed
     */
    private String fractionSimplifier(String toChange) {
        String answer;

        //get the numerator of toChange
        int changeNumerator = findIntFromStringNumerator(toChange);
        //get the denominator of toChange
        int changeDenominator = findIntFromStringDenominator(toChange);
        boolean breakOut = false;
        boolean changeBreakOut = true;

        //if the numerator is greater than the denominator, simplify the
        // fraction
        if(changeNumerator > changeDenominator) {
            while(changeDenominator != 1 && !breakOut) {
                for(int i = 2; i <= changeDenominator; i++) {
                    if(1.0*changeNumerator/i-changeNumerator/i == 0.0
                            && 1.0*changeDenominator/i-changeDenominator/i == 0.0) {
                        changeNumerator /= i;
                        changeDenominator /= i;
                        i = changeDenominator;
                        changeBreakOut = false;
                    }
                    else
                        changeBreakOut = true;
                }
                if(changeBreakOut)
                    breakOut = true;
            }
            answer = "" + changeNumerator + "/" + changeDenominator;
        }
        //special case if the changeNumerator and changeDenominator are the
        // same and same sign
        else if(changeNumerator == changeDenominator)
            answer = "1";
        //special case if the changeNumerator and changeDenominator are the
        // same but opposite signs
        else if(changeNumerator == -changeDenominator)
            answer = "-1";
        //if the denominator is greater than the numerator, simplify the
        // fraction
        else {
            while(changeDenominator != 1 && !breakOut) {
                for(int i = 2; i <= changeNumerator; i++) {
                    if(1.0*changeNumerator/i-changeNumerator/i == 0.0 &&
                            1.0*changeDenominator/i-changeDenominator/i == 0.0) {
                        changeNumerator /= i;
                        changeDenominator /= i;
                        i = changeNumerator;
                        changeBreakOut = false;
                    }
                    else
                        changeBreakOut = true;
                }
                if(changeBreakOut)
                    breakOut = true;
            }
            answer = "" + changeNumerator + "/" + changeDenominator;
        }

        //if the changeDenominator is 1, it's no longer a fraction, so
        // ignore the changeDenominator
        if(changeDenominator == 1)
            answer = "" + changeNumerator;

        return answer;
    }

    /**
     * Resets cardValues to a new String array of length 4, holding null.
     */
    public void resetCardValues() {
        cardValues = new String[4];
    }
}
