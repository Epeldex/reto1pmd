package com.example.pmdreto1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

public class LeaderboardActivity extends AppCompatActivity {
	@SuppressLint("Range")

    Intent intent = null;
    Intent chooser = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard);

		Button btnShare = (Button) findViewById(R.id.btnShare);
		Button btnBack = (Button) findViewById(R.id.btnBack);
		final Intent intentBack = new Intent(Leaderboard.this, MainActivity.class);

        btnShare.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareMail();
            }
        } );

		btnBack.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(intentBack, 1);
            }
        } );

		TableLayout tableLayout=(TableLayout)findViewById(R.id.tableLayout);

        SQLiteDatabase db = null;
        db = openOrCreateDatabase("TEST", Context.MODE_PRIVATE, null);
		Cursor cursor=db.rawQuery("SELECT * FROM TEST", null);

        if (cursor.moveToFirst()) {
            do {
                // Create a new row
                TableRow tableRow = new TableRow(this);

                // Get the values from the cursor
                int score = cursor.getInt(cursor.getColumnIndex("Puntuacion"));
                String username = cursor.getString(cursor.getColumnIndex("Nombre"));
                // Create TextViews for each column
                TextView scoreTextView = new TextView(this);
                scoreTextView.setText(String.valueOf(score));
                scoreTextView.setGravity(android.view.Gravity.CENTER);
                scoreTextView.setPadding(8, 8, 8, 8);

                TextView usernameTextView = new TextView(this);
                usernameTextView.setText(username);
                usernameTextView.setGravity(android.view.Gravity.CENTER);
                usernameTextView.setPadding(8, 8, 8, 8);

                // Add TextViews to the TableRow
                tableRow.addView(scoreTextView);
                tableRow.addView(usernameTextView);

                // Add the TableRow to the TableLayout
                tableLayout.addView(tableRow);

            } while (cursor.moveToNext());
        }

        // Close the cursor and database when done
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        db.close();
    }

	void showToast(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    void shareMail () {
        intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Ejemplo de asunto de tu correo");
        intent.putExtra(Intent.EXTRA_TEXT, "Ejemplo de dexto del correo");
        intent.setType("message/rfc822");
        //chooser = Intent.createChooser(intent, "Choose an app");
        //if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        //} else {
        //    showToast("No hay ninguna aplicación para manejar esta acción");
        //}
    }
}

