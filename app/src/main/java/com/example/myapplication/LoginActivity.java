package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        sharedPreferences = getSharedPreferences("loginPref",MODE_PRIVATE);
        if(sharedPreferences.getBoolean("logged",false)){

            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();


        }
    }

    public void onLogin(View view) {
        editor = sharedPreferences.edit();
        editor.putBoolean("logged",true);
        editor.apply();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();


    }
}
