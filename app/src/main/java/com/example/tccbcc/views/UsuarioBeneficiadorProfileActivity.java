package com.example.tccbcc.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tccbcc.R;
import com.example.tccbcc.adapter.SimpleAdapter;
import com.example.tccbcc.adapter.SimpleAdapterClass;
import com.example.tccbcc.models.Treinamento;
import com.example.tccbcc.models.UsuarioBeneficiador;
import com.example.tccbcc.models.Vacina;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsuarioBeneficiadorProfileActivity extends AppCompatActivity {
    private UsuarioBeneficiador usuario;

    private TextView txtProfileNome;
    private TextView txtProfileDataNascimento;
    private TextView txtProfileNomePet;
    private TextView txtProfileDataNascimentoPet;
    private TextView txtProfileRacaPet;
    private TextView txtProfileSexoPet;
    private TextView txtProfileDescricaoPet;
    private ListView ProfileListViewVacinacoes;
    private ListView ProfileListViewTreinamentos;
    private CircleImageView imgviewProfileFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_beneficiador_profile);

        String user = getIntent().getStringExtra("user");
        Gson gson = new Gson();
        usuario = gson.fromJson(user, UsuarioBeneficiador.class);

        txtProfileNome = findViewById(R.id.txtProfileNome);
        txtProfileDataNascimento = findViewById(R.id.txtProfileDataNascimento);
        txtProfileNomePet = findViewById(R.id.txtProfileNomePet);
        txtProfileDataNascimentoPet = findViewById(R.id.txtProfileDataNascimentoPet);
        txtProfileRacaPet = findViewById(R.id.txtProfileRacaPet);
        txtProfileSexoPet = findViewById(R.id.txtProfileSexoPet);
        txtProfileDescricaoPet = findViewById(R.id.txtProfileDescricaoPet);
        ProfileListViewVacinacoes = findViewById(R.id.ProfileListViewVacinacoes);
        ProfileListViewTreinamentos = findViewById(R.id.ProfileListViewTreinamentos);
        imgviewProfileFoto = findViewById(R.id.imgviewProfileFoto);

        if (usuario != null){
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

            txtProfileNome.setText("Nome: "+usuario.getUsername());
            txtProfileDataNascimento.setText("Data de Nascimento: "+ df.format(usuario.getDataNascimento()));

            String fotoUrl = (
                    usuario.getFotos() != null && usuario.getFotos().get(0) != null
            ) ? usuario.getFotos().get(0).getUri() : "";

            if (fotoUrl != null && !fotoUrl.equals("")) {
                Glide.with(this).load(fotoUrl)
                        .placeholder(R.drawable.avatar)
                        .into(imgviewProfileFoto);
            }

            if (usuario.getPet() != null){
                txtProfileNomePet.setText("Nome: "+usuario.getPet().getNome());
                txtProfileDataNascimentoPet.setText("Data de Nascimento: "+df.format(usuario.getPet().getData()));
                txtProfileRacaPet.setText("Raça: "+usuario.getPet().getRaca());
                txtProfileSexoPet.setText("Sexo: "+usuario.getPet().getSexo());
                txtProfileDescricaoPet.setText("Descrição: "+usuario.getPet().getDescricao());

                popularListViewVacinacoes();
                popularListViewTreinamentos();
            }
        }

    }

    public void popularListViewVacinacoes()
    {
        if (usuario == null ||
                usuario.getPet() == null ||
                usuario.getPet().getVacinacoes() == null ||
                usuario.getPet().getVacinacoes().size() == 0){
            return;
        }

        //Criando a lista de adapters
        ArrayList<SimpleAdapterClass> listaAdaptersVacinacoes = new ArrayList<>();

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        //Populando a lista de adapters
        for(Vacina vacina : usuario.getPet().getVacinacoes())
        {
            listaAdaptersVacinacoes.add(
                    new SimpleAdapterClass(
                            vacina.getNome(),
                            df.format(vacina.getData())
                    )
            );
        }

        SimpleAdapter adapter = new SimpleAdapter(
                this, R.layout.adapter_view_layout3, listaAdaptersVacinacoes);
        ProfileListViewVacinacoes.setAdapter(adapter);
    }

    public void popularListViewTreinamentos()
    {
        if (usuario == null ||
                usuario.getPet() == null ||
                usuario.getPet().getTreinamentos() == null ||
                usuario.getPet().getTreinamentos().size() == 0){
            return;
        }

        //Criando a lista de adapters
        ArrayList<SimpleAdapterClass> listaAdaptersTreinamentos = new ArrayList<>();

        //Populando a lista de adapters
        for(Treinamento treinamento : usuario.getPet().getTreinamentos())
        {
            listaAdaptersTreinamentos.add(
                    new SimpleAdapterClass(
                            treinamento.getNome(),
                            treinamento.getDescricao()
                    )
            );
        }

        SimpleAdapter adapter = new SimpleAdapter(
                this, R.layout.adapter_view_layout3, listaAdaptersTreinamentos);
        ProfileListViewTreinamentos.setAdapter(adapter);
    }

    public void Conversar(View view){
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("user",usuario);
        startActivity(intent);
    }

    public void Denunciar(View view){
        Intent intent = new Intent(this, DenunciaActivity.class);
        intent.putExtra("uid", FirebaseAuth.getInstance().getUid());
        intent.putExtra("did", usuario.getUuid());
        startActivity(intent);
    }
}