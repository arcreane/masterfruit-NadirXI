package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GuessNumber extends AppCompatActivity {
    private enum Difficulty { EASY, MEDIUM, HARD }

    private Difficulty difficulty;
    private EditText guessInput;
    private Button guessButton;
    private ProgressBar progressBar;
    private TextView attemptsText;
    private ListView guessesList;
    private CheckBox changeDifficultyCheckbox;

    private int targetNumber;
    private int attemptsLeft;
    private int score;
    private ArrayList<String> guesses;
    private ArrayAdapter<String> guessesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_number);

        guessInput = findViewById(R.id.editText_guess);
        guessButton = findViewById(R.id.button_guess);
        progressBar = findViewById(R.id.progressBar);
        attemptsText = findViewById(R.id.textView_attempts);
        guessesList = findViewById(R.id.listView_guesses);
        changeDifficultyCheckbox = findViewById(R.id.checkbox_change_difficulty);

        guessInput.setVisibility(View.GONE);
        guessButton.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        attemptsText.setVisibility(View.GONE);
        guessesList.setVisibility(View.GONE);
        changeDifficultyCheckbox.setVisibility(View.GONE);

        RadioGroup difficultyRadioGroup = findViewById(R.id.radioGroup_difficulty);
        difficultyRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButton_easy) {
                    difficulty = Difficulty.EASY;
                } else if (checkedId == R.id.radioButton_medium) {
                    difficulty = Difficulty.MEDIUM;
                } else if (checkedId == R.id.radioButton_hard) {
                    difficulty = Difficulty.HARD;
                }
                showGameElements();
            }
        });

        guesses = new ArrayList<>();
        guessesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, guesses);
        guessesList.setAdapter(guessesAdapter);

        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateGuess();
            }
        });
    }

    private void showGameElements() {
        guessInput.setVisibility(View.VISIBLE);
        guessButton.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        attemptsText.setVisibility(View.VISIBLE);
        guessesList.setVisibility(View.VISIBLE);
    }

    private void validateGuess() {
        String guessText = guessInput.getText().toString();

        if (!guessText.isEmpty()) {
            int guess = Integer.parseInt(guessText);

            if (guess == targetNumber) {
                score += attemptsLeft;
                showSuccessMessage();
                startNewGame();
            } else {
                if (guess < targetNumber) {
                    showMessage("Too low!");
                } else {
                    showMessage("Too high!");
                }

                attemptsLeft--;
                updateAttemptsText();
                progressBar.setProgress(attemptsLeft);
                guesses.add("Guess: " + guessText);
                guessesAdapter.notifyDataSetChanged();

                if (attemptsLeft == 0) {
                    showGameOverMessage();
                    startNewGame();
                }
            }

            guessInput.setText("");
        }
    }

    private int generateTargetNumber() {
        int min, max;

        switch (difficulty) {
            case EASY:
                min = 1;
                max = 10;
                break;
            case MEDIUM:
                min = 1;
                max = 50;
                break;
            case HARD:
                min = 1;
                max = 100;
                break;
            default:
                min = 1;
                max = 10;
                break;
        }

        return (int) (Math.random() * (max - min + 1) + min);
    }

    private void updateAttemptsText() {
        attemptsText.setText("Attempts left: " + attemptsLeft);
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showSuccessMessage() {
        Toast.makeText(this, "Congratulations! You guessed the number.", Toast.LENGTH_SHORT).show();
    }

    private void showGameOverMessage() {
        Toast.makeText(this, "Game Over. Better luck next time!", Toast.LENGTH_SHORT).show();
    }

    private void startNewGame() {
        changeDifficultyCheckbox.setVisibility(View.VISIBLE);
        targetNumber = generateTargetNumber();
        attemptsLeft = 10;
        score = 0;
        guesses.clear();
        guessesAdapter.notifyDataSetChanged();
        updateAttemptsText();
        progressBar.setMax(attemptsLeft);
        progressBar.setProgress(attemptsLeft);
        guessInput.setText("");
    }
}
