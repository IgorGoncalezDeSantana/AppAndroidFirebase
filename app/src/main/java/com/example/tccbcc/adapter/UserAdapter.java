package com.example.tccbcc.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tccbcc.R;
import com.example.tccbcc.models.Usuario;
import com.example.tccbcc.databinding.ItemProfileBinding;
import com.example.tccbcc.models.UsuarioBeneficiador;
import com.example.tccbcc.views.ChatActivity;
import com.example.tccbcc.views.ContatosActivity;
import com.example.tccbcc.views.UsuarioBeneficiadorProfileActivity;
import com.google.gson.Gson;

import java.util.ArrayList;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private Context context;
    private ArrayList<UsuarioBeneficiador> userList;

    public UserAdapter(Context context, ArrayList<UsuarioBeneficiador> userList) {
        this.context = context;
        this.userList = userList;
    }

    protected class UserViewHolder extends RecyclerView.ViewHolder {
        private ItemProfileBinding binding;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemProfileBinding.bind(itemView);
        }

    }

    @NonNull
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_profile,
                parent, false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, int position) {
        UsuarioBeneficiador user = userList.get(position);
        holder.binding.username.setText(user.getUsername());

        holder.binding.txtDescricaoProfile.setText(user.getPet().getDescricao());

        String fotoUrl = (
                user.getFotos() != null && user.getFotos().get(0) != null
        ) ? user.getFotos().get(0).getUri() : "";

        if (fotoUrl != null && !fotoUrl.equals("")) {
            Glide.with(context).load(fotoUrl)
                    .placeholder(R.drawable.avatar)
                    .into(holder.binding.profile);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UsuarioBeneficiadorProfileActivity.class);
                Gson gson = new Gson();
                intent.putExtra("user",  gson.toJson(user));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
