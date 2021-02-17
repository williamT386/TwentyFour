package com.example.twentyfour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * InstructionsActivity.java
 * Operates the Instructions gamemode.
 *
 * @author William Tang
 * @since 2/16/2021
 */

public class InstructionsActivity extends AppCompatActivity {

    private TextView instructions;
    private Button backButton, nextButton;
    private int instructionsPage = 1; //the default page to start on

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        setAllView();

        backButton.setVisibility(View.GONE);

        Context context = this.getApplicationContext();
        //set the test of instructions to the first text
        instructions.setText(context.getResources().getString(R.string.instructions_text_1));
    }

    /**
     * Sets the view by the id for instructions, backButton, and nextButton.
     */
    private void setAllView() {
        instructions = findViewById(R.id.instructions_text);
        backButton = findViewById(R.id.back_button);
        nextButton = findViewById(R.id.next_button);
    }

    /**
     * Called when the next button is clicked. Changes the text to display
     * and the instructionsPage number.
     * @param v the view for the next button
     */
    public void nextOnClick(View v) {
        switch (instructionsPage) {
            case 1:
                instructions.setText(this.getApplicationContext().getResources().
                        getString(R.string.instructions_text_2));
                //show the backButton
                backButton.setVisibility(View.VISIBLE);
                instructionsPage = 2;
                break;
            case 2:
                instructions.setText(this.getApplicationContext().getResources().
                        getString(R.string.instructions_text_3));
                instructionsPage = 3;
                //change the text of the nextButton to "Demo"
                nextButton.setText(R.string.demo);
                break;
            case 3:
                //hide the nextButton and instructions
                nextButton.setVisibility(View.GONE);
                instructions.setVisibility(View.GONE);

                instructionsPage = 4;
                //TODO - show demo
                break;
        }
    }

    /**
     * Called when the back button is clicked. Changes the text to display
     * and the instructionsPage number.
     * @param v the view for the back button
     */
    public void backOnClick(View v) {
        switch (instructionsPage) {
            case 2:
                instructions.setText(this.getApplicationContext().getResources().
                        getString(R.string.instructions_text_1));
                instructionsPage = 1;

                //hides the backButton
                backButton.setVisibility(View.GONE);
                break;
            case 3:
                instructions.setText(this.getApplicationContext().getResources().
                        getString(R.string.instructions_text_2));
                instructionsPage = 2;

                //changes the text of the nextButton to "Next", if not
                // already changed
                nextButton.setText(R.string.next);
                break;
            case 4:
                instructions.setText(this.getApplicationContext().getResources().
                        getString(R.string.instructions_text_3));
                instructionsPage = 3;

                //show the instructions and nextButton
                instructions.setVisibility(View.VISIBLE);
                nextButton.setVisibility(View.VISIBLE);
                break;
        }
    }


}