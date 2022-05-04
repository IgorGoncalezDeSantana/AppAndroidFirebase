package com.example.tccbcc.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tccbcc.R;
import com.example.tccbcc.adapter.CustomListAdapterClass;
import com.example.tccbcc.adapter.CustomListAdapterVacinacoes;
import com.example.tccbcc.models.UsuarioBeneficiador;
import com.example.tccbcc.models.Vacina;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ListagemVacinacaoActivity extends AppCompatActivity {
    private UsuarioBeneficiador usuario;

    private ListView listViewVacinacoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_vacinacao);

        String user = getIntent().getStringExtra("user");
        Gson gson = new Gson();
        usuario =  gson.fromJson(user, UsuarioBeneficiador.class);

        listViewVacinacoes = findViewById(R.id.listViewVacinacoes);
        popularListView();
    }

    public void popularListView()
    {
        if (usuario == null ||
                usuario.getPet() == null ||
                usuario.getPet().getVacinacoes() == null ||
                usuario.getPet().getVacinacoes().size() == 0){
            return;
        }

        //Criando a lista de adapters
        ArrayList<CustomListAdapterClass> listaAdaptersVacinacoes = new ArrayList<>();


        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        //Populando a lista de adapters
        for(Vacina vacina : usuario.getPet().getVacinacoes())
        {
            listaAdaptersVacinacoes.add(
                    new CustomListAdapterClass(
                            vacina.getNome(),
                            df.format(vacina.getData()),
                            usuario));
        }

        CustomListAdapterVacinacoes adapter = new CustomListAdapterVacinacoes(
                this, R.layout.adapter_view_layout2, listaAdaptersVacinacoes);
        listViewVacinacoes.setAdapter(adapter);
    }

    public void AdicionarVacina(View view){
        Gson gson = new Gson();
        Intent intent = new Intent(ListagemVacinacaoActivity.this,
                CadastroVacinaActivity.class);
        intent.putExtra("user", gson.toJson(usuario));
        startActivity(intent);
    }

    public void Cadastrar(View view){
        if (usuario == null ||
                usuario.getPet() == null ||
                usuario.getPet().getVacinacoes() == null ||
                usuario.getPet().getVacinacoes().size() == 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("O cadastro das vacinações do Pet é obrigatório!")
                    .create().show();
            return;
        }

        Gson gson = new Gson();
        Intent intent = new Intent(ListagemVacinacaoActivity.this,
                ListagemTreinamentosActivity.class);
        intent.putExtra("user", gson.toJson(usuario));
        startActivity(intent);

    }
}