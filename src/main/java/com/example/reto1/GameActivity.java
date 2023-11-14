package com.example.reto1;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.Locale;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvQuestion;
    private RadioGroup radioGroup;
    private Button button;
    private ProgressBar pb;

    private static String username;
    private static Intent intent;
    private Integer cycle = 1;
    private Integer correctCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        intent = getIntent();
        username = intent.getExtras().getString("username");

        radioGroup = findViewById(R.id.radioGroup1);
        tvQuestion = findViewById(R.id.tvQuestion);
        pb = findViewById(R.id.progressBar);
        button = findViewById(R.id.buttonGame);
        button.setOnClickListener(this::onClick);

        setQuestionsForCycle();
    }


    private void setQuestionsForCycle() {
        radioGroup.clearCheck();
        RadioButton rb1 = findViewById(R.id.radioButton);
        RadioButton rb2 = findViewById(R.id.radioButton2);
        RadioButton rb3 = findViewById(R.id.radioButton3);
        switch (cycle) {
            case 1:
                tvQuestion.setText(getString(R.string.question1));
                rb1.setText(getString(R.string.q1_a1));
                rb2.setText(getString(R.string.q1_a2));
                rb3.setText(getString(R.string.q1_a3));
                break;
            case 2:
                tvQuestion.setText(getString(R.string.question2));
                rb1.setText(getString(R.string.q2_a1));
                rb2.setText(getString(R.string.q2_a2));
                rb3.setText(getString(R.string.q2_a3));
                break;
            case 3:
                tvQuestion.setText(getString(R.string.question3));
                rb1.setText(getString(R.string.q3_a1));
                rb2.setText(getString(R.string.q3_a2));
                rb3.setText(getString(R.string.q3_a3));
                break;
            case 4:
                tvQuestion.setText(getString(R.string.question4));
                rb1.setText(getString(R.string.q4_a1));
                rb2.setText(getString(R.string.q1_a2));
                rb3.setText(getString(R.string.q1_a3));
                button.setText(getString(R.string.finish));
                break;
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == button.getId()) {
            if (!button.getText().equals(getString(R.string.finish))) {
                cycle++;
                pb.setProgress(pb.getProgress() + 25);
                RadioButton rb = findViewById(radioGroup.getCheckedRadioButtonId());
                if (rb.getText() == getString(R.string.q1_a2) || rb.getText() == getString(R.string.q2_a1) ||
                        rb.getText() == getString(R.string.q3_a3) || rb.getText() == getString(R.string.q4_a1)) {
                    correctCount++;
                }
                setQuestionsForCycle();
            } else {
                Intent newIntent = new Intent(GameActivity.this, ResultActivity.class);
                newIntent.putExtra("username", username);
                newIntent.putExtra("correctAnswers", correctCount);
                startActivityForResult(newIntent, 2);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }
}
