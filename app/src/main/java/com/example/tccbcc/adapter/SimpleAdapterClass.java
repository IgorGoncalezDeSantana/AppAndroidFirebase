package com.example.tccbcc.adapter;

public class SimpleAdapterClass {
    private String nome;
    private String Descricao;

    public SimpleAdapterClass(String nome, String descricao) {
        this.nome = nome;
        Descricao = descricao;
    }

    public SimpleAdapterClass() {
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
}
