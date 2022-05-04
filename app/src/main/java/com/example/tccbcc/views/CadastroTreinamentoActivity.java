package com.example.tccbcc.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.tccbcc.R;
import com.example.tccbcc.models.Treinamento;
import com.example.tccbcc.models.UsuarioBeneficiador;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CadastroTreinamentoActivity extends AppCompatActivity {
    private UsuarioBeneficiador usuario;

    private TextView edtNomeTreinamento;
    private TextView edtDescricaoTreinamento;
    private TextView edtDataInicioTreinamento;
    private TextView edtDataFimTreinamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_treinamento);

        String user = getIntent().getStringExtra("user");
        Gson gson = new Gson();
        usuario =  gson.fromJson(user, UsuarioBeneficiador.class);

        edtNomeTreinamento = findViewById(R.id.edtNomeTreinamento);
        edtDescricaoTreinamento = findViewById(R.id.edtDescricaoTreinamento);
        edtDataInicioTreinamento = findViewById(R.id.edtDataInicioTreinamento);
        edtDataFimTreinamento = findViewById(R.id.edtDataFimTreinamento);
    }

    public void Cadastrar(View view){
        if (edtNomeTreinamento.getText().toString().isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("O campo Nome é obrigatório!")
                    .create().show();
            return;
        }

        if (edtDescricaoTreinamento.getText().toString().isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("O campo Descrição é obrigatório!")
                    .create().show();
            return;
        }

        if (edtDataInicioTreinamento.getText().toString().isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("O campo Data início é obrigatório!")
                    .create().show();
            return;
        }

        if (edtDataFimTreinamento.getText().toString().isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("O campo Data término é obrigatório!")
                    .create().show();
            return;
        }

        Treinamento treinamento = new Treinamento();
        treinamento.setNome(edtNomeTreinamento.getText().toString());
        treinamento.setDescricao(edtDescricaoTreinamento.getText().toString());

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        try {
            treinamento.setDataInicio(formato.parse(edtDataInicioTreinamento.getText().toString()));

            if (treinamento.getDataInicio().getTime() > Calendar.getInstance().getTime().getTime()){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("A data de início do treinamento não pode ser maior do que hoje!")
                        .create().show();
                return;
            }
        } catch (ParseException e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("O campo data início foi preenchido incorretamente!")
                    .create().show();
            return;
        }

        try {
            treinamento.setDataFim(formato.parse(edtDataFimTreinamento.getText().toString()));
        } catch (ParseException e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("O campo data término foi preenchido incorretamente!")
                    .create().show();
            return;
        }

        usuario.getPet().addTreinamento(treinamento);
        Gson gson = new Gson();
        Intent intent = new Intent(CadastroTreinamentoActivity.this,
                ListagemTreinamentosActivity.class);
        intent.putExtra("user", gson.toJson(usuario));
        startActivity(intent);
    }
}