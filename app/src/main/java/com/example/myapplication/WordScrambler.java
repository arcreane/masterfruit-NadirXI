package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WordScrambler extends AppCompatActivity {
    private List<String> wordList;
    private String currentWord;
    private String scrambledWord;
    private int timeLimit;
    private int score;

    private TextView scrambledWordTextView;
    private EditText guessInput;
    private Button guessButton;
    private TextView scoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_scrambler);

        scrambledWordTextView = findViewById(R.id.textView_scrambled_word);
        guessInput = findViewById(R.id.editText_guess);
        guessButton = findViewById(R.id.button_guess);
        scoreTextView = findViewById(R.id.textView_score);

        wordList = new ArrayList<>(Arrays.asList("apple", "banana", "orange", "grape", "kiwi")); // Liste de mots à deviner (modifiable selon tes besoins)
        timeLimit = 30; // Temps limite pour deviner le mot (modifiable selon tes besoins)

        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateGuess();
            }
        });

        startNewGame();
    }

    private void startNewGame() {
        if (wordList.isEmpty()) {
            Toast.makeText(this, "No words available.", Toast.LENGTH_SHORT).show();
            finish(); // Fin de l'activité si la liste de mots est vide
            return;
        }

        // Choix aléatoire d'un mot de la liste
        Random random = new Random();
        int index = random.nextInt(wordList.size());
        currentWord = wordList.get(index);
        wordList.remove(index);

        // Mélange des lettres du mot
        scrambledWord = scrambleWord(currentWord);

        // Affichage du mot mélangé
        scrambledWordTextView.setText(scrambledWord);

        // Réinitialisation de la saisie et du score
        guessInput.setText("");
        score = 0;
        scoreTextView.setText("Score: 0");

        // Démarre le compte à rebours
        startTimer();
    }

    private void validateGuess() {
        String guess = guessInput.getText().toString();

        if (!guess.isEmpty()) {
            if (guess.equalsIgnoreCase(currentWord)) {
                // Mot deviné correctement
                score += timeLimit;
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show();
            }

            // Mise à jour du score
            scoreTextView.setText("Score: " + score);

            // Démarrage d'un nouveau tour
            startNewGame();
        }
    }

    private String scrambleWord(String word) {
        List<Character> characters = new ArrayList<>();
        for (char c : word.toCharArray()) {
            characters.add(c);
        }

        StringBuilder scrambled = new StringBuilder();
        while (!characters.isEmpty()) {
            int randomIndex = new Random().nextInt(characters.size());
            scrambled.append(characters.remove(randomIndex));
        }

        return scrambled.toString();
    }

    private void startTimer() {
        // Implémentation du compte à rebours
        // Ici, tu peux utiliser un CountDownTimer ou une autre méthode pour gérer le temps limite
        // et effectuer les actions appropriées lorsque le temps est écoulé.
    }
}
