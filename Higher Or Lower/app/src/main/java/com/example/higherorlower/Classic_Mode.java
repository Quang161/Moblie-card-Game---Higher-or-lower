package com.example.higherorlower;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;


public class Classic_Mode extends Activity {
    private GameLogic gameLogic;
    private Card currentCard;
    private ImageView cardImageView;
    private ImageView resultImageView;
    private TextView livesTextView;
    private TextView roundsTextView;
    private Button higherButton;
    private Button lowerButton;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classic_mode);

        gameLogic = new GameLogic(false);
        currentCard = gameLogic.drawCard();

        cardImageView = findViewById(R.id.cardImageView);
        resultImageView = findViewById(R.id.resultImageView);
        livesTextView = findViewById(R.id.livesTextView);
        roundsTextView = findViewById(R.id.roundsTextView);
        higherButton = findViewById(R.id.higherButton);
        lowerButton = findViewById(R.id.lowerButton);
        handler = new Handler();

        updateUI();

        higherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playRound(true);
            }
        });

        lowerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playRound(false);
            }
        });
    }

    private void showResultImage(int resId) {
        resultImageView.setImageResource(resId);
        resultImageView.setVisibility(View.VISIBLE);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                resultImageView.setVisibility(View.INVISIBLE);
            }
        }, 1000);
    }

    private void playRound(boolean guessedHigher) {
        Card nextCard = gameLogic.drawCard();
        boolean correctGuess = (guessedHigher && nextCard.getValue() > currentCard.getValue()) ||
                (!guessedHigher && nextCard.getValue() < currentCard.getValue());

        if (nextCard.getValue() == currentCard.getValue()) {
            correctGuess = true;
        }

        if (!correctGuess) {
            gameLogic.loseLife();
            showResultImage(R.drawable.red_x);
        } else {
            gameLogic.addRound();
            showResultImage(R.drawable.green_check);
        }

        currentCard = nextCard;
        updateUI();

        if (gameLogic.getLives() <= 0) {
            showGameOverLostDialog();
            disableButtons();
        } else if (gameLogic.getRounds() >= GameLogic.getMaxRounds()) {
            showGameOverWinDialog();
            disableButtons();
        }
    }

    private void disableButtons() {
        higherButton.setEnabled(false);
        lowerButton.setEnabled(false);
    }

    private void updateUI() {
        String cardResourceName = getCardResourceName(currentCard);
        int resId = getResources().getIdentifier(cardResourceName, "drawable", getPackageName());
        cardImageView.setImageResource(resId);
        livesTextView.setText("Lives: " + gameLogic.getLives());
        roundsTextView.setText("Round: " + (gameLogic.getRounds()));
    }

    private String getCardResourceName(Card card) {
        String value;
        switch (card.getValue()) {
            case 11:
                value = "j";
                break;
            case 12:
                value = "q";
                break;
            case 13:
                value = "k";
                break;
            case 14:
                value = "a";
                break;
            default:
                value = String.valueOf(card.getValue());
        }
        return card.getSuit().toLowerCase() + value;
    }

    private void showGameOverWinDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("You Win")
                .setMessage("You make it to 13 round!!! Now Would you like to play again or go back to the menu?")
                .setPositiveButton("Play Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        recreate();
                    }
                })
                .setNegativeButton("Home Menu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Classic_Mode.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setCancelable(false)
                .show();
    }
    private void showGameOverLostDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game Over")
                .setMessage("You lost all lives!!! Now Would you like to play again or go back to the menu?")
                .setPositiveButton("Play Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        recreate();
                    }})
                .setNegativeButton("Home Menu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Classic_Mode.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }})
                .setCancelable(false)
                .show();
    }
}
