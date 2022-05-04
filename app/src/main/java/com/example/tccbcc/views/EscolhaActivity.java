package com.example.tccbcc.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tccbcc.R;
import com.example.tccbcc.models.Usuario;
import com.google.gson.Gson;

public class EscolhaActivity extends AppCompatActivity {
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolha);

        String user = getIntent().getStringExtra("user");
        Gson gson = new Gson();
        usuario =  gson.fromJson(user, Usuario.class);
    }

    public void Beneficiador(View view){
        usuario.setBeneficiador(true);

        Gson gson = new Gson();
        String user = gson.toJson(usuario);

        Intent intent = new Intent(this, BeneficiadorActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);

    }

    public void Beneficiado(View view){
        usuario.setBeneficiador(false);

        Gson gson = new Gson();
        String user = gson.toJson(usuario);

        Intent intent = new Intent(this, BeneficiadoActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);

    }
}