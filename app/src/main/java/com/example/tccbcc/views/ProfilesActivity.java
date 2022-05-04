package com.example.tccbcc.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.example.tccbcc.R;
import com.example.tccbcc.models.Usuario;
import com.example.tccbcc.adapter.UserAdapter;
import com.example.tccbcc.databinding.ActivityProfilesBinding;
import com.example.tccbcc.models.UsuarioBeneficiador;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProfilesActivity extends AppCompatActivity {

    ActivityProfilesBinding binding;
    FirebaseDatabase database;
    ArrayList<UsuarioBeneficiador> users;
    UserAdapter usersAdapter;
    ProgressDialog dialog;
    UsuarioBeneficiador user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityProfilesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dialog = new ProgressDialog(this);
        dialog.setMessage("Uploading Image...");
        dialog.setCancelable(false);

        database = FirebaseDatabase.getInstance();
        users = new ArrayList<>();
        usersAdapter = new UserAdapter(this, users);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);

        binding.mRec.setLayoutManager(layoutManager);
        usersAdapter = new UserAdapter(this, users);
        binding.mRec.setLayoutManager(layoutManager);
        binding.mRec.setAdapter(usersAdapter);

        fetchUsers();
    }

    private void fetchUsers() {
        if (FirebaseAuth.getInstance().getUid() == null) return;

        FirebaseFirestore.getInstance().collection("/users")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value != null) {
                            if (error != null) {
                                Log.e("Teste", error.getMessage(), error);
                                return;
                            }

                            FirebaseFirestore.getInstance().collection("/users")
                                    .document(FirebaseAuth.getInstance().getUid())
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            Usuario usuario = documentSnapshot.toObject(Usuario.class);

                                            List<DocumentSnapshot> docs = value.getDocuments();
                                            for (DocumentSnapshot doc : docs) {
                                                Usuario user = doc.toObject(Usuario.class);

                                                if (user.isBeneficiador() &&
                                                        (user.getEndereco().getCidade().equals(
                                                                usuario.getEndereco().getCidade()))&&
                                                        !user.getUuid().equals(FirebaseAuth.getInstance().getUid()))
                                                    users.add(doc.toObject(UsuarioBeneficiador.class));
                                            }
                                            usersAdapter.notifyDataSetChanged();
                                        }
                                    });

                        }
                    }
                });
    }
}