package com.example.tccbcc.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tccbcc.R;
import com.example.tccbcc.adapter.CustomListAdapterClass;
import com.example.tccbcc.adapter.CustomListAdapterTreinamentos;
import com.example.tccbcc.models.Treinamento;
import com.example.tccbcc.models.UsuarioBeneficiador;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ListagemTreinamentosActivity extends AppCompatActivity {
    private UsuarioBeneficiador usuario;

    private ListView listViewTreinamentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_treinamentos);

        String user = getIntent().getStringExtra("user");
        Gson gson = new Gson();
        usuario =  gson.fromJson(user, UsuarioBeneficiador.class);

        listViewTreinamentos = findViewById(R.id.listViewTreinamentos);
        popularListView();
    }

    public void popularListView()
    {
        if (usuario == null ||
                usuario.getPet() == null ||
                usuario.getPet().getTreinamentos() == null ||
                usuario.getPet().getTreinamentos().size() == 0){
            return;
        }

        //Criando a lista de adapters
        ArrayList<CustomListAdapterClass> listaAdaptersTreinamentos = new ArrayList<>();

        //Populando a lista de adapters
        for(Treinamento treinamento : usuario.getPet().getTreinamentos())
        {
            listaAdaptersTreinamentos.add(
                    new CustomListAdapterClass(
                            treinamento.getNome(),
                            treinamento.getDescricao(),
                            usuario));
        }

        CustomListAdapterTreinamentos adapter = new CustomListAdapterTreinamentos(
                this, R.layout.adapter_view_layout2, listaAdaptersTreinamentos);
        listViewTreinamentos.setAdapter(adapter);
    }

    public void AdicionarTreinamento(View view){
        Gson gson = new Gson();
        Intent intent = new Intent(ListagemTreinamentosActivity.this,
                CadastroTreinamentoActivity.class);
        intent.putExtra("user", gson.toJson(usuario));
        startActivity(intent);
    }

    public void Cadastrar(View view){
        Gson gson = new Gson();
        Intent intent = new Intent(this,
                CadastroImagemBeneficiador.class);
        intent.putExtra("user", gson.toJson(usuario));
        startActivity(intent);
    }


}