package com.example.tccbcc.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tccbcc.R;
import com.example.tccbcc.models.Dependente;
import com.example.tccbcc.models.UsuarioBeneficiado;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CadastroDependenteActivity extends AppCompatActivity {
    private UsuarioBeneficiado usuario;

    private EditText edtNomeDependente;
    private EditText edtGeneroDependente;
    private EditText edtDataNascimentoDependente;
    private EditText edtDecricaoNecessidadeEspecialDepentente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_dependente);

        edtNomeDependente = findViewById(R.id.edtNomeDependente);
        edtGeneroDependente = findViewById(R.id.edtGeneroDependente);
        edtDataNascimentoDependente = findViewById(R.id.edtDataNascimentoDependente);
        edtDecricaoNecessidadeEspecialDepentente =
                findViewById(R.id.edtDescricaoNecessidadeEspecialDependente);

        String user = getIntent().getStringExtra("user");
        Gson gson = new Gson();
        usuario =  gson.fromJson(user, UsuarioBeneficiado.class);
    }

    public void Cadastrar (View view) {
        if (edtNomeDependente.getText().toString().isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("O campo Nome é obrigatório!")
                    .create().show();
            return;
        }

        if (edtGeneroDependente.getText().toString().isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("O campo Gênero é obrigatório!")
                    .create().show();
            return;
        }

        if (edtDataNascimentoDependente.getText().toString().isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("O campo Data de Nascimento é obrigatório!")
                    .create().show();
            return;
        }

        if (edtDecricaoNecessidadeEspecialDepentente.getText().toString().isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("É obrigatório informar a necessidade " +
                    "especial do dependente para os outros usuários!")
                    .create().show();
            return;
        }

        Dependente dependente = new Dependente();
        dependente.setNome(edtNomeDependente.getText().toString());
        dependente.setGenero(edtGeneroDependente.getText().toString());
        dependente.setNecessidadeEspecial(edtDecricaoNecessidadeEspecialDepentente.getText().toString());

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dependente.setDataNascimento(formato.parse(edtDataNascimentoDependente.getText().toString()));
        } catch (ParseException e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("O campo data de Nascimento foi preenchido incorretamente!")
                    .create().show();
            return;
        }

        usuario.setDependente(dependente);

        Gson gson = new Gson();
        Intent intent = new Intent(this,
                CadastroImagemBeneficiadoActivity.class);
        intent.putExtra("user", gson.toJson(usuario));
        startActivity(intent);
    }
}