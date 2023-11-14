package com.example.reto1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Button button = findViewById(R.id.buttonGame);
        button.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(GameActivity.this, ResultActivity.class);
        intent.putExtra("username", "antonia");
        intent.putExtra("correctAnswers", 4);
        startActivityForResult(intent, 2);

    }
}
