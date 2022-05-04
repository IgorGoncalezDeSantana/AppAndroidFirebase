package com.example.tccbcc.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tccbcc.R;
import com.example.tccbcc.models.UsuarioBeneficiador;
import com.example.tccbcc.models.Vacina;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CadastroVacinaActivity extends AppCompatActivity {
    private UsuarioBeneficiador usuario;

    private EditText edtNomeVacina;
    private EditText edtDescricaoVacina;
    private EditText edtDataVacina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_vacina);

        String user = getIntent().getStringExtra("user");
        Gson gson = new Gson();
        usuario =  gson.fromJson(user, UsuarioBeneficiador.class);

        edtNomeVacina = findViewById(R.id.edtNomeVacina);
        edtDescricaoVacina = findViewById(R.id.edtDescricaoVacina);
        edtDataVacina = findViewById(R.id.edtDataVacina);
    }

    public void Cadastrar(View view){
        if (edtNomeVacina.getText().toString().isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("O campo Nome é obrigatório!")
                    .create().show();
            return;
        }

        if (edtDescricaoVacina.getText().toString().isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("O campo Descrição é obrigatório!")
                    .create().show();
            return;
        }

        if (edtDataVacina.getText().toString().isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("O campo Data é obrigatório!")
                    .create().show();
            return;
        }

        Vacina vacina = new Vacina();
        vacina.setNome(edtNomeVacina.getText().toString());
        vacina.setDescricao(edtDescricaoVacina.getText().toString());


        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        try {
            vacina.setData(formato.parse(edtDataVacina.getText().toString()));

            if (vacina.getData().getTime() > Calendar.getInstance().getTime().getTime()){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("A data da vacinação não pode ser maior do que hoje!")
                        .create().show();
                return;
            }
        } catch (ParseException e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("O campo data foi preenchido incorretamente!")
                    .create().show();
            return;
        }

        usuario.getPet().addVacina(vacina);

        Gson gson = new Gson();
        Intent intent = new Intent(CadastroVacinaActivity.this,
                ListagemVacinacaoActivity.class);
        intent.putExtra("user", gson.toJson(usuario));
        startActivity(intent);
    }
}