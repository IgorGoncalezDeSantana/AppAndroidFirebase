package com.example.tccbcc.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tccbcc.R;
import com.example.tccbcc.Services.ValidaCPF;
import com.example.tccbcc.Services.ValidaCelular;
import com.example.tccbcc.Services.ValidaEmail;
import com.example.tccbcc.models.Foto;
import com.example.tccbcc.models.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class CadastroActivity extends AppCompatActivity {
    private EditText edtEmail;
    private EditText edtSenha;
    private EditText edtNome;
    private EditText edtCPF;
    private EditText edtDataNascimento;
    private EditText edtTelefone;
    private EditText edtGenero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtPassword);
        edtNome = findViewById(R.id.edtNome);
        edtCPF = findViewById(R.id.edtCPF);
        edtDataNascimento = findViewById(R.id.edtDataNascimento);
        edtTelefone = findViewById(R.id.edtTelefone);
        edtGenero = findViewById(R.id.edtGenero);
    }

    public void Cadastrar(View view) {
        createUser();
    }

    private void createUser() {
        String email = edtEmail.getText().toString();
        String senha = edtSenha.getText().toString();

        if (edtNome.getText().toString().isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("O campo nome é obrigatório!")
                    .create().show();
            return;
        }

        if (email.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Email é um campo obrigatório!")
                    .create().show();
            return;
        }

        if (!ValidaEmail.isValidEmailAddressRegex(email)){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("O campo Email foi preenchido incorretamente!")
                    .create().show();
            return;
        }

        if (senha.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("O campo senha é obrigatório!")
                    .create().show();
            return;

        }

        if (edtCPF.getText().toString().isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("O campo CPF é obrigatório!")
                    .create().show();
            return;
        }

        if (!ValidaCPF.isCPF(edtCPF.getText().toString())){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("O campo CPF foi preenchido incorretamente!")
                    .create().show();
            return;
        }

        if (edtDataNascimento.getText().toString().isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("O campo Data de Nascimento é obrigatório!")
                    .create().show();
            return;
        }

        Date dataNascimento;
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dataNascimento = formato.parse(edtDataNascimento.getText().toString());
        } catch (ParseException e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("O campo data de Nascimento foi preenchido incorretamente!")
                    .create().show();
            return;
        }

        if (dataNascimento.getTime() > Calendar.getInstance().getTime().getTime()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("A data de nascimento não pode ser maior do que hoje!")
                    .create().show();
            return;
        }

        if (edtGenero.getText().toString().isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("O campo Gênero é obrigatório!")
                    .create().show();
            return;
        }

        if (edtTelefone.getText().toString().isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("O campo Telefone é obrigatório!")
                    .create().show();
            return;
        }

        if (!ValidaCelular.ValidarCelular(edtTelefone.getText().toString())){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("O campo data de Telefone foi preenchido incorretamente!")
                    .create().show();
            return;
        }

        FirebaseFirestore.getInstance()
                .collection("users")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            if (task.getResult() != null && task.getResult().size() > 0 ){
                                AlertDialog.Builder builder = new AlertDialog.Builder(CadastroActivity.this);
                                builder.setMessage("Email já cadastrado!")
                                        .create().show();
                            } else {
                                CreateUserWithEmail(email, senha);
                            }
                        }
                        else {
                            Log.d("teste", "Erro na consulta!");
                            CreateUserWithEmail(email, senha);
                        }
                    }
                });
    }

    private void CreateUserWithEmail(String email, String password){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            saveUser();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Teste", e.getMessage());

                AlertDialog.Builder builder = new AlertDialog.Builder(CadastroActivity.this);
                builder.setMessage("Email já cadastrado!")
                        .create().show();
            }
        });
    }

    private void saveUser() {
        Usuario usuario = new Usuario();
        usuario.setUuid(FirebaseAuth.getInstance().getUid());
        usuario.setUsername(edtNome.getText().toString());
        usuario.setEmail(edtEmail.getText().toString());
        usuario.setCpf(edtCPF.getText().toString());

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        try {
            usuario.setDataNascimento(formato.parse(edtDataNascimento.getText().toString()));
        } catch (ParseException e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(CadastroActivity.this);
            builder.setMessage("O campo data de Nascimento foi preenchido incorretamente")
                    .create().show();
            return;
        }
        usuario.setTelefone(edtTelefone.getText().toString());
        usuario.setGenero(edtGenero.getText().toString());

        Gson gson = new Gson();
        String user = gson.toJson(usuario);

        Intent intent = new Intent(CadastroActivity.this,
                CadastroEnderecoActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}