package com.example.twentyfour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DifficultySelect extends AppCompatActivity {

    private Button[] difficultyButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_select);
        setDifficultyButtons();
    }

    public void difficultyOnClick(View v) {
        //find which difficulty was clicked
        int index = -1;
        if(difficultyButtons[0].equals(v))
            index = 0;
        else if(difficultyButtons[1].equals(v))
            index = 1;
        else if(difficultyButtons[2].equals(v))
            index = 2;
        else if(difficultyButtons[3].equals(v))
            index = 3;

        //error checking
        if(index == -1)
            return;

        Intent changeActivity = IntentUtilities.moveActivity(this,
                EndlessActivity.class);
        changeActivity.putExtra("difficulty", index);
        startActivity(changeActivity);
        finish();
    }

    private void setDifficultyButtons() {
        //sets difficultyButtons
        difficultyButtons = new Button[4];
        difficultyButtons[0] = findViewById(R.id.novice_button);
        difficultyButtons[1] = findViewById(R.id.casual_button);
        difficultyButtons[2] = findViewById(R.id.challenging_button);
        difficultyButtons[3] = findViewById(R.id.insane_button);
    }

    public void backOnClick(View v) {
        startActivity(IntentUtilities.moveActivity(this,
                MainActivity.class));
        finish();
    }
}