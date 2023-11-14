package com.example.reto1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView chooseLanguage;
    private Button buttonEs;
    private Button buttonEn;
    private TextView insertUsernameView;
    private EditText usernameEditText;
    private Button logInButton;
    private Intent intent1;
    private Intent intent2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createDataBase();

        chooseLanguage = findViewById(R.id.chooseLanguage);
        buttonEs = findViewById(R.id.buttonEs);
        buttonEn = findViewById(R.id.buttonEn);
        insertUsernameView = findViewById(R.id.insertUsernameView);
        usernameEditText = findViewById(R.id.usernameEditText);
        logInButton = findViewById(R.id.logInButton);

        intent1 = new Intent(LoginActivity.this, GameActivity.class);
        intent2 = new Intent(LoginActivity.this, ResultActivity.class);

        // Ocultar las vistas iniciales.
        insertUsernameView.setVisibility(View.GONE);
        usernameEditText.setVisibility(View.GONE);
        logInButton.setVisibility(View.GONE);

        // Configurar el click listener para los botones "Es" y "En".
        buttonEn = findViewById(R.id.buttonEn);
        buttonEs = findViewById(R.id.buttonEs);

        buttonEn.setOnClickListener(this::onClick);
        buttonEs.setOnClickListener(this::onClick);


        Locale locale = getResources().getConfiguration().locale;
        if (locale.getLanguage().equals("es"))
            setVisibility(true);
        else
            setVisibility(false);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonEs) {
            Configuration config = getResources().getConfiguration();
            config.setLocale(new Locale("es"));
            getResources().updateConfiguration(config, getResources().getDisplayMetrics());
            recreate();
        }
        if (view.getId() == R.id.buttonEn)
            setVisibility(true);

        if (view.getId() == R.id.logInButton) {

            try {
                if (!isUser()) {
                    startActivityForResult(intent1, 1);
                }
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        // Mostrar las vistas adicionales.

    }


    private void setVisibility(Boolean visible) {
        if (!visible) {
            //Aqui tienes que poner las cosas que van a estar ocultas mientras los botones sean visibles
            chooseLanguage.setVisibility(View.VISIBLE);
            buttonEn.setVisibility(View.VISIBLE);
            buttonEs.setVisibility(View.VISIBLE);
            insertUsernameView.setVisibility(View.INVISIBLE);
            usernameEditText.setVisibility(View.INVISIBLE);
            logInButton.setVisibility(View.INVISIBLE);
        } else {
            //Aqui lo contrario a lo anterior
            chooseLanguage.setVisibility(View.GONE);
            buttonEn.setVisibility(View.INVISIBLE);
            buttonEs.setVisibility(View.INVISIBLE);
            insertUsernameView.setVisibility(View.VISIBLE);
            usernameEditText.setVisibility(View.VISIBLE);
            logInButton.setVisibility(View.VISIBLE);
        }
    }

    private void createDataBase() {
        SQLiteDatabase db = openOrCreateDatabase("TEST", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS t_leaderboard(username VARCHAR, score INTEGER);");
    }


    public boolean isUser() throws Exception {
        String username = usernameEditText.getText().toString();
        SQLiteDatabase db = openOrCreateDatabase("TEST", Context.MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("SELECT username FROM TEST", null);

        if (cursor.moveToFirst()) {
            String storedUsername = cursor.getString(1);
            if (username.equals(storedUsername))
                throw new Exception(getString(R.string.userExists));
        }
        return false;
    }

}