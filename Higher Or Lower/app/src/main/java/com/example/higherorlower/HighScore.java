package com.example.higherorlower;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class HighScore extends Activity {
    private ListView leaderboardListView;
    private ArrayAdapter<Integer> adapter;
    private List<Integer> dataList;
    private TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscore);

        leaderboardListView = findViewById(R.id.leaderboardListView);
        emptyView = findViewById(R.id.emptyView);

        dataList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        leaderboardListView.setAdapter(adapter);
        leaderboardListView.setEmptyView(emptyView);
        loadScoresFromDatabase();
    }

    private void loadScoresFromDatabase() {
        List<Integer> highScores = GameLogic.getHighScores(this);
        if (highScores.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
            leaderboardListView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            leaderboardListView.setVisibility(View.VISIBLE);
            dataList.clear();
            dataList.addAll(highScores);
            adapter.notifyDataSetChanged();
        }
    }

    public void resetHighScores(View view) {
        SharedPreferences prefs = getSharedPreferences("Leaderboard", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear().apply();
        loadScoresFromDatabase();
    }

}
