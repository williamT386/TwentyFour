package com.example.twentyfour;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * TimedTrialsActivity.java
 * Operates the Timed Trials gamemode.
 *
 * @author William Tang
 * @since 2/16/2021
 */

public class TimedTrialsActivity extends AppCompatActivity {

    private RelativeLayout[] cardLayouts, operationLayouts;
    private Button[] cards, operations;
    private boolean[] cardsClicked, operationsClicked;
    private int[] cardValues, cardValuesOriginal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timed_trials);
        setAllView();

        setCardValues();

        //sets the text of the cards to the cardValues

        cardsClicked = new boolean[]{false, false, false, false};
        operationsClicked = new boolean[]{false, false, false, false};
    }

    /**
     * Sets the variables to store the views for cardLayouts, cards,
     * operationsLayouts, and operations.
     */
    private void setAllView() {
        cardLayouts = new RelativeLayout[4];
        cardLayouts[0] = findViewById(R.id.card_layout_0);
        cardLayouts[1] = findViewById(R.id.card_layout_1);
        cardLayouts[2] = findViewById(R.id.card_layout_2);
        cardLayouts[3] = findViewById(R.id.card_layout_3);

        cards = new Button[4];
        cards[0] = findViewById(R.id.card_0);
        cards[1] = findViewById(R.id.card_1);
        cards[2] = findViewById(R.id.card_2);
        cards[3] = findViewById(R.id.card_3);

        operationLayouts = new RelativeLayout[4];
        operationLayouts[0] = findViewById(R.id.addition_layout);
        operationLayouts[1] = findViewById(R.id.subtraction_layout);
        operationLayouts[2] = findViewById(R.id.multiplication_layout);
        operationLayouts[3] = findViewById(R.id.division_layout);

        operations = new Button[4];
        operations[0] = findViewById(R.id.addition_button);
        operations[1] = findViewById(R.id.subtraction_button);
        operations[2] = findViewById(R.id.multiplication_button);
        operations[3] = findViewById(R.id.division_button);
    }

    /**
     * Sets the text of the cards to cardValues.
     */
    private void setCardValues() {
        //TODO - temporary
        cardValuesOriginal = new int[]{1, 1, 11, 11};
        cardValues = new int[4];

        //set cardValues to store the values of cardValuesOriginal
        for(int i = 0; i < cardValuesOriginal.length; i++)
            cardValues[i] = cardValuesOriginal[i];

        //sets the text of the cards to the cardValues
        for(int i = 0; i < cardValues.length; i++)
            cards[i].setText("" + cardValues[i]);
    }

    /**
     * Finds the card that was clicked. If that card is already clicked,
     * then unclick it. Otherwise, if there are other cards that are
     * clicked, tell the user that is not allowed. If there are no other
     * cards that are clicked, then set that card to clicked.
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

        if(index != -1) {
            if(!cardsClicked[index]) {
                //button click fails if there is already another choice pressed
                boolean canClick = true;
                for (boolean isClicked : cardsClicked) {
                    //checks if any card is already clicked
                    if (isClicked) {
                        canClick = false;
                        break;
                    }
                }

                //if no other button is clicked, then change this button to
                // clicked
                if(canClick) {
                    cardLayouts[index].setBackgroundColor(Color.parseColor("#0D98BA"));
                    cardsClicked[index] = true;
                }
                //tell the user that another button is already clicked
                else
                    Toast.makeText(getApplicationContext(),
                            "Another choice already clicked.",
                            Toast.LENGTH_SHORT).show();
            }
            //change button to unclicked
            else {
                cardLayouts[index].setBackgroundColor(Color.TRANSPARENT);
                cardsClicked[index] = false;
            }
        }
    }

    /**
     * Finds the operation that was clicked. If that operation is already
     * clicked, then unclick it. Otherwise, if there are other operation
     * that are clicked, tell the user that is not allowed. If there are no
     * other operation that are clicked, then set that operation to clicked.
     * @param v the view of the operation
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
                boolean canClick = true;
                for (boolean isClicked : operationsClicked) {
                    //checks if any operation is already clicked
                    if (isClicked) {
                        canClick = false;
                        break;
                    }
                }

                //if no other button is clicked, then change this button to
                // clicked
                if(canClick) {
                    operationLayouts[index].setBackgroundColor(Color.parseColor(
                            "#0D98BA"));
                    operationsClicked[index] = true;
                }
                //tell the user that another button is already clicked
                else
                    Toast.makeText(getApplicationContext(),
                            "Another choice already clicked.",
                            Toast.LENGTH_SHORT).show();
            }
            //change button to unclicked
            else {
                operationLayouts[index].setBackgroundColor(Color.TRANSPARENT);
                operationsClicked[index] = false;
            }
        }
    }
}