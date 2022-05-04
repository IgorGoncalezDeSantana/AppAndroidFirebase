package com.example.tccbcc.adapter;

import com.example.tccbcc.models.Usuario;

public class CustomListAdapterClass {
    private String nome;
    private String Descricao;
    private Usuario usuario;

    public CustomListAdapterClass(String nome, String descricao, Usuario usuario) {
        this.nome = nome;
        Descricao = descricao;
        this.usuario = usuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
