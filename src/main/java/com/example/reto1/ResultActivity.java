package com.example.reto1;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

	private final Integer LEADERBOARD_ACTIVITY = 4;
	private final String INSERT_NEW_USER_DATA = "INSERT into t_leaderboard VALUES (?,?)";
	private final Integer GRABAR_VIDEO = 3;
	private final Integer CAPTURA_IMAGEN = 1;

	private SQLiteStatement stmt;
	private SQLiteDatabase db;

	private Intent intent;
	private Intent videoIntent;
	private Intent fotoIntent;
	private TextView tvCorrectAnswers;
	private Button btnLogout;
	private Button btnLeaderboard;
	private Button btnThumbnail;
	private Button btnVideo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);

		try {
			intent = getIntent();
			tvCorrectAnswers = findViewById(R.id.correctAnswers);
			btnLogout = findViewById(R.id.buttonLogout);
			btnLeaderboard = findViewById(R.id.buttonLeaderboard);
			btnThumbnail = findViewById(R.id.btn_thumbnail);
			btnVideo = findViewById(R.id.btn_video);

			btnLogout.setOnClickListener(this::onClick);
			btnLeaderboard.setOnClickListener(this::onClick);
			btnThumbnail.setOnClickListener(this::onClick);
			btnVideo.setOnClickListener(this::onClick);

			Integer correctAnswers = intent.getExtras().getInt("correctAnswers");
			String username = intent.getExtras().getString("username");
			String newText = getString(R.string.correctAnswers) + " " + correctAnswers;
			tvCorrectAnswers.setText(newText);


			if (intent.getExtras().getInt("correctAnswers") != 4) {
				btnVideo.setVisibility(View.VISIBLE);
				btnThumbnail.setVisibility(View.INVISIBLE);
			} else {
				btnThumbnail.setVisibility(View.VISIBLE);
				btnVideo.setVisibility(View.INVISIBLE);
			}

			insertNewLeaderboardEntry(username, correctAnswers);
		} catch (Exception e) {
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
			e.printStackTrace();
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
				Intent newIntent = new Intent(ResultActivity.this, LeaderboardActivity.class);
				startActivityForResult(newIntent, LEADERBOARD_ACTIVITY);
			} else if (v.getId() == btnVideo.getId()) {
				comenzarGrabacion();
			} else if (v.getId() == btnThumbnail.getId()) {
				fotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				if (fotoIntent.resolveActivity(getPackageManager()) != null) {
					startActivityForResult(fotoIntent, CAPTURA_IMAGEN);
				}
			}
		} catch (Exception e) {
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}

	public void comenzarGrabacion() throws Exception {
		// Creación del intent
		videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		// El vídeo se grabará en calidad baja (0)
		videoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
		// Limitamos la duración de la grabación a 5 segundos
		videoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 5);
		// Nos aseguramos de que haya una aplicación que pueda manejar el intent
		if (videoIntent.resolveActivity(getPackageManager()) != null) {
			// Lanzamos el intent
			startActivityForResult(videoIntent, GRABAR_VIDEO);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CAPTURA_IMAGEN && resultCode == RESULT_OK) {
			Toast.makeText(this, getString(R.string.txt_video_captured), Toast.LENGTH_LONG).show();
		}
		if (requestCode == GRABAR_VIDEO && resultCode == RESULT_OK) {
			Toast.makeText(this, getString(R.string.txt_video_captured), Toast.LENGTH_LONG).show();
		}
	}
}
