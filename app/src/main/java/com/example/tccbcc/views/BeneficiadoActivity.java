package com.example.tccbcc.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tccbcc.R;
import com.example.tccbcc.models.UsuarioBeneficiado;
import com.google.gson.Gson;

public class BeneficiadoActivity extends AppCompatActivity {
    UsuarioBeneficiado usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beneficiado);

        String user = getIntent().getStringExtra("user");
        Gson gson = new Gson();
        usuario =  gson.fromJson(user, UsuarioBeneficiado.class);
    }

    public void CadastroProprio(View view){
        Gson gson = new Gson();
        Intent intent = new Intent(this, NecessidadeEspecialActivity.class);
        intent.putExtra("user", gson.toJson(usuario));
        startActivity(intent);
    }

    public void CadastroDependente(View view){
        Gson gson = new Gson();
        Intent intent = new Intent(this, CadastroDependenteActivity.class);
        intent.putExtra("user", gson.toJson(usuario));
        startActivity(intent);
    }
}