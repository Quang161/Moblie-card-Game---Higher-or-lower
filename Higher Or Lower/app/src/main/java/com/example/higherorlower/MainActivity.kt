package com.example.higherorlower

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var soundToggle: ToggleButton
    lateinit var mediaPlayer: MediaPlayer
    val soundOnDrawable = R.drawable.soundon
    val soundOffDrawable = R.drawable.soundoff

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mediaPlayer = MediaPlayer.create(this, R.raw.versace)
        mediaPlayer.isLooping = true
        mediaPlayer.start()
        soundToggle = findViewById(R.id.sound_toggle)
        soundToggle.isChecked = true
        soundToggle.setBackgroundResource(soundOnDrawable)
        soundToggle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                soundToggle.setBackgroundResource(soundOnDrawable)
                mediaPlayer.start()
            } else {
                soundToggle.setBackgroundResource(soundOffDrawable)
                mediaPlayer.pause()
            }
        }
        //play
        val Play_button = findViewById<Button>(R.id.Play_button)
        Play_button.setOnClickListener {
            val intent = Intent(this@MainActivity, Classic_Mode::class.java)
            startActivity(intent)
        }
        //choose game mode
        val Mode_button = findViewById<Button>(R.id.Mode_button)
        Mode_button.setOnClickListener {
            val intent = Intent(this@MainActivity, Menu_Game::class.java)
            startActivity(intent)
        }
        //HighScore
        val leaderboard = findViewById<ImageView>(R.id.highscore)
        leaderboard.setOnClickListener {
            val intent = Intent(this@MainActivity, HighScore::class.java)
            startActivity(intent)
        }
        //exit
        val Exit_button = findViewById<Button>(R.id.Exit_button)
        Exit_button.setOnClickListener {
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    override fun onPause() {
        super.onPause()
        if (soundToggle.isChecked) {
            mediaPlayer.pause()
        }
    }
    override fun onResume() {
        super.onResume()
        if (soundToggle.isChecked) {
            mediaPlayer.start()
        }
    }
}
