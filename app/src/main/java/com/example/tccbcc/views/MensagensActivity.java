package com.example.tccbcc.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tccbcc.Services.UserType;
import com.example.tccbcc.models.Contato;
import com.example.tccbcc.R;
import com.example.tccbcc.models.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;
import com.xwray.groupie.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MensagensActivity extends AppCompatActivity {

    private GroupAdapter adapter;
    private boolean beneficiado;

    private ArrayList<ContactItem> itens;

    private TextView txtAviso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensagens);

        itens = new ArrayList<>();

        txtAviso = findViewById(R.id.txtAviso);

        RecyclerView rv = findViewById(R.id.recycler_contact);
        rv.setLayoutManager(new LinearLayoutManager(this));

        adapter = new GroupAdapter();
        rv.setAdapter(adapter);
        beneficiado = UserType.isBeneficiado;

        if (adapter.getItemCount() == 0) {
            txtAviso.setVisibility(View.VISIBLE);
            if (beneficiado)
                txtAviso.setText("Procure por um beneficiador clicando em buscar.");
            else
                txtAviso.setText("Aguarde a mensagem de uma pessoa beneficiada");
        } else
            txtAviso.setVisibility(View.INVISIBLE);

        updateToken();
        fetchLastMessage();
    }

    private void updateToken() {

    }

    private void fetchLastMessage() {
        String uid = FirebaseAuth.getInstance().getUid();

        if (uid == null) return;

        FirebaseFirestore.getInstance().collection("/last-messages")
                .document(uid)
                .collection("contacts")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value != null) {
                            List<DocumentChange> documentChanges = value.getDocumentChanges();

                            if (documentChanges != null) {
                                for (DocumentChange doc : documentChanges) {
                                    if (doc.getType() == DocumentChange.Type.ADDED) {
                                        Contato contact = doc.getDocument().toObject(Contato.class);

                                        adapter.add(new ContactItem(contact));
                                        txtAviso.setVisibility(View.INVISIBLE);
                                    }
                                    else if (doc.getType() == DocumentChange.Type.MODIFIED){
                                        Contato contact = doc.getDocument().toObject(Contato.class);

                                        for (ContactItem item: itens){
                                            if (contact.getUuid().equals(item.contact.getUuid())){
                                                adapter.remove(item);
                                                itens.remove(item);

                                                adapter.add(new ContactItem(contact));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (beneficiado)
            getMenuInflater().inflate(R.menu.menu_beneficiador, menu);
        else
            getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.logout:
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(this, MainActivity.class);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                case R.id.contatos:
                    Intent intent2 = new Intent(MensagensActivity.this, ProfilesActivity.class);
                    startActivity(intent2);
                    break;
            }


        return super.onOptionsItemSelected(item);
    }

    private class ContactItem extends Item<ViewHolder> {
        private final Contato contact;

        private ContactItem(Contato contact) {
            this.contact = contact;
            itens.add(this);
        }

        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {
            TextView username = viewHolder.itemView.findViewById(R.id.textView);
            TextView message = viewHolder.itemView.findViewById(R.id.textView2);
            ImageView imgPhoto = viewHolder.itemView.findViewById(R.id.imageView);

            username.setText(contact.getUsername());
            message.setText(contact.getLastMessage());
            Picasso.get()
                    .load(contact.getPhotoUrl())
                    .into(imgPhoto);

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseFirestore.getInstance().collection("/users")
                            .document(contact.getUuid())
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    Usuario user = documentSnapshot.toObject(Usuario.class);
                                    Intent intent = new Intent(MensagensActivity.this,
                                            ChatActivity.class);
                                    intent.putExtra("user",user);
                                    startActivity(intent);
                                }
                            });
                }
            });
        }

        @Override
        public int getLayout() {
            return R.layout.item_user_message;
        }
    }
}