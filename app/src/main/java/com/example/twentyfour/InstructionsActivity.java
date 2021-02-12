package com.example.twentyfour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

public class InstructionsActivity extends AppCompatActivity {

    private TextView instructions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        instructions = findViewById(R.id.instructions_text);
        Context context = this.getApplicationContext();
        instructions.setText(context.getResources().getString(R.string.instructions_text_1));
//        instructions.setLayoutParams(new LayoutParams(width, height));

    }
}