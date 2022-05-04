package com.example.tccbcc.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tccbcc.R;
import com.example.tccbcc.models.UsuarioBeneficiador;
import com.example.tccbcc.views.BeneficiadorActivity;
import com.google.gson.Gson;

import java.util.ArrayList;

public class CustomListAdapterTreinamentos extends CustomListAdapter {
    public CustomListAdapterTreinamentos(@NonNull Context context, int resource, @NonNull ArrayList<CustomListAdapterClass> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final View TempConvertView = super.getView(position, convertView, parent);

        TempConvertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //Não vai ter edição por enquanto
            }
        });


        Button btnExcluir = (Button) TempConvertView.findViewById(R.id.btnExcluir);
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                UsuarioBeneficiador user = (UsuarioBeneficiador)getItem(position).getUsuario();

                if (user == null ||
                        user.getPet() == null ||
                        user.getPet().getTreinamentos() == null ||
                        user.getPet().getTreinamentos().size() == 0){
                    return;
                }

                user.getPet().removeTreinamento(user.getPet().getTreinamentos().get(position));

                Gson gson = new Gson();
                Intent intent = new Intent(mContext, BeneficiadorActivity.class);
                intent.putExtra("user", gson.toJson(user));
                mContext.startActivity(intent);
            }
        });

        return TempConvertView;
    }
}
