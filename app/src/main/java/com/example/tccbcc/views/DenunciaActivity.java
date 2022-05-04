package com.example.tccbcc.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.tccbcc.R;
import com.example.tccbcc.Services.UserType;
import com.example.tccbcc.models.Contato;
import com.example.tccbcc.models.Denuncia;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class DenunciaActivity extends AppCompatActivity {
    private String uid;
    private String did;
    private TextView edtDenuncia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncia);

        edtDenuncia = findViewById(R.id.edtDenuncia);

        uid = getIntent().getStringExtra("uid");
        did = getIntent().getStringExtra("did");
    }

    public void EnviarDenuncia(View view){
        if (edtDenuncia.getText().toString().equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("O campo denúncia deve ser preenchido!").create().show();
        }

        Denuncia denuncia = new Denuncia(uid, edtDenuncia.getText().toString());
        FirebaseFirestore.getInstance().collection("denunciation")
                .document(did)
                .collection(uid)
                .add(denuncia)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Intent intent = new Intent(DenunciaActivity.this, MensagensActivity.class);
                        intent.putExtra("beneficiado", true);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Teste", e.getMessage());
                AlertDialog.Builder builder = new AlertDialog.Builder(DenunciaActivity.this);
                builder.setMessage("Ocorreu um erro ao tentar gravar a denúncia!")
                        .create().show();
            }
        });
    }
}