package com.example.tccbcc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tccbcc.R;

import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<CustomListAdapterClass> {
    private static final String TAG = "CustomListAdapter";

    protected Context mContext;
    int mResource;

    public CustomListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<CustomListAdapterClass> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String nome = getItem(position).getNome();
        String descricao = getItem(position).getDescricao();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView txtNome = (TextView) convertView.findViewById(R.id.txtNome);
        TextView txtDescricao = (TextView) convertView.findViewById(R.id.txtNome2);

        txtNome.setText(nome);
        txtDescricao.setText(descricao);

        return convertView;
    }
}
