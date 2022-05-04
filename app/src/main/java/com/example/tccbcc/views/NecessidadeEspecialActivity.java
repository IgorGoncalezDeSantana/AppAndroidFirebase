package com.example.tccbcc.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.tccbcc.R;
import com.example.tccbcc.models.UsuarioBeneficiado;
import com.google.gson.Gson;

public class NecessidadeEspecialActivity extends AppCompatActivity {
    private UsuarioBeneficiado usuario;

    private TextView edtDescricaoNecessidadeEspecial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_necessidade_especial_acivity);
        edtDescricaoNecessidadeEspecial = findViewById(R.id.edtDescricaoNecessidadeEspecial);

        String user = getIntent().getStringExtra("user");
        Gson gson = new Gson();
        usuario =  gson.fromJson(user, UsuarioBeneficiado.class);
    }

    public void Cadastrar(View view){
        if (edtDescricaoNecessidadeEspecial.getText().toString().isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("É obrigatório descrever sua necessidade especial "+
                    "para os outros usuários!")
                    .create().show();
            return;
        }
        usuario.setNecessidadeEspecial(edtDescricaoNecessidadeEspecial.getText().toString());

        Gson gson = new Gson();
        Intent intent = new Intent(this,
                CadastroImagemBeneficiadoActivity.class);
        intent.putExtra("user", gson.toJson(usuario));
        startActivity(intent);
    }
}