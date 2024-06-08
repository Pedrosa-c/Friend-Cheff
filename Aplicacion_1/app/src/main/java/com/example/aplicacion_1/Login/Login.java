package com.example.aplicacion_1.Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aplicacion_1.MainMenu;
import com.example.aplicacion_1.R;

public class Login extends AppCompatActivity {

    EditText name, password;
    String nameControl, passwordControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        name = findViewById(R.id.editTextName);
        password = findViewById(R.id.editTextPassword);
        nameControl = "Name";
        passwordControl = "Password";

    }

    public void toRegister(View vista){
        Intent register = new Intent(this, Register.class);
        startActivity(register);
    }

    public void verificaDatos(View vista){

        String myEmail, myPassword;
        boolean control;

        myEmail = name.getText().toString();
        myPassword = password.getText().toString();

        Log.d("Valor name: ", name.getText().toString());

        control = compruebaClaves(myEmail,myPassword);

        if(!control){
            Intent errorAcceso = new Intent(this,FailedLogin.class);
            startActivity(errorAcceso);
        }else{
            Intent acceso = new Intent(this, MainMenu.class);
            startActivity(acceso);
        }
    }

    private boolean compruebaClaves(String name, String clave){
        boolean cierto = false;
        if(name != null && clave!= null){
            if((name.equals(nameControl)) && clave.equals(passwordControl)){
                cierto = true;
            }
        }
        return cierto;
    }
}