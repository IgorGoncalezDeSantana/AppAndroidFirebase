package com.example.tccbcc.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tccbcc.R;
import com.example.tccbcc.Requests.RequestCEP;
import com.example.tccbcc.models.CEP;
import com.example.tccbcc.models.Endereco;
import com.example.tccbcc.models.Usuario;
import com.google.gson.Gson;

public class CadastroEnderecoActivity extends AppCompatActivity {
    private Usuario usuario;

    private EditText edtLogradouro;
    private EditText edtNumero;
    private EditText edtBairro;
    private EditText edtComplemento;
    private EditText edtCEP;
    private EditText edtCidade;
    private EditText edtEstado;
    private EditText edtPais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_endereco);

        edtLogradouro = findViewById(R.id.edtLogradouro);
        edtNumero = findViewById(R.id.edtNumero);
        edtBairro = findViewById(R.id.edtBairro);
        edtComplemento = findViewById(R.id.edtComplemento);
        edtCEP = findViewById(R.id.edtCEP);
        edtCidade = findViewById(R.id.edtCidade);
        edtEstado = findViewById(R.id.edtEstado);
        edtPais = findViewById(R.id.edtPais);

        edtLogradouro.setFocusable(false);
        edtBairro.setFocusable(false);
        edtCidade.setFocusable(false);
        edtEstado.setFocusable(false);
        edtPais.setFocusable(false);

        String user = getIntent().getStringExtra("user");
        Gson gson = new Gson();
        usuario =  gson.fromJson(user, Usuario.class);

        edtCEP.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                    CEP cep = null;
                    try {
                        cep = RequestCEP.Find(edtCEP.getText().toString());
                    }catch (Exception e){
                        AlertDialog.Builder builder = new AlertDialog.Builder(CadastroEnderecoActivity.this);
                        builder.setMessage(e.getMessage())
                                .create().show();
                        return;
                    }

                    edtLogradouro.setText(cep.getLogradouro());
                    edtBairro.setText(cep.getBairro());
                    edtCidade.setText(cep.getLocalidade());
                    edtEstado.setText(cep.getUf());
                    edtPais.setText("Brasil");
                }
            }
        });
    }

    public void Cadastrar(View view){
        if (edtLogradouro.getText().toString().isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Logradouro é um campo obrigatório!")
                    .create().show();
            return;
        }

        if (edtNumero.getText().toString().isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Número é um campo obrigatório!")
                    .create().show();
            return;
        }

        if (edtBairro.getText().toString().isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Bairro é um campo obrigatório!")
                    .create().show();
            return;
        }

        if (edtComplemento.getText().toString().isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Complemento é um campo obrigatório!")
                    .create().show();
            return;
        }

        if (edtCEP.getText().toString().isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("CEP é um campo obrigatório!")
                    .create().show();
            return;
        }

        if (edtCidade.getText().toString().isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Cidade é um campo obrigatório!")
                    .create().show();
            return;
        }

        if (edtEstado.getText().toString().isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Estado é um campo obrigatório!")
                    .create().show();
            return;
        }

        if (edtPais.getText().toString().isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("País é um campo obrigatório!")
                    .create().show();
            return;
        }

        CEP cep = null;
        try {
            cep = RequestCEP.Find(edtCEP.getText().toString());
        }catch (Exception e){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(e.getMessage())
                    .create().show();
            return;
        }

        Endereco endereco = new Endereco();
        endereco.setLogradouro(cep.getLogradouro());
        endereco.setBairro(cep.getBairro());
        endereco.setCidade(cep.getLocalidade());
        endereco.setUf(cep.getUf());
        endereco.setPais("Brasil");

        endereco.setNumero(edtNumero.getText().toString());
        endereco.setComplemento(edtComplemento.getText().toString());
        endereco.setCep(edtCEP.getText().toString());


        usuario.setEndereco(endereco);

        Gson gson = new Gson();
        Intent intent = new Intent(this, EscolhaActivity.class);
        intent.putExtra("user", gson.toJson(usuario));
        startActivity(intent);
    }
}