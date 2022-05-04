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
import android.widget.ImageView;

import com.example.tccbcc.R;
import com.example.tccbcc.Services.UserType;
import com.example.tccbcc.models.Foto;
import com.example.tccbcc.models.UsuarioBeneficiado;
import com.example.tccbcc.models.UsuarioBeneficiador;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class CadastroImagemBeneficiadoActivity extends AppCompatActivity {
    private ImageView imgViewFoto;
    private Uri selectedUri;
    private Button btnSelecionarFoto;
    private UsuarioBeneficiado usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_imagem_beneficiado);

        imgViewFoto = findViewById(R.id.imgviewFoto2);
        btnSelecionarFoto = findViewById(R.id.btnSelecionarFoto3);

        String user = getIntent().getStringExtra("user");
        Gson gson = new Gson();
        usuario =  gson.fromJson(user, UsuarioBeneficiado.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            selectedUri = data.getData();

            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedUri);
                imgViewFoto.setImageDrawable(new BitmapDrawable(bitmap));
                btnSelecionarFoto.setAlpha(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void SelecionarFoto(View view) {
        selectPhoto();
    }

    private void selectPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    public void Cadastrar(View view){
        String filename = UUID.randomUUID().toString();
        final StorageReference ref = FirebaseStorage.getInstance().getReference("/images/" + filename);
        ref.putFile(selectedUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.i("Teste", uri.toString());

                        ArrayList<Foto> fotos = new ArrayList<Foto>();
                        fotos.add(new Foto(uri.toString()));
                        usuario.setFotos(fotos);

                        FirebaseFirestore.getInstance().collection("users")
                                .document(usuario.getUuid())
                                .set(usuario)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        UserType.isBeneficiado = true;
                                        Intent intent = new Intent(CadastroImagemBeneficiadoActivity.this, MensagensActivity.class);
                                        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.i("Teste", e.getMessage());
                                        AlertDialog.Builder builder = new AlertDialog.Builder(CadastroImagemBeneficiadoActivity.this);
                                        builder.setMessage("Ocorreu um erro ao tentar salvar o usuário!")
                                                .create().show();
                                    }
                                });
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Teste", e.getMessage());
            }
        });
    }
}