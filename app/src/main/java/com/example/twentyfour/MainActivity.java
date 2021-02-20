package com.example.twentyfour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * MainActivity.java
 * Displays the 4 different gamemodes. Depending on what button for the
 * gamemode that the user clicks, starts that corresponding Activity.
 *
 * @author William Tang
 * @since 2/16/2021
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataFromFile.readDataFromFile(this);
    }

    /**
     * Called when the user clicks the level select button. Starts the
     * activity for LevelSelectActivity.
     * @param v the view for the level select button
     */
    public void levelSelectOnClick(View v) {
        startActivity(IntentUtilities.moveActivity(this,
                LevelSelectActivity.class));
    }

    /**
     * Called when the user clicks the time trials button. Starts the
     * activity for TimedTrialsActivity.
     * @param v the view for the timed trials button
     */
    public void timedTrialsOnClick(View v) {
        startActivity(IntentUtilities.moveActivity(this,
                TimedTrialsActivity.class));
    }

    /**
     * Called when the user clicks the endless button. Starts the
     * activity for EndlessActivity.
     * @param v the view for the endless button
     */
    public void endlessOnClick(View v) {
        startActivity(IntentUtilities.moveActivity(this,
                DifficultySelect.class));
    }

    /**
     * Called when the user clicks the instructions button. Starts the
     * activity for InstructionsActivity.
     * @param v the view for the instructions button
     */
    public void instructionsOnClick(View v) {
        startActivity(IntentUtilities.moveActivity(this,
                InstructionsActivity.class));
    }
}