package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button guessANumberButton;
    private Button wordScramblerButton;
// Ajoutez d'autres variables membres ici, selon vos besoins


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        guessANumberButton = findViewById(R.id.guessANumberButton);
        wordScramblerButton = findViewById(R.id.wordScramblerButton);

        guessANumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGuessNumber();
            }
        });

        wordScramblerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWordScrambler();
            }
        });
    }

    private void startWordScrambler() {
        Intent intent = new Intent(MainActivity.this, WordScrambler.class);
        startActivity(intent);
    }

    private void startGuessNumber() {
        Intent intent = new Intent(MainActivity.this, GuessNumber.class);
        startActivity(intent);
    }


}