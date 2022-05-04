package com.example.tccbcc.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tccbcc.R;
import com.example.tccbcc.Services.UserType;
import com.example.tccbcc.models.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {
    private EditText edtEmail;
    private EditText edtSenha;
    private Button btnEntrar;
    private TextView txtCriarConta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtPassword);
        btnEntrar = findViewById(R.id.btnEntrar);
        txtCriarConta = findViewById(R.id.txtCriarConta);

        if (getIntent().getBooleanExtra("verifyAuthentication", true)) {
            verifyAuthentication();
        }
    }

    public void Entrar(View view){
        String email = edtEmail.getText().toString();
        String senha = edtSenha.getText().toString();

        if (email.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Email é um campo obrigatório!")
                    .create().show();
            return;
        }
        if (senha.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("O campo senha é obrigatório!")
                    .create().show();
            return;
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.i("Teste", task.getResult().getUser().getUid());

                FirebaseFirestore.getInstance()
                        .collection("users")
                        .whereEqualTo("email", email)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()){
                                    Usuario user = task.getResult().toObjects(Usuario.class).get(0);

                                    Intent intent = new Intent(MainActivity.this, MensagensActivity.class);
                                    //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                                    if (user == null){
                                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                        builder.setMessage("Usuário não encontrado!")
                                                .create().show();
                                        return;
                                    }else if (user.isBeneficiador()) {
                                        UserType.isBeneficiado = false;
                                    }
                                    else {
                                        UserType.isBeneficiado = true;
                                    }

                                    startActivity(intent);
                                }
                            }
                        });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Teste", e.getMessage());
            }
        });
    }

    public void CriarConta(View view){
        startActivity(new Intent(view.getContext(), CadastroActivity.class));
    }

    private void verifyAuthentication() {
        if (FirebaseAuth.getInstance().getUid() != null){
            FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(FirebaseAuth.getInstance().getUid())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Usuario user = documentSnapshot.toObject(Usuario.class);
                            Log.i("Teste", Boolean.toString(user.isBeneficiador()));

                            Intent intent = new Intent(MainActivity.this, MensagensActivity.class);
                            UserType.isBeneficiado = !user.isBeneficiador();
                            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    });
        }


    }
}