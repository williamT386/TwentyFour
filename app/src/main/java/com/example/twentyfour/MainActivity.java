package com.example.twentyfour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void levelSelectOnClick(View v) {
        Intent intent = IntentUtilities.moveActivity(this,
                LevelSelectActivity.class);
        startActivity(intent);
    }

    public void timedTrialsOnClick(View v) {
        Intent intent = IntentUtilities.moveActivity(this,
                TimedTrialsActivity.class);
        startActivity(intent);
    }

    public void endlessOnClick(View v) {
        Intent intent = IntentUtilities.moveActivity(this,
                EndlessActivity.class);
        startActivity(intent);
    }

    public void instructionsOnClick(View v) {
        Intent intent = IntentUtilities.moveActivity(this,
                InstructionsActivity.class);
        startActivity(intent);
    }
}