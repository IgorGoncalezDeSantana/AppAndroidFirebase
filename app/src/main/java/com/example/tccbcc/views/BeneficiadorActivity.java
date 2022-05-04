package com.example.tccbcc.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tccbcc.R;
import com.example.tccbcc.models.Pet;
import com.example.tccbcc.models.UsuarioBeneficiador;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class BeneficiadorActivity extends AppCompatActivity {
    private UsuarioBeneficiador usuario;

    private EditText edtNomePet;
    private EditText edtDataNascimentoPet;
    private EditText edtDescricao;
    private EditText edtRaca;
    private EditText edtSexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beneficiador);

        edtNomePet = findViewById(R.id.edtNomePet);
        edtDataNascimentoPet = findViewById(R.id.edtDataNascimentoPet);
        edtDescricao = findViewById(R.id.edtDescricao);
        edtRaca = findViewById(R.id.edtRaca);
        edtSexo = findViewById(R.id.edtSexo);

        String user = getIntent().getStringExtra("user");
        Gson gson = new Gson();
        usuario =  gson.fromJson(user, UsuarioBeneficiador.class);
    }

    public void Cadastrar(View view){
        if (edtNomePet.getText().toString().isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Nome é um campo obrigatório!")
                    .create().show();
            return;
        }

        if (edtDataNascimentoPet.getText().toString().isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Data de Nascimento é um campo obrigatório!")
                    .create().show();
            return;
        }

        if (edtDescricao.getText().toString().isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Descrição é um campo obrigatório!")
                    .create().show();
            return;
        }

        if (edtRaca.getText().toString().isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Raça é um campo obrigatório!")
                    .create().show();
            return;
        }

        if (edtSexo.getText().toString().isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Sexo é um campo obrigatório!")
                    .create().show();
            return;
        }

        Pet pet = new Pet();
        pet.setNome(edtNomePet.getText().toString());

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        try {
            pet.setData(formato.parse(edtDataNascimentoPet.getText().toString()));
        } catch (ParseException e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("O campo data de Nascimento foi preenchido incorretamente!")
                    .create().show();
            return;
        }

        pet.setDescricao(edtDescricao.getText().toString());
        pet.setRaca(edtRaca.getText().toString());
        pet.setSexo(edtSexo.getText().toString());

        usuario.setPet(pet);

        Gson gson = new Gson();
        Intent intent = new Intent(BeneficiadorActivity.this,
                ListagemVacinacaoActivity.class);
        intent.putExtra("user", gson.toJson(usuario));
        startActivity(intent);
    }
}