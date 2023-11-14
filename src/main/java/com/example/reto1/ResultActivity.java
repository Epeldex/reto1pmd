package com.example.reto1;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.media.Session2Command;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    private final Integer LEADERBOARD_ACTIVITY = 3;

    private final String INSERT_NEW_USER_DATA = "INSERT into t_leaderboard VALUES (?,?)";

    private SQLiteStatement stmt;
    private SQLiteDatabase db;

    private Intent intent;
    private TextView tvCorrectAnswers;
    private Button btnLogout;
    private Button btnLeaderboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            intent = getIntent();
            tvCorrectAnswers = findViewById(R.id.correctAnswers);
            btnLogout = findViewById(R.id.buttonLogout);
            btnLeaderboard = findViewById(R.id.buttonLeaderboard);

            btnLogout.setOnClickListener(this::onClick);
            btnLeaderboard.setOnClickListener(this::onClick);

            Integer correctAnswers = intent.getExtras().getInt("correctAnswers");
            String username = intent.getExtras().getString("username");
            String newText = getString(R.string.correctAnswers) + " " + String.valueOf(correctAnswers);
            tvCorrectAnswers.setText(newText);

            insertNewLeaderboardEntry(username, correctAnswers);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void insertNewLeaderboardEntry(String username, Integer correctAnswers) {
        try {
            db = openOrCreateDatabase("TEST", Context.MODE_PRIVATE, null);

            stmt = db.compileStatement(INSERT_NEW_USER_DATA);
            stmt.bindString(1, username);
            stmt.bindLong(2, correctAnswers);

            stmt.executeInsert();
        } catch (SQLException e) {
            Logger.getLogger(ResultActivity.class.getName())
                    .log(Level.SEVERE, "Error insertando informacion");
        }
    }

    @Override
    public void onClick(View v) {
        try {
            if (v.getId() == btnLogout.getId()) {
                setResult(ResultActivity.RESULT_OK, intent);
                finish();
            } else if (v.getId() == btnLeaderboard.getId()) {
                Intent newIntent = new Intent(ResultActivity.this, LeaderboardActivty.class);
                startActivityForResult(newIntent, LEADERBOARD_ACTIVITY);
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}
