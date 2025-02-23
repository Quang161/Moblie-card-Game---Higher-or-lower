package com.example.higherorlower;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

public class Menu_Game extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_game);

        // Go to Classic Mode
        Button playClassicButton = findViewById(R.id.play_classic);
        playClassicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Game.this, Classic_Mode.class);
                startActivity(intent);
            }
        });

        // Go to Endless Mode
        Button playEndlessButton = findViewById(R.id.play_endless);
        playEndlessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Game.this, Endless_Mode.class);
                startActivity(intent);
            }
        });
    }
}
