package com.example.aplicacion_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.aplicacion_1.Login.Login;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_settings);
    }

    public void toMenu(View vista){
        Intent anterior = new Intent(this, MainMenu.class);
        startActivity(anterior);
    }
}