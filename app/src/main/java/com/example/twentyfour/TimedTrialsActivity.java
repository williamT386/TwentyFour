package com.example.twentyfour;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * TimedTrialsActivity.java
 * Operates the Timed Trials gamemode.
 * TODO - implement timer
 *
 * @author William Tang
 * @since 2/16/2021
 */

public class TimedTrialsActivity extends AppCompatActivity {

    private RelativeLayout[] cardLayouts, operationLayouts;
    private Button[] cards, operations;
    private boolean[] cardsClicked, operationsClicked;
    private String[] cardValues, cardValuesOriginal; //store as String to handle fractions
    private boolean firstCardClicked, oneOperationClicked, secondCardClicked;
    private int firstCardIndex, oneOperationIndex, secondCardIndex;
    private RunGame runGame;
    private boolean winRound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timed_trials);
        setAllView();

        //create a RunGame instance
        runGame = new RunGame();

        startRound();
    }

    /**
     * Starts a round. Set the cardValuesOriginal, cardValues, runGame's
     * cardValues, and the text for the cards. Resets cardsClicked,
     * operationsClicked, firstCardClicked, oneOperationClicked, and
     * secondCardClicked.
     */
    private void startRound() {
        winRound = false;

        //TODO - temporary. replace reading from file capability
        cardValuesOriginal = new String[]{"1", "1", "11", "11"};
        cardValues = new String[4];

        //set cardValues to store the values of cardValuesOriginal
        for(int i = 0; i < cardValuesOriginal.length; i++)
            cardValues[i] = cardValuesOriginal[i];
        runGame.setCardValues(cardValuesOriginal);
        setCardValues();

        cardsClicked = new boolean[]{false, false, false, false};
        operationsClicked = new boolean[]{false, false, false, false};
        firstCardClicked = oneOperationClicked = secondCardClicked = false;


    }

    /**
     * Sets the variables to store the views for cardLayouts, cards,
     * operationsLayouts, and operations.
     */
    private void setAllView() {
        //sets cardLayouts
        cardLayouts = new RelativeLayout[4];
        cardLayouts[0] = findViewById(R.id.card_layout_0);
        cardLayouts[1] = findViewById(R.id.card_layout_1);
        cardLayouts[2] = findViewById(R.id.card_layout_2);
        cardLayouts[3] = findViewById(R.id.card_layout_3);

        //sets cards
        cards = new Button[4];
        cards[0] = findViewById(R.id.card_0);
        cards[1] = findViewById(R.id.card_1);
        cards[2] = findViewById(R.id.card_2);
        cards[3] = findViewById(R.id.card_3);

        //sets operationLayouts
        operationLayouts = new RelativeLayout[4];
        operationLayouts[0] = findViewById(R.id.addition_layout);
        operationLayouts[1] = findViewById(R.id.subtraction_layout);
        operationLayouts[2] = findViewById(R.id.multiplication_layout);
        operationLayouts[3] = findViewById(R.id.division_layout);

        //sets operations
        operations = new Button[4];
        operations[0] = findViewById(R.id.addition_button);
        operations[1] = findViewById(R.id.subtraction_button);
        operations[2] = findViewById(R.id.multiplication_button);
        operations[3] = findViewById(R.id.division_button);
    }

    /**
     * Sets the text of the cards to cardValues and hides cards without a
     * value. Checks if won, and if so, changes winRound to true and makes a
     * Toast tellin the user that they won.
     */
    private void setCardValues() {
        //set the text of the cards to the cardValues
        for(int i = 0; i < cardValues.length; i++)
            cards[i].setText(cardValues[i]);

        //hide the remaining cards without a value
        for(int i = cardValues.length; i < 4; i++)
            cards[i].setVisibility(View.GONE);

        //check if user won. happens if there is only 1 cardValue left and
        // it is equal to "24"
        if(cardValues.length == 1 && "24".equals(cardValues[0])) {
            winRound = true;

            //TODO - replace win info with design element instead of Toast
            Toast.makeText(getApplicationContext(),
                    "You win this round!",
                    Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Finds the card that was clicked. Depending on the situation, allow
     * that card to be clicked or not clicked. If the card that was clicked
     * was the second card clicked and has an operation clicked, make a
     * move, reset the clicks, and set the card values again.
     * @param v the view of the card
     */
    public void cardOnClick(View v) {
        //find which card was clicked
        int index = -1;
        if(cards[0].equals(v))
            index = 0;
        else if(cards[1].equals(v))
            index = 1;
        else if(cards[2].equals(v))
            index = 2;
        else if(cards[3].equals(v))
            index = 3;

        //error checking
        if(index == -1)
            return;

        if(cardsClicked[index] && firstCardClicked) {
            //change button to unclicked
            cardLayouts[index].setBackgroundColor(Color.TRANSPARENT);
            cardsClicked[index] = false;
            firstCardClicked = false;
            firstCardIndex = -1;
        }
        else if(!firstCardClicked) {
            //change the first card to clicked
            cardLayouts[index].setBackgroundColor(Color.parseColor(
                    "#0D98BA"));
            cardsClicked[index] = true;
            firstCardClicked = true;
            firstCardIndex = index;
        }
        //assume firstCardClicked, since already checked if not
        else if(oneOperationClicked && !secondCardClicked) {
            //change the second card to clicked
            cardLayouts[index].setBackgroundColor(Color.parseColor(
                    "#000000"));
            cardsClicked[index] = true;
            secondCardClicked = true;
            secondCardIndex = index;

            cardValues = runGame.makeMove(firstCardIndex,
                    oneOperationIndex, secondCardIndex);

            //reset the clicks before setting values so that that indices
            // are not (possibly?) shifted during setCardValues
            resetClicks();
            setCardValues();
        }
        //assume firstCardClicked, since already checked if not
        else if(!oneOperationClicked && !secondCardClicked) {
            //tell the user to click an operation first
            Toast.makeText(getApplicationContext(),
                                    "Click an operation first.",
                                    Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Finds the operation that was clicked. Depending on the situation, allow
     * that operation to be clicked or not clicked.
     * @param v the view of the card
     */
    public void operationOnClick(View v) {
        //find which operation was clicked
        int index = -1;
        if(operations[0].equals(v))
            index = 0;
        else if(operations[1].equals(v))
            index = 1;
        else if(operations[2].equals(v))
            index = 2;
        else if(operations[3].equals(v))
            index = 3;

        if(index != -1) {
            if(!operationsClicked[index]) {
                //button click fails if there is already another choice pressed
                if(!oneOperationClicked && firstCardClicked) {
                    //if no other button is clicked and the first card is
                    // clicked, then change this button to clicked
                    operationLayouts[index].setBackgroundColor(Color.parseColor(
                            "#0D98BA"));
                    operationsClicked[index] = true;
                    oneOperationClicked = true;
                    oneOperationIndex = index;
                }
                //tell the user to click a card
                else if(!firstCardClicked) {
                    Toast.makeText(getApplicationContext(),
                            "Click a card first.",
                            Toast.LENGTH_SHORT).show();
                }
                //tell the user that another operation is already clicked
                else
                    Toast.makeText(getApplicationContext(),
                            "Another choice already clicked.",
                            Toast.LENGTH_SHORT).show();
            }
            //change operation to unclicked
            else {
                operationLayouts[index].setBackgroundColor(Color.TRANSPARENT);
                operationsClicked[index] = false;
                oneOperationClicked = false;
                oneOperationIndex = -1;
            }
        }
    }

    /**
     * Resets the first card, operation, and second card to all being not
     * clicked, and removes their backgrounds.
     */
    private void resetClicks() {
        //reset the first card
        cardLayouts[firstCardIndex].setBackgroundColor(Color.TRANSPARENT);
        cardsClicked[firstCardIndex] = false;
        firstCardClicked = false;

        //reset the operation
        operationLayouts[oneOperationIndex].setBackgroundColor(Color.TRANSPARENT);
        operationsClicked[oneOperationIndex] = false;
        oneOperationClicked = false;

        //reset the second card
        cardLayouts[secondCardIndex].setBackgroundColor(Color.TRANSPARENT);
        cardsClicked[secondCardIndex] = false;
        secondCardClicked = false;
    }
}