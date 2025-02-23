package com.example.higherorlower;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameLogic {
    private List<Card> deck;
    private int lives;
    private int rounds;
    private int points;
    private int streak;
    private static final int MAX_ROUNDS = 13;
    private boolean endlessMode;
    public GameLogic(boolean endlessMode) {
        this.lives = 3;
        this.rounds = 1;
        this.points = 0;
        this.streak = 0;
        this.endlessMode = endlessMode;
        initializeDeck();
    }

    private void initializeDeck() {
        String[] suits = {"c", "d", "h", "s"};
        deck = new ArrayList<>();
        for (String suit : suits) {
            for (int value = 2; value <= 14; value++) {
                deck.add(new Card(suit, value));
            }
        }
        Collections.shuffle(deck);
    }

    public Card drawCard() {
        if (deck.isEmpty()) {
            initializeDeck();
        }
        return deck.remove(deck.size() - 1);
    }

    public void loseLife() {
        lives--;
    }

    public void addLife() {
        lives++;
    }

    public void addPoints() {
        int point_multiplier = (rounds / 5) + 1;
        points += 1 * point_multiplier;
    }

    public void addStreak() {
        streak++;
        if (streak % 3 == 0) {
            addLife();
            resetStreak();
        }
    }

    public void addRound(){
        rounds++;
    }

    public void resetStreak() {
        streak = 0;
    }

    public int getLives() {
        return lives;
    }

    public int getPoints() {
        return points;
    }

    public int getStreak() {
        return streak;
    }

    public int getRounds() {
        return rounds;
    }

    public static int getMaxRounds() {
        return MAX_ROUNDS;
    }

    public static void saveHighScore(Context context, int score) {
        SharedPreferences prefs = context.getSharedPreferences("Leaderboard", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        List<Integer> highScores = getHighScores(context);
        highScores.add(score);
        Collections.sort(highScores, Collections.reverseOrder());
        if (highScores.size() > 10) {
            highScores = highScores.subList(0, 10);
        }

        editor.clear().apply();
        for (int i = 0; i < highScores.size(); i++) {
            editor.putInt("score" + i, highScores.get(i));
        }
        editor.apply();
    }


    public static List<Integer> getHighScores(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("Leaderboard", Context.MODE_PRIVATE);
        List<Integer> highScores = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            int score = prefs.getInt("score" + i, 0);
            if (score > 0) {
                highScores.add(score);
            }
        }
        return highScores;
    }
}
