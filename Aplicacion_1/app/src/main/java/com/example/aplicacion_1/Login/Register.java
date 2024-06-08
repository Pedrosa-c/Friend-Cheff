package com.example.aplicacion_1.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aplicacion_1.R;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);
    }

    public void toLogin(View vista){
        Intent anterior = new Intent(this, Login.class);
        startActivity(anterior);
    }

    public void Register(View vista){

    }
}